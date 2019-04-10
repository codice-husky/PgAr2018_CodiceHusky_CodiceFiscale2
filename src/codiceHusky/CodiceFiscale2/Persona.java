package codiceHusky.CodiceFiscale2;

public class Persona {
	int id;
	String nome;
	String cognome;
	char sesso;
	String comuneNascita;
	String dataNascita;
	/**
	 * @param nome
	 * @param cognome
	 * @param sesso
	 * @param comuneNascita
	 * @param dataNascita
	 */
	public Persona(int id, String nome, String cognome, char sesso, String comuneNascita, String dataNascita) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.comuneNascita = comuneNascita;
		this.dataNascita = dataNascita;
	}
	
	/**
	 * 
	 */
	public Persona() {
	}

	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public char getSesso() {
		return sesso;
	}
	
	public String getComuneNascita() {
		return comuneNascita;
	}
	
	public String getDataNascita() {
		return dataNascita;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setSesso(char sesso) {
		this.sesso = sesso;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", sesso=" + sesso + ", comuneNascita="
				+ comuneNascita + ", dataNascita=" + dataNascita + "]";
	}
	
	
	
	
	
	
}
