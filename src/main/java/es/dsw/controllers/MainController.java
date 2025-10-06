package es.dsw.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.dsw.model.Home;

@Controller
public class MainController {
	
	@GetMapping(value= {"/", "/index"})
	public String idx(Model model) {
		// Determinar el día
		LocalDateTime ahora = LocalDateTime.now();
		DayOfWeek dia = ahora.getDayOfWeek();
		
		// Obtener datos y pasarlos a la vista
		model.addAttribute("mensaje", Home.getSloganDelDia(dia));
		model.addAttribute("precio", Home.getPrecio(dia));
		model.addAttribute("hora", Home.getHora(ahora));
		model.addAttribute("fecha", Home.getFecha(ahora));
		
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
