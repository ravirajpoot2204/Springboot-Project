package search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@RequestMapping("/welcomeSuccess")

	public String welcomeS(@RequestParam("user") String name, Model m) {

		m.addAttribute("name", name);
		return "welcomeSuccess";
	}

}
