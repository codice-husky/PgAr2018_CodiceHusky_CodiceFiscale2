package codiceHusky.CodiceFiscale2;

public class PgAr2018_CodiceHusky_CodiceFiscale2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Persona persona;
		XMLInputOutput xml = new XMLInputOutput("xml/inputPersone.xml");
		
		
		do {
			System.out.println(xml.readNextPersona().toString());
		} while(true);


		
		//System.out.println(persona.toString());

	}

}
