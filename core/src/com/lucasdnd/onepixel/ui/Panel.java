package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.Resources;

public class Panel {
	public void drawFrame(ShapeRenderer sr, float x, float y, float width, float height, float lineWeight) {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		
		sr.setColor(Resources.Color.uiStroke);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x + lineWeight, y, lineWidth - lineWeight * 2, lineWeight);
		
		// Bottom
		sr.rect(x + lineWeight, y - lineHeight, lineWidth - lineWeight * 2, lineWeight); 
		
	}
}
