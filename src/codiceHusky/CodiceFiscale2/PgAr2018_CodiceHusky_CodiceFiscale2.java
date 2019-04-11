package codiceHusky.CodiceFiscale2;

import javax.xml.stream.XMLStreamException;

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
			if(persona!=null) {
				codice = Comune.codByNome(persona.getComuneNascita(), pathComuni);
				System.out.println(persona.toString());
				System.out.println(codice);
			} else return;
		} while(true);


		
		//System.out.println(persona.toString());

	}
	
	
	private String getCodiceErrato(String pathCF) throws XMLStreamException {
		String codice = null;
		XMLInputOutput inputCF = new XMLInputOutput(pathCF);
		do {
			String CFLetto = inputCF.readNextCF();
			if(!verificaCF(CFLetto)) return CFLetto;
		} while(true);
	}
	
	private static boolean verificaCF(String CF) {
		return true;
	}

}
