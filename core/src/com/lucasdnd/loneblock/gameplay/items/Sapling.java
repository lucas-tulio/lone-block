package com.lucasdnd.loneblock.gameplay.items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.loneblock.Resources;

public class Sapling extends Item {
	
	public static final int saveId = 1;
	
	private final static float margin = InventoryBox.SIZE / 3f;
	
	public Sapling() {
		this.setName("Sapling");
		this.setColor(Resources.Color.sapling);
	}

	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.begin(ShapeType.Filled);
		sr.setColor(this.getColor());
		sr.rect(x + margin + 2f, y - margin - margin + 2f, margin, margin);
		sr.end();
	}

	@Override
	public int getSaveId() {
		return saveId;
	}

}
