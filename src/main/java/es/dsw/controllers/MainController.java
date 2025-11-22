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

import es.dsw.connections.MySqlConnection;
import es.dsw.daos.BuyTicketsDAO;
import es.dsw.daos.RepositoryDAO;
import es.dsw.daos.SessionDAO;
import es.dsw.daos.TicketDAO;
import es.dsw.models.ControlError;
import es.dsw.models.IndexModel;
import es.dsw.models.Reserva;
import es.dsw.models.SessionFilm;
import es.dsw.models.Step1Model;
import es.dsw.models.Step3Model;
import es.dsw.models.Step4Model;

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
	public String step1(@ModelAttribute Reserva reserva, Model model) {
		DayOfWeek dia = LocalDate.now().getDayOfWeek();

		// Conexión a base de datos para recoger el idPelicula, nº de sala e idSesion
		SessionDAO sessionDAO = new SessionDAO();
		
		List<SessionFilm> listaSesiones = sessionDAO.getAll();
		
		// Limitar la cantidad de sesiones según el día
	    int numSalas = Step1Model.getNumSalas(dia);
	    if (listaSesiones.size() > numSalas) {
	    	listaSesiones = new ArrayList<>(listaSesiones.subList(0, numSalas));
	    }
		
		model.addAttribute("sesiones", listaSesiones);
		model.addAttribute("precioPeliculas", Step1Model.getPrecioPeliculas(dia));
		
		reserva.setPrecioEntrada(Step1Model.getPrecioPeliculas(dia));
		
		return "views/step1";
	}
	
	@GetMapping(value= {"/step2"})
	public String step2(@ModelAttribute Reserva reserva,
						@RequestParam(defaultValue="0") int idPelicula,
						@RequestParam(defaultValue="0") int sala,
						@RequestParam(defaultValue="0") int idSesion,
						@RequestParam(defaultValue="0") int codError,
						Model model) {
		
		// Si no hay película seleccionada, redirige a step1
	    if (idPelicula == 0) {
	        return "redirect:/step1";
	    }
	    
	    reserva.setIdPelicula(idPelicula);
	    reserva.setSala(sala);
	    reserva.setIdSesion(idSesion);
	    		
		ControlError objError = new ControlError(codError);
		
		model.addAttribute("idPelicula", idPelicula);
		model.addAttribute("controlError", objError);
		
		return "views/step2";
	}
	
	@PostMapping(value= {"/step3"})
	public String formulario(@ModelAttribute Reserva reserva,
							 Model model) {
		
		// Total de butacas reservadas
		Step3Model step3Model = new Step3Model(reserva);
	    int numButacas = step3Model.totalButacas();
		
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
	    
		// Total de butacas reservadas
		Step3Model step3Model = new Step3Model(reserva);
	    int numButacas = step3Model.totalButacas();
	    
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
		
		// Lista de butacas seleccionadas
		List<String> listaButacas = new ArrayList<>();
		
		for (String butacaSeleccionada : FButacasSelected.split(";")) {
			listaButacas.add(butacaSeleccionada);
		}
		
		String butacas = String.join(", ", listaButacas);
		
		reserva.setButacasSeleccionadas(listaButacas);
		model.addAttribute("butacasSeleccionadas", butacas);
		
		// Conexión a base de datos para recoger el nombre de la película
		RepositoryDAO repositoryDAO = new RepositoryDAO();
		String titulo = repositoryDAO.getTitulo(reserva.getIdPelicula());
		reserva.setPelicula(titulo);
		
		model.addAttribute("pelicula", titulo);
		
		// Fecha formateada dd/MM/yyyy
		Step4Model step4Model = new Step4Model(reserva);
		model.addAttribute("fdateFormateada", step4Model.getFdateFormateada());
		
		// Precio de adultos, menores y total
		model.addAttribute("totalAdultos", step4Model.totalAdultos());
		model.addAttribute("totalMenores", step4Model.totalMenores());
		model.addAttribute("total", step4Model.total());
		
		reserva.setPrecioTotal(step4Model.total());
		
		return "views/step4";
	}
	
	@GetMapping(value= {"/end"})
	public String end(@ModelAttribute Reserva reserva,
					  Model model) {
		
		model.addAttribute("reserva", reserva);
		model.addAttribute("butacas", reserva.getButacasSeleccionadas());
		
		// Fecha formateada dd/MM/yyyy
		Step4Model step4Model = new Step4Model(reserva);
		model.addAttribute("fdateFormateada", step4Model.getFdateFormateada());
		
		return "views/end";
	}
	
	@PostMapping(value={"/insert"})
	public String insert(@ModelAttribute Reserva reserva,
						 Model model) {
		
		MySqlConnection mySqlConnection = new MySqlConnection(false); // autocommit desactivado
		mySqlConnection.open();
		
		try {
	        if (mySqlConnection.isError()) {
	            throw new RuntimeException(mySqlConnection.msgError());
	        }

	        // Crear DAOs usando la misma conexión
	        BuyTicketsDAO buyTicketsDAO = new BuyTicketsDAO(mySqlConnection);
	        TicketDAO ticketDAO = new TicketDAO(mySqlConnection);

	        // Insertar compra
	        int idBuyTicket = buyTicketsDAO.insertBuyTickets(reserva);

	        if (idBuyTicket <= 0) {
	            throw new RuntimeException("Error insertando compra");
	        }

	        // Insertar tickets (1 por butaca)
	        int idSesion = reserva.getIdSesion();
	        List<String> codigosTickets = ticketDAO.insertTicket(reserva, idSesion, idBuyTicket);

	        if (codigosTickets.size() != reserva.getButacasSeleccionadas().size()) {
	            throw new RuntimeException("Error insertando tickets");
	        }
	        
	        model.addAttribute("codigosTickets", codigosTickets);

	        // TODO OK → commit
	        mySqlConnection.commit();
	    } catch (Exception e) {
	        // ERROR → rollback
	    	mySqlConnection.rollback();
	        System.out.println("Rollback: " + e.getMessage());
	    } finally {
	    	mySqlConnection.close();
	    }
		
		// Volver a mostrar los datos
	    model.addAttribute("reserva", reserva);
	    model.addAttribute("butacas", reserva.getButacasSeleccionadas());

	    Step4Model step4 = new Step4Model(reserva);
	    model.addAttribute("fdateFormateada", step4.getFdateFormateada());

	    return "views/end";
	}
}
