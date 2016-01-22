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

package com.bsencan.openchess.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.bsencan.openchess.Constants;
import com.bsencan.openchess.model.Board;
import com.bsencan.openchess.model.NextTitle;
import com.bsencan.openchess.model.Piece;
import com.bsencan.openchess.model.Tile;

import java.util.ArrayList;

import static com.bsencan.openchess.Constants.BOARD_WIDTH;


/**
 * This class is under heavy development. As it keeps changing with rapid speed,
 * it'll only receive proper javadoc comments when it's close to completion.
 * 
 * @author Baris Sencan
 */
public class BoardController extends ActorGestureListener {

	private final Board board;
	private int boardCount=0;
	public BoardController(Board board, NextTitle next) {
		this.board = board;
	}

	@Override
	public void tap(InputEvent event, float x, float y, int count, int button) {
		Actor target = event.getTarget(); // Tapped actor.
		int tx = (int) target.getX(); // Tapped tile x.
		int ty = (int) target.getY(); // Tapped tile y.

		if (!target.getClass().equals(Piece.class) && board.isActive) {
			CharSequence nextMove = board.getCurrentPiece();
			Piece piece = new Piece(tx,ty,Constants.names[Constants.names.length-1]);;
			for (int i = 0; i < Constants.names.length-1; i++){
				if (Constants.names[i].equalsIgnoreCase(nextMove.toString())){
					piece = new Piece(tx,ty,Constants.names[i]);
				}
				if (Constants.black_names[i].equalsIgnoreCase(nextMove.toString())){
					piece = new Piece(tx,ty,Constants.black_names[i]);
				}
			}
			this.board.saveUndo();
			this.board.getNextTitle().saveScore();
			this.board.addPiece(piece);
			boardCount++;
			checkAround(piece,tx,ty);
			this.board.getNextTitle().next();
		}
		if (boardCount == BOARD_WIDTH){
			board.getGameOver().setVisible(true);
			board.getGameOver().setY(Constants.VIEWPORT_HEIGHT);
			board.getGameOver().setScore(board.getNextTitle().getScore());
			board.isActive = false;
			board.getNextTitle().setVisible(false);
		}
	}

	private ArrayList<Tile> findSameNearBy(Piece piece, int tx, int ty, ArrayList<Tile> tiles){
		ArrayList<Tile> tempTiles = new ArrayList<Tile>();
		if (tx-1 >=0&& this.board.getPieceAt(tx-1,ty)!=null
				&& piece.getName().equals(this.board.getPieceAt(tx-1,ty).getName())
				&& !tiles.contains(this.board.getTileAt(tx - 1, ty))){
			tempTiles.add(this.board.getTileAt(tx - 1, ty));
		}
		if (tx +1 <BOARD_WIDTH&& this.board.getPieceAt(tx+1,ty)!=null
				&& piece.getName().equals(this.board.getPieceAt(tx + 1, ty).getName())
				&& !tiles.contains(this.board.getTileAt(tx + 1, ty))){
			tempTiles.add(this.board.getTileAt(tx + 1, ty));
		}
		if (ty-1 >=0&& this.board.getPieceAt(tx,ty-1)!=null
				&& piece.getName().equals(this.board.getPieceAt(tx, ty - 1).getName())
				&& !tiles.contains(this.board.getTileAt(tx, ty - 1))){
			tempTiles.add(this.board.getTileAt(tx, ty - 1));
		}
		if (ty+1 <BOARD_WIDTH&& this.board.getPieceAt(tx,ty+1)!=null
				&& piece.getName().equals(this.board.getPieceAt(tx,ty+1).getName())
				&& !tiles.contains(this.board.getTileAt(tx, ty + 1))) {
			tempTiles.add(this.board.getTileAt(tx, ty + 1));
		}
		for (int i = 0; i < tempTiles.size(); i++){
			if (!tiles.contains(tempTiles.get(i))){
				tiles.add(tempTiles.get(i));
				findSameNearBy(piece, (int)tempTiles.get(i).getX(),(int)tempTiles.get(i).getY(),tiles);
			}
		}
		return tiles;
	}

	private void checkAround(Piece piece, int tx, int ty){
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		tiles = findSameNearBy(piece,tx,ty,tiles);
		if (tiles.size()>2){
			for (int i =0; i < tiles.size(); i++) {
				this.board.removePieceAt((int) tiles.get(i).getX(), (int) tiles.get(i).getY());
				boardCount--;
			}
			upgrade(tx,ty,piece,tiles.size());
		}
	}

	private void upgrade(int tx, int ty, Piece piece,int multiply){
		Piece newPiece= piece;
		for (int i =0; i <Constants.names.length-1; i ++){
			if (piece.getName().equalsIgnoreCase(Constants.names[i])){
				newPiece = new Piece(tx,ty,Constants.names[i+1]);
			}
			if (piece.getName().equalsIgnoreCase(Constants.black_names[i])){
				newPiece = new Piece(tx,ty,Constants.black_names[i+1]);
			}
		}
		this.board.removePieceAt(tx, ty);
		this.board.addPiece(newPiece);
		boardCount++;
		this.board.getNextTitle().addScore(newPiece.getName(), multiply);
		checkAround(newPiece, tx, ty);
	}

}
