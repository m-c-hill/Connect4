import java.io.BufferedReader;
import java.io.IOException;

/**
 * Human player which takes and processes input column choices from the player using a BufferedReader.
 */

public class Human extends Player {

    private final BufferedReader input;

    public Human(String name, char colour, BufferedReader br) {
        super(name, colour);
        this.input = br;
    }

    // Takes player input from the BufferedReader and returns the column choice as an integer.
    @Override
    public int playerInput(Board b) {
        int columnChoice;
        String token;

        try {
            token = input.readLine();
            columnChoice = Integer.parseInt(token);
            // If player input is not a valid column choice, an InvalidColumnException is thrown.
            if (columnChoice < 1 || columnChoice > b.getBoardDimensions()[1]) {
                throw new InvalidColumnException(b.getBoardDimensions()[1]);
            }
            // If the chosen column is full, then a FullColumnException is thrown.
            if (b.checkFullColumn(columnChoice)) {
                throw new FullColumnException(columnChoice);
            }
        }
        catch (IOException | NumberFormatException | InvalidColumnException | FullColumnException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Input is not an integer - please enter a valid integer: ");
            }
            // Request the player inputs another value
            return playerInput(b);
        }
        return columnChoice;
    }
}
