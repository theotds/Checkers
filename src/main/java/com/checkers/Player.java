package com.checkers;

public enum Player {
    PLAYER1(-1),PLAYER2(1);
    final int dir;

    Player(int dir){
        this.dir=dir;
    }

    public int getDir() {
        return dir;
    }
}
