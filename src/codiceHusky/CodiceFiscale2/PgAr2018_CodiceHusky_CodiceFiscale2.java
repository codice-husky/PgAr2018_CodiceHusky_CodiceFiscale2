package codiceHusky.CodiceFiscale2;

import java.util.Scanner;

import javax.xml.stream.XMLStreamException;

public class PgAr2018_CodiceHusky_CodiceFiscale2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Persona persona;
		String codice;
		XMLInput xml = new XMLInput("xml/inputPersone.xml");
		String pathComuni = "xml/comuni.xml";
		String cmnd = "";
		String nome, cognome,sesso;
		boolean end = false;
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
				codice = calcolaCodice(persona);
				codice = Comune.codByNome(persona.getComuneNascita(), pathComuni);
				//System.out.println(persona.toString());
				//System.out.println(codice);
			} else return;
		} while(true);
		
	
		
		//System.out.println(persona.toString());

	}
	
	
	private String getCodiceErrato(String pathCF) throws XMLStreamException {
		String codice = null;
		XMLInput inputCF = new XMLInput(pathCF);
		do {
			String CFLetto = inputCF.readNextCF();
			if(!verificaCF(CFLetto)) return CFLetto;
		} while(true);
	}
	
	private static boolean verificaCF(String CF) {
		return true;
	}
	private static String calcolaCodice(Persona persona) {
		String nome = persona.getNome();
		String cognome = persona.getCognome();
		char sesso = persona.getSesso();
		String comune = persona.getComuneNascita();
		String data = persona.getDataNascita();
		cognome = codiceCognome(cognome);
		nome = codiceNome(nome);
		System.out.println(String.format("Cognome %s -> %s", persona.getCognome(),cognome));
		System.out.println(String.format("Nome %s -> %s", persona.getNome(),nome));
		return "";
	}
	
	private static boolean isVocale(char x) {
		if(x=='A' || x=='E' || x=='I' || x=='O' || x =='U') return true;
		else return false;
	}


	private static String codiceCognome(String cognome) {
		String memo = "";
		int length = 0;
		for(int i = 0; i<cognome.length();i++) {
			char x = cognome.charAt(i);
			if(!isVocale(x)) {
				memo = memo.concat(""+x);
				length ++;
			}
			if(length == 3) break;
		}
		if(length != 3) {
			for(int i = 0; i<cognome.length();i++) {
				char x = cognome.charAt(i);
				if(isVocale(x)) {
					String e = ""+x;
					memo = memo.concat(e);
					length ++;
				}
				if(length == 3) break;
			}
		}
		for(int i=length;i<3;i++) {
			memo = memo.concat("X");
		}
		
		return memo;
	}
	
	private static String codiceNome(String nome) {
		String memo = "";
		int length = 0;
		int consonanti=0;
		
		// conto quante consonanti ci sono nel nome
		for(int i = 0; i<nome.length();i++) {
			char x = nome.charAt(i);
			if(!isVocale(x)) {
				consonanti++;
			}
			if(consonanti == 4) break;
		}
		
		int n_consonante=0;
		for(int i = 0; i<nome.length();i++) {
			char x = nome.charAt(i);
			if(!isVocale(x)) {
				n_consonante++;
				// se ci sono più di 3 consonanti nel nome salto la 2°
				if (consonanti>=4 && n_consonante==2) continue;
				else {
					memo = memo.concat(""+x);
					length ++;
				}
			}
			if(length == 3) break;
		}
		
		if(length != 3) {
			for(int i = 0; i<nome.length();i++) {
				char x = nome.charAt(i);
				if(isVocale(x)) {
					String e = ""+x;
					memo = memo.concat(e);
					length ++;
				}
				if(length == 3) break;
			}
		}
		for(int i=length;i<3;i++) {
			memo = memo.concat("X");
		}
		
		return memo;
	}
}
