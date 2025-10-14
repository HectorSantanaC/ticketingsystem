package es.dsw.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.dsw.model.Step1Model;
import es.dsw.model.IndexModel;

@Controller
public class MainController {
	
	@GetMapping(value= {"/", "/index"})
	public String idx(Model model) {
		// Determinar el día
		LocalDateTime ahora = LocalDateTime.now();
		DayOfWeek dia = ahora.getDayOfWeek();
		
		// Obtener datos y pasarlos a la vista
		model.addAttribute("mensaje", IndexModel.getSloganDelDia(dia));
		model.addAttribute("precio", IndexModel.getPrecio(dia));
		model.addAttribute("hora", IndexModel.getHora(ahora));
		model.addAttribute("fecha", IndexModel.getFecha(ahora));
		
		return "index";
	}
	
	@GetMapping(value= {"/step1"})
	public String step1(Model model) {
		DayOfWeek dia = LocalDate.now().getDayOfWeek();
		
		// Obtener películas del día
		List<String> peliculas = Step1Model.getPeliculasDelDia(dia);
		double precioPeliculas = Step1Model.getPrecioPeliculas(dia);
		
		model.addAttribute("peliculas", peliculas);
		model.addAttribute("precioPeliculas", precioPeliculas);
		
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
