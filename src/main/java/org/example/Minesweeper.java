package org.example;
import java.util.*;

public class Minesweeper {
    private final int[][] visibleBoard = new int[8][8];
    private final int[][] hiddenBoard = new int[8][8];

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.startGame();
    }
    public void startGame() {
        System.out.println("|---------------MINESWEEPER GAME!---------------|");
        int difficulty = selectDifficulty();
        setupBombs(difficulty);

        boolean play = true;
        while(play) {
            displayVisible();
            play = playMove();
            if(checkWin()) {
                displayHidden();
                System.out.println("|--------------Congratulations You Win!--------------|");
                break;
            }
        }
    }
    public int selectDifficulty() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Select difficulty level:");
        System.out.println("1. Easy (8 bombs)");
        System.out.println("2. Medium (14 bombs)");
        System.out.println("3. Hard (20 bombs)");
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(reader.nextLine());
                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
            }
        }
        return switch (choice) {
            case 2 -> 14;
            case 3 -> 20;
            default -> 8;
        };
    }

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
    public void buildHidden()
    {
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
    public void displayVisible() {
        System.out.print("\t ");
        for (int i = 0; i < 8; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < 8; j++) {
                if (visibleBoard[i][j] == 0) {
                    System.out.print("?");
                } else if (visibleBoard[i][j] == 50) {
                    System.out.print(" ");
                } else if (visibleBoard[i][j] == 70) {
                    System.out.print("F");
                } else {
                    System.out.print(visibleBoard[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
    public boolean playMove() {
        Scanner reader = new Scanner(System.in);
        System.out.println("\nEnter 'R' to reveal a cell or 'F' to flag a cell: ");
        String choice = reader.nextLine().toUpperCase();

        if (choice.equals("R")) {
            System.out.print("\nEnter Row Number: ");
            int i = readIntegerInput(reader);
            System.out.print("Enter Column Number: ");
            int j = readIntegerInput(reader);

            if (i < 0 || i > 7 || j < 0 || j > 7 || visibleBoard[i][j] != 0) {
                System.out.println("\nIncorrect Input!!");
                return true;
            }

            if (hiddenBoard[i][j] == 64) {
                displayHidden();
                System.out.print("Oh no! You selected a mine!\n============GAME OVER============");
                return false;
            } else if (hiddenBoard[i][j] == 0) {
                updateVisible(i, j);
            } else {
                updateNeighbours(i, j);
            }
        } else if (choice.equals("F")) {
            System.out.print("\nEnter Row Number: ");
            int i = readIntegerInput(reader);
            System.out.print("Enter Column Number: ");
            int j = readIntegerInput(reader);

            if (i < 0 || i > 7 || j < 0 || j > 7 || visibleBoard[i][j] != 0) {
                System.out.println("\nIncorrect Input!!");
                return true;
            }

            char FLAGGED_CELL = 'F';
            visibleBoard[i][j] = FLAGGED_CELL;
        } else {
            System.out.println("Invalid choice. Please enter 'R' to reveal a cell or 'F' to flag a cell.");
        }

        return true;
    }

    private int readIntegerInput(Scanner reader) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(reader.nextLine());
                if (input >= 0) {
                    break;
                } else {
                    System.out.println("Please enter a non-negative integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
        return input;
    }
    public void updateVisible(int i, int j)
    {
        visibleBoard[i][j] = 50;
        if(i!=0)
        {
            visibleBoard[i-1][j] = hiddenBoard[i-1][j];
            if(visibleBoard[i-1][j]==0) visibleBoard[i-1][j] = 50;
            if(j!=0)
            {
                visibleBoard[i-1][j-1] = hiddenBoard[i-1][j-1];
                if(visibleBoard[i-1][j-1]==0) visibleBoard[i-1][j-1]=50;

            }
        }
        if(i!=7)
        {
            visibleBoard[i+1][j]=hiddenBoard[i+1][j];
            if(visibleBoard[i+1][j]==0) visibleBoard[i+1][j]=50;
            if(j!=7)
            {
                visibleBoard[i+1][j+1]= hiddenBoard[i+1][j+1];
                if(visibleBoard[i+1][j+1]==0) visibleBoard[i+1][j+1] = 50;
            }
        }
        if(j!=0)
        {
            visibleBoard[i][j-1]=hiddenBoard[i][j-1];
            if(visibleBoard[i][j-1]==0) visibleBoard[i][j-1] = 50;
            if(i!=7)
            {
                visibleBoard[i+1][j-1]=hiddenBoard[i+1][j-1];
                if(visibleBoard[i+1][j-1]==0) visibleBoard[i+1][j-1] = 50;
            }
        }
        if(j!=7)
        {
            visibleBoard[i][j+1]=hiddenBoard[i][j+1];
            if(visibleBoard[i][j+1]==0) visibleBoard[i][j+1] = 50;
            if(i!=0)
            {
                visibleBoard[i-1][j+1]=hiddenBoard[i-1][j+1];
                if(visibleBoard[i-1][j+1]==0) visibleBoard[i-1][j+1] = 50;
            }
        }
    }
    public void updateNeighbours(int i, int j)
    {
        Random random = new Random();
        int x = random.nextInt(4);

        visibleBoard[i][j] = hiddenBoard[i][j];

        if(x==0)
        {
            if(i!=0)
            {
                if(hiddenBoard[i-1][j]!=64)
                {
                    visibleBoard[i-1][j] = hiddenBoard[i-1][j];
                    if(visibleBoard[i-1][j]==0)  visibleBoard[i-1][j] = 50;
                }
            }
            if(j!=0)
            {
                if(hiddenBoard[i][j-1]!=64)
                {
                    visibleBoard[i][j-1] = hiddenBoard[i][j-1];
                    if(visibleBoard[i][j-1]==0)  visibleBoard[i][j-1] = 50;
                }

            }
            if(i!=0 && j!=0)
            {
                if(hiddenBoard[i-1][j-1]!=64)
                {
                    visibleBoard[i-1][j-1] = hiddenBoard[i-1][j-1];
                    if(visibleBoard[i-1][j-1]==0)  visibleBoard[i-1][j-1] = 50;
                }

            }
        }
        else if(x==1)
        {
            if(i!=0)
            {
                if(hiddenBoard[i-1][j]!=64)
                {
                    visibleBoard[i-1][j] = hiddenBoard[i-1][j];
                    if(visibleBoard[i-1][j]==0)  visibleBoard[i-1][j] = 50;
                }
            }
            if(j!=7)
            {
                if(hiddenBoard[i][j+1]!=64)
                {
                    visibleBoard[i][j+1] = hiddenBoard[i][j+1];
                    if(visibleBoard[i][j+1]==0)  visibleBoard[i][j+1] = 50;
                }

            }
            if(i!=0 && j!=7)
            {
                if(hiddenBoard[i-1][j+1]!=64)
                {
                    visibleBoard[i-1][j+1] = hiddenBoard[i-1][j+1];
                    if(visibleBoard[i-1][j+1]==0)  visibleBoard[i-1][j+1] = 50;
                }
            }
        }
        else if(x==2)
        {
            if(i!=7)
            {
                if(hiddenBoard[i+1][j]!=64)
                {
                    visibleBoard[i+1][j] = hiddenBoard[i+1][j];
                    if(visibleBoard[i+1][j]==0)  visibleBoard[i+1][j] = 50;
                }
            }
            if(j!=7)
            {
                if(hiddenBoard[i][j+1]!=64)
                {
                    visibleBoard[i][j+1] = hiddenBoard[i][j+1];
                    if(visibleBoard[i][j+1]==0)  visibleBoard[i][j+1] = 50;
                }

            }
            if(i!=7 && j!=7)
            {
                if(hiddenBoard[i+1][j+1]!=64)
                {
                    visibleBoard[i+1][j+1] = hiddenBoard[i+1][j+1];
                    if(visibleBoard[i+1][j+1]==0)  visibleBoard[i+1][j+1] = 50;
                }
            }
        }
        else
        {
            if(i!=7)
            {
                if(hiddenBoard[i+1][j]!=64)
                {
                    visibleBoard[i+1][j] = hiddenBoard[i+1][j];
                    if(visibleBoard[i+1][j]==0)  visibleBoard[i+1][j] = 50;
                }
            }
            if(j!=0)
            {
                if(hiddenBoard[i][j-1]!=64)
                {
                    visibleBoard[i][j-1] = hiddenBoard[i][j-1];
                    if(visibleBoard[i][j-1]==0)  visibleBoard[i][j-1] = 50;
                }

            }
            if(i!=7 && j!=0)
            {
                if(hiddenBoard[i+1][j-1]!=64)
                {
                    visibleBoard[i+1][j-1] = hiddenBoard[i+1][j-1];
                    if(visibleBoard[i+1][j-1]==0)  visibleBoard[i+1][j-1] = 50;
                }
            }
        }
    }
    public boolean checkWin()
    {
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
    public void displayHidden()
    {
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