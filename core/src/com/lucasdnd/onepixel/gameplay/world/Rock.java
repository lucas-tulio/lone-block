package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.lucasdnd.onepixel.gameplay.items.Stone;

public class Rock extends MapObject {
	
	int stone;

	public Rock(Disposer disposer, int x, int y, int z) {
		super(disposer, x, y, z);
		stone = new Random().nextInt(16) + 8;
		color = new Color(0.31f, 0.31f, 0.31f, 1f);
	}

	@Override
	public Object performAction() {
		
		if (stone > 0) {
			stone--;
		
			if (stone == 0) {
				disposer.dispose(this);
			}
			return actionCallback();
		}
		
		return null;
	}

	@Override
	public Object actionCallback() {
		return new Stone();
	}

}
