package com.lucasdnd.onepix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.lucasdnd.onepix.gameplay.Player;
import com.lucasdnd.onepix.gameplay.World;

public class InputHandler implements InputProcessor {
	
	public boolean wPressed, aPressed, sPressed, dPressed; // :(
	
	private int keyDelay = 0;
	private int maxKeyDelay = 10;
	
	private void handleInput() {
		
		Player player = ((OnePix)Gdx.app.getApplicationListener()).getPlayer();
		World world = ((OnePix)Gdx.app.getApplicationListener()).getWorld();
		
		if (wPressed) {
			if (player.canMoveUp(world)) {
				player.moveUp();
			}
		}
		if (sPressed) {
			if (player.canMoveDown(world)) {
				player.moveDown();
			}
		}
		if (aPressed) {
			if (player.canMoveLeft(world)) {
				player.moveLeft();
			}
		}
		if (dPressed) {
			if (player.canMoveRight(world)) {
				player.moveRight();
			}
		}
	}
	
	
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
		
		handleInput();
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if (keycode == Keys.W) {
			wPressed = false;
		} else if (keycode == Keys.A) {
			aPressed = false;
		} else if (keycode == Keys.S) {
			sPressed = false;
		} else if (keycode == Keys.D) {
			dPressed = false; // :(
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
