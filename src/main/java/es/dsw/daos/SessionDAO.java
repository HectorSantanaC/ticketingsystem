package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.SessionFilm;

public class SessionDAO {
	
	private MySqlConnection mySqlConnection;
	
	public SessionDAO() {
		mySqlConnection = new MySqlConnection();
	}
	
	public List<SessionFilm> getAll() {
		List<SessionFilm> listaSesiones = new ArrayList<SessionFilm>();
		mySqlConnection.open();
		
		if(!mySqlConnection.isError()) {
			ResultSet rs = mySqlConnection.executeSelect("SELECT NUMBERROOM_RCF AS NUMSALA, "
															  + "IDFILM_SSF AS IDPELICULA, "
															  + "IDSESSION_SSF AS IDSESION "
													   + "FROM DB_FILMCINEMA.SESSION_FILM, "
													   		+ "DB_FILMCINEMA.ROOMCINEMA_FILM "
													   + "WHERE S_ACTIVEROW_SSF = 1 AND "
													   		 + "IDROOMCINEMA_RCF = IDROOMCINEMA_SSF AND "
													   		 + "S_ACTIVEROW_RCF = 1 "
													   + "ORDER BY NUMBERROOM_RCF ASC");
			
			try {
				while (rs.next()) {
					SessionFilm sesion = new SessionFilm();
					sesion.setNumSala(rs.getInt("NUMSALA"));
					sesion.setIdPelicula(rs.getInt("IDPELICULA"));
					sesion.setIdSesion(rs.getInt("IDSESION"));
					listaSesiones.add(sesion);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mySqlConnection.close();
		
		return listaSesiones;
	}
}
