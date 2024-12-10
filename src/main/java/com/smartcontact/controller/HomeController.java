package com.smartcontact.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smartcontact.dao.UserRepo;
import com.smartcontact.entities.User;
import com.smartcontact.service.EmailService;
import com.smartcontact.service.OTPGenerator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

public class HomeController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private OTPGenerator otpGenerator;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepo userRepo;

    // Web Home
   

    @GetMapping("/")
    public String home(@RequestParam(value="error",required = false) String error,Model model) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	model.addAttribute("title", "Home : SmartContactManger");
	model.addAttribute("msg", "working......................***********");

	return "home";
    }

    // User SignUp Page

    @GetMapping("/signup")
    public String signup(@RequestParam(value="error",required = false) String error,Model model,HttpSession session) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	model.addAttribute("title", "Register : SmartContactManger");
	model.addAttribute("user", new User());
	   session.setAttribute("msg", "");
	    session.setAttribute("type", "");

	return "signup";
    }

    // Email Verification Before sign up
    @PostMapping("/email-verification")
    public String emailVerificationBeforeSignUp(@Valid @ModelAttribute("user") User user,@RequestParam(value="error",required = false) String error,BindingResult bindingResult, HttpSession session,
	     @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
	    Model model) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	session.setAttribute("user", user);

	if (!agreement) {
	    model.addAttribute("user", user);
	    session.setAttribute("message", "Please accept terms and Condition!! alert-danger");
	    session.setAttribute("type", "alert-danger");

	    return "signup";
	}
	if (bindingResult.hasErrors()) {
	    model.addAttribute("user", user);
	    System.out.println("Errors : " + bindingResult.toString());
	    return "signup";
	}
	String email = user.getEmail();
	boolean existsByEmail = this.userRepo.existsByEmail(email);
	/* Checking if user's email already exist or not */
	if (existsByEmail) {

	    session.setAttribute("msg", "User already Exist!! ");
	    session.setAttribute("type", "alert alert-danger");
	    return "signup";
	} else {


	    String subject = "OTP From Adding User in webApp";
	    int otp = this.otpGenerator.otpGenerator();

	    String message = "<div style='align-item:center; border: 1px solid #e2e2e2 ; padding:20px;'>" + "<h1> "
		    + "OTP : " + "<b>" + otp + " </h1>" + "</div>";

	    session.setAttribute("emailOTP", otp);
	    session.setAttribute("email", email);

	    this.emailService.sendEmail(message, subject, email);

	    session.setAttribute("msg", "OTP have been Sent to your email id..");
	    session.setAttribute("type", "alert alert-success");
	    return "EmailVerification";
	}
    }

    /* email OTP Verification */
    @PostMapping("/email-verify-process")
    public String emailVerifyProcess(@RequestParam("otp") int otp,@RequestParam(value="error",required = false) String error, HttpSession session, Model model,
	    Principal principal) {
	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}

	User user = (User) session.getAttribute("user");

	Integer emailOTP = (Integer) session.getAttribute("emailOTP");
	
	try {

	    /* Verifying otp */
	    if (emailOTP == otp) {

		/* AddingUser to db */

		model.addAttribute("title", "Home : SmartContactManger");

		user.setEnable(true);
		user.setRole("USER"); // Use uppercase to match the security configuration
		user.setImageurl("Default.jpg");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		User savedUser = this.userRepo.save(user);

		model.addAttribute("savedUser", savedUser);

		session.setAttribute("msg", "Successfully Signed Up!!");
		session.setAttribute("type", "alert alert-success");

		model.addAttribute("user", new User());
		return "signup";

	    } else {

		session.setAttribute("msg", "Wrong OTP!!");
		session.setAttribute("type", "alert alert-danger");

		return "EmailVerification";

	    }
	} catch (Exception e) {

	    model.addAttribute("user", user);
	    session.setAttribute("msg", "Something Went Wrong!! ");
	    session.setAttribute("type", "alert alert-danger");

	    e.printStackTrace();
	    return "signup";
	}
    }

    @GetMapping("/about")
    public String about(@RequestParam(value="error",required = false) String error,Model model) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	model.addAttribute("title", "About : SmartContactManger");

	return "about";
    }

    @GetMapping("/login")
    public String Login(@RequestParam(value="error",required = false) String error,Model model) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	model.addAttribute("title", "Login Page : SmartContactManger");
	model.addAttribute("user", new User());

	return "login";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error_page";
    }

  
    
}
