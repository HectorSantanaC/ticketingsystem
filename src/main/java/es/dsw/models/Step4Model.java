package es.dsw.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Step4Model {
	
	private Reserva reserva;
	
	public Step4Model(Reserva reserva) {
		super();
		this.reserva = reserva;
	}

	// Modificar fecha de "yyyy-MM-dd" a "dd/MM/yyyy"
	public String getFdateFormateada() {
		if (reserva.getFdate() == null || reserva.getFdate().isEmpty()) {
	           return "";
	    }
		
		LocalDate fecha = LocalDate.parse(reserva.getFdate());
		return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
		
	// Calcular el precio del dia
	public double precioDia() {
		// Determinar el día y precio
		LocalDateTime ahora = LocalDateTime.now();
		DayOfWeek dia = ahora.getDayOfWeek();
			
		double precio = Step1Model.getPrecioPeliculas(dia);
			
		return precio;
	}
		
	// Calcular el precio total de adultos
	public double totalAdultos() {	
		return precioDia() * reserva.getFnumentradasadult();
	}
		
	// Calcular el precio total de menores
	public double totalMenores() {		
		return precioDia() * reserva.getFnumentradasmen();
	}
		
	// Calcular el total de adultos y menores
	public double total() {
		return totalAdultos() + totalMenores();
	}
}
