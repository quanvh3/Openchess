package com.bsencan.openchess.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.bsencan.openchess.Assets;
import com.bsencan.openchess.Constants;
import com.bsencan.openchess.OpenChess;
import com.bsencan.openchess.screens.GameScreen;

import static com.bsencan.openchess.Constants.VIEWPORT_HEIGHT;
import static com.bsencan.openchess.Constants.VIEWPORT_WIDTH;

/**
 * Created by smartosc on 1/13/2016.
 */
public class GameOver extends Actor {

    private TextureRegion textureRegion;
    private Table hud;
    private int score;
    Stage stage;
    public GameOver(Stage stage) {
        this.textureRegion = Assets.gameAtlas.findRegion("gameover");
        this.hud = new Table();

        ImageButton resetButton = new ImageButton(Assets.skin,"newgame");
        resetButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (score > OpenChess.highScore)
                    OpenChess.nativeActions.saveHighScore(score);
                OpenChess.game.setScreen(new GameScreen());
            }
        });
        this.hud.setTransform(true);
        this.hud.setScale(1 / resetButton.getHeight());
        this.hud.setPosition(Constants.VIEWPORT_WIDTH / 3 - 0.5f, VIEWPORT_HEIGHT / 2 - 1);
        this.hud.addActor(resetButton);
        this.stage = stage;
    }

    public void setScore(int score){
        this.score = score;
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        super.draw(batch, 0.5f);
        batch.draw(this.textureRegion, Constants.VIEWPORT_WIDTH / 3 -0.5f, getY(), Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_WIDTH / 2);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (getY()> Constants.VIEWPORT_HEIGHT/2){
                    setY(getY()-0.5f);
                    draw(batch,parentAlpha);
                }
                else if (!stage.getActors().contains(hud,true)){
                    stage.addActor(hud);
                }
            }
        }
                , 0,0.2f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
