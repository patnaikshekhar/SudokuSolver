package com.shekharpatnaik.sudokusolver.solver;

/**
 * Created by shpatnaik on 3/21/14.
 * This is a helper class which represents a Sudoku Square
 */
public class SudokuSquare {

    private int value;
    private int row;
    private int column;
    private int quadrant;
    private boolean fixed;

    public SudokuSquare(int value, int row, int column, int quadrant, boolean fixed) {
        this.value = value;
        this.row = row;
        this.column = column;
        this.quadrant = quadrant;
        this.fixed = fixed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(int quadrant) {
        this.quadrant = quadrant;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}