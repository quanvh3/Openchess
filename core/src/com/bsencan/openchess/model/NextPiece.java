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

package com.bsencan.openchess.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.bsencan.openchess.Assets;
import com.bsencan.openchess.Constants;

/**
 * <code>Piece</code> is a simple {@link Actor} implementation of a chess piece.
 * 
 * @author Baris Sencan
 */
public class NextPiece extends Actor {


	private TextureRegion textureRegion;
	private TextureRegion borderRegion;
	private String currentName;

	public NextPiece(String regionName) {
		this.textureRegion = Assets.gameAtlas.findRegion(regionName);
		this.borderRegion = Assets.gameAtlas.findRegion("button");
		this.setScale(100);
	}

	public void setTextureRegion(String region){
		this.textureRegion = Assets.gameAtlas.findRegion(region);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(this.borderRegion, getX()-0.25f, getY()-0.25f, 1.5f, 1.5f);
		batch.draw(this.textureRegion, getX(), getY(), 1, 1);
	}

}
