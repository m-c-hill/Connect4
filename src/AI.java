import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

public class AI extends Player{

    public AI(String name, char colour) {
        super(name, colour);
    }

    @Override
    public int playerInput(Board b) {
        return ThreadLocalRandom.current().nextInt(1, 8);
    }
}