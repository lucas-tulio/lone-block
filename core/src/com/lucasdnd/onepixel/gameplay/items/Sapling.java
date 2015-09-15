package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Sapling extends Item {
	
	private final static float margin = InventoryBox.SIZE / 3f;
	
	public Sapling() {
		this.setName("Sapling");
		this.setColor(new Color(0f, 0.3f, 0f, 1f));
	}

	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.begin(ShapeType.Filled);
		sr.setColor(this.getColor());
		sr.rect(x + margin + 2f, y - margin - margin + 2f, margin, margin);
		sr.end();
	}

}
