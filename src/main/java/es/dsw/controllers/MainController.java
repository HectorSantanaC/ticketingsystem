package es.dsw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value= {"/", "/index"})
	public String idx() {
		return "index";
	}
	
	@GetMapping(value= {"/step1"})
	public String step1() {
		return "views/step1";
	}
	
	@GetMapping(value= {"/step2"})
	public String step2() {
		return "views/step2";
	}
	
	@GetMapping(value= {"/step3"})
	public String step3() {
		return "views/step3";
	}
	
	@GetMapping(value= {"/step4"})
	public String step4() {
		return "views/step4";
	}
	
	@GetMapping(value= {"/end"})
	public String end() {
		return "views/end";
	}
}
