package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.Reserva;
import es.dsw.models.Sesion;

public class SesionProgramadaDAO {
	
	private MySqlConnection mySqlConnection;
	
	public SesionProgramadaDAO() {
		mySqlConnection = new MySqlConnection();
	}
	
	public List<Sesion> getAll() {
		List<Sesion> listaSesiones = new ArrayList<Sesion>();
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
					Sesion sesion = new Sesion();
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
	
	public int setUpdate(Reserva reserva) {
		int numRowInsert = 0;
		
		mySqlConnection.open();
		
		if (!mySqlConnection.isError()) {
			String SQL = "INSERT INTO DB_FILMCINEMA.BUYTICKETS_FILM "
			        + "(IDBUYTICKETS_BTF, NAME_BTF, SURNAMES_BTF, EMAIL_BTF, CARDHOLDER_BTF, "
			        + "CARDNUMBER_BTF, MONTHCARD_BTF, YEARCARD_BTF, CCS_CARD_CODE_BTF, TOTALPRICE_BTF, "
			        + "S_ACTIVEROW_BTF, S_INSERTDATE_BTF, S_UPDATEDATE_BTF, S_IDUSER_BTF) "
			        + "VALUES (NULL, "
			        + "'" + reserva.getFnom() + "', "
			        + "'" + reserva.getFapell() + "', "
			        + "'" + reserva.getFmail() + "', "
			        + "'" + reserva.getFtitulartarjeta() + "', "
			        + reserva.getFnumtarjeta() + ", "
			        + "'" + reserva.getfMesCaduca() + "', "
			        + "'" + reserva.getfAnioCaduca() + "', "
			        + reserva.getFccstarjeta() + ", "
			        + reserva.getPrecioTotal() + ", "
			        + "b'1', NULL, NULL, '1')";
			
			numRowInsert = mySqlConnection.executeUpdateOrDelete(SQL);
		}
		
		mySqlConnection.close();
		
		return numRowInsert;
	}
}
