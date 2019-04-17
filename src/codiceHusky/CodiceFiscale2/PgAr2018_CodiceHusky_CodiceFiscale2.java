package codiceHusky.CodiceFiscale2;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class PgAr2018_CodiceHusky_CodiceFiscale2 {

	//XPath
	private static final String XPATH_SPAIATI_NUMERO = "//spaiati/@numero";
	private static final String XPATH_INVALIDI_NUMERO = "//invalidi/@numero";
	private static final String XPATH_PERSONE_NUMERO = "//persone/@numero";
	//Path file I/O
	private static final String PATH_CODICI_FISCALI_XML = "xml/codiciFiscali.xml";
	private static final String PATH_COMUNI_XML = "xml/comuni.xml";
	private static final String PATH_INPUT_PERSONE_XML = "xml/inputPersone.xml";
	private static final String PATH_CF_BUFFER_XML = "xml/CFBuffer.xml";
	private static final String PATH_CODICI_PERSONE_XML = "xml/codiciPersone.xml";

	public static void main(String[] args) {
		XMLOutput output = new XMLOutput(PATH_CODICI_PERSONE_XML, false);	//Inizializzazioni varie
		XMLOutput outputCF = new XMLOutput(PATH_CF_BUFFER_XML, true);
		Scanner sc = new Scanner(System.in);
		Persona persona;
		String codice;
		String codDaControllare = null;
		int nCodiciInvalidi = 0;
		int nCodiciSpaiati = 0;
		int nPersone = 0;
		XMLInput xml = new XMLInput(PATH_INPUT_PERSONE_XML);
		String cmnd = "";
		String nome, cognome,sesso;
		boolean end = false;
		output.openPersone(0);
		outputCF.openCodici();
		do {
			persona = xml.readNextPersona();
			if(persona!=null) {
				try {
				codice = calcolaCodice(persona,PATH_COMUNI_XML);
				}catch(DatiNonValidiException ex) {
					codice =  ex.getError();
				}
				persona.setCodiceFiscale(codice);
				output.writePersona(persona);
				outputCF.writeElement("codice", codice);
				nPersone++;
			}
		} while(persona!=null);
		
		output.closePersona();
		outputCF.closeAll();
		
		XMLInput xmlCodici = new XMLInput(PATH_CODICI_FISCALI_XML);
		output.openCodici();
		output.openCodInvalidi(0);
		
		do {
			try {
				codDaControllare = xmlCodici.readNextCF();
				if(codDaControllare!=null) {
					if(!controlloCodice(codDaControllare)) {
						nCodiciInvalidi++;
						output.writeElement("codice", codDaControllare);
					}
				}
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while(codDaControllare!=null);
		output.closeOnce();
		
		output.openCodSpaiati(0);
		nCodiciSpaiati = createCodiciSpaiati(output);
		output.closeOnce();
		output.closeOnce();
		output.closeAll();
		
		//System.out.println(persona.toString());
		
		caricaNumeroElementi(nPersone, nCodiciInvalidi, nCodiciSpaiati);

	}
	
	private static void caricaNumeroElementi(int persone, int invalidi, int spaiati) {
		String inputFile = PATH_CODICI_PERSONE_XML;
		String outputFile = PATH_CODICI_PERSONE_XML;
		
		try {
			Document doc = DocumentBuilderFactory.newInstance()
		            .newDocumentBuilder().parse(new InputSource(inputFile));

		XPath xpath = XPathFactory.newInstance().newXPath();
		Node nodo = (Node)xpath.evaluate(XPATH_PERSONE_NUMERO,
		                                          doc, XPathConstants.NODE);
		nodo.setNodeValue(Integer.toString(persone));
		
		xpath = XPathFactory.newInstance().newXPath();
		nodo = (Node)xpath.evaluate(XPATH_INVALIDI_NUMERO,
		                                          doc, XPathConstants.NODE);
		nodo.setNodeValue(Integer.toString(invalidi));
		
		xpath = XPathFactory.newInstance().newXPath();
		nodo = (Node)xpath.evaluate(XPATH_SPAIATI_NUMERO,
		                                          doc, XPathConstants.NODE);
		nodo.setNodeValue(Integer.toString(spaiati));

		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(new DOMSource(doc), new StreamResult(new File(outputFile)));
		} catch(Exception e) {
			
		}
		
	    
	}
	
	
	private static int createCodiciSpaiati(XMLOutput output) {
		XMLInput xmlCodici = new XMLInput(PATH_CODICI_FISCALI_XML);
		String codDaControllare = null;
		int codiciSpaiati = 0;
		do {
			try {
				codDaControllare = xmlCodici.readNextCF();
				if(codDaControllare!=null) {
					if(controlloCodice(codDaControllare)) {
						XMLInput xmlCodiciGenerati = new XMLInput(PATH_CF_BUFFER_XML);
						String codiceGenerato = null;
						boolean codValido = false;
						do {
							codiceGenerato = xmlCodiciGenerati.readNextCF();
								if(codDaControllare.equals(codiceGenerato)) {
									codValido = true;
								
							}
						} while(codiceGenerato!=null);
						
						if(!codValido) {
							output.writeElement("codice", codDaControllare);
							codiciSpaiati++;
						}
					}
				}
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		} while(codDaControllare!=null);
		
		return codiciSpaiati;
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
			String codice = "";
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
			String semiCodice = cognome+nome+data+comune;
			codice = semiCodice+getControllo(semiCodice);
			if(!verificaEsistenzaCodice(codice)) throw new DatiNonValidiException();
		return codice;
	}
	
	private static boolean verificaEsistenzaCodice(String codice) {
		XMLInput xmlCodici = new XMLInput(PATH_CODICI_FISCALI_XML);
		boolean trovato = false;
		String codEstratto = null;
		do {
			try {
				codEstratto = xmlCodici.readNextCF();
				if(codEstratto!=null) {
					if(codice.equalsIgnoreCase(codEstratto)) trovato = true;
				}
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		} while(codEstratto!=null);
		
		return trovato;
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
			if(!isVocale(x) && x!=(char)39 && x!=' ') {
				memo = memo.concat(""+x);
				length ++;
			}
			if(length == 3) break;
		}
		if(length != 3) {
			for(int i = 0; i<cognome.length();i++) {
				char x = cognome.charAt(i);
				if(isVocale(x) && x!=(char)39 && x!=' ') {
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
			if(!isVocale(x) && x!=(char)39 && x!=' ') {
				consonanti++;
			}
			if(consonanti == 4) break;
		}
		
		int n_consonante=0;
		for(int i = 0; i<nome.length();i++) {
			char x = nome.charAt(i);
			if(!isVocale(x) && x!=(char)39 && x!=' ') {
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
				if(isVocale(x) && x!=(char)39 && x!=' ') {
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
	/**
	 *Metodo che calcola il codice di controllo basandosi  sul resto della stringa
	 * @param semiCodice        E' il CF, senza il codice di controllo
	 * @return finale           il codice di controllo
	 */
	 private static String getControllo(String semiCodice) {
	        //--------------------------------------
	                //il codice di controllo associa ad ogni lettera/numero un valore siccome conosco
	                //quanti sono abbiamo optato per un array
	                char caratteri[]= {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	        int sommaDispari[]= {1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
	        int sommaPari[]= {0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
	        //----------------------------------
	                String finale = "";
	        int somma = 0;
	        for(int i=1;i<=semiCodice.length();i++){
	            char lettera = semiCodice.charAt(i-1); /*i-1 perchè il charAt
	            parte da 0 mentre il conteggio delle lettere parte da 1 */
	            for(int k=0;k<caratteri.length;k++) {
	                if(lettera == caratteri[k]) {
	                    if(i%2!=0) { //prima lettera, terza lettera ecc.
	                        somma +=sommaDispari[k];
	                       
	                    }else { //seconda lettera, quarta lettera ecc.
	                        somma +=sommaPari[k];
	                    }
	                }
	            }
	        }
	        finale = ""+((char)((somma%26)+65)); //si basa sul codice ASCII
	        return finale;
	    }
	
	
	/**
     * Questo metodo data una stringa(ovvero il codice fiscale) dice se
     * è corretto o meno controllando le varie parti che compongono il
     * codice fiscale, se almeno uno dei casi è sbagliato/da errore
     * allora tutto il codice è errato
     * @param codice    E' il codice fiscale da verificare
     * @return      true se è corretto, altrimenti false
     * */
    public static boolean controlloCodice(String codice) {
                char mesi[]={'A','B','C','D','E','H','L','M','P','R','S','T'};//array dei mesi
                int giorni[]={31,28,31,30,31,30,31,31,30,31,30,31}; //numero giorni per mese
                boolean trov = false; //viene utilizzato per verificare che il mese sia tra
                                      //uno dei valori accettabili
        if(codice.length()!=16) {//se la lunghezza non è 16 allora è sbagliato           
            return false;
        }else {
            int memo;
            if(!lettereOrdine(codice.substring(0,3))) return false; // controllo sul cognome
            if(!lettereOrdine(codice.substring(3,6))) return false; // controllo sul nome
            try {//se da un errore allora non è un numero
                memo = Integer.parseInt(codice.substring(6,8)); //controllo sul anno
            }catch(NumberFormatException e) {
                return false;
            }
            if(!letteraCorretta(codice.charAt(8)))return false; //controllo sul mese
            try {//se da un errore allora non è un numero
                memo = Integer.parseInt(codice.substring(9,11)); //controllo sul giorno
            }catch(NumberFormatException e) {
                return false;
            }
                        if(memo > 71) return false; //in un mese al massimo ci sono 31 giorni(71 per le donne)
                        else{
                           char mese= codice.charAt(8); //recupera il mese dal CF
                           for(int i=0;i<12;i++){
                               if(mese == mesi[i]){ //se esiste un accoppiamento mese-lettera
                                   trov = true;
                                   if((memo>giorni[i] && memo<41)||(memo>(giorni[i]+40))) return false;
                                   break;//se è un numero compreso tra il numero di giorni del mese per i
                               }//maschi e 41(minimo per le donne) o oltre il numero dei giorni del mese
                                //+40(donne) che è uguale ad avere un 32 marzo allora è sbagliato
                           }
                           if(!trov) return false; //se trov è falso il mese nel CF è sbagliato
                        }
            if(!letteraCorretta(codice.charAt(11))) return false; //lettera del comune
            try {//se da errore non è un numero
                memo = Integer.parseInt(codice.substring(12,15)); //numero del comune
            }catch(NumberFormatException e) {
                return false;
            }
            if(!letteraCorretta(codice.charAt(15))) return false; //codice di controllo
            //se arriva qui allora il codice di controllo è una lettera, allora bisogna verificare
                        String subStringa = codice.substring(0,15);
                        String controllo = getControllo(subStringa);
                        if(!controllo.equals(""+codice.charAt(15))) return false; //se quello teorico
                        //e quello effettivo sono diversi allora è sbagliato
                        return true;
        }
    }
    
    /**
    * Controlla se una lettera è sia maiuscola che una lettera in se
    * @return       true se è così,false altrimenti
    */
    public static boolean letteraCorretta(char x) {
		if(Character.isLetter(x) && Character.isUpperCase(x)) return true;
		return false;
	}
	
    /**
     * Questo metodo viene utilizzato per verificare il corretto ordinamento
     * delle lettere del cognome o del nome nel codice fiscale, ovvero
     * prima le consonanti poi le vocali ed infine le eventuali x
         * @param sub       Sono le 3 lettere che compongono il nome/cognome nel CF
         * @return          true se è corretto, false se qualcosa è errato
     * */
    public static boolean lettereOrdine(String sub) {
    	boolean corretto = true;
        for(int i=0;i<3;i++){ //cicla sulle 3 lettere che compongono il cognome/nome
            char x = sub.charAt(i); //prende una lettera
            if(Character.isLetter(x) && Character.isUpperCase(x)){ //controlla che
                            //si solo una lettera e che sia maiuscola
                if((x == 'A'|| x == 'E'|| x == 'I'|| x == 'O'|| x == 'U') && i!=2){
                    //qui controlla se dopo una vocale ci siano solo altre vocali o x
                    for(int j = i+1;j<3;j++){
                        char y = sub.charAt(j);
                        if(y != 'A'&& y != 'E'&& y != 'I'&& y != 'O'&& y != 'U' && y!='X'){
                            corretto =  false;
                            break;
                        }
                    }
                }
                if(!corretto) break;
            }else{
                corretto =  false;
                break;
            }
        }
        return corretto;
    }
}
