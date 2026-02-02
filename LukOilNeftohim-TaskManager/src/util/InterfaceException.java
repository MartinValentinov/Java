package util;

public class InterfaceException extends RuntimeException {
  public InterfaceException(String message) {
    super(message);
  }
    public static class CommandNotFound extends InterfaceException {
        public CommandNotFound(String command) { super("Unknown command: " + command + ". Type 'help' for available commands."); }}

    public static class UnknownArgument extends InterfaceException {
          public UnknownArgument (String arg) { super("Uknown argument: " + arg); }}
       
    public static class MissingArgument extends InterfaceException {
        public MissingArgument (String arg) { super("Missing argument: " + arg); }}
  
}
