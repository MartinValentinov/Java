package util;

public class IdGenerator {
    private static int nextId = 1000;

    public static String nextId() {
        return String.format("MEM-" + nextId ++);
    }

    public static String nextId(String prefix) {
        return String.format(prefix + "-" + nextId ++);
    }
}
