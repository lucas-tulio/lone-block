package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

public class Inventory {
	private int size;
	private ArrayList<Item> content;
	
	public Inventory(int size) {
		this.size = size;
		content = new ArrayList<Item>();
	}
	
	public boolean addItem(Item item) {
		if (content.size() < size) {
			content.add(item);
			return true;
		}
		
		return false;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Item> getContent() {
		return content;
	}

	public void setContent(ArrayList<Item> content) {
		this.content = content;
	}
}
