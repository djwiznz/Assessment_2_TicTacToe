/**
 * Name: Wiremu Te Moni 
 * Student ID: 5115927
 * Course Code: BIT504
 * Assessment 2
 */
import java.util.Scanner;

enum GameState {
    Playing, Draw, Cross_won, Nought_won
}

public class GameMain {
    private Board board;                // The game board
    private GameState currentState;     // The current state of the game
    private Player currentPlayer;       // The current player
    private static Scanner scanner = new Scanner(System.in); // Input scanner

    public GameMain() {
        boolean playAgain;
        do {
            board = new Board(); // Initialize the game board
            initGame();          // Initialize the game state and board
            do {
                playerMove(currentPlayer);  // Update the board based on player input
                board.paint();              // Show the updated board
                updateGame(currentPlayer);  // Update the game state
                if (currentState == GameState.Playing) {
                    currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
                }
            } while (currentState == GameState.Playing);  // Continue until the game ends
            
            printGameResult();
            playAgain = askToRestart();
        } while (playAgain); // Restart the game if the user chooses 'Y'
        
        System.out.println("Thank you for playing Tic Tac Toe! Goodbye.");
    }

    /**
     * Initializes the game state and starting player.
     */
    private void initGame() {
        board.init();                    // Clear the board
        currentState = GameState.Playing; // Set the game state to Playing
        currentPlayer = Player.Cross;    // Cross always starts
    }

    /**
     * Handles player moves with input validation.
     */
    private void playerMove(Player player) {
        boolean validInput = false;
        do {
            System.out.println("\nTo make a move, type the row and column numbers separated by a space (e.g., '1 1' for the top-left cell).");
            if (player == Player.Cross) {
                System.out.print("Player 'X', enter your move: ");
            } else {
                System.out.print("Player 'O', enter your move: ");
            }
            
            if (scanner.hasNextInt()) {
                int row = scanner.nextInt() - 1;  // Array index starts at 0
                int col = scanner.nextInt() - 1;
                if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS && board.cells[row][col].isEmpty()) {
                    board.cells[row][col].content = player; // Update the board
                    board.currentRow = row;
                    board.currentCol = col;
                    validInput = true; // Input is valid
                } else {
                    System.out.println("This move is not valid. Try again...");
                }
            } else {
                System.out.println("Invalid input! Please enter two numbers (row and column) separated by a space.");
                scanner.nextLine(); // Clear invalid input
            }
        } while (!validInput); // Repeat until the input is valid
    }

    /**
     * Updates the game state after a player's move.
     */
    private void updateGame(Player player) {
        if (board.hasWon(player)) {  // Check if the player has won
            currentState = (player == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) { // Check for a draw
            currentState = GameState.Draw;
        }
    }

    /**
     * Displays the game result.
     */
    private void printGameResult() {
        if (currentState == GameState.Cross_won) {
            System.out.println("\nCongratulations! Player 'X' won! 🎉");
        } else if (currentState == GameState.Nought_won) {
            System.out.println("\nCongratulations! Player 'O' won! 🎉");
        } else if (currentState == GameState.Draw) {
            System.out.println("\nIt's a Draw! Well played both players! 🤝");
        }
    }

    /**
     * Asks the user if they want to restart the game.
     * @return true if the user wants to restart, false otherwise.
     */
    private boolean askToRestart() {
        String userInput;
        do {
            System.out.print("\nDo you want to play again? (Y/N): ");
            userInput = scanner.next().trim().toUpperCase();
            if (userInput.equals("Y")) {
                return true;
            } else if (userInput.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input! Please enter 'Y' for Yes or 'N' for No.");
            }
        } while (true); // Loop until valid input is received
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        new GameMain(); // Run the game
    }
}
