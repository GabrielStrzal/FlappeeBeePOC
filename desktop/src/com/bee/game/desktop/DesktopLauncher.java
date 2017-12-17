package com.bee.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bee.game.FlapeeBeePOC;
import com.bee.game.config.GameConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) GameConfig.WORLD_WIDTH;
		config.height = (int) GameConfig.WORLD_HEIGHT;
		new LwjglApplication(new FlapeeBeePOC(), config);
	}
}
