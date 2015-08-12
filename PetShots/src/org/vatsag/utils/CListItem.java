package org.vatsag.utils;

import android.graphics.Bitmap;

/*
 * Item class
 * */
public class CListItem {
	
	String itemText;
	Bitmap image;
	
	public CListItem(String title,Bitmap image){
		this.itemText = title;
		this.image = image;
	}
	
	public String getItemText() {
		return itemText;
	}
	public void setItemText(String itemText) {
		this.itemText = itemText;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
}
