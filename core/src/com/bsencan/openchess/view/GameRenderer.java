/*
 * Copyright 2013 Baris Sencan (baris.sencan@me.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.bsencan.openchess.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bsencan.openchess.Assets;
import com.bsencan.openchess.OpenChess;
import com.bsencan.openchess.model.Board;
import com.bsencan.openchess.model.GameOver;
import com.bsencan.openchess.model.NextTitle;
import com.bsencan.openchess.screens.GameScreen;

import static com.bsencan.openchess.Constants.VIEWPORT_HEIGHT;
import static com.bsencan.openchess.Constants.VIEWPORT_WIDTH;

/**
 * A {@link Renderer} for {@link GameScreen}.
 * 
 * @author Baris Sencan
 */
public class GameRenderer implements Renderer {

	private final Stage stage = new Stage(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
	private Table hud;
	private Table hud2;
	private Table imageHude;
	private Table upperTextHud;
	private ImageButton resetButton,undoButton;
	private NextTitle nextLabel;
	private Label scoreLabel;
	private Label highScoreLabel;
	Container l;
	Board board;
	private Sprite backgroundSprite;

	public GameRenderer(Board board) {

		Gdx.input.setInputProcessor(this.stage);
		initBackground();
		this.stage.addActor(board);
		this.nextLabel = new NextTitle("Pawn", Assets.skin);
		this.scoreLabel = new Label("0", Assets.skin,"trumpyb");
		this.highScoreLabel = new Label(OpenChess.highScore+"", Assets.skin,"trumpy");
		this.nextLabel.setScoreLabel(scoreLabel);

		board.setNextTitle(nextLabel);
		this.board = board;
		initUpperImageTable();
		this.initUI();
		initUpperUI();
		initNextMoveUI();
		initGameOverUI();
	}

	public void initBackground(){
		Image image = new Image(Assets.gameAtlas.findRegion("background"));
		image.setPosition(0, 0);
		image.setSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		this.stage.addActor(image);
	}

	private void initUpperImageTable(){
		Image scoreBg = new Image(Assets.gameAtlas.findRegion("score_background"));
		scoreBg.setPosition(0.25f, VIEWPORT_HEIGHT - 0.75f);
		scoreBg.setSize(VIEWPORT_WIDTH / 2 - 0.5f, 0.5f);
		Image scoreIc = new Image(Assets.gameAtlas.findRegion("ic-score"));
		scoreIc.setPosition(0.05f, VIEWPORT_HEIGHT - 0.75f);
		scoreIc.setSize(0.6f, 0.6f);
		this.stage.addActor(scoreBg);
		this.stage.addActor(scoreIc);
	}

	private void initUpperUI(){
		this.upperTextHud = new Table(Assets.skin);
		this.upperTextHud.add(this.scoreLabel);
		this.upperTextHud.setTransform(true);
		this.upperTextHud.setScale(0.5f / this.resetButton.getHeight());
		this.scoreLabel.setColor(Color.WHITE);
		this.scoreLabel.setAlignment(Align.left);
		this.upperTextHud.align(Align.left);
		this.upperTextHud.setPosition(0.7f, VIEWPORT_HEIGHT - 0.52f);
		this.stage.addActor(this.upperTextHud);

		Table highScoreTable = new Table(Assets.skin);
		highScoreTable.add(this.highScoreLabel);
		highScoreTable.setTransform(true);
		highScoreTable.setScale(0.5f / this.resetButton.getHeight());
		highScoreLabel.setColor(Color.WHITE);
		highScoreLabel.setAlignment(Align.left);
		highScoreTable.align(Align.left);
		highScoreTable.setPosition(VIEWPORT_WIDTH/2 + 0.7f, VIEWPORT_HEIGHT - 0.52f);
		stage.addActor(highScoreTable);
	}

	private void initGameOverUI(){
		GameOver gameOver = new GameOver(stage);
		gameOver.setVisible(false);
		board.setGameOver(gameOver);
		this.stage.addActor(gameOver);
	}

	public void initNextMoveUI(){
		this.imageHude = new Table(Assets.skin);
		this.imageHude.add(nextLabel.getPiece());
		this.imageHude.setPosition((VIEWPORT_WIDTH) / 2 - 0.5f, 0.5f);
		this.stage.addActor(this.imageHude);
	}

	private void initUI() {
		this.hud = new Table();
		this.hud2 = new Table();
		this.resetButton = new ImageButton(Assets.skin,"reset");
		this.undoButton = new ImageButton(Assets.skin,"undo");
		Label resetLabel = new Label("Reset",Assets.skin);
		Label undoLabel = new Label("Undo",Assets.skin);
		this.resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				OpenChess.game.setScreen(new GameScreen());
			}
		});
		this.undoButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				board.undo();
			}
		});

		this.hud.setTransform(true);
		this.hud.setScale(1 / this.resetButton.getHeight());
		this.hud.setPosition(1f, 1f);
		this.hud.add(this.resetButton);
		this.hud.row();
		this.hud.add(resetLabel).expandX();

		this.hud2.setTransform(true);
		this.hud2.setScale(1 / this.resetButton.getHeight());
		this.hud2.setPosition(VIEWPORT_WIDTH-1, 1f);
		this.hud2.add(this.undoButton);
		this.hud2.row();
		this.hud2.add(undoLabel).expandX();

		this.stage.addActor(this.hud);
		this.stage.addActor(this.hud2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		this.stage.draw();
	}

	@Override
	public void setSize(int width, int height) {
		this.stage.getViewport().update(width, height, false);
		Gdx.graphics.requestRendering();
	}

	@Override
	public void dispose() {
		this.stage.dispose();
	}

}
