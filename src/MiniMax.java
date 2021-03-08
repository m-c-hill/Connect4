/**
 * Minimax algorithm (source: https://en.wikipedia.org/wiki/Minimax) is used to give the AI in our game the ability to
 * make decisions on where to place a counter based on the current state of the board.
 *
 * Useful guide to an implementation of Minimax applied to Tic-Tac-Toe can be found here: https://www.youtube.com/watch?v=trKjYdBASyQ.
 * This algorithm was partially derived from the above two sources.
 *
 * Please note that the below algorithm is only a rough idea of what can be achieved and the AI still makes some mistakes.
 * It still needs a lot of tweaking and fine tuning in order to perfect it, but in its current state, it allows for a
 * challenging game of Connect4.
 */

public class MiniMax {

    private final Counter AICounter;
    private final Counter oppCounter;

    public MiniMax(Counter playerCounter, Counter oppCounter) {
        this.AICounter = playerCounter;
        this.oppCounter = oppCounter;
    }

    public int[] getMiniMax(Board board, int depth, boolean isMaximising) {

        if(winOccurred(board, AICounter)){
            return new int[]{10_000_000, 0}; // If the AI has a chance to win, return an extremely high number.
        }
        if(winOccurred(board, oppCounter)) {
            return new int[]{-10_000_000, 0}; // If the opponent has the chance to win, we return an extremely low number.
        }
        if(depth == 0 || board.checkFullBoard()){
            return new int[]{getScore(board, AICounter), 0};
        }

        if(isMaximising){
            int bestScore = Integer.MIN_VALUE;
            int bestMove = 0; // column to place counter

            for (int i = 1; i < 8; i++) {
                if (!board.checkFullColumn(i)) {
                    board.placeCounter(AICounter, i);
                    int score = getMiniMax(board, depth-1, false)[0];
                    // Important not to alter the state of the actual board and undo any changes after a score has been calculated
                    board.undoMove();
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i;
                    }
                }
            }
            return new int[]{bestScore, bestMove};
        }
        else{ // Minimising
            int bestScore = Integer.MAX_VALUE;
            int bestMove = 0; // column to place counter

            for (int i = 1; i < 8; i++) {
                if (!board.checkFullColumn(i)) {
                    board.placeCounter(oppCounter, i);
                    int score = getMiniMax(board, depth-1, true)[0];
                    board.undoMove();
                    if (score < bestScore) {
                        bestScore = score;
                        bestMove = i;
                    }
                }
            }
            return new int[]{bestScore, bestMove};
        }
    }

    /**
     * For a given placement of a counter, calculate the score according to the following rules:
     *      > Middle column = 4 points
     *      > Lines of four counters = 100 points
     *      > Lines of three counters = 10 points
     *      > Lines of two counters = 2 points
     */

    public int getScore(Board board, Counter playerCounter){
        int score = 0;
        int row = board.getLastPlacedCounter()[0];
        int column = board.getLastPlacedCounter()[1];
        int nRows = board.getBoardDimensions()[0];
        int nColumns = board.getBoardDimensions()[1];
        Counter[][] board_arr = board.getBoard();

        // Check if middle column is available
        int centralColumn = nColumns / 2;
        if (column == centralColumn){
            score+=4;
        }

        // Check row and find scores for each sub row of four counters
        for(int i = 0; i < nColumns - 3; i++){
            Counter[] subRow = {board_arr[row][i], board_arr[row][i+1], board_arr[row][i+2], board_arr[row][i+3]};
            score += scoreSubArray(subRow, playerCounter);
        }

        // Check column and find scores for each sub column of four counters
        for(int i = 0; i < nRows - 3; i++){
            Counter[] subColumn = {board_arr[i][column], board_arr[i+1][column], board_arr[i+2][column], board_arr[i+3][column]};
            score += scoreSubArray(subColumn, playerCounter);
        }

        // Check ascending diagonal and find scores for each sub array of four counters
        int x_asc = column;
        int y_asc = row;
        while(x_asc > 0 && y_asc > 0) {
            x_asc -= 1;
            y_asc -= 1;
        }
        while(x_asc < nColumns - 3 && y_asc < nRows - 3){
            Counter[] ascDiag = {board_arr[y_asc][x_asc], board_arr[y_asc+1][x_asc+1], board_arr[y_asc+2][x_asc+2], board_arr[y_asc+3][x_asc+3]};
            score += scoreSubArray(ascDiag, playerCounter);
            x_asc += 1;
            y_asc += 1;
        }

        // Check descending diagonal and find scores for each sub array of four counters
        int x_des = column;
        int y_des = row;
        while(x_des > 0 && y_des < nRows - 1){
            x_des -= 1;
            y_des += 1;
        }
        while(x_des < nColumns - 3 && y_des >= 3){
            Counter[] desDiag = {board_arr[y_des][x_des], board_arr[y_des-1][x_des+1], board_arr[y_des-2][x_des+2], board_arr[y_des-3][x_des+3]};
            score += scoreSubArray(desDiag, playerCounter);
            x_des += 1;
            y_des -= 1;
        }

        return score;
    }


    /**
     * Return the scores of arrays containing four counters passed in from getScore
     */
    private int scoreSubArray(Counter[] counters, Counter playerCounter){
        int score = 0;
        int nullCount = 0;
        int playerCount = 0; // AI count

        for (Counter counter : counters) {
            if (counter == null) {
                nullCount += 1;
            }
            if (counter == playerCounter) {
                playerCount += 1;
            }
        }

        // Potential win (line of four)
        if(playerCount == 4){
            score += 100;
        }

        // Three counters and one gap (line of three)
        if(playerCount == 3 && nullCount == 1){
            score += 10;
        }

        // Two counters and two gaps (line of two)
        if(playerCount == 2 && nullCount == 2){
            score += 2;
        }
        return score;
    }

    private boolean winOccurred(Board board, Counter counter){
        return board.checkWin(counter);
    }
}
