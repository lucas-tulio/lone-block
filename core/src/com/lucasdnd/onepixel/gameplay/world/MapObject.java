package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.Color;

public abstract class MapObject implements Harvestable {
	
	Disposer disposer;
	int x, y, z;
	Color color;

	public MapObject(Disposer disposer, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.disposer = disposer;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
