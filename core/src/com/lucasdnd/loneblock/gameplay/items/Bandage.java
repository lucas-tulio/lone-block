package com.lucasdnd.loneblock.gameplay.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.loneblock.Resources;
import com.lucasdnd.loneblock.ui.SideBar;

public class Bandage extends Item implements Usable, Craftable {
	
	public static final int saveId = 7;
	
	public Bandage() {
		this.setName("Bandage");
		this.setColor(Resources.Color.bandage);
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
	public StatRecovery use() {
		return new StatRecovery(1000, 0, 0, 0);
	}

	@Override
	public int getSaveId() {
		return saveId;
	}

	@Override
	public ArrayList<Item> getIngredients() {
		ArrayList<Item> ingredients = new ArrayList<Item>();
		Cotton cotton = new Cotton();
		cotton.setAmount(5);
		ingredients.add(cotton);
		return ingredients;
	}

}
