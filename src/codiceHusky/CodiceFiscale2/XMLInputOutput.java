package codiceHusky.CodiceFiscale2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

public class XMLInputOutput {
	private InputStream xmlInputStream;
	private XMLInputFactory2 xmlInputFactory;
    private XMLStreamReader2 xmlStreamReader;
    int lastEventType;
    
    public XMLInputOutput(String xmlFileName) {
    	try {
    		xmlInputStream = new FileInputStream(xmlFileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        xmlInputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
		try {
			xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlInputStream);
			lastEventType = xmlStreamReader.getEventType();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Persona readNextPersona() {
    	int idPersona = 0;
    	String nomePersona = null, cognomePersona = null, comuneNascita = null, dataNascita = null;
    	char sessoPersona = 0;
    	try {
    		if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    		String read;
    		if(lastEventType == XMLEvent.START_ELEMENT || lastEventType == XMLEvent.END_ELEMENT) {
    			read = xmlStreamReader.getName().toString();
    		} else  {
    			read = xmlStreamReader.getText().toString();
    		}
			do {
				if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    			if(lastEventType == XMLEvent.START_ELEMENT || lastEventType == XMLEvent.END_ELEMENT) {
        			read = xmlStreamReader.getName().toString();
        		} else  {
        			read = xmlStreamReader.getText().toString();
        		}
    			
    			if(lastEventType!=XMLEvent.END_ELEMENT) {
    				switch(read) {
    				case "persona":
    					idPersona = xmlStreamReader.getAttributeAsInt(0);
    					if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    					break;
    				case "nome":
    					if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    					nomePersona = xmlStreamReader.getText().toString();
    					break;
    				case "cognome":
    					if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    					cognomePersona = xmlStreamReader.getText().toString();
    					break;
    				case "sesso":
    					if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    					sessoPersona = xmlStreamReader.getText().toString().charAt(0);
    					break;
    				case "comune_nascita":
    					if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    					comuneNascita = xmlStreamReader.getText().toString();
    					break;
    				case "data_nascita":
    					if(xmlStreamReader.hasNext()) lastEventType = xmlStreamReader.next();
    					dataNascita = xmlStreamReader.getText().toString();
    					break;
    				default:
    					
    					break;
    				}
    			}
			} while(!(lastEventType == XMLEvent.END_ELEMENT && read.equals("persona")));
			
			return new Persona(idPersona, nomePersona, cognomePersona, sessoPersona, comuneNascita, dataNascita);
			
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
    	
    	/*while(xmlStreamReader.hasNext()){
			int eventType = xmlStreamReader.next();
			switch (eventType) {
			case XMLEvent.START_ELEMENT:
	        System.out.print("<"+xmlStreamReader.getName().toString()+">");
		        break;
		    case XMLEvent.CHARACTERS:
		        System.out.print(xmlStreamReader.getText());
		    	break;
		    case XMLEvent.END_ELEMENT:
		        System.out.println("</"+xmlStreamReader.getName().toString()+">");
		        break;
		    default:
		        //do nothing
		        break;
			    }
			}*/
    }

}
