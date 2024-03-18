package org.example;

public class Minesweeper {
    public static void main(String[] args) {
        Board board = new Board();
        Game game = new Game(board);
        game.startGame();
    }
}
