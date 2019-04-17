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
	String path;

	/**
	 * Genera un oggetto XMLOutput capace di scrivere un nuovo documento XML
	 * @param path Indirizzo di destinazione del file
	 * @param isBuffer True se si tratta di un file di buffer, false altrimenti
	 */
    public XMLOutput(String path, boolean isBuffer) {
    	this.path = path;
    	try {
        	outputFactory = XMLOutputFactory.newInstance();
        	xmlWriter = outputFactory.createXMLStreamWriter(new FileOutputStream(path), "utf-8");
        	xmlIdentingWriter = new IndentingXMLStreamWriter(xmlWriter);
			xmlIdentingWriter.writeStartDocument("utf-8", "1.0");
			
			if(!isBuffer) xmlIdentingWriter.writeStartElement("output");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Apre un nuovo nodo di tipo Persone
     * @param num Numero di persone
     */
    public void openPersone(int num) {
		try {
			xmlIdentingWriter.writeStartElement("persone");
			xmlIdentingWriter.writeAttribute("numero", Integer.toString(num));
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Apre un nuovo nodo di tipo Codici. Da utilizzare con Invalidi e Spaiati
     */
    public void openCodici() {
    	try {
    		xmlIdentingWriter.writeStartElement("codici");
    	} catch (XMLStreamException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Apre un nuovo nodo di tipo Invalidi
     * @param num Numero di codici invalidi
     */
    public void openCodInvalidi(int num) {
    	try {
    		xmlIdentingWriter.writeStartElement("invalidi");
    		xmlIdentingWriter.writeAttribute("numero", Integer.toString(num));
    	} catch (XMLStreamException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Chiude un singolo nodo
     */
    public void closeOnce() {
    	try {
			xmlIdentingWriter.writeEndElement();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Apre un nuovo nodo di tipo Spaiati
     * @param num Numero di codici spaiati
     */
    public void openCodSpaiati(int num) {
		try {
			xmlIdentingWriter.writeStartElement("spaiati");
			xmlIdentingWriter.writeAttribute("numero", Integer.toString(num));
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Scrive le informazioni contenute all'interno di un oggetto Persona
     * @param persona Persona da scrivere
     */
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
			e.printStackTrace();
		}
    }
    
    /**
     * Scrive un elemento generico
     * @param name Nome del nodo
     * @param value Valore del nodo
     */
    public void writeElement(String name, String value) {
    	try {
    		xmlIdentingWriter.writeStartElement(name);
			xmlIdentingWriter.writeCharacters(value);
			xmlIdentingWriter.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Chiude i nodi rimanenti e, successivamente, il documento
     */
    public void closeAll() {
    	try {
			xmlIdentingWriter.writeEndElement();
			xmlIdentingWriter.writeEndDocument();
			xmlIdentingWriter.close();
			xmlWriter.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * Chiude l'intero file e svuota il buffer di scrittura
     */
    public void closeFile() {
    	try {
			xmlIdentingWriter.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
    }
}
