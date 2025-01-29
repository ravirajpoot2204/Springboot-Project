package com.project.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.project.entity.ProjectEntity;
import com.project.services.ProjectServices;



@Controller
public class MainController {
	@Autowired
	ProjectServices services;

	
	@RequestMapping("/home")
	public String home(Model m) {

		List<ProjectEntity> allProducts = services.getAllProducts();
		m.addAttribute("allProducts", allProducts);
		m.addAttribute("title", "Home");
		System.out.println("HOme");
		return "index";
	}

	@RequestMapping(value = "/form")
	public String form(Model m1) {
		m1.addAttribute("title", "Fill Form Page");
		m1.addAttribute("form_heading", "Fill Product Details");
		return "fill_form";
	}

	@RequestMapping(value = "/form-handle", method = RequestMethod.POST)
	public RedirectView formHandle(@ModelAttribute ProjectEntity entity, javax.servlet.http.HttpServletRequest request) {
		RedirectView redirectView = new RedirectView();
		services.addProduct(entity);
		System.out.println(request.getMethod());
		redirectView.setUrl(request.getContextPath() + "/home");
		return redirectView;
	}

	/* Delete Handler */
	@Transactional
	@RequestMapping(path = "/delete/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
	public RedirectView delete(@PathVariable("productId") int productId, javax.servlet.http.HttpServletRequest request) {
	    RedirectView redirectView = new RedirectView();
	    services.deleteProduct(productId);

	    System.out.println();
	    redirectView.setUrl(request.getContextPath()+"/home");
	    return redirectView;
	}

	@Transactional

	@RequestMapping(path = "/update/{productId}", method = RequestMethod.GET)
	public String update(@PathVariable("productId") int productId, Model m1) {
	    ProjectEntity product = this.services.getSingleProduct(productId);
	    product.getDescription();
	    product.getName();
	    m1.addAttribute("product", product);
	    m1.addAttribute("form_heading", "Update Details");
	    m1.addAttribute("title", "Update Product Details");
	    return "update_form";
	}
}
