package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class WoodBlock extends MapObject {

	public WoodBlock(Disposer disposer, int x, int y, int z) {
		super(disposer, x, y, z);
		color = Color.BROWN;
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
	}

	@Override
	public Object performAction() {
		disposer.dispose(this);
		return actionCallback(new Wood());
	}

	@Override
	public Object actionCallback(Object result) {
		return result;
	}

}
