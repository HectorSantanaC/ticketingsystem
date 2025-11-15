package es.dsw.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reserva {
	private int sala;
	private int idPelicula;
	private String fnom;
	private String fapell;
	private String fmail;
	private String frepmail;
	private String fdate;
	private String fhour;
	private int fnumentradasadult;
	private int fnumentradasmen;
	private String butacasSeleccionadas;
	private String pelicula;
	private String ftitulartarjeta;
	private String fnumtarjeta;
	private String fMesCaduca;
	private String fAnioCaduca;
	private String fccstarjeta;
	
	// Getter y Setters
	public int getSala() {
		return sala;
	}
	public void setSala(int sala) {
		this.sala = sala;
	}
	public int getIdPelicula() {
		return idPelicula;
	}
	public void setIdPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}
	public String getFnom() {
		return fnom;
	}
	public void setFnom(String fnom) {
		this.fnom = fnom;
	}
	public String getFapell() {
		return fapell;
	}
	public void setFapell(String fapell) {
		this.fapell = fapell;
	}
	public String getFmail() {
		return fmail;
	}
	public void setFmail(String fmail) {
		this.fmail = fmail;
	}
	public String getFrepmail() {
		return frepmail;
	}
	public void setFrepmail(String frepmail) {
		this.frepmail = frepmail;
	}
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	public String getFhour() {
		return fhour;
	}
	public void setFhour(String fhour) {
		this.fhour = fhour;
	}
	public int getFnumentradasadult() {
		return fnumentradasadult;
	}
	public void setFnumentradasadult(int fnumentradasadult) {
		this.fnumentradasadult = fnumentradasadult;
	}
	public int getFnumentradasmen() {
		return fnumentradasmen;
	}
	public void setFnumentradasmen(int fnumentradasmen) {
		this.fnumentradasmen = fnumentradasmen;
	}
	
	public String getButacasSeleccionadas() {
		return butacasSeleccionadas;
	}
	public void setButacasSeleccionadas(String butacasSeleccionadas) {
		this.butacasSeleccionadas = butacasSeleccionadas;
	}
	public String getPelicula() {
		return pelicula;
	}
	public void setPelicula(String pelicula) {
		this.pelicula = pelicula;
	}
	public String getFtitulartarjeta() {
		return ftitulartarjeta;
	}
	public void setFtitulartarjeta(String ftitulartarjeta) {
		this.ftitulartarjeta = ftitulartarjeta;
	}
	public String getFnumtarjeta() {
		return fnumtarjeta;
	}
	public void setFnumtarjeta(String fnumtarjeta) {
		this.fnumtarjeta = fnumtarjeta;
	}
	public String getfMesCaduca() {
		return fMesCaduca;
	}
	public void setfMesCaduca(String fMesCaduca) {
		this.fMesCaduca = fMesCaduca;
	}
	public String getfAnioCaduca() {
		return fAnioCaduca;
	}
	public void setfAnioCaduca(String fAnioCaduca) {
		this.fAnioCaduca = fAnioCaduca;
	}
	public String getFccstarjeta() {
		return fccstarjeta;
	}
	public void setFccstarjeta(String fccstarjeta) {
		this.fccstarjeta = fccstarjeta;
	}
	// Calcular el total de butacas reservadas
	public int totalButacas() {
		return fnumentradasadult + fnumentradasmen;
	}
	
	// Modificar fecha de "yyyy-MM-dd" a "dd/MM/yyyy"
	public String getFdateFormateada() {
        if (fdate == null || fdate.isEmpty()) {
            return "";
        }
        LocalDate fecha = LocalDate.parse(fdate);
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
		return precioDia() * fnumentradasadult;
	}
	
	// Calcular el precio total de menores
	public double totalMenores() {		
		return precioDia() * fnumentradasmen;
	}
	
	// Calcular el total de adultos y menores
	public double total() {
		return totalAdultos() + totalMenores();
	}
}
