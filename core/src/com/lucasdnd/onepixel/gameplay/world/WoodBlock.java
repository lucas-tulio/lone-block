package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class WoodBlock extends MapObject {

	public WoodBlock(Disposer disposer, int x, int y, int z) {
		super(disposer, x, y, z);
		color = Color.BROWN;
	}

	@Override
	public Object performAction() {
		disposer.dispose(this);
		return actionCallback();
	}

	@Override
	public Object actionCallback() {
		return new Wood();
	}

}
