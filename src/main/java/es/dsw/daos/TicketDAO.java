package es.dsw.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.Reserva;

public class TicketDAO {
	
	private MySqlConnection mySqlConnection;
	
	public TicketDAO(MySqlConnection mySqlConnection) {
		super();
		this.mySqlConnection = mySqlConnection;
	}

	public List<String> insertTicket(Reserva reserva, int idSesion, int idBuyTicket) {
		
		List<String> seriales = new ArrayList<>();
		int menores = reserva.getFnumentradasmen();
		
		for (String butaca : reserva.getButacasSeleccionadas()) {
			
			// Determinar si la butaca corresponde a un menor o adulto
            boolean esMenor = menores > 0;
            String younger = esMenor ? "b'1'" : "b'0'";
            if (esMenor) menores--;
            
            // Generar código serial único
            String serial = generarSerialTicket();
            seriales.add(serial);
			
				String SQL = "INSERT INTO DB_FILMCINEMA.TICKET_FILM "
						+ "(IDTICKET_TKF, IDSESSION_TKF, DATESESSION_TKF, TIMESESSION_TKF, "
						+ "SERIALCODE_TKF, YOUNGER_TKF, PRICE_TKF, ROWSEAT_TKF, "
						+ "IDBUYTICKETS_TKF, S_ACTIVEROW_TKF, S_INSERTDATE_TKF, S_UPDATEDATE_TKF, "
						+ "S_IDUSER_TKF) "
						+ "VALUES "
						+ "(NULL, "
						+ idSesion + ", '"
						+ reserva.getFdate() + "', '"
						+ reserva.getFhour()+ "', '"
						+ serial + "', "
						+ younger + ", "
						+ reserva.getPrecioEntrada() + ", '"
						+ butaca + "', "
						+ idBuyTicket + ", "
						+ "b'1', current_timestamp(), current_timestamp(), '1')";
				
				mySqlConnection.executeUpdateOrDelete(SQL);
		}
		
		return seriales;	
	}
	
	private String generarSerialTicket() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
	}
}
