package it.cardella.projectinvictus.data;

import java.io.Serializable;

public class Channel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Items items;
	private String title;
	private String link;
	private String description;
	private String lastBuildDate;
	private String language;

	public Channel() {
		setItems(null);
		setTitle(null);
		setLink(null);
		setDescription(null);
		setLastBuildDate(null);
		setLanguage(null);
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	

}
