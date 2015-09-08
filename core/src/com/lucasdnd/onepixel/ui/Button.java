package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;

public class Button {
	
	float x, y, width;
	float charSize, textSize;
	String text;
	
	final float height = 32f;
	final float textPaddingX = 2f;
	final float textPaddingY = 5f;
	final float paddingX = 32f;
	final float lineWeight = 4f;
	
	boolean mouseOver;
	
	FontUtils font;
	ShapeRenderer sr;
	ButtonClickListener clickListener;
	
	public Button(String text, float x, float y) {
		this.x = x;
		this.y = y;
		this.text = text;
		sr = new ShapeRenderer();
		font = new FontUtils();
		calcButtonSize();
	}
	
	public void update() {
		int mouseX = Gdx.input.getX();
		int mouseY = (int)(Gdx.graphics.getHeight() - Gdx.input.getY() + height);
		mouseOver = ((mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height));
		
		if (mouseOver && ((OnePixel)Gdx.app.getApplicationListener()).getInputHandler().leftMouseJustClicked) {
			if (clickListener != null) {
				clickListener.onClick();
			}
		}
	}
	
	public void render() {
		drawButtonFrame();
		font.drawWhiteFont(text, x + textPaddingX, y - textPaddingY, false, Align.center, (int)textSize);
	}
	
	private void calcButtonSize() {
		charSize = Resources.get().blackFont.getSpaceWidth();
		textSize = charSize * text.length();
		textSize += paddingX;
		width = textSize;
	}
	
	private void drawButtonFrame() {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		
		if (mouseOver) {
			sr.setColor(Color.GRAY);
		} else {
			sr.setColor(Color.WHITE);
		}
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x + lineWeight, y, lineWidth - lineWeight * 2, lineWeight);
		
		// Bottom
		sr.rect(x + lineWeight, y - lineHeight, lineWidth - lineWeight * 2, lineWeight); 
		
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

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public ButtonClickListener getClickListener() {
		return clickListener;
	}

	public void setClickListener(ButtonClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public void setText(String text) {
		this.text = text;
		calcButtonSize();
	}
}
