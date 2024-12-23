/**
 * Name: Wiremu Te Moni 
 * Student ID: 5115927
 * Course Code: BIT504
 * Assessment 2
 */
public class Board {
    public static final int ROWS = 3; // Number of rows
    public static final int COLS = 3; // Number of columns

    Cell[][] cells; // 2D array representing the grid
    int currentRow, currentCol; // Row and column of the last move

    public Board() {
        cells = new Cell[ROWS][COLS]; // Create the grid
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col); // Create cell objects
            }
        }
    }

    public void init() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].clear(); // Reset each cell
            }
        }
    }

    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].isEmpty()) {
                    return false; // If any cell is empty, it’s not a draw
                }
            }
        }
        return true; // All cells are filled
    }

    public boolean hasWon(Player player) {
        return (cells[currentRow][0].content == player // Row match
                && cells[currentRow][1].content == player
                && cells[currentRow][2].content == player
                || cells[0][currentCol].content == player // Column match
                && cells[1][currentCol].content == player
                && cells[2][currentCol].content == player
                || currentRow == currentCol // Diagonal match
                && cells[0][0].content == player
                && cells[1][1].content == player
                && cells[2][2].content == player
                || currentRow + currentCol == 2 // Opposite diagonal
                && cells[0][2].content == player
                && cells[1][1].content == player
                && cells[2][0].content == player);
    }

    public void paint() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(); // Paint each cell
                if (col < COLS - 1) System.out.print("|");
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("-----------");
            }
        }
    }
}