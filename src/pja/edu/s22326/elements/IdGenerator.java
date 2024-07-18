package pja.edu.s22326.elements;

public class IdGenerator {
    private static int globalIdCounter = 0;

    public static synchronized int getNextId() {
        return ++globalIdCounter;
    }
}
