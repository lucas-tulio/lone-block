package com.lucasdnd.onepixel.gameplay.world;

public abstract class MapObject implements Usable{
	int x, y, z;

	public MapObject(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
