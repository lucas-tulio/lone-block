package com.lucasdnd.onepixel.gameplay.world;

import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.items.Wood;

/**
 * Give it an item, receive a block of that item
 * @author lucasdnd
 *
 */
public class Exchanger {
	public static MapObject exchange(Item item, World world, int x, int y, int z) {
		if (item instanceof Wood) {
			return new WoodBlock(world, x, y, z);
		}
		
		return null;
	}
}
