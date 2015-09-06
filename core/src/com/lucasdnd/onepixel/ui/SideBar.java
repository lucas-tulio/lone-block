package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.Player;

public class SideBar {
	
	int x, y, width;
	float margin = 20f;
	
	// Status bar attributes
	float barHeight;
	float barWidth;
	
	// Inventory attributes
	int inventoryRows = 3;
	float inventoryBoxSize = margin * 2;
	
	FontUtils font;
	
	public SideBar(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		barHeight = margin * 1.8f;
		barWidth = width - margin * 2;
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
		Player player = ((OnePixel)Gdx.app.getApplicationListener()).getPlayer();
		
		drawRectFill(sr, x + margin, height - margin,       barWidth, barHeight, player.getHealth());
		drawRectFill(sr, x + margin, height - margin * 5,   barWidth, barHeight, player.getStamina());
		drawRectFill(sr, x + margin, height - margin * 9,   barWidth, barHeight, player.getFood());
		drawRectFill(sr, x + margin, height - margin * 13,  barWidth, barHeight, player.getDrink());
		
		drawRectFrame(sr, x + margin, height - margin,      barWidth, barHeight);
		drawRectFrame(sr, x + margin, height - margin * 5,  barWidth, barHeight);
		drawRectFrame(sr, x + margin, height - margin * 9,  barWidth, barHeight);
		drawRectFrame(sr, x + margin, height - margin * 13, barWidth, barHeight);
		
		// UI Text
		font.drawWhiteFont("Health",  x + margin, height - margin * 3, true);
		font.drawWhiteFont("Stamina", x + margin, height - margin * 7, true);
		font.drawWhiteFont("Hunger",  x + margin, height - margin * 11, true);
		font.drawWhiteFont("Thirst",  x + margin, height - margin * 15, true);
		
		// Inventory
		for (int i = 0; i < player.getInventory().getSize() / inventoryRows; i++) {
			for (int j = 0; j < inventoryRows; j++) {
				drawRectFrame(sr,
						x + inventoryBoxSize * i + margin,
						height - margin * 17 - inventoryBoxSize * j,
						inventoryBoxSize,
						inventoryBoxSize);
			}
		}
		font.drawWhiteFont("Inventory", x + margin, height - margin * 23 - 3f, true);
		
		// Game name and version
		font.drawWhiteFont(OnePixel.GAME_NAME, x + margin,         margin * 1.5f, true);
		font.drawWhiteFont(OnePixel.VERSION,   x + width - margin * 4 - 6f, margin * 1.5f, true);
	}
	
	private void drawRectFrame(ShapeRenderer sr, float x, float y, float width, float height) {
		final float lineWeight = 4f;
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x, y, lineWidth, lineWeight);
		
		// Bottom
		sr.rect(x, y - lineHeight, lineWidth, lineWeight); 
		
		sr.end();
	}
	
	private void drawRectFill(ShapeRenderer sr, float x, float y, float width, float height, int value) {
		final float lineWeight = 4f;
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		float lineValue = lineWidth * (value / (float)Player.MAX_STAT_VALUE) - lineWeight*2;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.LIGHT_GRAY);
		sr.rect(x + lineWeight, y - lineHeight, lineValue, lineHeight);
		sr.end();
	}
	
	public int getWidth() {
		return width;
	}
}
