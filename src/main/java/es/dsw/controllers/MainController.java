package es.dsw.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import es.dsw.connections.MySqlConnection;
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
		
		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.open();
		
		List<Sesion> listaSesiones = new ArrayList<Sesion>();
		
		if(!mySqlConnection.isError()) {
			ResultSet rs = mySqlConnection.executeSelect("SELECT NUMBERROOM_RCF AS NUMSALA, "
															  + "IDFILM_SSF AS IDPELICULA, "
															  + "IDSESSION_SSF AS IDSESION "
													   + "FROM DB_FILMCINEMA.SESSION_FILM, "
													   		+ "DB_FILMCINEMA.ROOMCINEMA_FILM "
													   + "WHERE S_ACTIVEROW_SSF = 1 AND "
													   		 + "IDROOMCINEMA_RCF = IDROOMCINEMA_SSF AND "
													   		 + "S_ACTIVEROW_RCF = 1 "
													   + "ORDER BY NUMBERROOM_RCF ASC");
			
			try {
				while (rs.next()) {
					Sesion sesion = new Sesion();
					sesion.setNumSala(rs.getInt("NUMSALA"));
					sesion.setIdPelicula(rs.getInt("IDPELICULA"));
					sesion.setIdSesion(rs.getInt("IDSESION"));
					listaSesiones.add(sesion);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mySqlConnection.close();
		
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
						@RequestParam(defaultValue="0") int codError,
						Model model) {
		
		// Si no hay película seleccionada, redirige a step1
	    if (idPelicula == 0) {
	        return "redirect:/step1";
	    }
	    
	    reserva.setIdPelicula(idPelicula);
	    		
		ControlError objError = new ControlError(codError);
		
		model.addAttribute("idPelicula", idPelicula);
		model.addAttribute("controlError", objError);
		
		return "views/step2";
	}
	
	@PostMapping(value= {"/step3"})
	public String formulario(@ModelAttribute Reserva reserva,
							 Model model) {
		
		int codigoError = Step3Model.codigoError(reserva.getFnom(), reserva.getFmail(), 
				reserva.getFrepmail(), reserva.getFdate(), reserva.getFhour(), 
				reserva.getFnumentradasadult());
		
		if (codigoError != 0) {
			return "redirect:/step2?codError=" + codigoError + "&idPelicula=" + reserva.getIdPelicula();
		}
		
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
