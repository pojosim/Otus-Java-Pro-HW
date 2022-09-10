package otus.hw.test;

/**
 * Main starter test
 */
final public class TestStarter {

    public static void main(String[] args) {
        if (args.length == 0)
            throw new RuntimeException("Error, args is null elements");

        new TestRunner(args[0]).run();
    }
}
