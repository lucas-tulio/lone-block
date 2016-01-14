package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.ui.SideBar;

public class Stone extends Item {
	
	public static final int saveId = 4;
	
	public Stone() {
		super();
		this.setName("Stone");
		this.setColor(Resources.Color.rock);
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

}
