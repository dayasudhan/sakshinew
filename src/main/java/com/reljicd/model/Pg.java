package com.reljicd.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Table(name = "PG")
public class Pg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;



    @Column(name = "occupation")
    private String occupation;
    
    @Column(name = "address")
    private String address;


	@Column(name = "phoneNo", nullable = true)
    @NotEmpty(message = "*Please provide an Phonnumber")
    @Length(min = 10,max=10, message = "*Phonnumber  must have  10  numebers")
    private String phoneNo;

    @Column(name = "parentsNo", nullable = true)
    private String parentsNo;
    
    @Column(name = "adharNo", nullable = true)
    @NotEmpty(message = "*Please provide an email")
    private String adharNo;
    
    @Column(name = "roomNo", nullable = true)
    private Integer roomNo;


	@Column(name = "email", unique = true, nullable = false)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;



    @Column(name = "username", nullable = false, unique = true)
    @Length(min = 5, message = "*Your username must have at least 5 characters")
    @NotEmpty(message = "*Please provide your name")
    
    private String username;
    
  
     @Lob
    private byte[] image;
    
    @Transient
    private MultipartFile pgImage;
    
    
    
    public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public MultipartFile getPgImage() {
		return pgImage;
	}

	public void setPgImage(MultipartFile pgImage) {
		this.pgImage = pgImage;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}






    
    public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getParentsNo() {
		return parentsNo;
	}

	public void setParentsNo(String parentsNo) {
		this.parentsNo = parentsNo;
	}

	public String getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}

	public Integer getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(Integer roomNo) {
		this.roomNo = roomNo;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pg product = (Pg) o;

        return id.equals(product.id);
    }



    
}
