public class Board {

    private int nRows;
    private int nColumns;
    private Counter[][] board;
    private int[] lastPlaced;

    public Board(int nRows, int nColumns){
        this.nRows = nRows;
        this.nColumns = nColumns;
        reset();
    }

    public void placeCounter(Counter counter, int column){
        if(board[nRows -1][column-1] != null){
            throw new FullColumnException("This column is full, please choose another column to place a counter in: ");
        }
        for (int i = 0; i < nRows; i++) {
            if (board[i][column-1] == null) {
                System.out.printf("Placing token in column %d...\n", column);
                board[i][column-1] = counter;
                lastPlaced = new int[] {i, column-1};
                break;
            }
        }
    }

    public boolean checkWin(Counter counter){
        int row = lastPlaced[0];
        int column = lastPlaced[1];

        return (checkRow(counter, row)
                || checkColumn(counter, column)
                || checkDiagonal(counter, row, column));
    }

    private boolean checkRow(Counter counter, int row) {
        int count = 0;
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

    private boolean checkColumn(Counter counter, int column){
        int count = 0;
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

    private boolean checkDiagonal(Counter count, int row, int column){
        return false;
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

    public void reset(){
        board = new Counter[nRows][nColumns];
    }
}