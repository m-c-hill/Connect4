import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Contains all the logic required to run the game, alternating terns between player 1 and player 2, while also checking
 * for a winner.
 */

public class Game {

    private final BufferedReader input;
    private final Board board;
    private final Human player1;
    private final AI player2;

    private boolean boardFull = false;
    private boolean hasWon = false;

    public Game() {
        board = new Board(6, 7);
        input = new BufferedReader(new InputStreamReader(System.in));
        player1 = new Human("Player 1", 'r', input);
        player2 = new AI("Player 2", 'y', player1);
        playGame();
    }

    private void playGame() {
        openingMessage();
        board.display();

        while (!hasWon && !boardFull) {

            // Player 1 (human) takes their turn
            System.out.printf("Player 1 (red), enter a number between 1 and %d: ", board.getBoardDimensions()[1]);
            hasWon = player1.playTurn(board);
            boardFull = board.checkFullBoard();
            board.display();

            if(boardFull || hasWon){
                break;
            }

            // Player 2 (computer) takes their turn
            try {
                TimeUnit.SECONDS.sleep(1); // Sleep for one second to make the gameplay seem more realistic
            } catch (InterruptedException e){
                continue;
            }
            hasWon = player2.playTurn(board);
            boardFull = board.checkFullBoard();
            board.display();
        }
        finalMessage();
        playAgain();
    }

    // Message to be printed when the game is started.
    private void openingMessage(){
        System.out.println("Welcome to Connect 4");
        System.out.println("There are 2 players: red and yellow");
        System.out.println("Player 1 is Red, Player 2 is Yellow");
        System.out.println("To play the game type in the number of the column you want to drop your counter in");
        System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
    }

    // Final message to be printed once the game has ended.
    private void finalMessage(){
        if(!hasWon && boardFull) {
            System.out.println("Game over: board is full - no winners.");
        } else {
            String winner = Player.getWinner(new Player[]{player1, player2});
            System.out.printf("%s is the winner!\n", winner);
        }
    }

    // If the user enters 'Y' at the end of a session, the game will reset and a new game is started.
    private void playAgain() {
        System.out.println("To play again, enter 'Y': ");
        try {
            String token = input.readLine();
            if(token.equalsIgnoreCase("y")) {
                resetGame();
            } else{
                System.out.println("Goodbye!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    // Reset all parameters to their initial state and starts a new game.
    private void resetGame(){
        hasWon = false;
        boardFull = false;
        player1.resetPlayer(); // Resets player.isWinner to false
        player2.resetPlayer();
        board.reset(); // Empties the board of counters
        playGame();
    }
}
