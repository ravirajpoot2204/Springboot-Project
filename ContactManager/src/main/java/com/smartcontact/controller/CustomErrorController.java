package com.smartcontact.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String errorHandler(@RequestParam(value="error",required = false) String error,HttpServletRequest httpServletRequest, Model model) {
	Integer statusCode = (Integer) httpServletRequest.getAttribute("jakarta.servlet.error.status_code");

	String errorMessage = (String) httpServletRequest.getAttribute("jakarta.servlet.error.message");

	model.addAttribute("statusCode", statusCode);
	model.addAttribute("errorMessage", errorMessage);

	return "error_page";
    }

 

   

}
