package com.lucasdnd.loneblock.gameplay.items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.loneblock.Resources;
import com.lucasdnd.loneblock.ui.SideBar;

public class Wood extends Item {
	
	public static final int saveId = 3;
	
	public Wood() {
		super();
		this.setName("Wood");
		this.setColor(Resources.Color.wood);
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
