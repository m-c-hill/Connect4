public class InvalidColumnException extends RuntimeException {
    public InvalidColumnException(int nColumns){
        System.out.printf("Invalid column choice. Please enter a number between 1 and %d: ", nColumns);
    }
}