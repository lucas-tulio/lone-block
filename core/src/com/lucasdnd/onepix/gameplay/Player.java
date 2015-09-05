package com.lucasdnd.onepix.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Player {
	
	private int x, y;
	private Color color;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.BLACK;
	}

	public void update() {
		
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(x, y, 1f, 1f);
		sr.end();
	}

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

	public void moveUp() {
		y++;
	}
	
	public void moveDown() {
		y--;
	}
	
	public void moveRight() {
		x++;
	}
	
	public void moveLeft() {
		x--;
	}

	public boolean canMoveUp(World world) {
		return world.getMap()[x][y+1] == 0;
	}

	public boolean canMoveLeft(World world) {
		return world.getMap()[x-1][y] == 0;
	}

	public boolean canMoveDown(World world) {
		return world.getMap()[x][y-1] == 0;
	}

	public boolean canMoveRight(World world) {
		return world.getMap()[x+1][y] == 0;
	}
	
}
