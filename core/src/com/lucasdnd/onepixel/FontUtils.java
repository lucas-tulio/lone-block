package com.lucasdnd.onepixel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FontUtils {
	
	SpriteBatch fontBatch;
	
	public FontUtils() {
		this.fontBatch = new SpriteBatch();
	}
	
	public void drawWhiteFont(String text, float x, float y, boolean withShadow) {
		fontBatch.begin();
		if (withShadow) {
			Resources.get().grayFont.draw(fontBatch, text, x + 1f, y - 1f);
		}
		Resources.get().whiteFont.draw(fontBatch, text, x, y);
		fontBatch.end();
	}
	
	public void drawWhiteFont(String text, float x, float y, boolean withShadow, int align) {
		fontBatch.begin();
		if (withShadow) {
			Resources.get().grayFont.draw(fontBatch, text, x + 1f, y - 1f, 0f, align, false);
		}
		Resources.get().whiteFont.draw(fontBatch, text, x, y, 0f, align, false);
		fontBatch.end();
	}
}
