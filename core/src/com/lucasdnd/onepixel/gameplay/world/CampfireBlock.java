package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Campfire;

public class CampfireBlock extends MapObject {
	
	static Color[] colors = {Color.FIREBRICK, Color.RED, Color.ORANGE, Color.YELLOW};
	int currentColor, timer, maxTimer;

	public CampfireBlock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
		maxTimer = 100;
		timer = 0;
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		timer++;
		if (timer % maxTimer == 0) {
			currentColor++;
		}
		sr.setColor(colors[currentColor % colors.length]);
		sr.rect(x, y, OnePixel.pixelSize, OnePixel.pixelSize);
	}

	@Override
	public Object performAction() {
		disposer.dispose(this);
		return new Campfire();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
