import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private BufferedReader input;
    private Board board;

    Human player1;
    AI player2;

    private boolean boardFull = false;
    private boolean hasWon = false;

    public Game() {
        board = new Board(6, 7);
        input = new BufferedReader(new InputStreamReader(System.in));
        player1 = new Human("Player 1", 'r', input);
        player2 = new AI("Player 2", 'y');
        playGame();
    }

    private void playGame() {
        openingMessage();
        board.display();
        Player currentPlayer;

        while (!hasWon && !boardFull) {

            // Player 1 takes turn
            hasWon = player1.playTurn(board);
            boardFull = board.checkFullBoard();
            if(boardFull || hasWon){
                break;
            }

            // Player 2 takes turn
            hasWon = player2.playTurn(board);
            boardFull = board.checkFullBoard();
        }
        finalMessage();
        playAgain();
    }

    private void openingMessage(){
        System.out.println("Welcome to Connect 4");
        System.out.println("There are 2 players red and yellow");
        System.out.println("Player 1 is Red, Player 2 is Yellow");
        System.out.println("To play the game type in the number of the column you want to drop you counter in");
        System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
    }

    private void finalMessage(){
        if(!hasWon && boardFull) {
            System.out.println("Game over: board is full - no winners.");
        } else {
            String winner = Player.getWinner(new Player[]{player1, player2});
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

    private void resetGame(){
        hasWon = false;
        boardFull = false;
        player1.resetPlayer();
        player2.resetPlayer();
        board.reset();
        playGame();
    }
}

/*
 To add:
    > Sort out confusing mess of player lists...
 */