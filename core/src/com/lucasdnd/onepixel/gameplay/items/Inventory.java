package com.lucasdnd.onepixel.gameplay.items;

public class Inventory {
	private int size;
	private Item[] content;
	
	public Inventory(int size) {
		this.size = size;
		content = new Item[size];
	}
	
	public boolean addItem(Item item) {
		for (int i = 0; i < content.length; i++) {
			if (content[i] == null) {
				content[i] = item;
				return true;
			}
		}
		
		return false;
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
