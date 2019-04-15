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
	
	
	public static String codByNome(String nome, String pathComuni) {
		XMLInput inputComuni = new XMLInput(pathComuni);
		do {
			Comune comuneLetto = inputComuni.readNextComune();
			if(comuneLetto.getNome().equals(nome)) return comuneLetto.getCodice();
		} while(true);

	}
	
	
}
