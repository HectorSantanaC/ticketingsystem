package es.dsw.models;

public class Reserva {
	private String sala;
	private int idPelicula;
	private String fnom;
	private String fapell;
	private String fmail;
	private String frepmail;
	private String fdate;
	private String fhour;
	private int fnumentradasadult;
	private int fnumentradasmen;
	
	// Getter y Setters
	public String getSala() {
		return sala;
	}
	public void setSala(String sala) {
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
}
