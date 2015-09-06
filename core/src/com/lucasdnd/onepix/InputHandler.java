package com.lucasdnd.onepix;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
	
	public boolean wPressed, aPressed, sPressed, dPressed; // :(
	
	public int delay = 0;
	public int maxDelay = 10;
	
	@Override
	public boolean keyDown(int keycode) {
				
		if (keycode == Keys.W) {
			wPressed = true;
		} else if (keycode == Keys.A) {
			aPressed = true;
		} else if (keycode == Keys.S) {
			sPressed = true;
		} else if (keycode == Keys.D) {
			dPressed = true; // :(
		}
	
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if (keycode == Keys.W) {
			wPressed = false;
			delay = maxDelay;
		} else if (keycode == Keys.A) {
			aPressed = false;
			delay = maxDelay;
		} else if (keycode == Keys.S) {
			sPressed = false;
			delay = maxDelay;
		} else if (keycode == Keys.D) {
			dPressed = false; // :(
			delay = maxDelay;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
