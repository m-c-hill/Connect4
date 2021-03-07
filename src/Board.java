import java.util.Stack;

public class Board{

    private final int nRows;
    private final int nColumns;
    private final int nCountersTotal;
    private int nCountersPlaced;
    private Counter[][] board;
    private Stack<int[]> lastPlacedStack = new Stack<>();

    public Board(int nRows, int nColumns){
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.nCountersTotal = nRows * nColumns;
        reset();
    }

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

    public boolean checkWin(Counter counter){
        int[] lastPlaced = lastPlacedCounter();
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

        while(x > 0 && y < nRows - 1){
            x -= 1;
            y += 1;
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
        }
        return false;
    }

    public boolean checkFullColumn(int column){
        return board[nRows - 1][column - 1] != null;
    }

    public boolean checkFullBoard(){
        return (nCountersPlaced >= nCountersTotal);
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

    public void displayRow(Counter[] row){
        System.out.print("| ");
        for(int i = 0; i < nColumns; i++){
            char colour = (row[i] != null) ? row[i].getColour() : ' ';
            System.out.print(colour + " | ");
        }
        System.out.println();
    }

    public void reset(){
        nCountersPlaced = 0;
        board = new Counter[nRows][nColumns];
    }

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

    public int[] lastPlacedCounter(){
        return lastPlacedStack.peek();
    }

    public int getScore(Counter AICounter){
        int score = 0;
        int row = lastPlacedCounter()[0];
        int column = lastPlacedCounter()[1];

        if(checkWin(AICounter)){
            return 1000000;
        }

        // Check if middle column is available
        int centralColumn = nColumns / 2;
        if (column == centralColumn){
            score+=4;
        }

        // Check row
        for(int i = 0; i < nColumns - 3; i++){
            Counter[] subRow = {board[row][i], board[row][i+1], board[row][i+2], board[row][i+3]};
            score += scoreSubArray(subRow, AICounter);
        }

        // Check column
        for(int i = 0; i < nRows - 3; i++){
            Counter[] subColumn = {board[i][column], board[i+1][column], board[i+2][column], board[i+3][column]};
            score += scoreSubArray(subColumn, AICounter);
        }

        // Check ascending diagonal

        int x_asc = column;
        int y_asc = row;
        while(x_asc > 0 && y_asc > 0) {
            x_asc -= 1;
            y_asc -= 1;
        }
        while(x_asc < nColumns - 3 && y_asc < nRows - 3){
            System.out.printf("x %d ", x_asc+3);
            System.out.printf("y %d\n", y_asc+3);
            Counter[] ascDiag = {board[y_asc][x_asc], board[y_asc+1][x_asc+1], board[y_asc+2][x_asc+2], board[y_asc+3][x_asc+3]};
            score += scoreSubArray(ascDiag, AICounter);
            x_asc += 1;
            y_asc += 1;
        }

        // Check descending diagonal

        int x_des = column;
        int y_des = row;
        while(x_des > 0 && y_des < nRows - 1){
            x_des -= 1;
            y_des += 1;
        }
        while(x_des < nColumns - 3 && y_des >= 3){
            Counter[] desDiag = {board[y_des][x_des], board[y_des-1][x_des+1], board[y_des-2][x_des+2], board[y_des-3][x_des+3]};
            score += scoreSubArray(desDiag, AICounter);
            x_des += 1;
            y_des -= 1;
        }

        return score;
    }

    private int scoreSubArray(Counter[] counters, Counter AICounter){
        int score = 0;
        int nullCount = 0;
        int redCount = 0; // Human count
        int yellowCount = 0; // AI count

        for(int i = 0; i < counters.length; i++){
            if(counters[i] == null){
                nullCount += 1;
            }
            if(counters[i] == AICounter){
                yellowCount += 1;
            }
            else{
                redCount +=1;
            }
        }

        if(yellowCount == 4){
            score += 10000;
        }
        if(yellowCount == 3 && nullCount == 1){
            score += 5;
        }
        if(yellowCount == 2 && nullCount == 2){
            score += 2;
        }
        return score;
    }

}