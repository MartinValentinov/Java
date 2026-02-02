package cli;

public class Style {
    // ANSI цветове
    public static final String RESET = "\u001B[0m";

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Болд
    public static final String BOLD = "\u001B[1m";

    // Емоджита
    public static final String TROPHY = "🏆";
    public static final String VS = "⚔️";
    public static final String DOT = "•";
    public static final String CHECK = "✔️";
    public static final String CROSS = "❌";
    public static final String STAR = "✨";
    public static final String BRACKET = "🎯";

    public static String title(String text) {
        return BOLD + MAGENTA + text + RESET;
    }

    public static String ok(String text) {
        return GREEN + CHECK + " " + text + RESET;
    }

    public static String bad(String text) {
        return RED + CROSS + " " + text + RESET;
    }
}
