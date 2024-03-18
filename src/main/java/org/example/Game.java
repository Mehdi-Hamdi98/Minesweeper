package org.example;
import java.util.*;
public class Game {
    private final Board board;


    public Game(Board board) {
        this.board = board;
    }
    public void startGame() {
        System.out.println("|---------------MINESWEEPER GAME!---------------|");
        int difficulty = selectDifficulty();
        board.setupBombs(difficulty);

        boolean play = true;
        while (play) {
            board.displayVisible();
            play = playMove();
            if (board.checkWin()) {
                board.displayHidden();
                System.out.println("|--------------Congratulations You Win!--------------|");
                break;
            }
        }
    }
    //Lets user decide difficulty level for the game
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
    //Lets user decide whether to flag or reveal a cell and inputs row and column of cell
    public boolean playMove() {
        Scanner reader = new Scanner(System.in);
        System.out.println("\nEnter 'R' to reveal a cell or 'F' to flag a cell: ");
        String choice = reader.nextLine().toUpperCase();

        if (choice.equals("R")) {
            System.out.print("\nEnter Row Number: ");
            int i = InputValidator.readIntegerInput(reader);
            System.out.print("Enter Column Number: ");
            int j = InputValidator.readIntegerInput(reader);

            if (i < 0 || i > 7 || j < 0 || j > 7) {
                System.out.println("\nIncorrect Input!!");
                return true;
            }

            if (board.getHiddenBoard()[i][j] == 64) {
                board.displayHidden();
                System.out.print("Oh no! You selected a mine!\n============GAME OVER============");
                return false;
            } else if (board.getHiddenBoard()[i][j] == 0) {
                board.updateVisible(i, j);
            } else {
                board.updateNeighbours(i, j);
            }
        } else if (choice.equals("F")) {
            System.out.print("\nEnter Row Number: ");
            int i = InputValidator.readIntegerInput(reader);
            System.out.print("Enter Column Number: ");
            int j = InputValidator.readIntegerInput(reader);

            if (i < 0 || i > 7 || j < 0 || j > 7) {
                System.out.println("\nIncorrect Input!!");
                return true;
            }

            char FLAGGED_CELL = 'F';
            board.getVisibleBoard()[i][j] = FLAGGED_CELL;
        } else {
            System.out.println("Invalid choice. Please enter 'R' to reveal a cell or 'F' to flag a cell.");
        }

        return true;
    }

}
