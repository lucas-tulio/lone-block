package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Campfire;

public class CampfireBlock extends MapObject {
	
	final static Color color = Color.FIREBRICK;

	public CampfireBlock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
	}

	@Override
	public Object performAction() {
		disposer.dispose(this);
		return new Campfire();
	}

}
