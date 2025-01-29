package com.project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
//used to inform spring mvc that this class have all exception handler that are being used in our project
public class MyExceptionHandler {
	/*
	 * can be used for all of the exception
	 */

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String exceptionHandler(Model model) {
		Exception exception = new Exception();
		String ex = exception.getMessage();
		model.addAttribute("msg", ex);
		return "error";
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = NullPointerException.class)
	public String exceptionHandlerNull(Model m) {
		m.addAttribute("msg", "nullpointerException");
		return "error";
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = NumberFormatException.class)
	public String exceptionHandlerNumber(Model mio) {
		mio.addAttribute("msg", "NumberFormatException");
		return "error";
	}

}
