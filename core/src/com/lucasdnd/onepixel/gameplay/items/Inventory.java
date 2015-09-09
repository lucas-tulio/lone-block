package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;

public class Inventory {
	
	protected int size;
	private int selectedItem;
	protected ArrayList<InventoryBox> inventoryBoxes;
	private int inventoryRows = 3;
	
	// Replacing items
	private Item aux;
	
	public Inventory(int size) {
		
		this.size = size;
		inventoryBoxes = new ArrayList<InventoryBox>();
		
		final float margin = 20f;
		final float x = ((OnePixel)Gdx.app.getApplicationListener()).getSideBar().getX();
		float height = Gdx.graphics.getHeight();
		
		// Create the inventory boxes (that show on the sidebar)
		for (int i = 0; i < size / inventoryRows; i++) {
			for (int j = 0; j < inventoryRows; j++) {
				
				InventoryBox ib = new InventoryBox(
						x + InventoryBox.SIZE * i + margin,
						height - margin * 17 - InventoryBox.SIZE * j);
				
				inventoryBoxes.add(ib);
			}
		}
	}
	
	public void update() {
		
		// Tooltip control
		boolean isDrawingTooltip = false;
		for (InventoryBox ib : inventoryBoxes) {
			ib.update();
			isDrawingTooltip = ib.isDrawingTooltip();
		}
		
		if (isDrawingTooltip == false) {
			((OnePixel)Gdx.app.getApplicationListener()).getTooltip().hide();
		}
		
		// Clicks
		InventoryBox clickedInventoryBox = null;
		for (InventoryBox ib : inventoryBoxes) {
			if (ib.isMouseOver() && ((OnePixel)Gdx.app.getApplicationListener()).getInputHandler().leftMouseJustClicked) {
				clickedInventoryBox = ib;
				break;
			}
		}
		
		if (clickedInventoryBox != null) {
			aux = inventoryBoxes.get(0).getItem();
			inventoryBoxes.get(0).setItem(clickedInventoryBox.getItem());
			clickedInventoryBox.setItem(aux);
		}
	}
	
	public void render(ShapeRenderer sr) {
		for (InventoryBox ib : inventoryBoxes) {
			ib.render(sr);
		}
	}
	
	/**
	 * Check if any items need to be removed from the list
	 */
	public void checkItems() {
		ArrayList<Item> itemsToRemove = new ArrayList<Item>();
		ArrayList<Item> items = getItems();
		for (Item item : items) {
			if (item != null) {
				if (item.getAmount() == 0) {
					itemsToRemove.add(item);
					inventoryBoxes.get(selectedItem).setItem(null);
				}
			}
		}
		items.removeAll(itemsToRemove);
	}
	
	/**
	 * Add an item to the inventory
	 * @param item
	 * @return
	 */
	public boolean addItem(Item item) {
		
		// If the item is there already, stack
		ArrayList<Item> items = getItems();
		int numItems = 0;
		for (Item i : items) {
			if (i != null) {
				numItems++;
				if (i.getClass() == item.getClass()) {
					i.increaseAmount();
					return true;
				}
			}
		}
		
		// If not, add
		if (numItems < size) {
			InventoryBox ib = findNextEmptyInventoryBox();
			ib.setItem(item);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get all the items inside the Inventory Boxes
	 * @return
	 */
	private ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (InventoryBox ib : inventoryBoxes) {
			items.add(ib.getItem());
		}
		return items;
	}
	
	/**
	 * Loop through the Inventory Boxes and return the next one without any items
	 * @return
	 */
	private InventoryBox findNextEmptyInventoryBox() {
		for (InventoryBox ib : inventoryBoxes) {
			if (ib.getItem() == null) {
				return ib;
			}
		}
		return null;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public InventoryBox getSelectedInventoryBox() {
		return inventoryBoxes.get(selectedItem);
	}
}
