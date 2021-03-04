class Main {
    public static void main(String[] args) {
        Counter r = new Counter('r');
        Counter y = new Counter('y');
        Board b = new Board(6, 7);
        b.placeCounter(r, 1);
        b.placeCounter(r, 1);
        b.placeCounter(r, 1);
        b.placeCounter(r, 1);
        b.placeCounter(r, 1);
        b.placeCounter(r, 1);
        b.placeCounter(r, 1);

        b.display();
    }
}