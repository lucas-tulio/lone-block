package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.StatRecovery;

public class Water extends MapObject {
	
	int depth;
	
	public Water(Disposer disposer, int x, int y, int z, int depth) {
		super(disposer, x, y, z);
		this.depth = depth;
		if (depth > 10) {
			color = new Color(0.15f, 0.29f, 0.92f, 1f);
		} else {
			color = new Color(0.11f, 0.19f, 0.74f, 1f);
		}
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
	}
	
	@Override
	public Object performAction() {
		Resources.get().randomDrinkingSound().play(0.7f);
		return new StatRecovery(0, 1, 0, 80);
	}
}
