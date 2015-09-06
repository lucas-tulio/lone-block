package com.lucasdnd.onepix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.lucasdnd.onepix.gameplay.Player;
import com.lucasdnd.onepix.gameplay.World;

public class InputHandler implements InputProcessor {
	
	@Override
	public boolean keyDown(int keycode) {
		Player player = ((OnePix)Gdx.app.getApplicationListener()).getPlayer();
		World world = ((OnePix)Gdx.app.getApplicationListener()).getWorld();
		
		if (keycode == Keys.W && player.canMoveUp(world)) {
			player.moveUp();
		} else if (keycode == Keys.A && player.canMoveLeft(world)) {
			player.moveLeft();
		} else if (keycode == Keys.S && player.canMoveDown(world)) {
			player.moveDown();
		} else if (keycode == Keys.D && player.canMoveRight(world)) {
			player.moveRight();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
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
