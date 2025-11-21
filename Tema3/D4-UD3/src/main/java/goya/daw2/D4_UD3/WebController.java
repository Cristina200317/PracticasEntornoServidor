package goya.daw2.D4_UD3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	// Mapping múltiple
	@RequestMapping({ "/", "/home" })
	public String home() {
		return "home";
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
