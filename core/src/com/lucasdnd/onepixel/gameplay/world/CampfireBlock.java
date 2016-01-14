package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.Campfire;

public class CampfireBlock extends MapObject {
	
	public static final int saveId = 6;
	
	static Color[] colors = {Resources.Color.campfire1, Resources.Color.campfire2, Resources.Color.campfire3, Resources.Color.campfire4};
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
		sr.rect(x, y, OnePixel.blockSize, OnePixel.blockSize);
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

	@Override
	public int getSaveId() {
		return saveId;
	}

}
