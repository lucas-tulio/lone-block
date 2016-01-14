package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.Resources;

public class Fruit extends Item implements Usable {
	
	public static final int saveId = 2;
	
	private final static float margin = InventoryBox.SIZE / 3f;
	
	public Fruit() {
		this.setName("Fruit");
		this.setColor(Resources.Color.fruit);
	}
	
	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.begin(ShapeType.Filled);
		sr.setColor(this.getColor());
		sr.rect(x + margin + 2f, y - margin - margin + 2f, margin, margin);
		sr.end();
	}

	@Override
	public StatRecovery use() {
		return new StatRecovery(0, 20, 100, 10);
	}

	@Override
	public int getSaveId() {
		return saveId;
	}
}
