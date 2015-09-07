package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.ui.SideBar;

public class Fruit extends Item implements Usable {
	
	public Fruit() {
		this.setName("Fruit");
		this.setColor(Color.RED);
	}
	
	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.begin(ShapeType.Filled);
		sr.setColor(this.getColor());
		float margin = SideBar.INVENTORY_BOX_SIZE / 3f;
		sr.rect(x + margin + 2f, y - margin - margin + 2f, margin, margin);
		sr.end();
	}

	@Override
	public StatRecovery use() {
		return useCallback();
	}

	@Override
	public StatRecovery useCallback() {
		return new StatRecovery(0, 0, 50, 10);
	}
}