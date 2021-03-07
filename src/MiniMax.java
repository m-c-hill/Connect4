public class MiniMax {


    private Counter AICounter;
    private Counter oppCounter;

    public MiniMax(Counter playerCounter, Counter oppCounter) {
        this.AICounter = playerCounter;
        this.oppCounter = oppCounter;
    }

    public int[] getMiniMax(Board board, int depth, boolean isMaximising) {

        if(depth == 0 || board.checkFullBoard()){
            return new int[]{board.getScore(AICounter), 0};
        }

        if(winOccured(board, AICounter) || winOccured(board, oppCounter)){
            if(winOccured(board, AICounter)){
                return new int[]{10000000, 0};
            }
            if(winOccured(board, oppCounter)){
                return new int[]{-10000000, 0};
            }
        }

        if(isMaximising){
            int bestScore = Integer.MIN_VALUE;
            int bestMove = 0;

            for (int i = 1; i < 8; i++) {
                if (!board.checkFullColumn(i)) {
                    board.placeCounter(AICounter, i);
                    int score = getMiniMax(board, depth-1, false)[0];  //board.getScore(AICounter);
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
            int bestMove = 0;

            for (int i = 1; i < 8; i++) {
                if (!board.checkFullColumn(i)) {
                    board.placeCounter(oppCounter, i);
                    int score = getMiniMax(board, depth-1, true)[0];  //board.getScore(AICounter);
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

    private boolean winOccured(Board board, Counter counter){
        return board.checkWin(counter);
    }

}
