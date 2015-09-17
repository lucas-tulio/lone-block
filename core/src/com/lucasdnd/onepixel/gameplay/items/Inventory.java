package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.ui.SideBar;

public class Inventory {
	
	protected int size;
	int selectedItem;
	protected ArrayList<InventoryBox> inventoryBoxes;
	protected ArrayList<InventoryBox> craftingBoxes;
	protected ArrayList<InventoryBox> craftingResultBoxes;
	Craftable craftable;	// Item being made
	final int inventoryRows = 3;
	final int numCraftingBoxes = 3;
	
	// Swapping items
	Item itemOnMouse;
	FontUtils font;
	
	public Inventory(int size) {
		
		this.size = size;
		inventoryBoxes = new ArrayList<InventoryBox>();
		craftingBoxes = new ArrayList<InventoryBox>();
		craftingResultBoxes = new ArrayList<InventoryBox>();
		font = new FontUtils();
		
		final float margin = 20f;
		final float x = ((OnePixel)Gdx.app.getApplicationListener()).getSideBar().getX();
		float height = Gdx.graphics.getHeight();
		
		// Create the inventory boxes
		for (int j = 0; j < inventoryRows; j++) {
			for (int i = 0; i < size / inventoryRows; i++) {
				InventoryBox ib = new InventoryBox(
						x + InventoryBox.SIZE * i + margin,
						height - margin * 17 - InventoryBox.SIZE * j);
				inventoryBoxes.add(ib);
			}
		}
		
		// Create the Crafting boxes
		for (int i = 0; i < numCraftingBoxes; i++) {
			InventoryBox ib = new InventoryBox(x + InventoryBox.SIZE * (i + 6) + margin,
						height - margin * 15 - InventoryBox.SIZE);
			craftingBoxes.add(ib);
		}
		
		// Create the Crafting Result boxes
		for (int i = 0; i < numCraftingBoxes; i++) {
			InventoryBox ib = new InventoryBox(x + InventoryBox.SIZE * (i + 6) + margin,
					height - margin * 19 - InventoryBox.SIZE);
			craftingResultBoxes.add(ib);
		}
	}
	
