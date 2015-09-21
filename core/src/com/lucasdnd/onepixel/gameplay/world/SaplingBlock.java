package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.Sapling;

public class SaplingBlock extends MapObject {

	public static final int saveId = 1;
	
	public SaplingBlock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}

	@Override
	public Object performAction() {
		Resources.get().randomLeavesSound().play(0.4f);
		return new Sapling();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(Tree.color);
		sr.rect(x, y, OnePixel.blockSize, OnePixel.blockSize);
	}

	@Override
	public int getSaveId() {
		return saveId;
	}

}
