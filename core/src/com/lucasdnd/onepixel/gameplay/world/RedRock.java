package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.RedStone;

public class RedRock extends MapObject {
	
	public static final int saveId = 8;
	
	private static final Color color = Color.RED;

	public RedRock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.blockSize, OnePixel.blockSize);
	}

	@Override
	public Object performAction() {
		Resources.get().miningSound.play(0.4f);
		disposer.dispose(this);
		return new RedStone();
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
