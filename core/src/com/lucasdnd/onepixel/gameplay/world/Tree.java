package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.lucasdnd.onepixel.gameplay.items.Wood;

public class Tree extends MapObject {

	int wood;
	Disposer disposer;
	
	public Tree(Disposer disposer, int x, int y, int z) {
		super(x, y, z);
		this.disposer = disposer;
		wood = new Random().nextInt(8) + 4;
	}
	
	@Override
	public Object performAction() {
		if (wood > 0) {
			wood--;
			return actionCallback();
		}
		
		if (wood == 0) {
			disposer.dispose(this);
		}
		
		return null;
	}
	
	@Override
	public Object actionCallback() {
		return new Wood();
	}

}
