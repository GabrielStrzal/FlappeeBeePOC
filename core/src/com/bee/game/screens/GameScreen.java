package com.bee.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bee.game.Utils.GdxUtils;
import com.bee.game.sprites.Flappee;
import com.bee.game.config.GameConfig;
import com.bee.game.sprites.Flower;

/**
 * Created by lelo on 17/12/17.
 */

public class GameScreen extends ScreenAdapter {

    private static final float GAP_BETWEEN_FLOWERS = 200F;

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    private Texture background;
    private Texture flowerBottom;
    private Texture flowerTop;
    private Texture flappeeTexture;


    private int score = 0;

    private Flappee flappee;
    private Array<Flower> flowers = new Array<Flower>();

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(GameConfig.WORLD_WIDTH / 2, GameConfig.WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();
        background = new Texture(Gdx.files.internal("bg.png"));
        flowerBottom = new Texture(Gdx.files.internal("flowerBottom.png"));
        flowerTop = new Texture(Gdx.files.internal("flowerTop.png"));
        flappeeTexture = new Texture(Gdx.files.internal("bee.png"));
        flappee = new Flappee(flappeeTexture);
        flappee.setPosition(GameConfig.WORLD_WIDTH / 4, GameConfig.WORLD_HEIGHT / 2);
    }
    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        draw();
        drawDebug();
        update(delta);
    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        flappee.drawDebug(shapeRenderer);
        for (Flower flower : flowers) {
            flower.drawDebug(shapeRenderer);
        }
        shapeRenderer.end();
    }

    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0);
        drawFlowers();
        flappee.draw(batch);
        drawScore();
        batch.end();
    }

    private void blockFlappeeLeavingTheWorld() {
        flappee.setPosition(flappee.getX(),
                MathUtils.clamp(flappee.getY(), 0, GameConfig.WORLD_HEIGHT));
    }
    private void update(float delta) {
        updateFlappe(delta);
        updateFlowers(delta);
        updateScore();
        if (checkForCollision()) {
            restart();
        }
    }
    private void updateFlappe(float delta){
        flappee.update(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) flappee.flyUp();
        blockFlappeeLeavingTheWorld();

    }
    private void updateFlowers(float delta) {
        for (Flower flower : flowers) {
            flower.update(delta);
        }
        checkIfNewFlowerIsNeeded();
        removeFlowersIfPassed();
    }
    private void updateScore() {
        Flower flower = flowers.first();
        if (flower.getX() < flappee.getX() && !flower.isPointClaimed())
        {
            flower.markPointClaimed();
            score++;
        }
    }
    private void createNewFlower() {
        Flower newFlower = new Flower(flowerBottom, flowerTop);
        newFlower.setPosition(GameConfig.WORLD_WIDTH + Flower.WIDTH);
        flowers.add(newFlower);
    }
    private void checkIfNewFlowerIsNeeded() {
        if (flowers.size == 0) {
            createNewFlower();
        } else {
            Flower flower = flowers.peek();
            if (flower.getX() < GameConfig.WORLD_WIDTH - GAP_BETWEEN_FLOWERS) {
                createNewFlower();
            }
        }
    }
    private void removeFlowersIfPassed() {
        if (flowers.size > 0) {
            Flower firstFlower = flowers.first();
            if (firstFlower.getX() < -Flower.WIDTH) {
                flowers.removeValue(firstFlower, true);
            }
        }
    }
    private void drawFlowers() {
        for (Flower flower : flowers) {
            flower.draw(batch);
        }
    }

    private boolean checkForCollision() {
        for (Flower flower : flowers) {
            if (flower.isFlappeeColliding(flappee)) {
                return true;
            }
        }
        return false;
    }
    private void restart() {
        flappee.setPosition(GameConfig.WORLD_WIDTH / 4, GameConfig.WORLD_HEIGHT / 2);
        flowers.clear();
        score = 0;
    }
    private void drawScore() {
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        bitmapFont.draw(batch, scoreAsString,
                (viewport.getWorldWidth()/2 - glyphLayout.width / 2),
                (4 * viewport.getWorldHeight() / 5) - glyphLayout.height / 2);
    }

}
