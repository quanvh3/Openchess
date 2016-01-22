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

package com.bsencan.openchess.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.bsencan.openchess.Assets;
import com.bsencan.openchess.OpenChess;
import com.bsencan.openchess.view.MainMenuRenderer;

/**
 * Game's main menu screen. Any touch event causes a transition to game screen.
 *
 * @author Baris Sencan
 */
public class TestScreen implements Screen {

	private MainMenuRenderer renderer;

	@Override
	public void render(float delta) {
		this.renderer.render(delta);

		if (Gdx.input.justTouched()) {
		}
	}

	@Override
	public void resize(int width, int height) {
		this.renderer.setSize(width, height);
	}

	@Override
	public void show() {
		Assets.loadMainMenu();

		Assets.menuMusic.setLooping(true);
		Assets.menuMusic.play();

		this.renderer = new MainMenuRenderer();
		this.renderer
				.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void hide() {
		this.renderer.dispose();
		Assets.disposeMainMenu();
	}

	@Override
	public void pause() {
		Assets.menuMusic.pause();
	}

	@Override
	public void resume() {
		Assets.menuMusic.play();
	}

	@Override
	public void dispose() {
	} // Never called automatically.

}
