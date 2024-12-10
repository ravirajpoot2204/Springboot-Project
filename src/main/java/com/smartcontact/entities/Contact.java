package com.smartcontact.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CONTACT")
public class Contact {

    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email must follow the format 'example@domain.com'")
    @NotBlank(message = "Email is required")
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(max = 50, message = "Nickname cannot exceed 50 characters")
    private String nickName;

    @NotBlank(message = "Work field is required")
    private String work;

    private String imageurl;

    @Column(length = 50000)
    @Size(max = 50000, message = "Description cannot exceed 50000 characters")
    private String description;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @JsonIgnore
    @ManyToOne
    private User user;

    public int getCid() {
	return cid;
    }

    public void setCid(int cid) {
	this.cid = cid;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getNickName() {
	return nickName;
    }

    public void setNickName(String nickName) {
	this.nickName = nickName;
    }

    public String getWork() {
	return work;
    }

    public void setWork(String work) {
	this.work = work;
    }

    public String getImageurl() {
	return imageurl;
    }

    public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    @Override
    public String toString() {
	return "Contact [email=" + email + ", cid=" + cid + ", name=" + name + ", nickName=" + nickName + ", work="
		+ work + ", imageurl=" + imageurl + ", description=" + description + ", phone=" + phone + ", user="
		+ user.getUid() + "]";
    }

}
