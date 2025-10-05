package es.dsw.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.dsw.model.Cine;

@Controller
public class MainController {
	
	@GetMapping(value= {"/", "/index"})
	public String idx(Model cine) {
		// Determinar el día
		DayOfWeek dia = LocalDate.now().getDayOfWeek();
		
		// Obtener el slogan
		String mensaje = Cine.getSloganDelDia(dia);
		String precio = Cine.getPrecio(dia);
		
		// Pasarlo a la vista
		cine.addAttribute("mensaje", mensaje);
		cine.addAttribute("precio", precio);
		
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
