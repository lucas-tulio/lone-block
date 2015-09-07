package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Item {
	private String name;
	private Color color;
	private int amount;
	
	public Item() {
		this.amount = 1;
	}
	
	public void increaseAmount() {
		amount++;
	}
	
	public void decreaseAmount() {
		amount--;
	}
	
	public abstract void render(ShapeRenderer sr, float x, float y);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}