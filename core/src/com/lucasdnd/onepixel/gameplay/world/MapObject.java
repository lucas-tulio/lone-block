package com.lucasdnd.onepixel.gameplay.world;

public abstract class MapObject implements Harvestable {
	
	Disposer disposer;
	int x, y;

	public MapObject(Disposer disposer, int x, int y) {
		this.x = x;
		this.y = y;
		this.disposer = disposer;
	}
}
