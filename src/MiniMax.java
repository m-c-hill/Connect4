public class MiniMax {

    private int depth;
    private Counter playerCounter;
    private Counter oppCounter;

    public MiniMax(int depth, Counter playerCounter) {
        this.depth = depth;
        this.playerCounter = playerCounter;
    }

    public int bestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;

        for (int i = 1; i < 8; i++) {
            if (!board.checkFullColumn(i) && !board.checkFullBoard()) {
                board.placeCounter(playerCounter, i);
                int score = board.getScore(playerCounter);
                board.undoMove();
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private int getMiniMax(Board board, int depth, boolean isMaximising) {

        if(board.checkWin(this.playerCounter)){
            return 10000;
        }
        return 1;
    }

    // Loop through all the possible moves of the initial board

}
