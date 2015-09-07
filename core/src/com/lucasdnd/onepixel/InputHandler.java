package com.lucasdnd.onepixel;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
	
	public boolean leftMouseDown, leftMouseJustClicked, rightMouseDown, rightMouseJustClicked;
	public boolean upPressed, leftPressed, downPressed, rightPressed;
	public boolean ePressed, wPressed;
	public boolean shiftPressed;
	
	// Controls hold down delay. When it reaches 0, the player can perform another action
	public int movementDelay = 0;
	public int actionDelay = 0;
	public int useDelay = 0;
	public final int maxMovementDelay = 6;
	public final int maxActionDelay = 16;
	public final int maxUseDelay = 16;
	
	public InputHandler() {

	}
	
	public void applyMovementDelay() {
		movementDelay = maxMovementDelay;
	}
	
	public void applyActionDelay() {
		actionDelay = maxActionDelay;
	}
	
	public void applyUseDelay() {
		useDelay = maxUseDelay;
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
		
		// When releasing the movement keys, reset the key delay so it can be pressed again immediately
		if (keycode == Keys.UP) {
			upPressed = false;
			movementDelay = 0;
		} else if (keycode == Keys.LEFT) {
			leftPressed = false;
			movementDelay = 0;
		} else if (keycode == Keys.DOWN) {
			downPressed = false;
			movementDelay = 0;
		} else if (keycode == Keys.RIGHT) {
			rightPressed = false;
			movementDelay = 0;
		}
		
		if (keycode == Keys.E) {
			ePressed = false;
			actionDelay = 0;
		} else if (keycode == Keys.W) {
			wPressed = false;
			useDelay = 0;
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
