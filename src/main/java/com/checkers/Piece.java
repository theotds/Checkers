package com.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.checkers.Constants.*;
import static com.checkers.PieceType.*;
import static com.checkers.Player.*;

public class Piece extends Circle {
    protected PieceType type;
    protected final Player player;
    protected final Point point;

    public Piece(PieceType type, Player player, Point point) {
        this.type = type;
        this.player = player;
        this.point = point;
        setPieceTexture(player, type);
    }

    private void setPieceTexture(Player player, PieceType type) {
        if (type == STANDARD) {
            setRadius(RADIUS);
            setFill(Color.valueOf((player == PLAYER2) ? PLAYER2_COLOR : PLAYER1_COLOR));
            setStroke(Color.BLACK);
            setStrokeWidth(PIECE_STROKE);
        }
        if (type == QUEEN) {
            setRadius(RADIUS);
            setFill(Color.valueOf((player == PLAYER2) ? PLAYER2_COLOR : PLAYER1_COLOR));
            setStroke(Color.GOLD);
            setStrokeWidth(PIECE_STROKE);
        }
    }

    public Piece move(Board board, int x, int y) {
        if (!validate(board, this, x, y) || board.whoseTurn!=getPlayer()) {
            return this;
        }

        Piece target = board.getTile(x, y).getPiece();

        if (validateAttack(board, this, x, y)) {
            normalAttack(board, x, y);
            board.changeTurn();
        }
        if (validateNormalMove(this, x, y)) {
            normalMove(board, x, y);
            board.changeTurn();
        }
        if (validateQueenMove(board, this, x, y)) {
            queenMove(board, x, y);
            board.changeTurn();
        } else if (validateQueenAttack(board, this, x, y)) {
            queenAttack(board, x, y);
            board.changeTurn();
        }

        return target;
    }

    private void queenAttack(Board board, int x, int y) {
        int enemyX = x;
        int enemyY = y;

        int queenX = point.getX();
        int queenY = point.getY();
        if (y < queenY) {
            enemyY++;
            if (queenX < x) {
                enemyX--;
            } else {
                enemyX++;
            }
        } else {
            enemyY--;
            if (queenX < x) {
                enemyX--;
            } else {
                enemyX++;
            }
        }
        deletePiece(board, enemyX, enemyY);
        queenMove(board, x, y);
    }

    private void queenMove(Board board, int x, int y) {
        board.getTile(x, y).setPiece(this);
        board.getTile(point.getX(), point.getY()).setPiece(null);

        point.setX(x);
        point.setY(y);
    }

    private void normalMove(Board board, int x, int y) {
        board.getTile(x, y).setPiece(this);
        board.getTile(point.getX(), point.getY()).setPiece(null);
        point.setX(x);
        point.setY(y);
        if(getPlayer()==PLAYER1&&y==0){
            board.getTile(x,y).getChildren().remove(this);
            board.getTile(x,y).setPiece(new Piece(QUEEN,PLAYER1,new Point(x,y)));
        }
        if(getPlayer()==PLAYER2&&y==BOARD_HEIGHT-1){
            board.getTile(x,y).getChildren().remove(this);
            board.getTile(x,y).setPiece(new Piece(QUEEN,PLAYER2,new Point(x,y)));
        }
    }

    private void normalAttack(Board board, int x, int y) {
        int enemyX = (x + this.getPoint().getX()) / 2;
        int enemyY = (y + this.getPoint().getY()) / 2;

        normalMove(board, x, y);
        deletePiece(board, enemyX, enemyY);
    }

    private static void deletePiece(Board board, int enemyX, int enemyY) {
        Piece deletePiece = board.getTile(enemyX, enemyY).getPiece();
        board.getTile(enemyX, enemyY).getChildren().remove(deletePiece);
        board.getTile(enemyX, enemyY).setPiece(null);
    }

    private boolean validate(Board board, Piece piece, int x, int y) {
        if (board.getTile(x, y).hasPiece() || (x + y) % 2 != 0) {
            return false;
        }

        if (validateNormalMove(piece, x, y)) return true;
        if (validateAttack(board, piece, x, y)) return true;
        if (validateQueenMove(board, piece, x, y)) return true;
        if (validateQueenAttack(board, piece, x, y)) return true;

        return false;
    }

