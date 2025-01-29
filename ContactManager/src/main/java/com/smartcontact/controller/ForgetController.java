package com.smartcontact.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontact.dao.UserRepo;
import com.smartcontact.entities.User;
import com.smartcontact.helper.Message;
import com.smartcontact.service.EmailService;
import com.smartcontact.service.OTPGenerator;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgetController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;
    @Autowired
    private OTPGenerator generator;

    @GetMapping("/forget")
    public String forgetHandler(@RequestParam(value = "error", required = false) String error, HttpSession session,
	    Model model) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	model.addAttribute("title", "Forget Password?");
	session.setAttribute("msg", "");
	session.setAttribute("type", "");
	return "forget-form";
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("f-email") String fEmail,
	    @RequestParam(value = "error", required = false) String error, Model model, HttpSession session) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	System.out.println(fEmail);

	int otp = this.generator.otpGenerator();

	System.out.println("otp : " + otp);
//	write code to send otp to email..

	String subject = "OTP From JustChecking!!";
	String message = "<div style='align-item:center; border: 1px solid #e2e2e2 ; padding:20px;'>" + "<h1> "
		+ "OTP : " + "<b>" + otp + "/n" + "</h1>" + "</div>";
	String to = fEmail;

	model.addAttribute("title", "Forgrt Password");
	boolean existsByEmail = this.userRepo.existsByEmail(fEmail);
	if (existsByEmail) {
	    boolean flag = this.emailService.sendEmail(message, subject, to);

	    if (flag) {
		session.setAttribute("emailOTP", otp);
		session.setAttribute("email", fEmail);

		session.setAttribute("msg", "OTP have been sent to your email..");
		session.setAttribute("type", "alert alert-success");
		System.out.println("User does exist.. and email sent");
		return "verify-otp";
	    } else {
		session.setAttribute("msg", "Something went wrong, Try again Later!!");
		session.setAttribute("type", "alert alert-warning");
		System.out.println("User does exist.. but email can not be sent..");

		return "forget-form";
	    }

	} else {
	    session.setAttribute("msg", "User does not exist.. Check your email id..");
	    session.setAttribute("type", "alert alert-danger");
	    System.out.println("User does not exist.. and email can not be sent");

	    return "forget-form";
	}

    }

    @PostMapping("/otp-verify")
    public String verifyOtp(@RequestParam("otp") Integer otp,
	    @RequestParam(value = "error", required = false) String error, HttpSession session) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}
	Integer emailOTP = (Integer) session.getAttribute("emailOTP");
	String userEmail = (String) session.getAttribute("email");

	if (emailOTP == otp) {

	    session.setAttribute("msg", "");
	    session.setAttribute("type", "");
	    session.setAttribute("email", userEmail);

	    // Password Change form
	    return "changePasswordByEmail";
	} else {

	    session.setAttribute("message", new Message("OTP is wrong!!", "alert alert-danger"));

	    return "verify-otp";

	}

    }

    @PostMapping("/Update-Password-By-Email")
    public String updatePasswordByEmail(@RequestParam("newPassword") String newPassword,
	    @RequestParam(value = "error", required = false) String error,
	    @RequestParam("confirmNewPassword") String confirmNewPassword, HttpSession session, Model model,
	    Principal principal) {

	if (error != null) {
	    throw new IllegalArgumentException("An Error occurred");
	}

	String email = (String) session.getAttribute("email");
	User user = this.userRepo.getUserByUserName(email);

	if (newPassword == confirmNewPassword) {

	    user.setPassword(bCryptPasswordEncoder.encode(newPassword));

	    session.setAttribute("msg", "Your password have been changed successfully..");
	    session.setAttribute("type", "alert alert-success");

	    this.userRepo.save(user);
	    // Password Change form
	    return "redirect:/user/profile/" + user.getUid();
	} else {

	    session.setAttribute("msg", "Your password does not match!!");
	    session.setAttribute("type", "alert alert-danger");

	    model.addAttribute("title", "Password Changed");
	    return "redirect:/user/Update-Password-By-Email";

	}

    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error_page";
    }

}
