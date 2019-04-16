package codiceHusky.CodiceFiscale2;
/**
 * Classe che viene utilizzata per creare una eccezione personalizzata
 * */
public class DatiNonValidiException extends Exception {
	/**
	 * Per funzionare deve avere un 'identificatore' ed in questo caso
	 * restituisce anche la stringa "ASSENTE" utile per un eventuale errore
	 * nella verifica del codice
	 */
	private static final long serialVersionUID = -4938959363357025254L;
	String stringaDatiNonValidi = "ASSENTE";
	
	public DatiNonValidiException() {
	}
	/**
	 * Getter per ottenere la stringa di assenza del codice fiscale
	 * @return 		resituisce "ASSENTE"
	 * */
	public String getError() {
		return stringaDatiNonValidi;
	}
	
}
