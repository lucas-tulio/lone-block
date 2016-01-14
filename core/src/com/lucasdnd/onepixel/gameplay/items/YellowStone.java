package com.lucasdnd.onepixel.gameplay.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.ui.SideBar;

public class YellowStone extends Item implements Craftable {
	
	public static final int saveId = 11;
	
	public YellowStone() {
		super();
		this.setName("Yellow Stone");
		this.setColor(Resources.Color.yellowRock);
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
		
		GreenStone greenStone = new GreenStone();
		greenStone.setAmount(1);
		ingredients.add(greenStone);
		
		return ingredients;
	}
}
