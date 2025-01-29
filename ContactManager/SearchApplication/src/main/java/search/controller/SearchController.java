package search.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import search.entity.Address;
import search.entity.Student;

@Controller
public class SearchController {

	/*
	 * Practical of @pathvariable annotation is use to bind the method parameter to
	 * URI template variable
	 */

	@RequestMapping("/user/{userID}/{userName}")
	public String pathvariable(@PathVariable("userID") int userID, @PathVariable("userName") String userName) {
		System.out.println(userID);
		System.out.println(userName);
		Integer.parseInt(userName);
		return "home";
	}

	@RequestMapping("/home")
	public String home() {
		System.out.println("homeview");
		/* Error handling spring mvc */
		String str = null;
		System.out.println(str.length());

		return "home";
	}

	@RequestMapping("/search")
	public RedirectView search(@RequestParam("query-box") String query) {
		RedirectView redirectView = new RedirectView();
		String url = "https://www.google.com/search?q=" + query;

		redirectView.setUrl(url);

		return redirectView;
	}

	@RequestMapping("/complex")
	public String complex() {
		return "complexform";
	}

	@RequestMapping(path = "/handleform", method = RequestMethod.POST)
	public String formhandle(@ModelAttribute("student") Student student, BindingResult result) {
		if (result.hasErrors()) {

			return "complexform";
		}
		return "success";
	}

//	/*
//	 * can be used for all of the exception
//	 */
//	
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(value = Exception.class)
//	public String exceptionHandler() {
//		return "null_page";
//	}
//
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(value = NullPointerException.class)
//	public String exceptionHandlerNull(Model m) {
//		m.addAttribute("msg", "nullpointerException");
//		return "null_page";
//	}
//
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(value = NumberFormatException.class)
//	public String exceptionHandlerNumber(Model mio) {
//		mio.addAttribute("msg", "NumberFormatException");
//		return "null_page";
//	}
}
