package it.cardella.projectinvictus.util;

import it.cardella.projectinvictus.data.Item;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

public class RssReader {

	/**
	 * Get RSS items. This method will be called to get the parsing process
	 * result.
	 * 
	 * @return
	 */
	public static List<Item> getItems(InputStream is) {
		List<Item> items = null;
		try {
			// create a XMLReader from SAXParser
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
					.getXMLReader();
			// create a XMLHandler
			XMLHandler saxHandler = new XMLHandler();
			// store handler in XMLReader
			xmlReader.setContentHandler(saxHandler);
			// the process starts
			xmlReader.parse(new InputSource(is));
			// get the `Item list`
			items = saxHandler.getItems();

		} catch (Exception ex) {
			Log.d("XML", "RssReader: parse() failed");
			ex.printStackTrace();
		}

		// return Item list
		return items;
	}

}
