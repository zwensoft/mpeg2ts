package mts;

import java.io.StringReader;


import mts.api.SITableFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;



public class XMLParsingTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mts.api.psi.PAT table = SITableFactory.createPAT(15, 0xABC);
		mts.api.psi.PATProgram program1 = SITableFactory.createPATProgram(0xC0, 0xD0);
		mts.api.psi.PATProgram program2 = SITableFactory.createPATProgram(0xE0, 0xF0);
		table.addProgram(program1);
		table.addProgram(program2);
		
		table.toXMLString(0);
		
		try {
			XMLReader parser = XMLReaderFactory.createXMLReader();
			org.xml.sax.ContentHandler handler 
		       = new TestHandler();
			parser.setContentHandler(handler);
			InputSource inputSrc = new InputSource(new StringReader(table.toXMLString(0)));
			parser.parse(inputSrc);
		} catch (Exception e) { e.printStackTrace(); } 
	}
}

class TestHandler extends DefaultHandler
{
	public void startElement(String namespaceURI, String localName,
			String qualifiedName, Attributes atts) throws SAXException {
		System.out.println("startElement: "+namespaceURI+", "+localName+", "+
				qualifiedName+", "+atts);
	}

	public void endElement(String namespaceURI, String localName,
			String qualifiedName) throws SAXException {
		System.out.println("endElement: "+namespaceURI+", "+localName+", "+
				qualifiedName);
	}

	public void characters(char[] ch, int start, int length)
		throws SAXException {
		System.out.println("characters: "+ch+", "+start+", "+
				length);
	}
}
