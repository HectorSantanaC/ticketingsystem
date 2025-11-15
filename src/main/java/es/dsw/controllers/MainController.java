package es.dsw.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import es.dsw.daos.NombrePeliculaDAO;
import es.dsw.daos.SesionProgramadaDAO;
import es.dsw.models.ControlError;
import es.dsw.models.IndexModel;
import es.dsw.models.Reserva;
import es.dsw.models.Sesion;
import es.dsw.models.Step1Model;
import es.dsw.models.Step3Model;

@Controller
@SessionAttributes({"reserva"})
public class MainController {
	
	// Inicializa la clase de la variable de sesión
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
		
		SesionProgramadaDAO sesionProgramadaDAO = new SesionProgramadaDAO();
		
		List<Sesion> listaSesiones = sesionProgramadaDAO.getAll();
		
		// Limitar la cantidad de sesiones según el día
	    int numSalas = Step1Model.getNumSalas(dia);
	    if (listaSesiones.size() > numSalas) {
	    	listaSesiones = new ArrayList<>(listaSesiones.subList(0, numSalas));
	    }
		
		model.addAttribute("sesiones", listaSesiones);
		model.addAttribute("precioPeliculas", Step1Model.getPrecioPeliculas(dia));
		
		
		return "views/step1";
	}
	
	@GetMapping(value= {"/step2"})
	public String step2(@ModelAttribute Reserva reserva,
						@RequestParam(defaultValue="0") int idPelicula,
						@RequestParam(defaultValue="0") int sala,
						@RequestParam(defaultValue="0") int codError,
						Model model) {
		
		// Si no hay película seleccionada, redirige a step1
	    if (idPelicula == 0) {
	        return "redirect:/step1";
	    }
	    
	    reserva.setIdPelicula(idPelicula);
	    reserva.setSala(sala);
	    		
		ControlError objError = new ControlError(codError);
		
		model.addAttribute("idPelicula", idPelicula);
		model.addAttribute("controlError", objError);
		
		return "views/step2";
	}
	
	@PostMapping(value= {"/step3"})
	public String formulario(@ModelAttribute Reserva reserva,
							 Model model) {
		
		int numButacas = reserva.totalButacas();
		
		model.addAttribute("numButacas", numButacas);
		
		int codigoError = Step3Model.codigoError(reserva.getFnom(), reserva.getFmail(), 
				reserva.getFrepmail(), reserva.getFdate(), reserva.getFhour(), 
				reserva.getFnumentradasadult());
		
		if (codigoError != 0) {
			return "redirect:/step2?codError=" + codigoError + "&idPelicula=" + reserva.getIdPelicula();
		}
		
		model.addAttribute("idPelicula", reserva.getIdPelicula());
		
		return "views/step3";
	}
	
	@GetMapping("/step3")
	public String volverStep3(@ModelAttribute Reserva reserva, 
							  Model model) {
	    
	    int numButacas = reserva.totalButacas();
	    
	    if (reserva.getIdPelicula() <= 0) {
			return "redirect:/step1";
		} else if (numButacas <= 0) {
			return "redirect:/step2?idPelicula=" + reserva.getIdPelicula();
		}
	    
	    model.addAttribute("numButacas", numButacas);

	    return "views/step3";
	}
	
	@PostMapping(value= {"/step4"})
	public String step4(@ModelAttribute Reserva reserva,
						@RequestParam String FButacasSelected,
						Model model) {
		
		String butacas = FButacasSelected.replace(";", ", ");
		if (butacas.endsWith(", ")) {
		    butacas = butacas.substring(0, butacas.length() - 2);
		}
		
		reserva.setButacasSeleccionadas(butacas);
		model.addAttribute("butacasSeleccionadas", butacas);
		
		NombrePeliculaDAO nombrePeliculaDAO = new NombrePeliculaDAO();
		String titulo = nombrePeliculaDAO.getAll(reserva.getIdPelicula());
		
		// Nombre de la película
		model.addAttribute("pelicula", titulo);
		
		// Precio de adultos, menores y total
		model.addAttribute("totalAdultos", reserva.totalAdultos());
		model.addAttribute("totalMenores", reserva.totalMenores());
		model.addAttribute("total", reserva.total());
		
		return "views/step4";
	}
	
	@PostMapping(value= {"/end"})
	public String end() {
		return "views/end";
	}
}
