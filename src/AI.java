/**
 * AI player used when a user is playing against the computer. Implements a version of the Minimax algorithm to make
 * decisions on where to place a counter.
 */

public class AI extends Player{

    private final MiniMax minimax;

    public AI(String name, char colour, Player opponent) {
        super(name, colour);
        minimax = new MiniMax(getCounter(), opponent.getCounter());
    }

    @Override
    public int playerInput(Board b) {
        return minimax.getMiniMax(b, 5, true)[1];
    }
}