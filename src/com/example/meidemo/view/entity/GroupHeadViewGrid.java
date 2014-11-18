package com.example.meidemo.view.entity;

import android.graphics.drawable.Drawable;

public class GroupHeadViewGrid {
	private String id;
	private String title;
	private String iconUrl;
	private Drawable icon;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "GroupHeadViewGrid [id=" + id + ", title=" + title
				+ ", iconUrl=" + iconUrl + ", icon=" + icon + "]";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	
}
