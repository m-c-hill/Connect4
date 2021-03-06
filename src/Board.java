public class Board {

    private final int nRows;
    private final int nColumns;
    private final int nCounters;
    private Counter[][] board;
    private int[] lastPlaced;
    private int counterCount = 0;

    public Board(int nRows, int nColumns){
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.nCounters = nRows * nColumns;
        reset();
    }

    public void placeCounter(Counter counter, int column){

        if(checkFullColumn(column)){
            throw new FullColumnException(column);
        }

        for (int i = 0; i < nRows; i++) {
            if (board[i][column-1] == null) {
                System.out.printf("Placing token in column %d...\n", column);
                board[i][column-1] = counter;
                counterCount += 1;
                lastPlaced = new int[] {i, column-1};
                break;
            }
        }
    }

    public boolean checkWin(Counter counter){
        int currentRow = lastPlaced[0];
        int currentColumn = lastPlaced[1];

        return (checkRow(counter, currentRow)
                || checkColumn(counter, currentColumn)
                || checkAscendingDiagonal(counter, currentRow, currentColumn))
                || checkDescendingDiagonal(counter, currentRow, currentColumn);
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

    private boolean checkAscendingDiagonal(Counter counter, int startRow, int startColumn){
        int x = startColumn;
        int y = startRow;

        // Step down to find starting coordinate
        while(x > 0 && y > 0) {
            x -= 1;
            y -= 1;
        }

        int count = 0;

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

    private boolean checkDescendingDiagonal(Counter counter, int startRow, int startColumn){
        int x = startColumn;
        int y = startRow;
        System.out.printf("Starting coordinates: (%d, %d)\n", startColumn, startRow);
        while(x > 0 && y < nRows - 1){
            x -= 1;
            y += 1;
            System.out.printf("Count down: (%d, %d)\n", x, y);
        }

        int count = 0;

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
            System.out.printf("Count up: (%d, %d)\n", x, y);
            System.out.printf("Count: %d\n", count);
        }
        return false;
    }

    public boolean checkFullColumn(int column){
        if(board[nRows -1][column-1] != null){
            return true;
        }
        return false;
    }

    public boolean checkFullBoard(){
        return (counterCount >= nCounters);
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
        counterCount = 0;
        board = new Counter[nRows][nColumns];
    }
}