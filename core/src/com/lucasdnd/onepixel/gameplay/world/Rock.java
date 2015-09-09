package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Stone;

public class Rock extends MapObject {
	
	static final Color color = new Color(0.31f, 0.31f, 0.31f, 1f);

	public Rock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	public Rock(Disposer disposer, int x, int y, int amount) {
		super(disposer, x, y);
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
	}

	@Override
	public Object performAction() {
		disposer.dispose(this);
		return new Stone();
	}
}
