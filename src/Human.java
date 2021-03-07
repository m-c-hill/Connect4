import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class Human extends Player {

    private BufferedReader input;

    public Human(String name, char colour, BufferedReader br) {
        super(name, colour);
        this.input = br;
    }

    @Override
    public int playerInput(Board b) {
        int columnChoice;
        String token;

        try {
            token = input.readLine();

            columnChoice = Integer.parseInt(token);
            if (columnChoice < 1 || columnChoice > 7) {
                throw new InvalidColumnException(7);
            }
            if (b.checkFullColumn(columnChoice)) {
                throw new FullColumnException(columnChoice);
            }
        }
        catch (IOException | NumberFormatException | InvalidColumnException | FullColumnException e) {
            if (e instanceof NumberFormatException){
                System.out.println("Input is not an integer - please enter a valid integer: ");
            }
            return playerInput(b);
        }
        return columnChoice;
    }
}
