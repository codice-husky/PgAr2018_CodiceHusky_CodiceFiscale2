package codiceHusky.CodiceFiscale2;

public class DatiNonValidiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4938959363357025254L;
	String stringaDatiNonValidi = "ASSENTE";

	public DatiNonValidiException() {
	}
	
	public String getError() {
		return stringaDatiNonValidi;
	}
	
}
