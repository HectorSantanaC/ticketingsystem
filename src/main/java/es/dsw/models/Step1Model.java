package es.dsw.models;

import java.time.DayOfWeek;

public class Step1Model {
	
	public static int getNumSalas(DayOfWeek dia) {
		return switch (dia) {
			case MONDAY, WEDNESDAY, SUNDAY -> 4;
	        default -> 7;
		};
	}
	
	// Obtener el precio de las películas
	public static double getPrecioPeliculas(DayOfWeek dia) {
		return switch (dia) {
			case WEDNESDAY ->  3.5;
	        default -> 6;
		};
	}	
}
