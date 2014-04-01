import com.shekharpatnaik.sudokusolver.solver.SudokuGame;
import org.junit.Test;

import javax.naming.CannotProceedException;

import static org.junit.Assert.*;

/**
 * Created by shpatnaik on 3/21/14.
 */
public class TestSudoku {

    @Test
    public void SolveSudokuPuzzle_EasyPuzzleIsSolved_ReturnsSolution() throws Exception {
        SudokuGame game = getStandardEasyGame();
        game.solve();

        String result = "";

        result += "2 9 5 8 6 3 7 1 4\n";
        result += "4 8 3 7 2 1 5 6 9\n";
        result += "7 6 1 5 4 9 8 2 3\n";
        result += "9 3 7 1 8 6 4 5 2\n";
        result += "1 5 4 2 3 7 6 9 8\n";
        result += "8 2 6 9 5 4 3 7 1\n";
        result += "5 1 9 3 7 8 2 4 6\n";
        result += "6 7 8 4 9 2 1 3 5\n";
        result += "3 4 2 6 1 5 9 8 7\n";

        assertEquals(result, game.toString());
    }

    @Test
    public void SolveSudokuPuzzle_HardPuzzleIsSolved_ReturnsSolution() throws Exception {
        SudokuGame game = getStandardHardGame();
        game.solve();

        String result = "";

        result += "9 3 5 6 2 4 8 7 1\n";
        result += "4 7 6 8 1 3 5 2 9\n";
        result += "1 2 8 7 9 5 6 3 4\n";
        result += "5 6 1 9 8 2 7 4 3\n";
        result += "2 8 3 4 6 7 9 1 5\n";
        result += "7 4 9 3 5 1 2 6 8\n";
        result += "6 5 2 1 3 9 4 8 7\n";
        result += "3 9 7 2 4 8 1 5 6\n";
        result += "8 1 4 5 7 6 3 9 2\n";

        assertEquals(result, game.toString());
    }

    @Test(expected = CannotProceedException.class)
    public void SolveSudokuPuzzle_ImpossiblePuzzle_ThrowsError() throws Exception {
        SudokuGame game =  getImpossibleGame();
        game.solve();
    }

    @Test
    public void ToString_CorrectStringRepresentationIsReturned_ReturnsString() throws Exception {
        SudokuGame game = getStandardEasyGame();

        String result = "";

        result += "0 0 5 0 6 0 7 0 4\n";
        result += "0 8 0 0 0 1 0 6 9\n";
        result += "7 0 0 5 4 0 8 0 0\n";
        result += "9 3 0 0 8 6 0 0 0\n";
        result += "1 0 4 0 0 0 6 0 8\n";
        result += "0 0 0 9 5 0 0 7 1\n";
        result += "0 0 9 0 7 8 0 0 6\n";
        result += "6 7 0 4 0 0 0 3 0\n";
        result += "3 0 2 0 1 0 9 0 0\n";

        assertEquals(result, game.toString());
    }

    private SudokuGame getStandardEasyGame() {
        String board[] = new String[9];

        board[0] = "0 0 5 0 6 0 7 0 4";
        board[1] = "0 8 0 0 0 1 0 6 9";
        board[2] = "7 0 0 5 4 0 8 0 0";
        board[3] = "9 3 0 0 8 6 0 0 0";
        board[4] = "1 0 4 0 0 0 6 0 8";
        board[5] = "0 0 0 9 5 0 0 7 1";
        board[6] = "0 0 9 0 7 8 0 0 6";
        board[7] = "6 7 0 4 0 0 0 3 0";
        board[8] = "3 0 2 0 1 0 9 0 0";

        SudokuGame game = new SudokuGame(board);
        return game;
    }

    private SudokuGame getImpossibleGame() {
        String board[] = new String[9];

        board[0] = "0 0 5 0 6 0 7 0 4";
        board[1] = "2 8 0 0 0 1 0 6 9";
        board[2] = "7 0 0 5 4 0 8 0 0";
        board[3] = "9 3 0 0 8 6 0 0 0";
        board[4] = "1 0 4 0 0 0 6 0 8";
        board[5] = "0 0 0 9 5 0 0 7 1";
        board[6] = "0 0 9 0 7 8 0 0 6";
        board[7] = "6 7 0 4 0 0 0 3 0";
        board[8] = "3 0 2 0 1 0 9 0 0";

        SudokuGame game = new SudokuGame(board);
        return game;
    }

    private SudokuGame getStandardHardGame() {
        String board[] = new String[9];

        board[0] = "9 3 0 0 0 0 8 0 0";
        board[1] = "4 0 0 8 0 0 0 0 0";
        board[2] = "0 0 8 7 0 5 0 3 0";
        board[3] = "0 0 0 0 0 0 7 4 0";
        board[4] = "0 8 3 4 0 7 9 1 0";
        board[5] = "0 4 9 0 0 0 0 0 0";
        board[6] = "0 5 0 1 0 9 4 0 0";
        board[7] = "0 0 0 0 0 8 0 0 6";
        board[8] = "0 0 4 0 0 0 0 9 2";

        SudokuGame game = new SudokuGame(board);
        return game;
    }
}
