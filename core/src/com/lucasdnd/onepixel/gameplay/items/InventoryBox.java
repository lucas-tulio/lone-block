package com.lucasdnd.onepixel.gameplay.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.OnePixel;

/**
 * The visual representation of the inventory "box" on the sidebar
 * @author lucasdnd
 *
 */
public class InventoryBox {
	
	private float x, y;
	private boolean mouseOver;
	private Item item;
	public static float SIZE = 40f;
	private final float lineWeight = 4f;
	
	private int tooltipOffsetX = 32;
	private int tooltipOffsetY = 32;
	
	FontUtils font;
	boolean drawingTooltip;

	public InventoryBox(float x, float y) {
		this.x = x;
		this.y = y;
		font = new FontUtils();
	}
	
	public void update() {
		int mouseX = Gdx.input.getX();
		int mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) + 34;
		mouseOver = ((mouseX > x && mouseX < x + SIZE) && (mouseY > y && mouseY < y + SIZE));
	}
	
	public void render(ShapeRenderer sr) {
		
		// Draw the box
		drawRectFrame(sr);
		
		// Draw the item
		if (item != null) {
			item.render(sr, x, y);
			if (item.getAmount() > 1) {
				font.drawWhiteFont("" + item.getAmount(), x + 38f, y - 18f, false, Align.right);
			}
		}
	}
	
	private void drawRectFrame(ShapeRenderer sr) {
		final float lineHeight = SIZE;
		final float lineWidth = SIZE + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		if (mouseOver) {
			if (item != null) {
				((OnePixel)Gdx.app.getApplicationListener()).getTooltip().setTooltip(item.getName(), x + tooltipOffsetX, y - tooltipOffsetY);
				drawingTooltip = true;
			} else {
				drawingTooltip = false;
			}
		} else {
			drawingTooltip = false;
		}
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + SIZE, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x, y, lineWidth, lineWeight);
		
		// Bottom
		sr.rect(x, y - lineHeight, lineWidth, lineWeight); 
		
		sr.end();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public boolean isDrawingTooltip() {
		return drawingTooltip;
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
}
