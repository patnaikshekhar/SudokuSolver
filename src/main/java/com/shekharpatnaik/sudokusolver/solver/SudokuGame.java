package com.shekharpatnaik.sudokusolver.solver;

import javax.naming.CannotProceedException;

/**
 * Created by shpatnaik on 3/21/14.
 * This is a Sudoku Game solver
 */
public class SudokuGame {

    private final static int BOARD_SIZE = 9;
    private SudokuSquare[][] board;

    private boolean[][] rowTracker;
    private boolean[][] colTracker;
    private boolean[][] quadTracker;

    /**
     * Create sudoku squares using the string data
     * @param board The string representation of the board
     */
    public SudokuGame(String[] board) {
        this.board = new SudokuSquare[BOARD_SIZE][BOARD_SIZE];
        this.rowTracker = new boolean[BOARD_SIZE][BOARD_SIZE + 1];
        this.colTracker = new boolean[BOARD_SIZE][BOARD_SIZE + 1];
        this.quadTracker = new boolean[BOARD_SIZE][BOARD_SIZE + 1];

        for (int row = 0; row < board.length; row++) {

            String cols[] = board[row].split("\\s");
            for (int col = 0; col < cols.length; col++) {

                int quad = ((row / 3) * 3) + (col / 3);
                int value = Integer.valueOf(cols[col]);

                SudokuSquare newSquare = new SudokuSquare(value, row, col, quad, value != 0);
                this.board[row][col] = newSquare;

                if (value != 0) {
                    this.rowTracker[row][value] = true;
                    this.colTracker[col][value] = true;
                    this.quadTracker[quad][value] = true;
                }
            }
        }
    }

    /**
     * This solves the Sudoku Game using backtracking. Tries the next possible combination of each square until it reaches the
     * end. If it cannot solve the puzzle then it throws a CannotProceedException
     * @throws CannotProceedException
     */
    public void solve() throws CannotProceedException {

        int row = 0;
        while (row < this.board.length) {

            int col = 0;
            boolean backtrack = false;

            while (col < this.board[row].length) {

                /*System.out.println("row = " + row + " , col = " + col);
                System.out.println(this);
                System.out.println("__________________________________");*/

                if(!this.board[row][col].isFixed()) {

                    int currentValue = this.board[row][col].getValue();
                    int quad = this.board[row][col].getQuadrant();

                    this.rowTracker[row][currentValue] = false;
                    this.colTracker[col][currentValue] = false;
                    this.quadTracker[quad][currentValue] = false;

                    Integer possibleValue = getNextPossibleValue(row, col, quad, currentValue);
                    this.board[row][col].setValue(0);

                    if (possibleValue != null) {
                        this.board[row][col].setValue(possibleValue);
                        this.rowTracker[row][possibleValue] = true;
                        this.colTracker[col][possibleValue] = true;
                        this.quadTracker[quad][possibleValue] = true;
                        col++;
                        backtrack = false;
                    } else {
                        if (col == 0) {
                            if (row == 0) {
                                throw new CannotProceedException();
                            } else {
                                row--;
                                col = this.board[row].length - 1;
                                backtrack = true;
                            }
                        } else {
                            col--;
                            backtrack = true;
                        }
                    }
                } else {
                    if (backtrack) {
                        if (col == 0) {
                            if (row == 0) {
                                throw new CannotProceedException();
                            } else {
                                row--;
                                col = this.board[row].length - 1;
                                backtrack = true;
                            }
                        } else {
                            col--;
                            backtrack = true;
                        }
                    } else {
                        col++;
                        backtrack = false;
                    }
                }
            }
            row++;
        }
    }

    /**
     * Used by the backtracking algorithm to return the next possible value
     * @param row The row number
     * @param col The column number
     * @param quad The quadrant number
     * @param currentValue The current value of the square
     * @return The next possible value. Returns null if no such possible value is found.
     */
    private Integer getNextPossibleValue(int row, int col, int quad, int currentValue) {
        Integer number = null;

        for (int i = currentValue + 1; i < 10; i++) {
            if (!this.rowTracker[row][i] && !this.colTracker[col][i] && !this.quadTracker[quad][i]) {
                number = i;
                break;
            }
        }

        return number;
    }

    /**
     * Return the String representation
     * @return A string representation of the board
     */
    public String toString() {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < this.board.length; i++) {
            StringBuffer row = new StringBuffer();
            for (int j = 0; j < this.board[i].length; j++) {

                if (j != 0) {
                    row.append(" ");
                }

                row.append(this.board[i][j].getValue());
            }
            row.append("\n");
            result.append(row);
        }

        return result.toString();
    }
}
