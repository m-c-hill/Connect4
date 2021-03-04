public class Board {

    int rows;
    int columns;
    private Counter[][] board;

    public Board(int nRows, int nColumns){
        this.rows = nRows;
        this.columns = nColumns;
        reset();
    }

    public void placeCounter(Counter counter, int column){
        if(board[rows-1][column-1] != null){
            throw new FullColumnException("This column is full, please choose another column to place a counter in: ");
        }
        else {
            for (int i = 0; i < rows; i++) {
                if (board[i][column-1] == null) {
                    board[i][column-1] = counter;
                    break;
                }
            }
        }
    }

    public void checkConnect(Counter colour){

    }

    public void display(){
        for(int row = rows - 1; row >= 0; row--) {
            displayRow(board[row]);
        }
    }

    private void displayRow(Counter[] row){
        System.out.print("| ");
        for(int i = 0; i < columns; i++){
            char colour = (row[i] != null) ? row[i].getColour() : ' ';
            System.out.print(colour + " | ");
        }
        System.out.println();
    }

    public void reset(){
        board = new Counter[rows][columns];
    }
}