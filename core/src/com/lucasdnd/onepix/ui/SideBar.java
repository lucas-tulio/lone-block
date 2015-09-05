package com.lucasdnd.onepix.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class SideBar {
	int width;
	
	public SideBar(int width) {
		this.width = width;
	}
	
	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(Gdx.graphics.getWidth() - width, 0f, width, Gdx.graphics.getHeight());
		sr.end();
	}
	
	public int getWidth() {
		return width;
	}
}
