import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private BufferedReader input;
    private Board board;
    private boolean boardFull = false;
    private boolean hasWon = false;
    private int currentPlayerIndex = -1;
    private int nPlayers = 2;

    Human player1;
    Human player2; // Change to AI
    Player[] playerList;

    public Game() {
        board = new Board(6, 7);
        input = new BufferedReader(new InputStreamReader(System.in));
        player1 = new Human("Player 1", 'r', input);
        player2 = new Human("Player 2", 'y', input);
        //player2 = new AI("Player 2", 'y');
        playerList = new Player[] {player1, player2};
        playGame();
    }

    private void playGame() {
        openingMessage();
        board.display();
        Player currentPlayer;

        while (!hasWon && !boardFull) {
            currentPlayer = getCurrentPlayer();
            hasWon = currentPlayer.playTurn(board);
            boardFull = board.checkFullBoard();
            if(hasWon){
                currentPlayer.setWinner();
            }
        }
        finalMessage();
        playAgain();
    }

    private Player getCurrentPlayer(){
        currentPlayerIndex += 1;
        currentPlayerIndex = (currentPlayerIndex > nPlayers - 1) ? 0:currentPlayerIndex;
        return playerList[currentPlayerIndex];
    }

    private void openingMessage(){
        System.out.println("Welcome to Connect 4");
        System.out.println("There are 2 players red and yellow");
        System.out.println("Player 1 is Red, Player 2 is Yellow");
        System.out.println("To play the game type in the number of the column you want to drop you counter in");
        System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
    }

    private void finalMessage(){
        String winner;
        if(!hasWon && boardFull) {
            System.out.println("Game over: board is full - no winners.");
        } else {
            winner = Player.getWinner(new Player[]{player1, player2});
            System.out.printf("%s is the winner!\n", winner);
        }
    }

    private void playAgain() {
        System.out.println("To play again, enter 'Y': ");
        try {
            String token = input.readLine();
            if(token.toLowerCase().equals("y")) {
                resetGame();
            } else{
                System.out.println("Goodbye!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void clearConsole(){
        // Clear the console
    }

    private void resetGame(){
        hasWon = false;
        boardFull = false;
        board.reset();
        playGame();
    }

}

/*
 To add:
    > Ability to choose colour
    > Limited colours to choose from
    > Sort out confusing mess of player lists...
 */