package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.TimeController;
import com.lucasdnd.onepixel.gameplay.items.Fruit;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class Tree extends MapObject {

	final static Color color = new Color(0f, 0.3f, 0f, 1f);
	int wood;
	
	boolean hasFruits = false;
	int fruit = -1;
	long ticks = 0;
	long fruitTicks = 0;
	
	public Tree(Disposer disposer, int x, int y) {
		super(disposer, x, y);
		Random r = new Random();
		wood = r.nextInt(4) + 2;
		
		// 1/20 of the trees have fruits
		hasFruits = r.nextInt(20) == 0;
		if (hasFruits) {
			fruitTicks = TimeController.ONE_DAY * (r.nextInt(2) + 1) + (r.nextInt((int)TimeController.ONE_DAY));
			refreshFruits();
		}
	}
	
	private void refreshFruits() {
		fruit = new Random().nextInt(4) + 1;
	}
	
	public void update() {
		if (hasFruits) {
			ticks++;
			if (ticks % fruitTicks == 0) {
				refreshFruits();
			}
		}
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		sr.setColor(color);
		sr.rect(x, y, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
		
		if (fruit > 0) {
			sr.setColor(Color.RED);
			sr.rect(x + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
					y + OnePixel.PIXEL_SIZE - OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 8f,
					OnePixel.PIXEL_SIZE / 4f,
					OnePixel.PIXEL_SIZE / 4f);
		}
	}
	
	@Override
	public Object performAction() {
		
		if (fruit > 0) {
			fruit--;
			return new Fruit();
		}
		
		if (wood > 0) {
			wood--;
		
			if (wood == 0) {
				disposer.dispose(this);
			}
			return new Wood();
		}
		
		return null;
	}

}
