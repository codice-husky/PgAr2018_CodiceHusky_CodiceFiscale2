package codiceHusky.CodiceFiscale2;

public class Persona {
	private int id;
	private String nome;
	private String cognome;
	private char sesso;
	private String comuneNascita;
	private String dataNascita;
	private String codiceFiscale;
	/**
	 * @param nome
	 * @param cognome
	 * @param sesso
	 * @param comuneNascita
	 * @param dataNascita
	 */
	public Persona(int id, String nome, String cognome, char sesso, String comuneNascita, String dataNascita, String codiceFiscale) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.comuneNascita = comuneNascita;
		this.dataNascita = dataNascita;
		this.codiceFiscale = codiceFiscale;
	}
	
	/**
	 * Getter per ottenere  l'id della persona, ottenuto dal file xml
	 * @return 		restituisce l'id della persona
	 * 	 */
	public int getId() {
		return id;
	}
	/**
	 * Getter per ottenere il nome della persona
	 * @return		resituisce il nome della persona
	 * */
	public String getNome() {
		return nome;
	}
	/**
	 * Getter per ottenere il cognome della persona
	 * @return		resituisce il cognome della persona
	 * */
	public String getCognome() {
		return cognome;
	}
	/**
	 * Getter per ottenere il sesso della persona
	 * @return		resituisce il sesso della persona
	 * */
	public char getSesso() {
		return sesso;
	}
	/**
	 * Getter per ottenere il comune di nascita della persona
	 * @return		resituisce il comune di nascita della persona
	 * */
	public String getComuneNascita() {
		return comuneNascita;
	}
	/**
	 * Getter per ottenere la data di nascita della persona
	 * @return		resituisce la data di nascita della persona
	 * */
	public String getDataNascita() {
		return dataNascita;
	}
	/**
	 * Getter per ottenere il codice fiscale della persona
	 * @return 		restituisce il codice fiscale di quella persona
	 * */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	/**
	 * Setter dell'attributo id 
	 * @param id    id della persona
	 * */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Setter dell'attributo nome 
	 * @param nome    nome della persona
	 * */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * Setter dell'attributo cognome 
	 * @param cognome    cognome della persona
	 * */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * Setter dell'attributo sesso  
	 * @param sesso    sesso della persona
	 * */
	public void setSesso(char sesso) {
		this.sesso = sesso;
	}
	/**
	 * Setter dell'attributo  comuneNascita 
	 * @param comuneNascita    il comune di nascita della persona
	 * */
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	/**
	 * Setter dell'attributo dataNascita 
	 * @param dataNascita    la data di nascita della persona
	 * */
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	/**
	 * Setter dell'attributo codiceFiscale 
	 * @param codiceFiscale    il codice fiscale della persona
	 * */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	/**
	 * Override del metodo già esistente toString per visualizzare 
	 * graficamente le informazioni della persona
	 * @return		stringa delle varie info della persona
	 * */
	@Override
	public String toString() {
		return "Persona [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", sesso=" + sesso + ", comuneNascita="
				+ comuneNascita + ", dataNascita=" + dataNascita + "]";
	}
	
	
	
	
	
	
}
