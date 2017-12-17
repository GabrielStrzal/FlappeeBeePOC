package com.bee.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.bee.game.FlapeeBeePOC;
import com.bee.game.config.GameConfig;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration((int)GameConfig.WORLD_WIDTH, (int) GameConfig.WORLD_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new FlapeeBeePOC();
        }
}