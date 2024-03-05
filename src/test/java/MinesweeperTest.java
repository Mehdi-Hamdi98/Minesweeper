import org.junit.jupiter.api.*;
import org.example.Minesweeper;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {
//    @Test
//    public void testSelectDifficulty_Easy() {
//        Minesweeper minesweeper = new Minesweeper();
//        int difficulty = minesweeper.selectDifficulty();
//        assertEquals(8, difficulty, "Easy difficulty does not have 8 bombs");
//    }
//
//    @Test
//    public void testSelectDifficulty_Medium() {
//        Minesweeper minesweeper = new Minesweeper();
//        int difficulty = minesweeper.selectDifficulty();
//        assertEquals(14, difficulty, "Medium difficulty does not have 14 bombs");
//    }
//
//    @Test
//    public void testSelectDifficulty_Hard() {
//        Minesweeper minesweeper = new Minesweeper();
//        int difficulty = minesweeper.selectDifficulty();
//        assertEquals(20, difficulty, "Hard difficulty does not have 20 bombs");
//    }
@Test
public void testVisibleBoardDimensions() {
    Minesweeper minesweeper = new Minesweeper();

    assertEquals(8, minesweeper.visibleBoard.length);
    assertEquals(8, minesweeper.visibleBoard[0].length);
}
    @Test
    public void testHiddenBoardDimensions() {
        Minesweeper minesweeper = new Minesweeper();

        assertEquals(8, minesweeper.hiddenBoard.length);
        assertEquals(8, minesweeper.hiddenBoard[0].length);
    }

    @Test
    public void testCheckWinAllCellsUncovered() {
        Minesweeper minesweeper = new Minesweeper();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                minesweeper.visibleBoard[i][j] = 50;
            }
        }
        assertTrue(minesweeper.checkWin());
    }
    @Test
    public void testCheckWinSomeCellsCovered() {
        Minesweeper minesweeper = new Minesweeper();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                minesweeper.visibleBoard[i][j] = 0;
            }
        }
        assertFalse(minesweeper.checkWin());
    }
    @Test
    public void testSetupBombsEasyDifficulty() {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.setupBombs(8);
        int bombCount = 0;
        for (int[] row : minesweeper.hiddenBoard) {
            for (int cell : row) {
                if (cell == 64) {
                    bombCount++;
                }
            }
        }
        assertEquals(8, bombCount);
    }
    @Test
    public void testSetupBombsMediumDifficulty() {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.setupBombs(14);
        int bombCount = 0;
        for (int[] row : minesweeper.hiddenBoard) {
            for (int cell : row) {
                if (cell == 64) {
                    bombCount++;
                }
            }
        }
        assertEquals(14, bombCount);
    }

    @Test
    public void testSetupBombsHardDifficulty() {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.setupBombs(20);
        int bombCount = 0;
        for (int[] row : minesweeper.hiddenBoard) {
            for (int cell : row) {
                if (cell == 64) {
                    bombCount++;
                }
            }
        }
        assertEquals(20, bombCount);
    }




}