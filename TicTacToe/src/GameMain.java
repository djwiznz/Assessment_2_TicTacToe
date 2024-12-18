import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

// Enum for game states
enum GameState {
    Playing, Draw, Cross_won, Nought_won
}

// Main game class
public class GameMain extends JPanel implements MouseListener {
    // Constants for game dimensions and settings
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    // Game variables
    private Board board; // The game board
    private GameState currentState; // Current state of the game
    private Player currentPlayer; // Current player
    private JLabel statusBar; // Status message bar
    private static Scanner scanner = new Scanner(System.in); // Input scanner

    // Constructor to set up UI and initialize game
    public GameMain() {
        // Set up mouse listener
        this.addMouseListener(this);

        // Set up status bar
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // Set up layout and add status bar
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Initialize the game
        board = new Board();
        initGame();
    }

    // Initialize game state and board
    private void initGame() {
        board.init(); // Clear the board
        currentState = GameState.Playing; // Set game state to Playing
        currentPlayer = Player.Cross; // Cross starts the game
    }

    // Player's move
    private void playerMove(Player player) {
        boolean validInput = false;
        do {
            if (player == Player.Cross) {
                System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
            }
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board.cells[row][col].isEmpty()) {
                board.cells[row][col].content = player;
                board.currentRow = row;
                board.currentCol = col;
                validInput = true;
            } else {
                System.out.println("This move is not valid. Try again...");
            }
        } while (!validInput);
    }

    // Update game state after a move
    private void updateGame(Player player) {
        if (board.hasWon(player)) {
            currentState = (player == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) {
            currentState = GameState.Draw;
        }
    }

    // Print game result
    private void printGameResult() {
        if (currentState == GameState.Cross_won) {
            System.out.println("'X' won! Bye!");
        } else if (currentState == GameState.Nought_won) {
            System.out.println("'O' won! Bye!");
        } else if (currentState == GameState.Draw) {
            System.out.println("It's a Draw! Bye!");
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        GameMain gamePanel = new GameMain();
        frame.setContentPane(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Handle mouse click
    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentState == GameState.Playing) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            int rowSelected = mouseY / CELL_SIZE;
            int colSelected = mouseX / CELL_SIZE;

            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
                    && board.cells[rowSelected][colSelected].isEmpty()) {
                board.cells[rowSelected][colSelected].content = currentPlayer;
                board.currentRow = rowSelected;
                board.currentCol = colSelected;
                updateGame(currentPlayer);
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
                repaint();
            }
        }
    }

    // Other mouse event handlers (not used)
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
