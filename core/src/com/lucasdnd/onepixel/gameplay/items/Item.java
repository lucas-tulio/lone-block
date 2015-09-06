package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.graphics.Color;

public class Item {
	private String name;
	private Color color;
	
	public Item() {
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
}
