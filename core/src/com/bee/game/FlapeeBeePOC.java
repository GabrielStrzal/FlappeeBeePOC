package com.bee.game;

import com.badlogic.gdx.Game;
import com.bee.game.screens.GameScreen;
import com.bee.game.screens.StartScreen;

public class FlapeeBeePOC extends Game {
	@Override
	public void create() {
		setScreen(new StartScreen(this));
	}
}
