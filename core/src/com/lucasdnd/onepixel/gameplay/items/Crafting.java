package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.Gdx;
import com.lucasdnd.onepixel.OnePixel;

public class Crafting extends Inventory {

	public Crafting(int size) {
		super(size);
		updateBoxPosition();
	}
	
	private void updateBoxPosition() {
		
		final float margin = 20f;
		final float x = ((OnePixel)Gdx.app.getApplicationListener()).getSideBar().getX();
		float height = Gdx.graphics.getHeight();
		
		int i = 0;
		for (InventoryBox ib : inventoryBoxes) {
			ib.setX(x + InventoryBox.SIZE * (i + 6) + margin);
			ib.setY(height - margin * 15 - InventoryBox.SIZE);
			i++;
		}
	}
}
