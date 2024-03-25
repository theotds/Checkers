package com.checkers;

import javafx.stage.Screen;

public class Constants {
    public static final int BOARD_HEIGHT = 8;
    public static final int BOARD_WIDTH = 8;
    public static final double MONITOR_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final double MONITOR_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double TILE_SIZE = MONITOR_HEIGHT / (BOARD_HEIGHT*2);
    public static final double RADIUS = (TILE_SIZE/2) * 0.80;
    public static final float PIECE_STROKE = 4;

    public static final String PLAYER1_COLOR = "#4c47ad";
    public static final String PLAYER2_COLOR = "#FFFDD0";
    public static final String BOARD_COLOR = "#769656";
    public static final String BACKGROUND_COLOR = "#1E1E1E";

}
