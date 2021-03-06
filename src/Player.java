public abstract class Player {

    String name;
    Counter counter;
    private Boolean isWinner = false;

    public Player(String name, char colour) {
        this.name = name;
        this.counter = new Counter(colour);
    }

    abstract public int playerInput(Board board);

    public boolean playTurn(Board board) {
        board.placeCounter(counter, playerInput(board));
        board.display();
        return board.checkWin(counter);
    }

    public void setWinner(){
        this.isWinner = true;
    }

    public static String getWinner(Player[] playerList){
        for(int i = 0; i < playerList.length; i++){
            if(playerList[i].isWinner){
                return playerList[i].name;
            }
        }
        return "";
    }

    public static void resetPlayers(Player[] playerList){
        for(int i = 0; i < playerList.length; i++){
            playerList[i].isWinner = false;
        }
    }
}