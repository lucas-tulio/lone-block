package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.StatRecovery;

public class Water extends MapObject {
	
	public static final int saveId = 4;
	
	final static Color color = new Color(0.15f, 0.29f, 0.92f, 1f);
	
	public Water(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.pixelSize, OnePixel.pixelSize);
	}
	
	@Override
	public Object performAction() {
		Resources.get().randomDrinkingSound().play(0.7f);
		return new StatRecovery(0, 1, 0, 150);
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
