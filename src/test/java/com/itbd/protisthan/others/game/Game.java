package com.itbd.protisthan.others.game;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final int GRID_SIZE = 5;
    private Player player;
    private Robot[] robots;
    private int[][] board;
    private int playerRow;
    private int playerCol;
    private int[] robotRows;
    private int[] robotCols;
    private Random random;
    private Scanner scanner;

    public Game() {
        this.player = new Player("Hero");
        this.robots = new Robot[]{
                new Robot("Robot1"),
                new Robot("Robot2"),
                new Robot("Robot3")
        };
        this.board = new int[GRID_SIZE][GRID_SIZE];
        this.random = new Random();
        this.scanner = new Scanner(System.in);
// Initialize player position
        playerRow = 0;
        playerCol = 0;
        placeCharacter(player, playerRow, playerCol);
// Initialize robot positions
        robotRows = new int[robots.length];
        robotCols = new int[robots.length];
        for (int i = 0; i < robots.length; i++) {
            int row, col;
            do {
                row = random.nextInt(GRID_SIZE);
                col = random.nextInt(GRID_SIZE);
            } while (board[row][col] == 1); // Ensure the position is not occupied
            robotRows[i] = row;
            robotCols[i] = col;
            placeCharacter(robots[i], row, col);
        }
    }

    private void placeCharacter(GameCharacter character, int row, int col) {
        board[row][col] = 1;
    }

    private void moveCharacter(int oldRow, int oldCol, int newRow, int newCol) {
        board[oldRow][oldCol] = 0;
        board[newRow][newCol] = 1;
    }

    private void printBoard() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i == playerRow && j == playerCol) {
                    System.out.print("P "); // Player position
                } else if (isRobotPosition(i, j)) {
                    System.out.print("R "); // Robot position
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private boolean isRobotPosition(int row, int col) {
        for (int i = 0; i < robots.length; i++) {
            if (robotRows[i] == row && robotCols[i] == col) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        System.out.println("Game started!");
        printBoard();
        while (true) {
// Player's turn
            System.out.println("Enter your move (w/a/s/d): ");
            String move = scanner.nextLine();
            if (makeMove(player, move)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }
// Robots' turn
            for (int i = 0; i < robots.length; i++) {
                makeRandomMove(robots[i], i);
            }
// Check for win/loss condition
            if (checkWin()) {
                System.out.println("You won!");
                break;
            }
            if (checkLoss()) {
                System.out.println("You lost!");
                break;
            }
            printBoard();
        }
    }

    private boolean makeMove(GameCharacter character, String move) {
        int newRow = playerRow;
        int newCol = playerCol;
        switch (move) {
            case "w":
                newRow--;
                break;
            case "a":
                newCol--;
                break;
            case "s":
                newRow++;
                break;
            case "d":
                newCol++;
                break;
            default:
                return true;
        }
        if (newRow < 0 || newRow >= GRID_SIZE || newCol < 0 || newCol >= GRID_SIZE) {
            return true;
        }
        if (board[newRow][newCol] == 1) {
            handleBattle(player, newRow, newCol);
        } else {
            moveCharacter(playerRow, playerCol, newRow, newCol);
            playerRow = newRow;
            playerCol = newCol;
        }
        return false;
    }

    private void makeRandomMove(GameCharacter robot, int index) {
        int oldRow = robotRows[index];
        int oldCol = robotCols[index];
        int newRow, newCol;
        do {
            newRow = oldRow + random.nextInt(3) - 1;
            newCol = oldCol + random.nextInt(3) - 1;
        } while (newRow < 0 || newRow >= GRID_SIZE || newCol < 0 || newCol >= GRID_SIZE || (newRow == oldRow && newCol == oldCol));
        if (newRow == playerRow && newCol == playerCol) {
            handleBattle(robot, newRow, newCol);
        } else if (board[newRow][newCol] == 0) {
            moveCharacter(oldRow, oldCol, newRow, newCol);
            robotRows[index] = newRow;
            robotCols[index] = newCol;
        }
    }

    private void handleBattle(GameCharacter character, int row, int col) {
        GameCharacter opponent = null;
        if (row == playerRow && col == playerCol) {
            opponent = player;
        } else {
            for (int i = 0; i < robots.length; i++) {
                if (robotRows[i] == row && robotCols[i] == col) {
                    opponent = robots[i];
                    break;
                }
            }
        }
        if (opponent != null) {
            character.damageCharacter(opponent);
            System.out.println(opponent.sayDamage());
            if (opponent.getHealth() <= 0) {
                System.out.println(opponent.sayName() + " is defeated!");
                if (opponent instanceof Robot) {
                    removeRobot((Robot) opponent);
                } else if (opponent instanceof Player) {
                    System.out.println("Game Over! Player is defeated.");
                    System.exit(0);
                }
            }
        }
    }

    private void removeRobot(Robot robot) {
        for (int i = 0; i < robots.length; i++) {
            if (robots[i] == robot) {
                robotRows[i] = -1;
                robotCols[i] = -1;
                break;
            }
        }
    }

    private boolean checkWin() {
        int defeatedRobots = 0;
        for (Robot robot : robots) {
            if (robot.getHealth() <= 0) {
                defeatedRobots++;
            }
        }
        return defeatedRobots >= 3;
    }

    private boolean checkLoss() {
        return player.getHealth() <= 0;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}