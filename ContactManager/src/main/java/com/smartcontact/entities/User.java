package com.smartcontact.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;
    @NotBlank(message = "Name can not be null")
    @Size(min = 3, max = 20, message = "number of characters should be in range 2-20")
    private String name;

    @Column(unique = true)
    private String email;
    private String role;
    private String password;
    private String imageurl;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @Column(length = 50000)
    private String description;
    private boolean enable;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Contact> contact;

    public int getUid() {
	return uid;
    }

    public void setUid(int uid) {
	this.uid = uid;
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

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
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

    public boolean isEnable() {
	return enable;
    }

    public void setEnable(boolean enable) {
	this.enable = enable;
    }

    public List<Contact> getContact() {
	return contact;
    }

    public void setContact(List<Contact> contact) {
	this.contact = contact;
    }

    @Override
    public String toString() {
	return "User : [uid=" + uid + ", name=" + name + ", email=" + email + ", role=" + role + ", password="
		+ password + ", imageurl=" + imageurl + ", phone=" + phone + ", description=" + description
		+ ", enable=" + enable + ", contact=" + contact + "]";
    }

}
