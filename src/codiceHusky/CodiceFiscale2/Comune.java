package codiceHusky.CodiceFiscale2;

public class Comune {
	private String nome;
	private String codice;
	/**
	 * @param come
	 * @param codice
	 */
	public Comune(String nome, String codice) {
		this.nome = nome;
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public String getCodice() {
		return codice;
	}
	
	
	public static String codByNome(String nome) {
		XMLInputOutput inputCodice = new XMLInputOutput("xml/comuni.xml");
		do {
			Comune comuneLetto = inputCodice.readNextComune();
			if(comuneLetto.getNome().equals(nome)) return comuneLetto.getCodice();
		} while(true);

	}
	
	
}
