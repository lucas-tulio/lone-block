package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

public class Inventory {
	private int size;
	private ArrayList<Item> content;
	
	public Inventory(int size) {
		this.size = size;
		content = new ArrayList<Item>();
	}
	
	/**
	 * Check if any items need to be removed from the list
	 */
	public void checkItems() {
		ArrayList<Item> itemsToRemove = new ArrayList<Item>();
		for (Item i : content) {
			if (i.getAmount() == 0) {
				itemsToRemove.add(i);
			}
		}
		content.removeAll(itemsToRemove);
	}
	
	public boolean addItem(Item item) {
		// If the item is there already, stack
		for (Item i : content) {
			if (i.getClass() == item.getClass()) {
				i.increaseAmount();
				return true;
			}
		}
		
		// If not, add
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
