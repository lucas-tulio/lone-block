package com.lucasdnd.onepixel.gameplay;

public class Inventory {
	private int size;
	private Item[] content;
	
	public Inventory(int size) {
		this.size = size;
		content = new Item[size];
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Item[] getContent() {
		return content;
	}

	public void setContent(Item[] content) {
		this.content = content;
	}
}
