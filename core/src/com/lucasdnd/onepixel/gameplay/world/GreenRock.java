package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.GreenStone;

public class GreenRock extends MapObject {

	public static final int saveId = 9;
	
	private static final Color color = Resources.Color.greenRock;

	public GreenRock(Disposer disposer, int x, int y) {
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
		return new GreenStone();
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
