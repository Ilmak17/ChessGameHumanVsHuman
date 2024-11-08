package com.game.chess.pieces;

import com.game.chess.pieces.enums.Color;
import com.game.chess.board.Board;

import static java.util.Objects.isNull;

public abstract class Piece implements Movement {

    private Position position;
    private Color color;
    private Boolean isCaptured = Boolean.FALSE;
    private Board board;
    private boolean moved;

    Piece(Board board, Color color, Position position) {
        this.board = board;
        this.color = color;
        this.position = position;
    }

    @Override
    public void move(Position position) {
        validateMove(position);

        if (board.pieceExistsAt(position) && board.isNotPieceColor(position, getColor())) {
            board.capture(position);
        }

        setPosition(position);
        setMoved(true);
    }

    @Override
    public void forceMove(Position position) {
        setPosition(position);
    }

    protected void validateMove(Position position) {
        if (getPosition().equals(position)) {
            throw new IllegalArgumentException("Invalid move: Position cannot be the same as the current position");
        }

        if (Boolean.TRUE.equals(isCaptured)) {
            throw new IllegalArgumentException("Invalid move: Piece is captured. Please try again.");
        }

        if (!isValidMove(position)) {
            throw new IllegalArgumentException("Invalid move: Piece move is invalid. Please try again.");
        }
    }

    public abstract boolean canAttack(Position destPosition);

    public abstract String getPieceType();

    public abstract String getSymbol();

    protected boolean isDestinationAvailable(Position destPosition) {
        Piece targetPiece = board.getPieceByPosition(destPosition);

        return isNull(targetPiece) || !targetPiece.getColor().equals(getColor());
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean getCaptured() {
        return isCaptured;
    }

    public void setCaptured(Boolean captured) {
        isCaptured = captured;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
