package com.lucasdnd.onepix.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepix.OnePix;

public class Player {
	
	public static final int MAX_STAT_VALUE = 10000;
	private int x, y;
	private Color color;
	private Inventory inventory;
	
	private int health, stamina, food, drink;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.BLACK;
		health = MAX_STAT_VALUE;
		stamina = MAX_STAT_VALUE;
		food = MAX_STAT_VALUE;
		drink = MAX_STAT_VALUE;
		
		inventory = new Inventory(27);
	}

	public void update() {
		
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(x * OnePix.PIXEL_SIZE, y * OnePix.PIXEL_SIZE, OnePix.PIXEL_SIZE, OnePix.PIXEL_SIZE);
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
		stamina--;
		y++;
	}
	
	public void moveDown() {
		stamina--;
		y--;
	}
	
	public void moveRight() {
		stamina--;
		x++;
	}
	
	public void moveLeft() {
		stamina--;
		x--;
	}

	public boolean canMoveUp(World world) {
		return y + 1 < world.getSize() && world.getMap()[x][y+1] == 0;
	}

	public boolean canMoveLeft(World world) {
		return x - 1 >= 0 && world.getMap()[x-1][y] == 0;
	}

	public boolean canMoveDown(World world) {
		return y - 1 >= 0 && world.getMap()[x][y-1] == 0;
	}

	public boolean canMoveRight(World world) {
		return x + 1 < world.getSize() && world.getMap()[x+1][y] == 0;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getDrink() {
		return drink;
	}

	public void setDrink(int drink) {
		this.drink = drink;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
