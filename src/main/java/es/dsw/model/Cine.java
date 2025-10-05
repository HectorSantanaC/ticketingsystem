package es.dsw.model;

import java.time.DayOfWeek;

public class Cine {
	public static String getSloganDelDia(DayOfWeek dia) {
		return switch (dia) {
			case MONDAY -> "Comienza la semana a lo grande.";
	        case TUESDAY -> "Hoy doble de palomitas.";
	        case WEDNESDAY -> "Día del espectador.";
	        case THURSDAY -> "La noche de las aventuras.";
	        case FRIDAY -> "No te quedes en tu casa.";
	        case SATURDAY -> "¿Ya has hecho planes para esta noche?";
	        case SUNDAY -> "Vente y carga las pilas.";
		};
	}
	
	public static String getPrecio(DayOfWeek dia) {
		if (dia == DayOfWeek.WEDNESDAY) {
			return "desde 3,5 €";
		} else {
			return "desde 6 €";
		}
	}
}
