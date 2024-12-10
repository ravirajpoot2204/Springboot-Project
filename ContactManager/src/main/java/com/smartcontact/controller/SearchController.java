package com.smartcontact.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontact.dao.ContactRepo;
import com.smartcontact.dao.UserRepo;
import com.smartcontact.entities.Contact;
import com.smartcontact.entities.User;

@RestController
public class SearchController {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ContactRepo contactRepo;

	@GetMapping("/search/{query}")
	public ResponseEntity<?> searchHandler(@RequestParam(value="error",required = false) String error,@PathVariable("query") String query, Principal principal) {

	    
	    if (error != null) {
		    throw new IllegalArgumentException("An Error occurred");
		}
		System.out.println(query);
		
		 
		User user = this.userRepo.getUserByUserName(principal.getName());
		
		List<Contact> contacts = this.contactRepo.findByNameContainingAndUser(query, user);
		
		return ResponseEntity.ok(contacts);
	}
	 @ExceptionHandler(IllegalArgumentException.class)
	    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
	        model.addAttribute("errorMessage", ex.getMessage());
	        return "error_page";
	    }
}
