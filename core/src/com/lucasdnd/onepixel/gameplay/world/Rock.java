package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Stone;

public class Rock extends MapObject {
	
	int stone;

	public Rock(Disposer disposer, int x, int y, int z) {
		super(disposer, x, y, z);
		stone = new Random().nextInt(8) + 4;
		color = new Color(0.31f, 0.31f, 0.31f, 1f);
	}
	
	public Rock(Disposer disposer, int x, int y, int z, int amount) {
		super(disposer, x, y, z);
		stone = 1;
		color = new Color(0.31f, 0.31f, 0.31f, 1f);
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
	}

	@Override
	public Object performAction() {
		
		if (stone > 0) {
			stone--;
		
			if (stone == 0) {
				disposer.dispose(this);
			}
			return new Stone();
		}
		
		return null;
	}
}
