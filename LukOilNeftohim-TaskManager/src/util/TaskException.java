package util;

public class TaskException extends RuntimeException {
    public TaskException(String message) { super(message); }

    public static class TaskNotFound extends TaskException{
        public TaskNotFound (String id) { super("Task not found: " + id); }}
     
    public static class CannotDeleteTask extends TaskException{
        public CannotDeleteTask (String id) { super("Cannot delete task: " + id); }}
    
    public static class InvalidArgument extends TaskException{
        public InvalidArgument (String msg) { super("Invalid argument: " + msg); }}

}
