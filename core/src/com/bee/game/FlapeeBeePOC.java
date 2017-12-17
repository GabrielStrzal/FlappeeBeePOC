package com.bee.game;

import com.badlogic.gdx.Game;
import com.bee.game.screens.GameScreen;

public class FlapeeBeePOC extends Game {
	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
