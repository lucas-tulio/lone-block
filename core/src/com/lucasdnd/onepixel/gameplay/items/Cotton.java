package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.Resources;

public class Cotton extends Item {
	
	public static final int saveId = 6;
	
	private final static float margin = InventoryBox.SIZE / 3f;
	
	public Cotton() {
		this.setName("Cotton");
		this.setColor(Resources.Color.cotton);
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
