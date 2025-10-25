package es.dsw.models;

public class ControlError {
	private boolean error;
	private String descripcionError;

	
	// Constructor
	public ControlError() {
		this.error = false;
		this.descripcionError = "";
	}

	// Controlador de errores
	public ControlError(int codError) {
		this.error = false;
		this.descripcionError = "";
		
		if(codError == 1) {
			this.error = true;
			this.descripcionError = "Por favor, complete todos los campos obligatorios";
		} else if (codError == 2) {
			this.error = true;
			this.descripcionError = "Por favor, verifique que ambos e-mails coincidan";
		}
	}
	
	// Getters y Setters
	public boolean isError() {
		return error;
	}


	public void setError(boolean error) {
		this.error = error;
	}


	public String getDescripcionError() {
		return descripcionError;
	}


	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
}
