package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.Resources;

public class Tooltip {
	
	float x, y, width;
	final float height = 32f;
	final float textMarginX = 2f;
	final float textMarginY = 5f;
	final float tooltipPaddingX = 32f;
	final float lineWeight = 4f;
	
	FontUtils font;
	
	public Tooltip() {
		font = new FontUtils();
	}
	
	public void showTooltip(ShapeRenderer sr, String text, float x, float y) {
		
		float charSize = Resources.get().blackFont.getSpaceWidth();
		float textSize = charSize * text.length();
		
		textSize += tooltipPaddingX;
		
		drawTooltipFrame(sr, x, y, textSize, height);
		drawTooltipBackground(sr, x, y, textSize, height);
		font.drawWhiteFont(text, x + textMarginX, y - textMarginY, false, Align.center, (int)textSize);
	}
	
	private void drawTooltipFrame(ShapeRenderer sr, float x, float y, float width, float height) {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		
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
	
	private void drawTooltipBackground(ShapeRenderer sr, float x, float y, float width, float height) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.LIGHT_GRAY);
		sr.rect(x + lineWeight, y - height + lineWeight, width - lineWeight, height - lineWeight);
		sr.end();
	}	
}
