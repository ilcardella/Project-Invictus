package it.cardella.projectinvictus.data;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

    private Bitmap image;
	private String title;
	private String description;
	private String link;
	private String content;

	public Item() {
        setImage(null);
		setTitle(null);
		setDescription(null);
		setLink(null);
		setContent(null);
	}

    public Bitmap getImage(){ return image; }

    public void setImage(Bitmap image) { this.image = image; }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}

	

}