    private static boolean validateNormalMove(Piece piece, int x, int y) {
        if (Math.abs(x - piece.getPoint().getX()) == 1 && y - piece.getPoint().getY() == piece.getPlayer().getDir() && piece.getType() == STANDARD) {
            return true;
        }
        return false;
    }

    private static boolean validateAttack(Board board, Piece piece, int x, int y) {
        if (Math.abs(x - piece.getPoint().getX()) == 2 && (y - piece.getPoint().getY()) / 2 == piece.getPlayer().getDir()) {
            int enemyX = (x + piece.getPoint().getX()) / 2;
            int enemyY = (y + piece.getPoint().getY()) / 2;
            if (board.getTile(enemyX, enemyY).hasPiece() && board.getTile(enemyX, enemyY).getPiece().getPlayer() != piece.getPlayer() && piece.getType() == STANDARD) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateQueenMove(Board board, Piece piece, int x, int y) {
        if (piece.getType() != QUEEN) {
            return false;
        }
        if (!checkMoves(board, piece, x, y)) return false;

        return true;
    }

    private static boolean checkMoves(Board board, Piece piece, int x, int y) {
        int checkPosY = piece.getPoint().getY();
        int checkPosX = piece.getPoint().getX();
        if (y < checkPosY) {
            if (checkMoveUp(board, x, y, checkPosY, checkPosX)) return true;
        }
        //move down
        else {
            if (checkMoveDown(board, x, y, checkPosY, checkPosX)) return true;
        }
        return false;
    }

    private static boolean checkMoveDown(Board board, int x, int y, int checkPosY, int checkPosX) {
        checkPosY += 1;
        if (checkPosX > x) {
            checkPosX -= 1;
            while (checkPosY < y) {
                if (board.getTile(checkPosX, checkPosY).hasPiece()) return false;
                checkPosX--;
                checkPosY++;
            }
        } else {
            checkPosX += 1;
            while (checkPosY < y) {
                if (board.getTile(checkPosX, checkPosY).hasPiece()) return false;
                checkPosX++;
                checkPosY++;
            }
        }
        return true;
    }

    private static boolean checkMoveUp(Board board, int x, int y, int checkPosY, int checkPosX) {
        //prevent checking yourself
        checkPosY -= 1;
        if (checkPosX > x) {
            checkPosX -= 1;
            while (checkPosY > y) {
                if (board.getTile(checkPosX, checkPosY).hasPiece()) return false;
                checkPosX--;
                checkPosY--;
            }
        } else {
            //move right up
            checkPosX += 1;
            while (checkPosY > y) {
                if (board.getTile(checkPosX, checkPosY).hasPiece()) return false;
                checkPosX++;
                checkPosY--;
            }
        }
        return true;
    }

    private static boolean validateQueenAttack(Board board, Piece piece, int x, int y) {
        if (piece.getType() != QUEEN) {
            return false;
        }
        int checkPosY = piece.getPoint().getY();
        int checkPosX = piece.getPoint().getX();

        if (y < checkPosY) {
            //right
            if (checkPosX < x) {
                //ENEMY EXIST
                if (!board.getTile(x - 1, y + 1).hasPiece()) {
                    return false;
                }
                //check if other enemy exist in the way
                if (checkMoveUp(board, x - 1, y + 1, checkPosY, checkPosX))return true;

            } else {
                //ENEMY EXIST
                if (!board.getTile(x + 1, y + 1).hasPiece()) {
                    return false;
                }
                //check if other enemy exist in the way
                if (checkMoveUp(board, x + 1, y + 1, checkPosY, checkPosX)) return true;
            }
        }
        //move down
        else {
            //right
            if (checkPosX < x) {
                //ENEMY EXIST
                if (!board.getTile(x - 1, y - 1).hasPiece()) {
                    return false;
                }
                //check if other enemy exist in the way
                if (checkMoveDown(board, x - 1, y - 1, checkPosY, checkPosX)) return true;
            } else {
                //ENEMY EXIST
                if (!board.getTile(x + 1, y - 1).hasPiece()) {
                    return false;
                }
                //check if other enemy exist in the way
                if (checkMoveDown(board, x + 1, y - 1, checkPosY, checkPosX)) return true;
            }
        }
        return false;
    }


    public void setType(PieceType type) {
        this.type=type;
    }

    public PieceType getType() {
        return type;
    }

    public Point getPoint() {
        return point;
    }

    public Player getPlayer() {
        return player;
    }
}
