package es.dsw.models;

import org.springframework.web.bind.annotation.RequestParam;

public class Step2Model {
	public static String getCartelera(
			@RequestParam("sala") int sala,
			@RequestParam("imgPelicula") int imgPelicula) {
		return "cartelera";
	}
}
