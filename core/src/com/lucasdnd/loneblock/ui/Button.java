package com.lucasdnd.loneblock.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.loneblock.FontUtils;
import com.lucasdnd.loneblock.LoneBlock;
import com.lucasdnd.loneblock.Resources;

public class Button {
	
	protected float x, y, width;
	protected final float height = 32f;
	private final float textPaddingX = 2f;
	private final float textPaddingY = 5f;
	private final float paddingX = 32f;
	private final float lineWeight = 4f;
	
	private float charSize, textSize;
	private String text;
	
	private boolean mouseOver, enabled;
	
	private FontUtils font;
	private ShapeRenderer sr;
	private ButtonClickListener clickListener;
	
	private final Color disabledColor = Resources.Color.buttonDisabled;
	private final Color normalColor = Resources.Color.button;
	private final Color hoverColor = Resources.Color.buttonHover;
	
	public Button(String text) {
		this.text = text;
		sr = new ShapeRenderer();
		font = new FontUtils();
		calcButtonSize();
		enabled = true;
	}
	
	public Button(String text, float x, float y) {
		this(text);
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		if (enabled) {
			int mouseX = Gdx.input.getX();
			int mouseY = (int)(Gdx.graphics.getHeight() - Gdx.input.getY() + height);
			mouseOver = ((mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height));
			
			if (mouseOver && ((LoneBlock)Gdx.app.getApplicationListener()).getInputHandler().leftMouseJustClicked) {
				if (clickListener != null) {
					clickListener.onClick();
				}
			}
		}
	}
	
	public void render() {
		drawButtonFrame();
		if (enabled) {
			font.drawWhiteFont(text, x + textPaddingX, y - textPaddingY, false, Align.center, (int)width);
		} else {
			font.drawGrayFont(text, x + textPaddingX, y - textPaddingY, false, Align.center, (int)width);
		}
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
		
		if (enabled) {
			if (mouseOver) {
				sr.setColor(hoverColor);
			} else {
				sr.setColor(normalColor);
			}
		} else {
			sr.setColor(disabledColor);
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
