package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.Player;
import com.lucasdnd.onepixel.gameplay.items.Item;

public class SideBar {
	
	int x, y, width;
	final float margin = 20f;
	public static final float lineWeight = 4f;
	
	// Status bar attributes
	float barHeight;
	float barWidth;
	
	// Inventory attributes
	int inventoryRows = 3;
	public static float INVENTORY_BOX_SIZE = 40f;
	
	FontUtils font;
	
	public SideBar(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		barHeight = margin * 1.8f;
		barWidth = width - margin * 2;
		font = new FontUtils();
	}
	
	public void update(Tooltip tooltip, int mouseX, int mouseY) {
		
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
		
		drawRectFill(sr, null, x + margin, height - margin,       barWidth, barHeight, player.getHealth(),  Player.MAX_STAT_VALUE);
		drawRectFill(sr, null, x + margin, height - margin * 5,   barWidth, barHeight, player.getStamina(), Player.MAX_STAT_VALUE);
		drawRectFill(sr, null, x + margin, height - margin * 9,   barWidth, barHeight, player.getFood(),    Player.MAX_STAT_VALUE);
		drawRectFill(sr, null, x + margin, height - margin * 13,  barWidth, barHeight, player.getDrink(),   Player.MAX_STAT_VALUE);
		
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
				
				// Inventory frame
				drawRectFrame(sr,
						x + INVENTORY_BOX_SIZE * i + margin,
						height - margin * 17 - INVENTORY_BOX_SIZE * j,
						INVENTORY_BOX_SIZE,
						INVENTORY_BOX_SIZE);
				
				// Inventory items
				Item item = null;
				try {
					item = player.getInventory().getContent().get(j * inventoryRows + i);
				} catch (Exception e) {
//					System.out.println("item is null");
				}
				
				if (item != null) {
					
					float itemX = x + INVENTORY_BOX_SIZE * i + margin;
					float itemY = height - margin * 17 - INVENTORY_BOX_SIZE * j;
					item.render(sr, itemX, itemY);

					if (item.getAmount() > 1) {
						font.drawWhiteFont("" + item.getAmount(), itemX + 38f, itemY - 18f, false, Align.right);
					}
				}
				
			}
		}
		font.drawWhiteFont("Inventory", x + margin, height - margin * 23 - 3f, true);
		
		// Game name and version
		font.drawWhiteFont(OnePixel.GAME_NAME, x + margin,         margin * 1.5f, true);
		font.drawWhiteFont(OnePixel.VERSION,   x + width - margin * 4 - 6f, margin * 1.5f, true);
	}
	
	private void drawRectFrame(ShapeRenderer sr, float x, float y, float width, float height) {
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
	
	private void drawRectFill(ShapeRenderer sr, Color c, float x, float y, float width, float height, int value, int maxValue) {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		float lineValue = lineWidth * ((float)value / (float)maxValue) - lineWeight * 2f;
		sr.begin(ShapeType.Filled);
		if (c == null) {
			sr.setColor(Color.LIGHT_GRAY);
		} else {
			sr.setColor(c);
		}
		
		sr.rect(x + lineWeight, y - lineHeight, lineValue, lineHeight);
		sr.end();
	}
	
	public int getWidth() {
		return width;
	}
}
