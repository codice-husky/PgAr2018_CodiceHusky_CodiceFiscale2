package codiceHusky.CodiceFiscale2;

public class DatiNonValidiException extends Exception {
	String stringaDatiNonValidi = "ASSENTE";

	public DatiNonValidiException() {
	}
	
	public String getError() {
		return stringaDatiNonValidi;
	}
	
}
