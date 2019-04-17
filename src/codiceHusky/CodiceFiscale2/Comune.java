package codiceHusky.CodiceFiscale2;

public class Comune {
	private String nome;
	private String codice;
	/**
	 * Crea un nuovo oggetto di tipo Comune
	 * @param nome Nome del comune
	 * @param codice Codice del comune
	 */
	public Comune(String nome, String codice) {
		this.nome = nome;
		this.codice = codice;
	}
	/**
	 * Getter per ottenere il nome del comune di nascita
	 * @return		resituisce il nome del comune di nascita
	 * */
	public String getNome() {
		return nome;
	}
	/**
	 * Getter per ottenere il codice del comune di nascita
	 * @return	 	resituisce il codice del comune di nascita
	 * */
	public String getCodice() {
		return codice;
	}
	
	/**
	 * Metodo che dato il nome del comune e il file in cui cercarlo
	 * restituisce il codice ad esso associato
	 * @return 		resituisce il codice del comune cercato
	 * */
	public static String codByNome(String nome, String pathComuni) {
		XMLInput inputComuni = new XMLInput(pathComuni);
		do {
			Comune comuneLetto = inputComuni.readNextComune();
			if(comuneLetto.getNome().equals(nome)) return comuneLetto.getCodice();
		} while(true);

	}
	
	
}
