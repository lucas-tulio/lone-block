package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.ui.SideBar;

public class TealStone extends Item implements Craftable {
	
	public static final int saveId = 12;
	
	public TealStone() {
		super();
		this.setName("Teal Stone");
		this.setColor(Color.TEAL);
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
		
		RedStone redStone = new RedStone();
		redStone.setAmount(1);
		ingredients.add(redStone);
		
		BlueStone blueStone = new BlueStone();
		blueStone.setAmount(1);
		ingredients.add(blueStone);
		
		return ingredients;
	}
}
