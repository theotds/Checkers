package com.checkers;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

import static com.checkers.Constants.*;
import static com.checkers.PieceType.*;
import static com.checkers.Player.*;

public class Board extends Group {

    private final Tile[][] tiles = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
    private Piece movePiece = null;
    public Player whoseTurn = PLAYER1;

    public Board() {
        initTiles();
        initPieces();
        this.setOnMouseClicked(this::mouseEvent);
    }

    private void mouseEvent(MouseEvent mouseEvent) {
        int x = (int) Math.floor(mouseEvent.getX() / TILE_SIZE);
        int y = (int) Math.floor(mouseEvent.getY() / TILE_SIZE);

        //first select
        if (movePiece == null) {
            if (getTile(x, y).hasPiece() && getTile(x, y).getPiece().getPlayer()==whoseTurn) {
                movePiece = getTile(x, y).getPiece();
                getTile(x, y).selectTileBackground();
                if (movePiece == null) {
                    getTile(x, y).unSelectTileBackground();
                }
            } else {
                movePiece = null;
            }
        } else {
            //second select
            int oldX = movePiece.getPoint().getX();
            int oldY = movePiece.getPoint().getY();
            getTile(oldX, oldY).unSelectTileBackground();
            getTile(oldX, oldY).getPiece().move(this, x, y);
            movePiece = null;
        }
    }

    private void initTiles() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                setTile(x, y);
                getChildren().add(getTile(x, y));
            }
        }
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public void setTile(int x, int y) {
        tiles[y][x] = new Tile(x, y);
    }


    private void initPieces() {
        initPlayer1();
        initPlayer2();
    }

    private void initPlayer1() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (y >= BOARD_HEIGHT - 3 && ((x + y) % 2 == 0)) {
                    getTile(x, y).setPiece(new Piece(STANDARD, PLAYER1, new Point(x, y)));
                }
            }
        }
    }

    private void initPlayer2() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (y <= 2 && (x + y) % 2 == 0) {
                    getTile(x, y).setPiece(new Piece(STANDARD, PLAYER2, new Point(x, y)));
                }
            }
        }
    }

    public void changeTurn() {
        if (whoseTurn == PLAYER1) {
            whoseTurn = PLAYER2;
        } else {
            whoseTurn = PLAYER1;
        }
    }
}
