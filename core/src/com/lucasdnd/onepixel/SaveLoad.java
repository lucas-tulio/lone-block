package com.lucasdnd.onepixel;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.lucasdnd.onepixel.gameplay.Player;
import com.lucasdnd.onepixel.gameplay.items.Inventory;
import com.lucasdnd.onepixel.gameplay.items.InventoryBox;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.world.MapObject;
import com.lucasdnd.onepixel.gameplay.world.Tree;

public class SaveLoad {
	
	private static final String separator = ";";
	private static final String innerSeparator = ",";
	private static final String lineBreak = "\n";
	
	public static boolean save(int slot) {
		
		OnePixel game = (OnePixel)Gdx.app.getApplicationListener();
		StringBuilder sb = new StringBuilder();
		
		// Get the Save Slot
		FileHandle saveFile;
		switch (slot) {
		case 0:
			saveFile = Resources.get().save1;
			break;
		case 1:
			saveFile = Resources.get().save2;
			break;
		default:
			saveFile = Resources.get().save3;
		}
		
		// Write stuff
		
		// Line 1: playerX, playerY, facing direction, health, cold, hunger, thirst
		Player p = game.getPlayer();
		sb.append("" +
				p.getX() + separator +
				p.getY() + separator +
				p.getDirection() + separator +
				p.getHealth() + separator +
				p.getCold() + separator +
				p.getFood() + separator +
				p.getDrink()
				);
		
		sb.append(lineBreak);
		
		// Line 2: inventory items and their amounts (from left to right, top to bottom),
		// then crafting box items
		// Items' saveId and amounts are separated by innerSeparators
		// Different items are separated by separators
		Inventory inventory = p.getInventory();
		ArrayList<InventoryBox> inventoryBoxes = new ArrayList<InventoryBox>();
		inventoryBoxes.addAll(inventory.getInventoryBoxes());
		inventoryBoxes.addAll(inventory.getCraftingBoxes());
		
		boolean isFirst = true;
		for (InventoryBox ib : inventoryBoxes) {
			
			if (isFirst == false) {
				sb.append(separator);
			}
			isFirst = false;
			
			Item item = ib.getItem();
			if (item == null) {
				sb.append("0" + innerSeparator + "0");
			} else {
				sb.append(item.getSaveId() + innerSeparator + item.getAmount());
			}
		}
		
		sb.append(lineBreak);
		
		// Line 3: time
		sb.append(game.getTimeController().getTicks());
		
		sb.append(lineBreak);
		
		// Line 4: map size
		sb.append(game.getWorld().getSize());
		
		sb.append(lineBreak);
		
		// Map objects
		MapObject[][] mapObjects = game.getWorld().getMapObjects();
		for (int i = 0; i < game.getWorld().getSize(); i++) {
			
			isFirst = true;
			
			for (int j = 0; j < game.getWorld().getSize(); j++) {
				
				if (isFirst == false) {
					sb.append(separator);
				}
				isFirst = false;
				
				if (mapObjects[i][j] == null) {
					sb.append("0");
				} else if (mapObjects[i][j] instanceof Tree) {
					// Tree data:
					// saveId, isGrown, hasFruits, saplings, saplingsToGrow, fruits, hitsToChopDown,
					// growthTicks, fruitTicks, saplingTicks
					Tree t = (Tree)mapObjects[i][j];
					sb.append("" +
							t.getSaveId() + innerSeparator +
							t.isGrown() + innerSeparator +
							t.hasFruits() + innerSeparator +
							t.getSaplings() + innerSeparator +
							t.getSaplingsToGrow() + innerSeparator +
							t.getFruits() + innerSeparator +
							t.getHitsToChopDown() + innerSeparator +
							t.getGrowthTicks() + innerSeparator +
							t.getFruitTicks() + innerSeparator +
							t.getSaplingTicks()
							);
				} else {
					// Any other map object
					sb.append(mapObjects[i][j].getSaveId());
				}
			}
			sb.append(lineBreak);
		}
		
		// Write to file
		try {
			saveFile.writeString(sb.toString(), false);
		} catch (GdxRuntimeException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean load() {
		boolean success = false;
		
		return success;
	}
}
