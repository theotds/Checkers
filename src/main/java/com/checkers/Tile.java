package com.checkers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.checkers.Constants.*;

public class Tile extends StackPane {

    private Piece piece;

    private Rectangle tileBackground;

    public Tile(int x, int y) {
        relocate(x * TILE_SIZE, y * TILE_SIZE);
        tileBackground = new Rectangle(TILE_SIZE, TILE_SIZE, ((x + y) % 2 == 0) ? Color.valueOf(BOARD_COLOR) : Color.WHITE);
        getChildren().add(tileBackground);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            getChildren().remove(piece);
            getChildren().add(piece);
        }

    }

    public void selectTileBackground() {
        tileBackground.setFill(Color.GRAY);
    }

    public void unSelectTileBackground() {
        tileBackground.setFill(Color.valueOf(BOARD_COLOR));
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return piece!=null;
    }
}
