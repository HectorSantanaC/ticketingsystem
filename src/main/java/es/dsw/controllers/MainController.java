package es.dsw.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import es.dsw.models.ControlError;
import es.dsw.models.IndexModel;
import es.dsw.models.Reserva;
import es.dsw.models.Step1Model;
import es.dsw.models.Step3Model;

@Controller
@SessionAttributes({"reserva"})
public class MainController {
	
	// Inicializamos el objeto de la variable de sesión
	@ModelAttribute("reserva")
	public Reserva crearReserva() {
		return new Reserva();
	}
	
	@GetMapping(value= {"/", "/index"})
	public String idx(Model model, SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		
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
	public String step2(
			// Recoger los datos de la sala y la película
			@ModelAttribute Reserva reserva,
			@RequestParam(required = false) String sala,
			@RequestParam(required = false) String imgPelicula,
			@RequestParam(defaultValue="0") int codError,
			Model model
		) {
		
		// Si no hay película seleccionada, redirige a step1
	    if (reserva.getImgPelicula() == null || reserva.getImgPelicula().isBlank()) {
	        return "redirect:/step1";
	    }
	    
		// Solo actualiza si vienen por parámetro (acceso desde step1)
		if (sala != null) reserva.setSala(sala);
		if (imgPelicula != null) reserva.setImgPelicula(imgPelicula);
		
		ControlError objError = new ControlError(codError);
		
		model.addAttribute("controlError", objError);
		
		return "views/step2";
	}
	
	@PostMapping(value= {"/step3"})
	public String formulario(
			@ModelAttribute Reserva reserva,
			@RequestParam(defaultValue="", required=false) String fnom,
			@RequestParam(defaultValue="", required=false) String fapell,
			@RequestParam(defaultValue="", required=false) String fmail,
			@RequestParam(defaultValue="", required=false) String frepmail,
			@RequestParam(defaultValue="", required=false) String fdate,
			@RequestParam(defaultValue="", required=false) String fhour,
			@RequestParam(defaultValue="1", required=false) int fnumentradasadult,
			@RequestParam(defaultValue="0", required=false) int fnumentradasmen,
			Model model
		) {
		
		int codigoError = Step3Model.codigoError(fnom, fmail, frepmail, fdate, fhour, fnumentradasadult);
		
		if (codigoError != 0) {
			return "redirect:/step2?codError=" + codigoError +
			"&sala=" + reserva.getSala() + 
			"&imgPelicula=" + reserva.getImgPelicula();
		}
		
		reserva.setFnom(fnom);
		reserva.setFapell(fapell);
		reserva.setFmail(fmail);
		reserva.setFrepmail(frepmail);
		reserva.setFdate(fdate);
		reserva.setFhour(fhour);
		reserva.setFnumentradasadult(fnumentradasadult);
		reserva.setFnumentradasmen(fnumentradasmen);
		
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
