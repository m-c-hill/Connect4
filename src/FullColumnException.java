public class FullColumnException extends RuntimeException {
    public FullColumnException(int columnNumber){
        System.out.printf("Column %d is full. Please choose another column: ", columnNumber);
    }
}