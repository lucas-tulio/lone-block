package com.lucasdnd.loneblock;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
* Input Controller. It handles mouse clicks, key press and delays.
*/
public class InputHandler implements InputProcessor, ControllerListener {
	
	public boolean leftMouseDown, leftMouseJustClicked, rightMouseDown, rightMouseJustClicked;
	public boolean upPressed, leftPressed, downPressed, rightPressed;
	public boolean ePressed, wPressed;
	public boolean shiftPressed, ctrlPressed;
	
	public boolean startJustPressed;
	
	// Controls hold down delay. When it reaches 0, the player can perform another action
	public int movementDelay = 0;
	public int actionDelay = 0;
	public int useDelay = 0;
	public final int maxMovementDelay = 6;
	public final int maxActionDelay = 16;
	public final int maxUseDelay = 16;
	
	private final float deadzone = 0.1f;
	
	public InputHandler() {
		Controllers.addListener(this);
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
		
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT) {
			ctrlPressed = true;
		}
	
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		// When releasing keys, reset the key delay so they can be pressed again immediately
		
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
		
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT) {
			ctrlPressed = false;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void connected(Controller controller) {
		
	}

	@Override
	public void disconnected(Controller controller) {
		
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		
		if (buttonCode == 0) {
			ePressed = true;
		} else if (buttonCode == 1) {
			wPressed = true;
		} else if (buttonCode == 9) {
			startJustPressed = true;
		}
		
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		
		if (buttonCode == 0) {
			ePressed = false;
			actionDelay = 0;
		} else if (buttonCode == 1) {
			wPressed = false;
			useDelay = 0;
		}
		
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		
		if (axisCode == 1) {
		
			// Up / down
			if (value <= -1f + deadzone) {
				upPressed = true;
			} else if (value >= 1f - deadzone) {
				downPressed = true;
			} else {
				upPressed = false;
				downPressed = false;
			}
			
		} else {
			
			// Left / right
			if (value <= -1f + deadzone) {
				leftPressed = true;
			} else if (value >= 1f - deadzone) {
				rightPressed = true;
			} else {
				leftPressed = false;
				rightPressed = false;
			}
		}
		
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		return false;
	}

	public boolean isStartJustPressed() {
		return startJustPressed;
	}

	public void setStartJustPressed(boolean startJustPressed) {
		this.startJustPressed = startJustPressed;
	}
}
