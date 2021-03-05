import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Game {
    private BufferedReader input;
    private Board board;
    private boolean hasWon;

    public Game() {
        board = new Board(6, 7);
        input = new BufferedReader(new InputStreamReader(System.in));
        playGame();
    }

    private void playGame() {
        hasWon = false;
        Counter red = new Counter('r');
        Counter yellow = new Counter('y');

        openingMessage();
        board.display();

        while (!hasWon) {
            playTurn(true, red);
            playTurn(false, yellow);
        }

    }

    private void playTurn(boolean human, Counter counter){
        int column = human ? playerInput():computerInput();
        board.placeCounter(counter, column);
        clearConsole();
        board.display();
        System.out.println(board.checkWin(counter));
    }

    private int playerInput() {
        int n;
        try {
            n = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            return -1; // change this
        }
        return n;
    }

    private void openingMessage(){
        System.out.println("Welcome to Connect 4");
        System.out.println("There are 2 players red and yellow");
        System.out.println("Player 1 is Red, Player 2 is Yellow");
        System.out.println("To play the game type in the number of the column you want to drop you counter in");
        System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
    }

    private void clearConsole(){
        // Clear the console
    }

    private int computerInput(){
        /*
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
         */
        // Minimax to get computer to choose the optimum value
        return 6;
    }

}
/*
 To add:
    > Ability to choose colour
    > Check if the board is full


 */