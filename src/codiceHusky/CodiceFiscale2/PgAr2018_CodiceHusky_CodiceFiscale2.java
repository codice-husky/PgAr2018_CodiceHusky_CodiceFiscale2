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
				codice = calcolaCodice(persona,pathComuni);
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
	private static String calcolaCodice(Persona persona,String pathComuni) throws DatiNonValidiException {
		String nome = persona.getNome();
		String cognome = persona.getCognome();
		char sesso = persona.getSesso();
		String comune = persona.getComuneNascita();
		String data = persona.getDataNascita();
		cognome = codiceCognome(cognome);
		nome = codiceNome(nome);
		data = codiceAnno(data,sesso);
		comune = Comune.codByNome(persona.getComuneNascita(), pathComuni);
		if(comune == null) throw new DatiNonValidiException();
		System.out.println("______________________________");
		System.out.println(String.format("Cognome %s -> %s", persona.getCognome(),cognome));
		System.out.println(String.format("Nome %s -> %s", persona.getNome(),nome));
		System.out.println(String.format("Data %s -> %s", persona.getDataNascita(),data));
		System.out.println(String.format("Comune %s -> %s",persona.getComuneNascita(),comune));
		return "";
	}
	
	private static boolean isVocale(char x) {
		if(x=='A' || x=='E' || x=='I' || x=='O' || x =='U') return true;
		return false;
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
				// se ci sono pi� di 3 consonanti nel nome salto la 2�
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
	public static String codiceAnno(String nascita,char sesso) throws DatiNonValidiException{
        String codice = "",anno,mese,giorno;
        anno = nascita.substring(2, 4);
        giorno = nascita.substring(8,10);
        if(sesso == 'M' || sesso == 'F') {
        	if(sesso == 'F') {
        		int getG = Integer.parseInt(giorno);
        		getG = getG +40;
        		giorno = ""+getG;
        	}
        }else throw new DatiNonValidiException();
        mese = nascita.substring(5,7);
        switch(mese){
            case "01":
                mese = "A"; break;
            case "02":
                mese = "B"; break;
            case "03":
                mese = "C"; break;
            case "04":
                mese = "D"; break;
            case "05":
                mese = "E"; break;
            case "06":
                mese = "H"; break;
            case "07":
                mese = "L"; break;
            case "08":
                mese = "M"; break;
            case "09":
                mese = "P"; break;
            case "10":
                mese = "R"; break;
            case "11":
                mese = "S"; break;
            case "12":
                mese = "T"; break;
            default: 
               throw new DatiNonValidiException();
        }
        codice = anno+mese+giorno;
        return codice;
    }
}
