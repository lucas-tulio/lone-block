package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.ui.SideBar;

public class RedStone extends Item implements Craftable {
	
	public static final int saveId = 8;
	
	public RedStone() {
		super();
		this.setName("Red Stone");
		this.setColor(Resources.Color.redRock);
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
	public int getSaveId() {
		return saveId;
	}

	@Override
	public ArrayList<Item> getIngredients() {
		ArrayList<Item> ingredients = new ArrayList<Item>();
		Stone stone = new Stone();
		stone.setAmount(1);
		ingredients.add(stone);
		return ingredients;
	}

}
