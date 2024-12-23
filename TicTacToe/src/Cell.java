/**
 * Name: Wiremu Te Moni 
 * Student ID: 5115927
 * Course Code: BIT504
 * Assessment 2
 */
public class Cell {
    Player content; // Content of the cell
    int row, col; // Row and column

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear(); // Initialize cell
    }

    public void clear() {
        content = Player.Empty; // Set the content of the cell to Empty
    }

    public void paint() {
        switch (content) {
            case Cross:
                System.out.print(" X ");
                break;
            case Nought:
                System.out.print(" O ");
                break;
            case Empty:
                System.out.print("   ");
                break;
        }
    }

    public boolean isEmpty() {
        return content == Player.Empty;
    }
}