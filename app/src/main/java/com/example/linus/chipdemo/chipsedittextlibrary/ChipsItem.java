package com.example.linus.chipdemo.chipsedittextlibrary;

public class ChipsItem {

	private String title;
	private int imageid;
	private boolean selected = false;
	public ChipsItem(){
		
	}
	public ChipsItem(String title,int imageId, boolean selected){
		this.title = title;
		this.imageid = imageId;
		this.selected = selected;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
