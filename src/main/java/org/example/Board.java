package org.example;
import java.util.*;
public class Board {
    private final int[][] visibleBoard;
    private final int[][] hiddenBoard;
    private final boolean[][] flaggedCells;
    private final boolean[][] visited;
    public static final int UNDISCOVERED = 0;
    public static final int BOMB = 64;
    public static final int FLAGGED_CELL = 70;
    public static final int EMPTY_CELL = 50;
    public static final int BOARD_SIZE = 8;

    public Board() {
        visibleBoard = new int[BOARD_SIZE][BOARD_SIZE];
        hiddenBoard = new int[BOARD_SIZE][BOARD_SIZE];
        flaggedCells = new boolean[BOARD_SIZE][BOARD_SIZE];
        visited = new boolean[BOARD_SIZE][BOARD_SIZE];
    }
    public int[][] getHiddenBoard() {
        return hiddenBoard;
    }
    public int[][] getVisibleBoard() {
        return visibleBoard;
    }
    // Places selected number of bombs randomly on the board
    public void setupBombs(int numBombs) {
        int bombsPlaced = 0;
        while (bombsPlaced != numBombs) {
            Random random = new Random();
            int i = random.nextInt(BOARD_SIZE);
            int j = random.nextInt(BOARD_SIZE);

            if (hiddenBoard[i][j] != BOMB) {
                hiddenBoard[i][j] = BOMB;
                bombsPlaced++;
            }
        }
        buildHidden();
    }
    // Populates the hidden board with the number of adjacent bombs for each cell that is not a bomb itself
    public void buildHidden() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int count = 0;
                if (hiddenBoard[i][j] != BOMB) {
                    if (i != 0) {
                        if (hiddenBoard[i - 1][j] == BOMB) count++;
                        if (j != 0) {
                            if (hiddenBoard[i - 1][j - 1] == BOMB) count++;
                        }
                    }
                    if (i != 7) {
                        if (hiddenBoard[i + 1][j] == BOMB) count++;
                        if (j != 7) {
                            if (hiddenBoard[i + 1][j + 1] == BOMB) count++;
                        }
                    }
                    if (j != 0) {
                        if (hiddenBoard[i][j - 1] == BOMB) count++;
                        if (i != 7) {
                            if (hiddenBoard[i + 1][j - 1] == BOMB) count++;
                        }
                    }
                    if (j != 7) {
                        if (hiddenBoard[i][j + 1] == BOMB) count++;
                        if (i != 0) {
                            if (hiddenBoard[i - 1][j + 1] == BOMB) count++;
                        }
                    }
                    hiddenBoard[i][j] = count;
                }
            }
        }
    }
    // Displays the board in the CLI
    public void displayVisible() {
        System.out.print("\t ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (visibleBoard[i][j] == UNDISCOVERED) { // Check if it's an undiscovered cell
                    System.out.print("-");
                } else if (visibleBoard[i][j] == EMPTY_CELL) { // Check if it's an empty cell
                    System.out.print(" ");
                } else if (visibleBoard[i][j] == FLAGGED_CELL) { // Check if it's a flagged cell
                    System.out.print("F");
                } else if (hiddenBoard[i][j] == BOMB) { // Check if it's a bomb cell
                    System.out.print("?"); // Hide bomb cells
                } else {
                    System.out.print(visibleBoard[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
    // Updates the visible board after a player chooses to reveal a cell
    public void revealCell(int i, int j) {
        if (!isValidCell(i, j) || flaggedCells[i][j] || visited[i][j]) {
            return; // Exit if cell is out of bounds, flagged, or already visited
        }
        visited[i][j] = true; // Mark the current cell as visited

        if (hiddenBoard[i][j] == UNDISCOVERED) {
            visibleBoard[i][j] = EMPTY_CELL; //

            // Recursively reveal neighbouring empty cells
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    revealCell(i + x, j + y);
                }
            }
        } else {
            visibleBoard[i][j] = hiddenBoard[i][j]; // Reveal current cell
        }
    }
    // Checks if every cell revealed isn't a bomb
    public boolean checkWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (visibleBoard[i][j] == UNDISCOVERED && hiddenBoard[i][j] != BOMB) {
                    return false;
                }
            }
        }
        return true;
    }
    public void displayHidden() {
        System.out.println("Hidden Board:");
        System.out.print("\t ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (flaggedCells[i][j]) { // If cell is flagged, show 'F'
                    System.out.print("F | ");
                } else { // Otherwise, show the content of the cell
                    if (hiddenBoard[i][j] == BOMB) { // If cell contains a bomb
                        System.out.print("X | "); // Show bomb
                    } else {
                        System.out.print(hiddenBoard[i][j] + " | "); // Show number of adjacent bombs
                    }
                }
            }
            System.out.println();
        }
    }
    // Helper method to check if a cell is within the bounds of the board
    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }
}
