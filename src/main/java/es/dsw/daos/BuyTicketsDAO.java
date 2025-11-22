package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.Reserva;

public class BuyTicketsDAO {

	private MySqlConnection mySqlConnection;
	
	public BuyTicketsDAO(MySqlConnection mySqlConnection) {
		super();
		this.mySqlConnection = mySqlConnection;
	}
	
	public int insertBuyTickets(Reserva reserva) {
		
		String SQL = "INSERT INTO DB_FILMCINEMA.BUYTICKETS_FILM "
			       + "(IDBUYTICKETS_BTF, NAME_BTF, SURNAMES_BTF, EMAIL_BTF, CARDHOLDER_BTF, "
			       + "CARDNUMBER_BTF, MONTHCARD_BTF, YEARCARD_BTF, CCS_CARD_CODE_BTF, TOTALPRICE_BTF, "
			       + "S_ACTIVEROW_BTF, S_INSERTDATE_BTF, S_UPDATEDATE_BTF, S_IDUSER_BTF) "
			       + "VALUES (NULL, '"
			       + reserva.getFnom() + "', '"
			       + reserva.getFapell() + "', '"
			       + reserva.getFmail() + "', '"
			       + reserva.getFtitulartarjeta() + "', "
			       + reserva.getFnumtarjeta() + ", '"
			       + reserva.getfMesCaduca() + "', '"
			       + reserva.getfAnioCaduca() + "', "
			       + reserva.getFccstarjeta() + ", "
			       + reserva.getPrecioTotal() + ", b'1', current_timestamp(), current_timestamp(), '1')";
			
		ResultSet rs = mySqlConnection.executeInsert(SQL);
			
				
		try {
			if (rs != null && rs.next()) {
				return rs.getInt(1); // IDBUYTICKETS generado
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return -1;
	}
}
