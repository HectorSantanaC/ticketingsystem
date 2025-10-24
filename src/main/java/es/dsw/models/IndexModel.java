package es.dsw.models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class IndexModel {
	
	// Obtener slogan del día
	public static String getSloganDelDia(DayOfWeek dia) {
		return switch (dia) {
			case MONDAY -> "Comienza la semana a lo grande.";
	        case TUESDAY -> "Hoy doble de palomitas.";
	        case WEDNESDAY -> "Día del espectador.";
	        case THURSDAY -> "La noche de las aventuras.";
	        case FRIDAY -> "No te quedes en tu casa.";
	        case SATURDAY -> "¿Ya has hecho planes para esta noche?";
	        case SUNDAY -> "Vente y carga las pilas.";
	        default -> "";
		};
	}
	
	// Obtener precio
	public static String getPrecio(DayOfWeek dia) {
		if (dia == DayOfWeek.WEDNESDAY) {
			return "desde 3,5 €";
		} else {
			return "desde 6 €";
		}
	}
	
	// Obtener hora
	public static String getHora(LocalDateTime fechaHora) {
		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH'h' mm'm'", new Locale("es", "ES"));
		return fechaHora.format(formatterHora);		
	}
	
	// Obtener fecha
	public static String getFecha(LocalDateTime fechaHora) {
	    Locale locale = new Locale("es", "ES");
	    String dia = fechaHora.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, locale);
	    String mes = fechaHora.getMonth().getDisplayName(java.time.format.TextStyle.FULL, locale);

	    // Capitalizar primera letra del día y mes
	    dia = Character.toUpperCase(dia.charAt(0)) + dia.substring(1);
	    mes = Character.toUpperCase(mes.charAt(0)) + mes.substring(1);

	    return dia + ", día " + fechaHora.getDayOfMonth() + " de " + mes;
	}
}
