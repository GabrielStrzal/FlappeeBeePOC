package com.bee.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bee.game.config.GameConfig;

/**
 * Created by lelo on 18/12/17.
 */

public class StartScreen extends ScreenAdapter {

    private Texture backgroundTexture;
    private Texture playTexture;
    private Texture playPressTexture;
    private Texture titleTexture;

    private Stage stage;
    private final Game game;

    public StartScreen(Game game) {
        this.game = game;
    }

    public void show() {
        stage = new Stage(new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture(Gdx.files.internal("bg.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        playTexture = new Texture(Gdx.files.internal("play.png"));
        playPressTexture = new Texture(Gdx.files.internal("playPress.png"));
        ImageButton play = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(playTexture)),
                new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(GameConfig.WORLD_WIDTH / 2 - play.getWidth() / 2, GameConfig.WORLD_HEIGHT /
                4 - play.getHeight() / 2);

        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count,
                            int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new GameScreen());
                dispose();
            }
        });
        stage.addActor(play);
        titleTexture = new Texture(Gdx.files.internal("title.png"));
        Image title = new Image(titleTexture);
        title.setPosition(GameConfig.WORLD_WIDTH /2, 3 * GameConfig.WORLD_HEIGHT / 4,
                Align.center);
        stage.addActor(title);
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        playTexture.dispose();
        playPressTexture.dispose();
        titleTexture.dispose();
    }
}