	public void update() {
		
		// TODO: THIS SUCKS, we should create an int in InventoryBox to indicate which type of box it is 
		// All Inventory Boxes together
		ArrayList<InventoryBox> allBoxes = new ArrayList<InventoryBox>();
		allBoxes.addAll(inventoryBoxes);
		allBoxes.addAll(craftingBoxes);
		allBoxes.addAll(craftingResultBoxes);
		// All Inventory Boxes together, except the crafting result ones
		ArrayList<InventoryBox> allBoxesExceptCraftingResult = new ArrayList<InventoryBox>();
		allBoxesExceptCraftingResult.addAll(inventoryBoxes);
		allBoxesExceptCraftingResult.addAll(craftingBoxes);
		
		// Box update
		boolean isDrawingTooltip = false;
		for (InventoryBox ib : allBoxes) {
			ib.update();
			isDrawingTooltip = ib.isDrawingTooltip();
		}
		
		if (isDrawingTooltip == false) {
			((OnePixel)Gdx.app.getApplicationListener()).getTooltip().hide();
		}
		
		// Crafting
		checkCraftingRecipes();
		
		// Get clicks
		boolean leftClick = ((OnePixel)Gdx.app.getApplicationListener()).getInputHandler().leftMouseJustClicked;
		boolean rightClick = ((OnePixel)Gdx.app.getApplicationListener()).getInputHandler().rightMouseJustClicked;

		if (leftClick == false && rightClick == false) {
			return;
		}
		
		// Moving items update
		if (itemOnMouse == null) {
			
			// Pick Item
			
			for (InventoryBox ib : allBoxes) {
				if (ib.getItem() != null && ib.isMouseOver()) {
					if (leftClick) {	// Pick up the whole stack
						leftClick = false;
						if (craftingResultBoxes.contains(ib)) { // Taking up crafting result
							consumeIngredients((Craftable)ib.getItem()); // Trust me, compiler :)
						}
						itemOnMouse = ib.getItem();
						ib.setItem(null);
					} else if (rightClick) {	// Split the stack in two and pick one half
						rightClick = false;
						try {
							if (ib.getItem().getAmount() == 1) {
								itemOnMouse = ib.getItem();
								ib.setItem(null);
							} else {
								itemOnMouse = ib.getItem().getClass().newInstance();
								int originalAmount = ib.getItem().getAmount();
								int half = originalAmount / 2;
								int otherHalf = originalAmount - half;
								ib.getItem().setAmount(otherHalf);
								if (ib.getItem().getAmount() == 0) {
									ib.setItem(null);
								}
								itemOnMouse.setAmount(half);
							}
						} catch (InstantiationException e) {
							e.printStackTrace();
							Gdx.app.exit();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							Gdx.app.exit();
						}
					}
					break;
				}
			}
			
		} else {
			
			// Drop item
			
			if (leftClick && Gdx.input.getX() < Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH) {
				itemOnMouse = null;
			}
			
			// Place Item
			
			for (InventoryBox ib : allBoxesExceptCraftingResult) {
				
				if (ib.isMouseOver()) {
					
					// Place item in an empty slot
					
					if (ib.getItem() == null) {
						if (leftClick) {
							leftClick = false;			// Whole Stack (left click)
							ib.setItem(itemOnMouse);
							itemOnMouse = null;
							break;
						} else if (rightClick) {
							rightClick = false;			// One item (right click)
							try {
								Item newItem = itemOnMouse.getClass().newInstance();
								newItem.setAmount(1);
								ib.setItem(newItem);
								itemOnMouse.decreaseAmount();
								if (itemOnMouse.getAmount() <= 0) {
									itemOnMouse = null;
								}
								break;
							} catch (InstantiationException e) {
								e.printStackTrace();
								Gdx.app.exit();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
								Gdx.app.exit();
							}
						}
					} else {
						
						if (ib.getItem().getClass() == itemOnMouse.getClass()) {
							
							// Stack item
							
							if (leftClick) {
								leftClick = false;	// Left click: all of them
								ib.getItem().increaseAmountBy(itemOnMouse.getAmount());
								itemOnMouse = null;
								break;
							} else if (rightClick) {
								rightClick = false;	// Right click: only one
								ib.getItem().increaseAmount();
								itemOnMouse.decreaseAmount();
								if (itemOnMouse.getAmount() <= 0) {
									itemOnMouse = null;
								}
								break;
							}
						} else {
							
							if (leftClick || rightClick) {
								leftClick = false;
								rightClick = false;
								
								// Swap
								
								Item aux = ib.getItem();
								ib.setItem(itemOnMouse);
								itemOnMouse = aux;
								isDrawingTooltip = false;
								break;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Check if the player made any items in the Crafting boxes
	 */
	private void checkCraftingRecipes() {
		
		// Empty the crafting result boxes
		for (InventoryBox ib : craftingResultBoxes) {
			ib.setItem(null);
		}
		
		// Check what the player is trying to create, then show that item there
		// Testing this way to do it
		Craftable campfire = new Campfire();
		if (isCraftableMatch(campfire)) {
			craftingResultBoxes.get(0).setItem((Item)campfire);
		}
		
		// Add other craftables here
		
	}
	
	/**
	 * Consume the crafting ingredients
	 * @param craftable
	 */
	private void consumeIngredients(Craftable craftable) {
		
		for (Item item : craftable.getIngredients()) {
			for (InventoryBox ib : craftingBoxes) {
				if (ib.getItem() != null) {
					if (ib.getItem().getClass() == item.getClass() && ib.getItem().getAmount() >= item.getAmount()) {
						ib.getItem().decreaseAmountBy(item.getAmount());
						break;
					}
				}
			}
		}
		
		// Clear the crafting boxes if consumed all items
		for (InventoryBox consumedIb : craftingBoxes) {
			if (consumedIb.getItem() != null && consumedIb.getItem().getAmount() == 0) {
				consumedIb.setItem(null);
			}
		}
	}
	
	/**
	 * Check if the player is trying to create this item
	 * @return
	 */
	private boolean isCraftableMatch(Craftable craftable) {
		
		ArrayList<Item> ingredients = craftable.getIngredients();
		boolean[] ingredientsOk = new boolean[ingredients.size()];
		int i = 0;
		for (Item item : ingredients) {
			// Check if it's in the box
			for (InventoryBox ib : craftingBoxes) {
				if (ib.getItem() != null && ib.getItem().getClass() == item.getClass()) {
					// Check if it's the right amount
					if (ib.getItem().getAmount() >= item.getAmount()) {
						ingredientsOk[i] = true;
						break;
					}
				}
			}
			i++;
		}
		
		for (i = 0; i < ingredientsOk.length; i++) {
			if (ingredientsOk[i] == false) {
				return false;
			}
		}
		
		return true;
	}
	
	public void render(ShapeRenderer sr) {
		
		// Render all boxes
		for (InventoryBox ib : inventoryBoxes) {
			ib.render(sr);
		}
		for (InventoryBox ib : craftingBoxes) {
			ib.render(sr);
		}
		for (InventoryBox ib : craftingResultBoxes) {
			ib.render(sr);
		}
		
		// Render the item on the mouse
		if (itemOnMouse != null) {
			int x = Gdx.input.getX();
			int y = Gdx.graphics.getHeight() - Gdx.input.getY();
			itemOnMouse.render(sr, x, y);
			if (itemOnMouse.getAmount() > 1) {
				font.drawWhiteFont("" + itemOnMouse.getAmount(), x + 38f, y - 18f, false, Align.right, 0);
			}
		}
	}
	
	/**
	 * Check if any boxes became empty, then clear it
	 */
	public void clearEmptyBoxes() {
		ArrayList<Item> items = getItems();
		for (Item item : items) {
			if (item != null) {
				if (item.getAmount() == 0) {
					inventoryBoxes.get(selectedItem).setItem(null);
				}
			}
		}
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
					checkCraftingRecipes();					
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
		for (InventoryBox ib : craftingBoxes) {
			items.add(ib.getItem());
		}
		for (InventoryBox ib : craftingResultBoxes) {
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

	public ArrayList<InventoryBox> getInventoryBoxes() {
		return inventoryBoxes;
	}

	public ArrayList<InventoryBox> getCraftingBoxes() {
		return craftingBoxes;
	}
}
