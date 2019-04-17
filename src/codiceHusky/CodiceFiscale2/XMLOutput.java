package codiceHusky.CodiceFiscale2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class XMLOutput {
	XMLOutputFactory outputFactory;
	XMLStreamWriter xmlWriter;
	XMLStreamWriter xmlIdentingWriter;

    public XMLOutput(String path, boolean isBuffer) {
    	try {
        	outputFactory = XMLOutputFactory.newInstance();
        	xmlWriter = outputFactory.createXMLStreamWriter(new FileOutputStream(path), "utf-8");
        	xmlIdentingWriter = new IndentingXMLStreamWriter(xmlWriter);
			xmlIdentingWriter.writeStartDocument("utf-8", "1.0");
			
			if(!isBuffer) xmlIdentingWriter.writeStartElement("output");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void openPersone(int num) {
		try {
			xmlIdentingWriter.writeStartElement("persone");
			xmlIdentingWriter.writeAttribute("numero", Integer.toString(num));
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void openCodici() {
    	try {
    		xmlIdentingWriter.writeStartElement("codici");
    	} catch (XMLStreamException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    public void openCodInvalidi(int num) {
    	try {
    		xmlIdentingWriter.writeStartElement("invalidi");
    		xmlIdentingWriter.writeAttribute("numero", Integer.toString(num));
    	} catch (XMLStreamException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    public void closeOnce() {
    	try {
			xmlIdentingWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void openCodSpaiati(int num) {
		try {
			xmlIdentingWriter.writeStartElement("spaiati");
			xmlIdentingWriter.writeAttribute("numero", Integer.toString(num));
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void writePersona(Persona persona) {
    	try {
    		xmlIdentingWriter.writeStartElement("persona");
			xmlIdentingWriter.writeAttribute("id", Integer.toString(persona.getId()));
			writeElement("nome", persona.getNome());
	    	writeElement("cognome", persona.getCognome());
	    	writeElement("sesso", Character.toString(persona.getSesso()));
	    	writeElement("comune_nascita", persona.getComuneNascita());
	    	writeElement("data_nascita", persona.getDataNascita());
	    	writeElement("codice_fiscale", persona.getCodiceFiscale());
	    	xmlIdentingWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void writeElement(String name, String value) {
    	try {
    		xmlIdentingWriter.writeStartElement(name);
			xmlIdentingWriter.writeCharacters(value);
			xmlIdentingWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void closeAll() {
    	try {
			xmlIdentingWriter.writeEndElement();
			xmlIdentingWriter.writeEndDocument();
			xmlIdentingWriter.close();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void closePersona() {
    	try {
			xmlIdentingWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
