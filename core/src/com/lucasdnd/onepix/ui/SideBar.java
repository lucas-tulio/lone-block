package com.lucasdnd.onepix.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepix.FontUtils;
import com.lucasdnd.onepix.OnePix;
import com.lucasdnd.onepix.gameplay.Player;

public class SideBar {
	
	int x, y, width;
	int margin = 20;
	
	FontUtils font;
	
	public SideBar(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		font = new FontUtils();
	}
	
	public void render(ShapeRenderer sr) {
		
		float height = Gdx.graphics.getHeight();
		
		// Black background
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(x, y, width, height);
		sr.end();
		
		// Status bars
		Player p = ((OnePix)Gdx.app.getApplicationListener()).getPlayer();
		drawBarFill(sr, x + margin, height - margin,  width - margin * 2, p.getHealth());
		drawBarFill(sr, x + margin, height - margin * 5,  width - margin * 2, p.getStamina());
		drawBarFill(sr, x + margin, height - margin * 9, width - margin * 2, p.getFood());
		drawBarFill(sr, x + margin, height - margin * 13, width - margin * 2, p.getDrink());
		
		drawBarFrame(sr, x + margin, height - margin, width - margin * 2, margin);
		drawBarFrame(sr, x + margin, height - margin * 5, width - margin * 2, margin);
		drawBarFrame(sr, x + margin, height - margin * 9, width - margin * 2, margin);
		drawBarFrame(sr, x + margin, height - margin * 13, width - margin * 2, margin);
		
		// UI Text
		font.drawWhiteFont("Health",  x + margin, height - margin * 3, true);
		font.drawWhiteFont("Stamina", x + margin, height - margin * 7, true);
		font.drawWhiteFont("Hunger",  x + margin, height - margin * 11, true);
		font.drawWhiteFont("Thirst",  x + margin, height - margin * 15, true);
	}
	
	private void drawBarFrame(ShapeRenderer sr, float x, float y, float width, float height) {
		final float lineWeight = 4f;
		final float lineHeight = margin * 1.8f;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(Gdx.graphics.getWidth() - margin, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x, y, lineWidth, lineWeight);
		
		// Bottom
		sr.rect(x, y - lineHeight, lineWidth, lineWeight); 
		
		sr.end();
	}
	
	private void drawBarFill(ShapeRenderer sr, float x, float y, float width, int value) {
		final float lineWeight = 4f;
		final float lineHeight = margin * 1.8f;
		final float lineWidth = width + lineWeight;
		float lineValue = lineWidth * (value / 1000) - lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.LIGHT_GRAY);
		sr.rect(x + lineWeight, y - lineHeight, lineValue, lineHeight);
		sr.end();
	}
	
	public int getWidth() {
		return width;
	}
}
