package es.dsw.models;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Step1Model {
	// Lista de películas
	private static List<String> peliculas = new ArrayList<>(List.of(
			"film1.jpg", "film2.jpg", "film3.jpg", "film4.jpg",
			"film5.jpg", "film6.jpg", "film7.jpg", "film8.jpg",
			"film9.jpg", "film10.jpg", "film11.jpg", "film12.jpg",
			"film13.jpg", "film14.jpg")
	);
	
	// Proyección de películas según el día de la semana
	public static List<String> getPeliculasDelDia(DayOfWeek dia) {
		int numSalas;
		
		// Determinar número de salas en función del día
		switch (dia) {
			case MONDAY, WEDNESDAY, SUNDAY -> numSalas = 4;
	        default -> numSalas = 7;
		};
		
		// Crear copia de la lista de películas
		List<String> peliculasAleatorias = new ArrayList<>(peliculas);
		
		// Reordenar las películas aleatoriamente
		Collections.shuffle(peliculasAleatorias);
		
		return peliculasAleatorias.subList(0, numSalas);
	}
	
	// Obtener el precio de las películas
	public static double getPrecioPeliculas(DayOfWeek dia) {
		return switch (dia) {
			case WEDNESDAY ->  3.5;
	        default -> 6;
		};
	}	
}
