package it.cardella.projectinvictus.util;

import org.xml.sax.helpers.DefaultHandler;

import it.cardella.projectinvictus.data.Item;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLHandler extends DefaultHandler {

	// List of items parsed
	private List<Item> rssItems;

	private Item currentItem;

	private boolean parsingTitle = false;
	private boolean parsingLink = false;
	private boolean parsingDescription = false;
	private boolean parsingContent = false;

	private StringBuilder strCharacters;
	
	public XMLHandler() {
		rssItems = new ArrayList<Item>();
	}

	// We have an access method which returns a list of items that are read from
	// the RSS feed. This method will be called when parsing is done.
	public List<Item> getItems() {
		return rssItems;
	}

	// The StartElement method creates an empty Item object when an item start
	// tag is being processed. When a title or link tag are being processed
	// appropriate indicators are set to true.
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		strCharacters = new StringBuilder();
		
		if ("item".equals(qName)) {
			currentItem = new Item();
		} else if ("title".equals(qName)) {
			parsingTitle = true;
		} else if ("link".equals(qName)) {
			parsingLink = true;
		} else if ("description".equals(qName)) {
			parsingDescription = true;
		} else if ("content:encoded".equals(qName)) {
			parsingContent = true;
		}
	}

	// The EndElement method adds the current Item to the list when a closing
	// item tag is processed. It sets appropriate indicators to false - when
	// title and link closing tags are processed
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if(parsingTitle){
			if(currentItem != null){
			currentItem.setTitle(strCharacters.toString());
			parsingTitle = false;
			}
		}else if(parsingDescription){
			if(currentItem != null){
			currentItem.setDescription(strCharacters.toString());
			parsingDescription = false;
			}
		}else if(parsingLink){
			if(currentItem != null){
			currentItem.setLink(strCharacters.toString());
			parsingLink = false;
			}
		}else if(parsingContent){
			if(currentItem != null){
			currentItem.setContent(strCharacters.toString());
			parsingContent = false;
			}
		}
		
		if ("item".equals(qName)) {
			rssItems.add(currentItem);
			currentItem = null;
		}

	}

	// Characters method fills current Item object with data when title and
	// link tag content is being processed
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		for (int i=start; i<start+length; i++) {
            strCharacters.append(ch[i]);
        }

	}

} // End of class
