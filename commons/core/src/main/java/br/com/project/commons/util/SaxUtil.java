package br.com.project.commons.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxUtil extends DefaultHandler {
	
	private Map<String, List<String>> roots = null;
	private List<Object> returnList = null;
	private boolean root = false;
	private boolean node = false;

	public List<Object> read(Object object, Map<String, List<String>> roots) {
		
		try {
			
			SAXParserFactory factory = SAXParserFactory.newInstance(); 
			SAXParser saxParser = factory.newSAXParser();
			InputSource inputSource = null;
			InputStream inputStream = null;
			this.returnList = new ArrayList<Object>();
			this.roots = roots;
			
			if (object instanceof String) {
				inputStream = new ByteArrayInputStream(StringUtil.convertNode(((String)object)).getBytes());  
			} else if (object instanceof InputStream) {
				inputStream = new ByteArrayInputStream(StringUtil.convertNode(StringUtil.convertInputStreamToString(((InputStream)object))).getBytes());			
			} else if (object instanceof File) {
				inputStream = new ByteArrayInputStream(StringUtil.convertNode(FileUtil.getStringContent(((File)object)).toString()).getBytes());
			}	

			inputSource = new InputSource(inputStream); 
			inputSource.setEncoding("UTF-8"); 
			saxParser.parse(inputSource, this); 

		} catch (IOException ex ) { 
			ex.printStackTrace(); 
		} catch (ParserConfigurationException ex) { 
			ex.printStackTrace(); 	
		} catch (SAXException ex) { 
			ex.printStackTrace();
		} 

		return this.returnList;
	}
	

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if(SetUtil.nonEmpty(roots)) {
			for (Entry<String, List<String>> iterable : roots.entrySet()) {

				if(StringUtil.isEqual(qName, iterable.getKey())) {
					root = true;				
					if (iterable.getValue() == null) {
						node = true;					
					}
				}

				if ((iterable.getValue() != null)) {
					for (String string : (ArrayList<String>)iterable.getValue()) {
						if(StringUtil.isEqual(qName, string)) {
							node = true;
						}			
					}				
				}
			}			
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = String.copyValueOf(ch,start,length).trim();
		if(root && node && value.length() > 0) {
			returnList.add(value);			
		}

		if(SetUtil.isEmpty(roots) && value.length() > 0) {
			returnList.add(value);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(SetUtil.nonEmpty(roots)) {
			for (Entry<String, List<String>> iterable : roots.entrySet()) {
				if(StringUtil.isEqual(qName, iterable.getKey())) {
					root = false;
					if (iterable.getValue() == null) {
						node = false;					
					}
				}

				if ((iterable.getValue() != null)) {
					for (String string : (ArrayList<String>)iterable.getValue()) {
						if(StringUtil.isEqual(qName, string)) {
							node = false;
						}			
					}				
				}
			}
		}
	}
}
