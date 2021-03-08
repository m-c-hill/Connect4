/**
 * Player is an abstract class that both Human and AI players can extend from. Each player is assigned a name, a
 * counter and a boolean "isWinner" to track the winning player. The class controls any input from the user and
 * processes it accordingly by playing a turn.
 */
public abstract class Player {

    final private String name;
    final private Counter counter;
    private Boolean isWinner = false;

    public Player(String name, char colour) {
        this.name = name;
        this.counter = new Counter(colour);
    }

    public Counter getCounter(){
        return this.counter;
    }

    /** Abstract method to generate the player's input and return the integer column choice. This differs between Human
     * (bufferedReader input) and AI (column choice derived from minimax algorithm) players.
     *
     * @param board current board state
     * @return int: column to place counter in
     */
    abstract public int playerInput(Board board);

    /**
     * Places counter in column returned from playerInput and analyses the board to check if the player has won.
     * @param board current board state
     * @return boolean: if player has won or not
     */
    public boolean playTurn(Board board) {
        board.placeCounter(counter, playerInput(board));
        this.isWinner = board.checkWin(counter);
        return isWinner;
    }

    // Returns the name of the winner out of a list of players. If no winner is found, an empty string is returned.
    public static String getWinner(Player[] playerList){
        for (Player player : playerList) {
            if (player.isWinner) {
                return player.name;
            }
        }
        return "";
    }

    public void resetPlayer(){
        this.isWinner = false;
    }
}