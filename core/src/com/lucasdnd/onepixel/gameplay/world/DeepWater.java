package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.StatRecovery;

public class DeepWater extends MapObject {
	
	public static final int saveId = 5;
	
	final static Color color = new Color(0.11f, 0.19f, 0.74f, 1f);

	public DeepWater(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.pixelSize, OnePixel.pixelSize);
	}

	@Override
	public Object performAction() {
		Resources.get().randomDrinkingSound().play(0.7f);
		return new StatRecovery(0, 1, 0, 80);
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
