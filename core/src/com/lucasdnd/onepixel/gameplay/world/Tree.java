package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Fruit;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class Tree extends MapObject {

	int wood;
	int fruit = -1;
	
	public Tree(Disposer disposer, int x, int y, int z) {
		super(disposer, x, y, z);
		Random r = new Random();
		wood = r.nextInt(8) + 4;
		color = new Color(0f, 0.3f, 0f, 1f);
		
		// 1/20 of the trees have fruits
		if (r.nextInt(20) == 0) {
			fruit = r.nextInt(4) + 1;
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
