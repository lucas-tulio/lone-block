package com.lucasdnd.loneblock.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.loneblock.LoneBlock;
import com.lucasdnd.loneblock.Resources;
import com.lucasdnd.loneblock.gameplay.items.YellowStone;

public class YellowRock extends MapObject {
	
	public static final int saveId = 11;
	
	private static final Color color = Resources.Color.yellowRock;

	public YellowRock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	@Override
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, LoneBlock.blockSize, LoneBlock.blockSize);
	}

	@Override
	public Object performAction() {
		Resources.get().miningSound.play(0.4f);
		disposer.dispose(this);
		return new YellowStone();
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
