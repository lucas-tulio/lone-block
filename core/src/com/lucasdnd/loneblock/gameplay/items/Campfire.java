package com.lucasdnd.loneblock.gameplay.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.loneblock.Resources;
import com.lucasdnd.loneblock.ui.SideBar;

public class Campfire extends Item implements Craftable {
	
	public static final int saveId = 5;
	
	public Campfire() {
		this.setName("Campfire");
		this.setColor(Resources.Color.campfire1);
	}
	
	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.begin(ShapeType.Filled);
		sr.setColor(this.getColor());
		sr.rect(x + SideBar.lineWeight,
				y - SideBar.lineWeight + offsetY,
				InventoryBox.SIZE - SideBar.lineWeight,
				InventoryBox.SIZE - SideBar.lineWeight);
		sr.end();
	}

	@Override
	public ArrayList<Item> getIngredients() {
		ArrayList<Item> ingredients = new ArrayList<Item>();
		Wood wood = new Wood();
		wood.setAmount(3);
		ingredients.add(wood);
		return ingredients;
	}

	@Override
	public int getSaveId() {
		return saveId;
	}

}
