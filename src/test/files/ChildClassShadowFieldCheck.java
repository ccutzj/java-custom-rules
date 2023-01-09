public class Fruit {
    protected int ripe;
    protected String flesh;

    public class Raspberry extends Fruit {
        private boolean ripe;  // Noncompliant
        private static String flesh; // Noncompliant
    }
}

