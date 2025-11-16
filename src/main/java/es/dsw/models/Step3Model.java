package es.dsw.models;

public class Step3Model {
	
	private Reserva reserva;
		
	public Step3Model(Reserva reserva) {
		super();
		this.reserva = reserva;
	}

	public static int codigoError(String fnom, String fmail, String frepmail, String fdate, String fhour, int fnumentradasadult) {
		
		if (fnom == null || fnom.isBlank() ||
		    fmail == null || fmail.isBlank() ||
		    frepmail == null || frepmail.isBlank() ||
		    fdate == null || fdate.isBlank() ||
		    fhour == null || fhour.isBlank() ||
		    fnumentradasadult <= 0) {
				
				return 1;
			} 
			
			if (!fmail.toLowerCase().equals(frepmail.toLowerCase())) {
				return 2;
			}
			
		return 0;
	}
	
	// Calcular el total de butacas reservadas
		public int totalButacas() {
			return reserva.getFnumentradasadult() + reserva.getFnumentradasmen();
		}
		
}
