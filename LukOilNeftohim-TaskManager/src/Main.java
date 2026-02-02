import service.TaskStorage;
import service.CommandLineInterface;

public class Main {
    public static void main(String[] args) {

        TaskStorage taskStorage = new TaskStorage("./tasks");
        CommandLineInterface cli = new CommandLineInterface(taskStorage);
        
        cli.start();
    }
}