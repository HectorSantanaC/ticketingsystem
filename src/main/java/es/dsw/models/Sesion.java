package es.dsw.models;

public class Sesion {
	private int idSession;
	private int idFilm;
	private int idRoomCinema;
	private int sActiveRow;
	private String sInsertDate;
	private String sUpdateDate;
	private int sIdUser;
	
	public Sesion() {
		super();
	}
	
	public int getIdSession() {
		return idSession;
	}
	public void setIdSession(int idSession) {
		this.idSession = idSession;
	}
	public int getIdFilm() {
		return idFilm;
	}
	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}
	public int getIdRoomCinema() {
		return idRoomCinema;
	}
	public void setIdRoomCinema(int idRoomCinema) {
		this.idRoomCinema = idRoomCinema;
	}
	public int getsActiveRow() {
		return sActiveRow;
	}
	public void setsActiveRow(int sActiveRow) {
		this.sActiveRow = sActiveRow;
	}
	public String getsInsertDate() {
		return sInsertDate;
	}
	public void setsInsertDate(String sInsertDate) {
		this.sInsertDate = sInsertDate;
	}
	public String getsUpdateDate() {
		return sUpdateDate;
	}
	public void setsUpdateDate(String sUpdateDate) {
		this.sUpdateDate = sUpdateDate;
	}
	public int getsIdUser() {
		return sIdUser;
	}
	public void setsIdUser(int sIdUser) {
		this.sIdUser = sIdUser;
	}
}
