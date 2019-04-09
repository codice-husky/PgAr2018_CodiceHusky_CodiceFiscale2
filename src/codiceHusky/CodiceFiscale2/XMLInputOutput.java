package codiceHusky.CodiceFiscale2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLInputOutput {
	private File file;
	private XMLInputFactory factory;
    private XMLEventReader eventReader;
    
    public XMLInputOutput(String filePath) {
    	
    	file = new File(filePath);
    	factory = XMLInputFactory.newInstance();
    	try {
			eventReader = factory.createXMLEventReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
    }
    
    public Persona readNextPersona() {
    	try {
    		
    		while(eventReader.hasNext()) {
    			XMLEvent xmlEvent = eventReader.nextEvent();
    			
    			if (xmlEvent.isStartElement())
                {
                    StartElement startElement = xmlEvent.asStartElement();
                    
                    if(startElement.getName().getLocalPart().equals("persona")) {
                        int idPersona = Integer.parseInt(startElement.getAttributeByName(new QName("id")).getValue());
                        Persona persona = new Persona();
                        persona.setId(idPersona);
                        
                        
                        
                        while(eventReader.hasNext()) {
                        	String popo = startElement.getName().getLocalPart();
                        	switch(popo) {
                        	case "nome":
                        		persona.setNome(xmlEvent.asCharacters().getData());
                        		break;
                        	case "cognome":
                        		persona.setCognome(xmlEvent.asCharacters().getData());
                        		break;
                        	case "sesso":
                        		persona.setSesso(xmlEvent.asCharacters().getData().charAt(0));
                        		break;
                        	case "comune_nascita":
                        		persona.setComuneNascita(xmlEvent.asCharacters().getData());
                        		break;
                        	case "data_nascita":
                        		persona.setDataNascita(xmlEvent.asCharacters().getData());
                        		break;
                        	}
                        	xmlEvent = eventReader.nextEvent();
                        }
                        
                        if (xmlEvent.isEndElement()) {
                        	EndElement endElement = xmlEvent.asEndElement();
                        	if(endElement.getName().getLocalPart().equals("persona")) return persona;
                        }
                    }
                }
    		}
    	} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return null;
    }

}
