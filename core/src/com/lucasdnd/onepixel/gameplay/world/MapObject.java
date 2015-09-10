package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class MapObject implements Harvestable {
	
	Disposer disposer;
	int x, y;

	public MapObject(Disposer disposer, int x, int y) {
		this.x = x;
		this.y = y;
		this.disposer = disposer;
	}
	
	public abstract void render(ShapeRenderer sr, float x, float y);
}
