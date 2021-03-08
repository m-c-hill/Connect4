/**
 * Full column exception - thrown when a player attempts to place a counter in a column that has no empty slots
 * available.
 */

public class FullColumnException extends RuntimeException {
    public FullColumnException(int columnNumber){
        System.out.printf("Column %d is full. Please choose another column: ", columnNumber);
    }
}