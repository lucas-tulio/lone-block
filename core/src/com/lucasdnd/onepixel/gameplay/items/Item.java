package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Item {
	
	private String name;
	private Color color;
	private int amount;
	protected final float offsetY = -32f;
	
	public Item() {
		this.amount = 1;
	}
	
	public abstract int getSaveId();
	public abstract void render(ShapeRenderer sr, float x, float y);
	
	public void increaseAmountBy(int amount) {
		this.amount += amount;
	}
	
	public void decreaseAmountBy(int amount) {
		this.amount -= amount;
	}
	
	public void increaseAmount() {
		amount++;
	}
	
	public void decreaseAmount() {
		amount--;
	}

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
