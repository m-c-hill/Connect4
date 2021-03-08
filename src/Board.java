import java.util.Stack;

/**
 * Connect 4 board - contains all the method needed to add counters to board and check if a winning move has been
 * played.
 */

public class Board{

    private final int nRows;
    private final int nColumns;
    private final int nCountersTotal;
    private int nCountersPlaced; // Track the number of counters that have been played so we can check for a full board.
    private Counter[][] board;
    // Track the coordinates of the recent moves using a Stack - this way a move can be undone if necessary.
    private Stack<int[]> lastPlacedStack = new Stack<>();

    public Board(int nRows, int nColumns){
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.nCountersTotal = nRows * nColumns;
        reset();
    }

    public void reset(){
        nCountersPlaced = 0;
        board = new Counter[nRows][nColumns];
    }

    public Counter[][] getBoard(){
        return this.board;
    }

    public int[] getLastPlacedCounter(){
        return lastPlacedStack.peek();
    }

    public int[] getBoardDimensions(){
        return new int[]{this.nRows, this.nColumns};
    }

    // Place a counter on the board after checking if the column is full.
    public void placeCounter(Counter counter, int column){

        if(checkFullColumn(column)){
            throw new FullColumnException(column);
        }

        for (int i = 0; i < nRows; i++) {
            if (board[i][column-1] == null) {
                board[i][column-1] = counter;
                nCountersPlaced += 1;
                lastPlacedStack.push(new int[] {i, column-1});
                break;
            }
        }
    }

    // Undo the last move played - this is crucial in implemting the Minimax algorithm in the MiniMax class.
    public void undoMove(){

        if(nCountersPlaced == 0){
            return;
        }

        int[] lastPlaced = lastPlacedStack.pop();
        int column = lastPlaced[0];
        int row = lastPlaced[1];
        this.board[column][row] = null;
        nCountersPlaced -= 1;
    }

    public void display(){
        System.out.println("|===========================|");
        for(int row = nRows - 1; row >= 0; row--) {
            displayRow(board[row]);
        }
        System.out.println("|===========================|");
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
        System.out.println("|===========================|");
    }

    private void displayRow(Counter[] row){
        System.out.print("| ");
        for(int i = 0; i < nColumns; i++){
            char colour = (row[i] != null) ? row[i].getColour() : ' ';
            System.out.print(colour + " | ");
        }
        System.out.println();
    }

    public boolean checkFullColumn(int column){
        return board[nRows - 1][column - 1] != null; // checks if the top element of a column is null or not.
    }

    public boolean checkFullBoard(){
        return (nCountersPlaced >= nCountersTotal);
    }

    // Check if a placed counter is the winning move.
    public boolean checkWin(Counter counter){
        int[] lastPlaced = getLastPlacedCounter();
        int currentRow = lastPlaced[0];
        int currentColumn = lastPlaced[1];

        return (checkRow(counter, currentRow)
                || checkColumn(counter, currentColumn)
                || checkAscendingDiagonal(counter, currentRow, currentColumn))
                || checkDescendingDiagonal(counter, currentRow, currentColumn);
    }

    // Check for a horizontal win.
    private boolean checkRow(Counter counter, int row) {
        int count = 0;
        // Traverse the current row, checking for four counters in a row
        for (int i = 0; i < nColumns; i++) {
            if (board[row][i] != null && board[row][i].getColour() == counter.getColour()) {
                count += 1;
            } else {
                count = 0;
            }
            if (count >= 4) {
                return true;
            }
        }
        return false;
    }

    // Check for a vertical win.
    private boolean checkColumn(Counter counter, int column){
        int count = 0;
        // Traverse the current column, checking for four counters in a row
        for (int i = 0; i < nRows; i++) {
            if (board[i][column] != null && board[i][column].getColour() == counter.getColour()) {
                count += 1;
            } else {
                count = 0;
            }
            if (count >= 4) {
                return true;
            }
        }
        return false;
    }

    // Check for a diagonal (ascending) win.
    private boolean checkAscendingDiagonal(Counter counter, int startRow, int startColumn){
        int x = startColumn;
        int y = startRow;

        // Step down from the counter position to find starting coordinate at the bottom of the diagonal
        while(x > 0 && y > 0) {
            x -= 1;
            y -= 1;
        }

        int count = 0;

        // Traverse the diagonal from the bottom, moving upwards and checking for four counters in a row
        while(x < nColumns && y < nRows){
            if (board[y][x] != null && board[y][x].getColour() == counter.getColour()) {
                count += 1;
            } else{
                count = 0;
            }
            if(count >= 4){
                return true;
            }
            x += 1; y += 1;
        }
        return false;
    }

    // Check for a diagonal (descending) win.
    private boolean checkDescendingDiagonal(Counter counter, int startRow, int startColumn){
        int x = startColumn;
        int y = startRow;

        // Step back from the counter position to find starting coordinate at the top of the diagonal
        while(x > 0 && y < nRows - 1){
            x -= 1;
            y += 1;
        }

        int count = 0;

        // Traverse downwards from the start of the diagonal, checking for four counters in a row
        while(x < nColumns && y >= 0){
            if (board[y][x] != null && board[y][x].getColour() == counter.getColour()) {
                count += 1;
            } else{
                count = 0;
            }
            if(count >= 4){
                return true;
            }
            x += 1; y -= 1;
        }
        return false;
    }

}
