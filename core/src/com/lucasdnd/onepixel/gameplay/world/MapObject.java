package com.lucasdnd.onepixel.gameplay.world;

public abstract class MapObject implements Usable{
	
	Disposer disposer;
	int x, y, z;

	public MapObject(Disposer disposer, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.disposer = disposer;
	}
}
