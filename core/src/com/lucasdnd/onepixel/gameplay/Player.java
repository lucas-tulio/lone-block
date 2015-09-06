package com.lucasdnd.onepixel.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;

public class Player {
	
	public static final int MAX_STAT_VALUE = 10000;
	private int x, y, direction;
	public final int UP = 0;
	public final int LEFT = 1;
	public final int DOWN = 2;
	public final int RIGHT = 3;
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
		
		faceUp();
		inventory = new Inventory(27);
	}

	public void update() {
		
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(x * OnePixel.PIXEL_SIZE, y * OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
		float directionOffsetX = 0f;
		float directionOffsetY = 0f;
		switch (direction) {
		case UP:
			directionOffsetX = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE + OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case LEFT:
			directionOffsetX = -OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case DOWN:
			directionOffsetX = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = -OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case RIGHT:
			directionOffsetX = OnePixel.PIXEL_SIZE + OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		default:
			break;
		}
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
	
	public void faceUp() {
		direction = UP;
	}
	
	public void faceLeft() {
		direction = LEFT;
	}
	
	public void faceDown() {
		direction = DOWN;
	}
	
	public void faceRight() {
		direction = RIGHT;
	}

	public boolean canMoveUp(World world) {
		faceUp();
		return y + 1 < world.getSize() && world.getMap()[x][y+1] == 0;
	}

	public boolean canMoveLeft(World world) {
		faceLeft();
		return x - 1 >= 0 && world.getMap()[x-1][y] == 0;
	}

	public boolean canMoveDown(World world) {
		faceDown();
		return y - 1 >= 0 && world.getMap()[x][y-1] == 0;
	}

	public boolean canMoveRight(World world) {
		faceRight();
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
