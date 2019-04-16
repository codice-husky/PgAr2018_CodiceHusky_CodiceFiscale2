package codiceHusky.CodiceFiscale2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLOutput {
	XMLOutputFactory outputFactory;
	XMLStreamWriter xmlWriter;

    public XMLOutput(String path) {
    	try {
        	outputFactory = XMLOutputFactory.newInstance();
        	xmlWriter = outputFactory.createXMLStreamWriter(new FileOutputStream(path), "utf-8");
			xmlWriter.writeStartDocument("utf-8", "1.0");
			
			xmlWriter.writeStartElement("output");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void writeNumPersone(int num) {
		try {
			xmlWriter.writeStartElement("persone");
			xmlWriter.writeAttribute("numero", Integer.toString(num));
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void writePersona(Persona persona) {
    	try {
    		xmlWriter.writeStartElement("persona");
			xmlWriter.writeAttribute("id", Integer.toString(persona.getId()));
			writeElement("nome", persona.getNome());
	    	writeElement("cognome", persona.getCognome());
	    	writeElement("sesso", Character.toString(persona.getSesso()));
	    	writeElement("comune_nascita", persona.getComuneNascita());
	    	writeElement("data_nascita", persona.getDataNascita());
	    	writeElement("codice_fiscale", persona.getCodiceFiscale());
	    	xmlWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void writeElement(String name, String value) {
    	try {
    		xmlWriter.writeStartElement(name);
			xmlWriter.writeCharacters(value);
			xmlWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void close() {
    	try {
			xmlWriter.writeEndElement();
			xmlWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
