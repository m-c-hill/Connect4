public class AI extends Player{

    MiniMax minimax;

    public AI(String name, char colour, Counter oppCounter) {
        super(name, colour);
        minimax = new MiniMax(counter, oppCounter);
    }

    @Override
    public int playerInput(Board b) {
        return minimax.getMiniMax(b, 7, true)[1];
    }
}