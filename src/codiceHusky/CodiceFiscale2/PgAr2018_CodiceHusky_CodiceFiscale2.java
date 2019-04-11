package codiceHusky.CodiceFiscale2;

public class PgAr2018_CodiceHusky_CodiceFiscale2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Persona persona;
		String codice;
		XMLInputOutput xml = new XMLInputOutput("xml/inputPersone.xml");
		String pathComuni = "xml/comuni.xml";
		
		/*persona = xml.readNextPersona();
		persona = xml.readNextPersona();
		persona = xml.readNextPersona();
		persona = xml.readNextPersona();
		System.out.println(persona.toString());
		//codice = Comune.codByNome(persona.getComuneNascita(), comuni);
		//System.out.println(codice);
		persona = xml.readNextPersona();
		System.out.println(persona.toString());
		codice = Comune.codByNome(persona.getComuneNascita(), comuni);
		System.out.println(codice);*/
		
		do {
			persona = xml.readNextPersona();
			codice = Comune.codByNome(persona.getComuneNascita(), pathComuni);
			System.out.println(persona.toString());
			System.out.println(codice);
			
		} while(true);


		
		//System.out.println(persona.toString());

	}

}
