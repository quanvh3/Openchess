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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.bsencan.openchess.Constants;

import java.util.Random;

/**
 * Represents a single tile on a chess board.
 * 
 * @author Baris Sencan
 */
public class NextTitle extends Label {

	Random r = new Random();
	NextPiece piece;
	Label scoreLb;
	int score=0;
	int previousScore = 0;
	String previousMove;

	public NextTitle(CharSequence text, Skin skin){
		super(text,skin);
		piece = new NextPiece("piece01");
	}

	public Actor getPiece(){
		return piece;
	}

	public void setScoreLabel(Label label){
		this.scoreLb = label;
	}

	public String next(){
		previousMove = getText().toString();
		addScore();
		scoreLb.setText(""+score);
		int nextMove = r.nextInt(500);
		String result = "Pawn";
		for (int i =0; i < Constants.names.length; i ++){
			if (nextMove>Constants.percentages[i]){
				int random = r.nextInt(5);
				if (random<4) {
					this.setText(Constants.names[i]);
					piece.setTextureRegion(Constants.regionNames[i]);
					return Constants.names[i];
				}
				else {
					this.setText(Constants.black_names[i]);
					piece.setTextureRegion(Constants.regionbNames[i]);
					return Constants.black_names[i];
				}
			}
		}
		return result;
	}

	public void addScore(){
		String text = this.getText().toString();
		for (int i =0; i < Constants.names.length; i ++){
			if (text.equalsIgnoreCase(Constants.names[i])){
				score+=Constants.points[i];
			}
			if (text.equalsIgnoreCase(Constants.black_names[i])){
				score+=Constants.points[i];
			}
		}
	}

	public int getScore(){
		return score;
	}

	public void saveScore(){
		this.previousScore = score;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.piece.setVisible(visible);
	}

	public void addScore(String input,int multiply){
		String text = input;
		for (int i =0; i < Constants.names.length; i ++){
			if (text.equalsIgnoreCase(Constants.names[i])){
				score+=Constants.points[i]*multiply;
			}
			if (text.equalsIgnoreCase(Constants.black_names[i])){
				score+=Constants.points[i]*multiply;
			}
		}
	}

	public void undo(){
		score = previousScore;
		scoreLb.setText(""+score);
		for (int i =0; i < Constants.names.length;i++){
			if (Constants.names[i].equalsIgnoreCase(previousMove)){
				this.setText(previousMove);
				piece.setTextureRegion(Constants.regionNames[i]);
			}
			if (Constants.black_names[i].equalsIgnoreCase(previousMove)){
				this.setText(previousMove);
				piece.setTextureRegion(Constants.regionbNames[i]);
			}
		}
	}
}
