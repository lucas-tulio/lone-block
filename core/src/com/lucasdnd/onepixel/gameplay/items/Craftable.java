package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

public interface Craftable {
	/**
	 * This method should be static but Java doesn't support static methods in Interfaces. If we happen to have
	 * too many Craftable items in the game at once, memory use will be fun to watch :)
	 * @return
	 */
	public ArrayList<Item> getIngredients();
}
