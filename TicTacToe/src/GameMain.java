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
    }

    private void initGame() {
        board.init();                    // Clear the board
        currentState = GameState.Playing; // Set the game state to Playing
        currentPlayer = Player.Cross;    // Cross always starts
    }

    private void playerMove(Player player) {
        boolean validInput = false;
        do {
            if (player == Player.Cross) {
                System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
            }
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
        } while (!validInput); // Repeat until the input is valid
    }

    private void updateGame(Player player) {
        if (board.hasWon(player)) {  // Check if the player has won
            currentState = (player == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) { // Check for a draw
            currentState = GameState.Draw;
        }
    }

    private void printGameResult() {
        if (currentState == GameState.Cross_won) {
            System.out.println("'X' won! Bye!");
        } else if (currentState == GameState.Nought_won) {
            System.out.println("'O' won! Bye!");
        } else if (currentState == GameState.Draw) {
            System.out.println("It's a Draw! Bye!");
        }
    }

    public static void main(String[] args) {
        new GameMain(); // Run the game
    }
}
