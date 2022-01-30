package com.reljicd.controller;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reljicd.model.Pg;
import com.reljicd.model.Product;
import com.reljicd.service.PayingGuestService;
import com.reljicd.util.Pager;


@Controller
public class PGController {
	private final PayingGuestService userService;
	   private static final int INITIAL_PAGE = 0;
	@Autowired
	public PGController(PayingGuestService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/application", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		Pg pg = new Pg();
		modelAndView.addObject("pg", pg);
		modelAndView.setViewName("/application");
		return modelAndView;
	}

	@RequestMapping(value = "/application", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Pg user, BindingResult bindingResult) {

		if (userService.findByEmail(user.getEmail()).isPresent()) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (userService.findByUsername(user.getUsername()).isPresent()) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the username provided");
		}

		if (user.getPgImage()==null || user.getPgImage().isEmpty() || user.getPgImage().getSize()<=0)  {
			bindingResult.rejectValue("pgImage", "error.user",
					"please provide image");
		}
		ModelAndView modelAndView = new ModelAndView();
MultipartFile image=user.getPgImage();
try {
	user.setImage(image.getBytes());
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/application");
		} else {
			// Registration successful, save user
			// Set user role to USER and set it as active
			userService.saveUser(user);

			modelAndView.addObject("successMessage", "User "+user.getUsername()+" has been registered successfully");
			modelAndView.addObject("user", new Pg());
			modelAndView.setViewName("/home");
		}
		 return modelAndView;
	}
	
    @GetMapping("/home")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Pg> products = userService.findAllPgsPageable(new PageRequest(evalPage, 5));
        Pager pager = new Pager(products);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        modelAndView.addObject("imgUtil", new ImageUtil());
        return modelAndView;
    }
    
    @GetMapping("/application/removePg/{pgId}")
    public String  removeProductFromCart(@PathVariable("pgId") Long pgId) {
    	userService.findById(pgId).ifPresent(userService::removePg);
        return "redirect:/home";
    }
    
    @GetMapping("/application1/{pgId}")
    public ModelAndView showBookdById(@PathVariable("pgId") Long pgId) {
    	System.out.println("Get call");
        Pg book = userService.findById(pgId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pg Id:" + pgId));
    
       
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pg", book);

        modelAndView.setViewName("/editpg");
     	System.out.println("Get call end");
        return modelAndView;
    }
    
    @PostMapping("/application/update/{id}")
    public ModelAndView updateBook(@PathVariable Long id, @Valid @ModelAttribute Pg book, BindingResult result, Model model) {
    
        ModelAndView modelAndView = new ModelAndView();
    	if (book.getPgImage()==null || book.getPgImage().isEmpty() || book.getPgImage().getSize()<=0) {
    		result.rejectValue("pgImage", "error.user",
					"please provide image");
		}
        MultipartFile image=book.getPgImage();
        try {
        	book.setImage(image.getBytes());
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        if (result.hasErrors()) {
			modelAndView.setViewName("/application/update/"+id);
		}
        else
        {
        userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pg Id:" + id));
        userService.saveUser(book);
       
    	modelAndView.addObject("successMessage", "User has been registered successfully");
		modelAndView.addObject("user", new Pg());
		
        int evalPage = 20;

        Page<Pg> products = userService.findAllPgsPageable(new PageRequest(evalPage, 5));
        Pager pager = new Pager(products);
     
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/application/update/"+id);
        }
        return modelAndView;
    }
	@GetMapping(path = "/application/{pgId}")
	public String getEditForm(Model model,@PathVariable("pgId") Long pgId) {
		System.out.println("Get call");
        Pg book = userService.findById(pgId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pg Id:" + pgId));
    
   
		model.addAttribute("pg", book);
		return "/editpg";
	}

	// request mapping method to submit edited details
	@PostMapping(path = "/edit")
	public ModelAndView submitForm(@ModelAttribute Pg user, Model model,BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();	
	   	if (user.getPgImage()==null || user.getPgImage().isEmpty() || user.getPgImage().getSize()<=0) {
	   		bindingResult.rejectValue("pgImage", "error.user",
					"please provide image");
		}
	   	
	   	MultipartFile image=user.getPgImage();
	   	try {
	   		user.setImage(image.getBytes());
	   	} catch (IOException e) {
	   		// TODO Auto-generated catch block
	   		e.printStackTrace();
	   	}
		model.addAttribute("pg", user);
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("redirect:/application/"+user.getId());
			return modelAndView;
		} else {
			
		  userService.findById(user.getId())
          .orElseThrow(() -> new IllegalArgumentException("Invalid pg Id:" + user.getId()));
  userService.saveUser(user);
  modelAndView.addObject("successMessage", "User "+user.getUsername()+" has been update successfully");
  modelAndView.setViewName("/home");
		return modelAndView;
		}
	}
	

}
