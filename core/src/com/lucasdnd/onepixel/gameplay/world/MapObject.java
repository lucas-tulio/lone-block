package com.lucasdnd.onepixel.gameplay.world;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class MapObject implements Harvestable {
	
	protected Disposer disposer;
	protected int x, y;

	public MapObject(Disposer disposer, int x, int y) {
		this.x = x;
		this.y = y;
		this.disposer = disposer;
	}
	
	public abstract int getSaveId();
	public abstract void update();
	public abstract void render(ShapeRenderer sr, float x, float y);

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
