package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;

public class Rock extends MapObject {
	
	int stone;

	public Rock(Disposer disposer, int x, int y, int z) {
		super(disposer, x, y, z);
		color = new Color(0.31f, 0.31f, 0.31f, 1f);
	}

	@Override
	public Object performAction() {
		return null;
	}

	@Override
	public Object actionCallback() {
		return null;
	}

}
