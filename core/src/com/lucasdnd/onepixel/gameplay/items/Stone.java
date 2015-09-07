package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.ui.SideBar;

public class Stone extends Item {
	
	final float offsetY = -32f;
	
	public Stone() {
		super();
		this.setName("Stone");
		this.setColor(Color.GRAY);
	}
	
	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.begin(ShapeType.Filled);
		sr.setColor(this.getColor());
		sr.rect(x + SideBar.lineWeight,
				y - SideBar.lineWeight + offsetY,
				SideBar.INVENTORY_BOX_SIZE - SideBar.lineWeight,
				SideBar.INVENTORY_BOX_SIZE - SideBar.lineWeight);
		sr.end();
	}

}