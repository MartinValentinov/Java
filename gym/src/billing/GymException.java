package billing;

public class GymException extends RuntimeException {
    public GymException(String message) {
        super(message);
    }

    public GymException(String message, Throwable cause) {
        super(message, cause);
    }
}
