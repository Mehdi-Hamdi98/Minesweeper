package org.example;
import java.util.*;
public class Board {
    private final int[][] visibleBoard;
    private final int[][] hiddenBoard;
    public Board() {
        visibleBoard = new int[8][8];
        hiddenBoard = new int[8][8];
    }
    public int[][] getHiddenBoard() {
        return hiddenBoard;
    }
    public int[][] getVisibleBoard() {
        return visibleBoard;
    }

    //places selected number of bombs randomly in the board
    public void setupBombs(int numBombs) {
        int var = 0;
        while (var != numBombs) {
            Random random = new Random();
            int i = random.nextInt(8);
            int j = random.nextInt(8);

            if (hiddenBoard[i][j] != 64) {
                hiddenBoard[i][j] = 64;
                var++;
            }
        }
        buildHidden();
    }
    //populates the hidden board with the number of adjacent bombs for each cell that is not a bomb itself
    public void buildHidden() {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                int count=0;
                if(hiddenBoard[i][j]!=64)
                {

                    if(i!=0)
                    {
                        if(hiddenBoard[i-1][j]==64) count++;
                        if(j!=0)
                        {
                            if(hiddenBoard[i-1][j-1]==64) count++;
                        }

                    }
                    if(i!=7)
                    {
                        if(hiddenBoard[i+1][j]==64) count++;
                        if(j!=7)
                        {
                            if(hiddenBoard[i+1][j+1]==64) count++;
                        }
                    }
                    if(j!=0)
                    {
                        if(hiddenBoard[i][j-1]==64) count++;
                        if(i!=7)
                        {
                            if(hiddenBoard[i+1][j-1]==64) count++;
                        }
                    }
                    if(j!=7)
                    {
                        if(hiddenBoard[i][j+1]==64) count++;
                        if(i!=0)
                        {
                            if(hiddenBoard[i-1][j+1]==64) count++;
                        }
                    }

                    hiddenBoard[i][j] = count;
                }
            }
        }

    }
    //Displays the board in the CLI
    public void displayVisible() {
        System.out.print("\t ");
        for (int i = 0; i < 8; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < 8; j++) {
                if (visibleBoard[i][j] == 0) { //Check if it's an undiscovered cell
                    System.out.print("?");
                } else if (visibleBoard[i][j] == 50) { //Check if it's an empty cell
                    System.out.print(" ");
                } else if (visibleBoard[i][j] == 70) { //Check if it's a flagged cell
                    System.out.print("F");
                } else if (hiddenBoard[i][j] == 64) { // Check if it's a bomb cell
                    System.out.print("?"); // Hide bomb cells
                } else {
                    System.out.print(visibleBoard[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
    //Updates the visible board after a player chooses to reveal a cell
    public void updateVisible(int i, int j) {
        if (hiddenBoard[i][j] != 64) { // Check if the cell is not a bomb
            visibleBoard[i][j] = 50;
            if (i != 0) {
                visibleBoard[i - 1][j] = hiddenBoard[i - 1][j];
                if (visibleBoard[i - 1][j] == 0) visibleBoard[i - 1][j] = 50;
                if (j != 0) {
                    visibleBoard[i - 1][j - 1] = hiddenBoard[i - 1][j - 1];
                    if (visibleBoard[i - 1][j - 1] == 0) visibleBoard[i - 1][j - 1] = 50;
                }
            }
            if (i != 7) {
                visibleBoard[i + 1][j] = hiddenBoard[i + 1][j];
                if (visibleBoard[i + 1][j] == 0) visibleBoard[i + 1][j] = 50;
                if (j != 7) {
                    visibleBoard[i + 1][j + 1] = hiddenBoard[i + 1][j + 1];
                    if (visibleBoard[i + 1][j + 1] == 0) visibleBoard[i + 1][j + 1] = 50;
                }
            }
            if (j != 0) {
                visibleBoard[i][j - 1] = hiddenBoard[i][j - 1];
                if (visibleBoard[i][j - 1] == 0) visibleBoard[i][j - 1] = 50;
                if (i != 7) {
                    visibleBoard[i + 1][j - 1] = hiddenBoard[i + 1][j - 1];
                    if (visibleBoard[i + 1][j - 1] == 0) visibleBoard[i + 1][j - 1] = 50;
                }
            }
            if (j != 7) {
                visibleBoard[i][j + 1] = hiddenBoard[i][j + 1];
                if (visibleBoard[i][j + 1] == 0) visibleBoard[i][j + 1] = 50;
                if (i != 0) {
                    visibleBoard[i - 1][j + 1] = hiddenBoard[i - 1][j + 1];
                    if (visibleBoard[i - 1][j + 1] == 0) visibleBoard[i - 1][j + 1] = 50;
                }
            }
        }
    }
    //reveals adjacent cells recursively
    public void updateNeighbours(int i, int j) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int neighbourX = i + x;
                int neighbourY = j + y;

                // Skip the current cell itself
                if (x == 0 && y == 0) continue;
                // Check if the neighbour is within bounds
                if (neighbourX >= 0 && neighbourX < 8 && neighbourY >= 0 && neighbourY < 8) {
                    // Skip revealing bombs
                    if (hiddenBoard[neighbourX][neighbourY] == 64) continue;
                    // Update the neighbour if it's not already revealed
                    if (visibleBoard[neighbourX][neighbourY] == 0) {
                        updateVisible(neighbourX, neighbourY);
                    }
                }
            }
        }
    }
    //checks if every cell revealed isn't a bomb
    public boolean checkWin() {
        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                if(visibleBoard[i][j]==0)
                {
                    if(hiddenBoard[i][j]!=64)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void displayHidden() {
        System.out.print("\t ");
        for(int i=0; i<8; i++)
        {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for(int i=0; i<8; i++)
        {
            System.out.print(i + "\t| ");
            for(int j=0; j<8; j++)
            {
                if(hiddenBoard[i][j]==0)
                {
                    System.out.print(" ");
                }
                else if(hiddenBoard[i][j]==64)
                {
                    System.out.print("X");
                }
                else
                {
                    System.out.print(hiddenBoard[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
}
