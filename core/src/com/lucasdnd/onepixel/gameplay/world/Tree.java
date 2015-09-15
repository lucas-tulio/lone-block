package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.TimeController;
import com.lucasdnd.onepixel.gameplay.items.Fruit;
import com.lucasdnd.onepixel.gameplay.items.Sapling;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class Tree extends MapObject {

	final static Color color = new Color(0f, 0.3f, 0f, 1f);
	final static Color saplingColor = new Color(0.3f, 0.8f, 0.3f, 1f);
	int wood;
	
	boolean isGrown;
	boolean hasFruits = false;
	boolean hasSapling = true;
	
	int fruit;
	long ticks, fruitTicks;
	long growth;
	long maxGrowth = 100;
	
	public Tree(Disposer disposer, int x, int y, boolean isGrown) {
		super(disposer, x, y);
		Random r = new Random();
		
		// Grown or sapling
		this.isGrown = isGrown;
		if (isGrown == false) {
			hasSapling = true;
			wood = 0;
			fruit = 0;
		} else {
			// 1/20 of the trees have fruits
			refreshWood();
			hasFruits = r.nextInt(20) == 0;
			if (hasFruits) {
				calculateMaxFruitTicks();
				refreshFruits();
			} else {
				fruit = 0;
			}
		}
	}
	
	private void refreshFruits() {
		fruit = new Random().nextInt(4) + 1;
	}
	
	private void refreshWood() {
		wood = new Random().nextInt(4) + 2;
	}
	
	private void calculateMaxFruitTicks() {
		Random r = new Random();
		fruitTicks = TimeController.ONE_DAY * (r.nextInt(2) + 1) + (r.nextInt((int)TimeController.ONE_DAY));
	}
	
	public void update() {
		
		if (isGrown == false) {
			// Sapling growth
			growth++;
			if (growth >= maxGrowth) {
				isGrown = true;
				hasFruits = new Random().nextInt(2) == 0;
				if (hasFruits) {
					refreshFruits();
					fruitTicks = 100;
//					calculateMaxFruitTicks();
				}
				refreshWood();
			}
		} else {
			// Fruit renewal
			if (hasFruits) {
				ticks++;
				if (ticks % fruitTicks == 0) {
					refreshFruits();
				}
			}
		}
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		
		if (isGrown) {
			
			// Tree
			sr.setColor(color);
			sr.rect(x, y, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
			
			if (hasSapling && fruit > 0) {

				// Fruits and Sapling
					
				sr.setColor(saplingColor);
				sr.rect(x + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 3f,
						y + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
						OnePixel.PIXEL_SIZE / 4f,
						OnePixel.PIXEL_SIZE / 4f);
				
				sr.setColor(Color.RED);
				sr.rect(x + OnePixel.PIXEL_SIZE / 8f,
						y + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
						OnePixel.PIXEL_SIZE / 4f,
						OnePixel.PIXEL_SIZE / 4f);
			
			} else if (hasSapling && fruit <= 0) {
				
				// Sapling only
				
				sr.setColor(saplingColor);
				sr.rect(x + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
						y + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
						OnePixel.PIXEL_SIZE / 4f,
						OnePixel.PIXEL_SIZE / 4f);
			
			} else if (hasSapling == false && fruit > 0) {
				
				// Fruits only
				
				sr.setColor(Color.RED);
				sr.rect(x + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
						y + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
						OnePixel.PIXEL_SIZE / 4f,
						OnePixel.PIXEL_SIZE / 4f);
			}
			
		} else {
			
			// Growing sapling
			
			sr.setColor(saplingColor);
			sr.rect(x + OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f,
					y + OnePixel.PIXEL_SIZE / 4f,
					OnePixel.PIXEL_SIZE / 2f,
					OnePixel.PIXEL_SIZE / 2f);
			
		}
	}
	
	@Override
	public Object performAction() {
		
		if (hasSapling) {
			
			hasSapling = false;
			Resources.get().randomLeavesSound().play(0.4f);
			
			if (isGrown == false) {
				disposer.dispose(this);
			}
			
			return new Sapling();
		}
		
		if (fruit > 0) {
			fruit--;
			Resources.get().randomLeavesSound().play(0.4f);
			return new Fruit();
		}
		
		if (wood > 0) {
			wood--;
			Resources.get().woodcuttingSound.play(0.3f);
		
			if (wood == 0) {
				disposer.dispose(this);
				return new Wood();
			}
		}
		
		return null;
	}

}
