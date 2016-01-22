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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bsencan.openchess.Constants;
import com.bsencan.openchess.controller.BoardController;
import static com.bsencan.openchess.Constants.BOARD_WIDTH;

/**
 * A chess board with pieces on it. Every instance of <code>Board</code> is
 * actually a playable chess game.
 * 
 * @author Baris Sencan
 */
public class Board extends Table {

	/**
	 * Pointers to tiles for easy access.
	 */
	private final Tile[][] tiles = new Tile[BOARD_WIDTH][BOARD_WIDTH];
	private NextTitle nextTitle;
	private GameOver gameOver;
	public boolean isActive = true;
	/**
	 * Pointers to pieces for easy access.
	 */
	private final Piece[][] pieces = new Piece[BOARD_WIDTH][BOARD_WIDTH];
	private final Piece[][] undoPieces = new Piece[BOARD_WIDTH][BOARD_WIDTH];

	/* -- Getters -- */

	public Tile getTileAt(int x, int y) {
		return this.tiles[x][y];
	}

	public Piece getPieceAt(int x, int y) {
		return this.pieces[x][y];
	}

	/* -- Getters -- */

	/**
	 * Creates an empty chess board.
	 */
	public Board() {
		/* Basic board setup. */
		float mid = (Constants.VIEWPORT_WIDTH-BOARD_WIDTH)/2;
		this.setBounds(mid,3,9,9);
		this.setClip(true);
		this.addListener(new BoardController(this, nextTitle));
		/* Add tiles. */
		for (int i = 0; i < BOARD_WIDTH; i++) {

			for (int j = 0; j < BOARD_WIDTH; j++) {
				this.tiles[i][j] = new Tile(i, j, ((i + j) % 2) == 0);
				this.addActor(this.tiles[i][j]);
			}
		}
	}

	public GameOver getGameOver() {
		return gameOver;
	}

	public void setGameOver(GameOver gameOver) {
		this.gameOver = gameOver;
	}

	public void setNextTitle(NextTitle nextTitle){
		this.nextTitle = nextTitle;
	}

	public NextTitle getNextTitle(){
		return nextTitle;
	}



	public String getCurrentPiece(){
		return this.nextTitle.getText().toString();
	}

	public void saveUndo(){
		for (int i =0; i < BOARD_WIDTH; i++){
			for (int j = 0; j < BOARD_WIDTH; j++){
				this.undoPieces[i][j] = this.pieces[i][j];
			}
		}
	}

	public void undo(){
		for (int i =0; i < BOARD_WIDTH; i++){
			for (int j = 0; j < BOARD_WIDTH; j++){
				if (pieces[i][j] !=null && this.undoPieces[i][j] == null)
					removePieceAt(i,j);
				if (pieces[i][j] ==null && this.undoPieces[i][j] != null)
					addPiece(this.undoPieces[i][j]);
			}
		}
		this.nextTitle.undo();
	}

	/**
	 * Places a chess piece on this <code>Board</code>.
	 * 
	 * @param piece
	 *            Piece to place.
	 */
	public void addPiece(Piece piece) {
		this.addActor(piece);
		this.pieces[(int) piece.getX()][(int) piece.getY()] = piece;
	}

	/**
	 * Removes a piece that is on a given tile location.
	 * 
	 * @param x
	 *            Horizontal index of the tile.
	 * @param y
	 *            Vertical index of the tile.
	 */
	public void removePieceAt(int x, int y) {
		Piece piece = this.pieces[x][y];
		if (piece != null) {
			piece.remove();
			this.pieces[x][y] = null;
		}
	}


}
