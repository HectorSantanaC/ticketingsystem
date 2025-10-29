package es.dsw.models;

public class Step3Model {
	
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
}
