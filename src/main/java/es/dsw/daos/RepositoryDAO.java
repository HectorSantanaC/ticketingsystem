package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.dsw.connections.MySqlConnection;

public class RepositoryDAO {
	private MySqlConnection mySqlConnection;
	
	public RepositoryDAO() {
		mySqlConnection = new MySqlConnection();
	}
	
	public String getTitulo(int idPelicula) {
		
		String titulo = null;
		
		mySqlConnection.open();
		
		if(!mySqlConnection.isError()) {
			ResultSet rs = mySqlConnection.executeSelect("SELECT TITLE_RF AS PELICULA "
													   + "FROM DB_FILMCINEMA.REPOSITORY_FILM "
													   + "WHERE IDFILM_RF = " + idPelicula);
			try {
				if (rs.next()) {
					titulo = (rs.getString("PELICULA"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mySqlConnection.close();
		
		return titulo;
	}
}
