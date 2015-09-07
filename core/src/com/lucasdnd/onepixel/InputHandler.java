package com.lucasdnd.onepixel;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
	
	public boolean leftMouseDown, leftMouseJustClicked, rightMouseDown, rightMouseJustClicked;
	public boolean upPressed, leftPressed, downPressed, rightPressed;
	public boolean ePressed, wPressed;
	public boolean shiftPressed;
	
	// Controls hold down delay
	public int delay = 0;
	public int maxDelay;
	
	public InputHandler(boolean debug) {
		if (debug) {
			maxDelay = 8;
		} else {
			maxDelay = 8;
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
				
		if (keycode == Keys.UP) {
			upPressed = true;
		} else if (keycode == Keys.LEFT) {
			leftPressed = true;
		} else if (keycode == Keys.DOWN) {
			downPressed = true;
		} else if (keycode == Keys.RIGHT) {
			rightPressed = true;
		}
		
		if (keycode == Keys.E) {
			ePressed = true;
		} else if (keycode == Keys.W) {
			wPressed = true;
		}
		
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT) {
			shiftPressed = true;
		}
	
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if (keycode == Keys.UP) {
			upPressed = false;
			delay = maxDelay;
		} else if (keycode == Keys.LEFT) {
			leftPressed = false;
			delay = maxDelay;
		} else if (keycode == Keys.DOWN) {
			downPressed = false;
			delay = maxDelay;
		} else if (keycode == Keys.RIGHT) {
			rightPressed = false;
			delay = maxDelay;
		}
		
		if (keycode == Keys.E) {
			ePressed = false;
			delay = maxDelay;
		} else if (keycode == Keys.W) {
			wPressed = false;
			delay = maxDelay;
		}
		
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT) {
			shiftPressed = false;
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
		if (button == 0) {
			leftMouseDown = true;
			leftMouseJustClicked = true;
		} else if (button == 1) {
			rightMouseDown = true;
			rightMouseJustClicked = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			leftMouseDown = false;
		} else if (button == 1) {
			rightMouseDown = false;
		}
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
