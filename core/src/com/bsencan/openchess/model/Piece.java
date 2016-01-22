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
import com.bsencan.openchess.Assets;
import com.bsencan.openchess.Constants;

/**
 * <code>Piece</code> is a simple {@link Actor} implementation of a chess piece.
 * 
 * @author Baris Sencan
 */
public class Piece extends Actor {

	private final TextureRegion textureRegion;
	private String name;

	public Piece(int x, int y,String name) {
		this.setBounds(x, y, 1, 1);
		this.name = name;
		this.textureRegion = Assets.gameAtlas.findRegion(getRegion(name));
	}

	public String getName(){
		return name;
	}

	private String getRegion(String name){
		String result = "piece01";
		this.name = name;
		for (int i =0; i < Constants.names.length; i ++){
			if (Constants.names[i].equalsIgnoreCase(name)){
				return Constants.regionNames[i];
			}
			if (Constants.black_names[i].equalsIgnoreCase(name)){
				return Constants.regionbNames[i];
			}
		}
		return result;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(this.textureRegion, this.getX(), this.getY(), 1, 1);
	}

}
