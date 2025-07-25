package br.edu.ifpb.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWebController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message", 
				"Minha 1º aplicação web, que emoção!");
		return "hello";
	}
}