package com.lucasdnd.loneblock.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.loneblock.LoneBlock;
import com.lucasdnd.loneblock.Resources;
import com.lucasdnd.loneblock.gameplay.items.Wood;

public class WoodBlock extends MapObject {
	
	public static final int saveId = 2;
	
	private final static Color color = Resources.Color.wood;

	public WoodBlock(Disposer disposer, int x, int y) {
		super(disposer, x, y);
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, LoneBlock.blockSize, LoneBlock.blockSize);
	}

	@Override
	public Object performAction() {
		disposer.dispose(this);
		return new Wood();
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
