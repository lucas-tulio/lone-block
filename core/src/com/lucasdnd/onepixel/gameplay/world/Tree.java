package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class Tree extends MapObject {

	int wood;
	
	public Tree(Disposer disposer, int x, int y, int z) {
		super(disposer, x, y, z);
		wood = new Random().nextInt(8) + 4;
		color = new Color(0f, 0.3f, 0f, 1f);
	}
	
	@Override
	public Object performAction() {
		
		if (wood > 0) {
			wood--;
		
			if (wood == 0) {
				disposer.dispose(this);
			}
			return actionCallback();
		}
		
		return null;
	}
	
	@Override
	public Object actionCallback() {
		return new Wood();
	}

}
