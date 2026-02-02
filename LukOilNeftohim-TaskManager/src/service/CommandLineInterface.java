package service;

import model.Task;
import util.InterfaceException.*;
import model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CommandLineInterface {
    private final TaskStorage taskStorage;
    private final Scanner scanner;

    public CommandLineInterface(TaskStorage taskStorage) {
        this.taskStorage = taskStorage;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Task Manager CLI. Type 'help' for commands or 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) break;

            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;
            if (input.equalsIgnoreCase("exit")) break;

            try { processCommand(input); } 
            catch (Exception e) { System.err.println(e.getMessage()); } 
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private void processCommand(String input) {

        String[] tokens = parseInput(input);
        if (tokens.length == 0) return;


        String command = tokens[0].toLowerCase();
        tokens = Arrays.copyOfRange(tokens, 1, tokens.length);

        switch (command) {
            case "add" -> handleAdd(tokens);
            case "remove", "rm" -> handleRemove(tokens);
            case "edit", "update" -> handleEdit(tokens);
            case "list", "ls" -> handleList();
            case "show" -> handleShow(tokens);
            case "help" -> handleHelp();
            default -> throw new CommandNotFound(command);
        }
    }

    private String[] parseInput(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
    
        for (char c : input.toCharArray()) {
            if (c == '"') inQuotes = !inQuotes;
            else if (Character.isWhitespace(c) && !inQuotes) {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current.setLength(0);
                }
            } else current.append(c);
            
        }

        if (current.length() > 0)  tokens.add(current.toString());

        return tokens.toArray(new String[0]);
    }

    private void parseArgs(Task t, String[] args) {    
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            boolean missingArg = false;

            switch (arg) {
                case "-n", "--name"        -> { if (++i >= args.length) missingArg = true; t.name = args[i]; }
                case "-p", "--pic"         -> { if (++i >= args.length) missingArg = true; t.pic = args[i]; }
                case "-d", "--description" -> { if (++i >= args.length) missingArg = true; t.desc = args[i]; }
                case "-s", "--start"       -> { if (++i >= args.length) missingArg = true; t.startDate = args[i]; }
                case "-e", "--end"         -> { if (++i >= args.length) missingArg = true; t.endDate = args[i]; }
                case "-t", "--status"      -> { if (++i >= args.length) missingArg = true; t.status = Status.from(args[i]); }        
                case "-u", "--urgency"     -> { if (++i >= args.length) missingArg = true; t.urgency = Integer.parseInt(args[i]); }

                case String s when s.startsWith("+") -> t.tags.add(s.substring(1));
                case String s when s.startsWith("-") -> t.tags.remove(s.substring(1));

                default -> throw new UnknownArgument(arg);
            }

            if (missingArg) throw new MissingArgument(arg);
        }
    }

    private void handleAdd(String[] args)
    {
        Task t = new Task("name", "pic", Status.NEW, null, null, null, 0, new HashSet<>());
        parseArgs(t, args);

        taskStorage.create(t);
    }

    private void handleEdit(String[] args)
    {
        if (args.length == 0) throw new MissingArgument("taskId");

        String taskId = args[0];
        Task t = taskStorage.read(taskId);
        parseArgs(t, Arrays.copyOfRange(args, 1, args.length));

        taskStorage.update(taskId, t);
    }

    private void handleRemove(String[] args) {
        if (args.length == 0) throw new MissingArgument("taskId");
        
        String taskId = args[0];
        taskStorage.delete(taskId);
    }    

    private static String urgencyColor(int urgency) 
    {
        if (urgency <= 3) return "\u001B[32m"; 
        if (urgency <= 6) return "\u001B[33m"; 
        if (urgency <= 8) return "\u001B[91m"; 
        return "\u001B[31m"; 
    }
    
    private void handleList() {
        final String RESET = "\u001B[0m";
        final String ID_COLOR = "\u001B[94m";  

        String topBorder = "╔════════╦══════════════════════╦══════════════════════════════════════════╦═════════╗";
        String header = String.format("║ %-6s ║ %-20s ║ %-40s ║ %-7s ║", "ID", "Name", "Description", "Urgency");
        String separator = "╠════════╬══════════════════════╬══════════════════════════════════════════╬═════════╣";
        String bottomBorder = "╚════════╩══════════════════════╩══════════════════════════════════════════╩═════════╝";
    
        System.out.println(topBorder);
        System.out.println(header);
        System.out.println(separator);
    
        for (Map.Entry<String, Task> entry : taskStorage.list().entrySet()) {
            String id = entry.getKey();
            Task task = entry.getValue();
    
            String nameDisplay = task.name.length() > 20 ? task.name.substring(0, 17) + "..." : task.name;
            String descDisplay = task.desc != null && task.desc.length() > 40 ? task.desc.substring(0, 37) + "..." : (task.desc != null ? task.desc : "");
    
            String row = String.format("║ %s%-6s%s ║ %-20s ║ %-40s ║ %s%-7d%s ║",
                ID_COLOR, id, RESET,
                nameDisplay,
                descDisplay,
                urgencyColor(task.urgency), task.urgency, RESET
        );

            System.out.println(row);
        }
    
        System.out.println(bottomBorder);
    }
    
    public void handleShow(String[] args) {
        if (args.length == 0) throw new MissingArgument("taskId");

        String taskId = args[0];
        Task t = taskStorage.read(taskId);

        String RESET = "\u001B[0m";
        String BLUE = "\u001B[94m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        String YELLOW = "\u001B[33m";
        String CYAN = "\u001B[36m";

        String statusColor = switch (t.status) {
            case NEW, READY -> YELLOW;
            case IN_PROGRESS -> CYAN;
            case BLOCKED, ON_HOLD -> RED;
            case DONE -> GREEN;
            case CANCELLED -> RED;
            default -> RESET;
        };

        System.out.println("Task: " + BLUE + t.name + RESET);
        System.out.println("Person in charge: " + t.pic);
        System.out.println("Status: " + statusColor + t.status.label() + RESET);
        System.out.println("Urgency: " + urgencyColor(t.urgency) + t.urgency + RESET);
        System.out.println("Start Date: " + t.startDate);
        System.out.println("End Date: " + t.endDate);
        System.out.println("Description: " + t.desc);

        System.out.print("Tags: ");
        Set<String> tags = t.tags;
        if (tags != null && !tags.isEmpty()) 
            for (String tag : tags) 
                System.out.print(YELLOW + tag + RESET + " ");
            
        
        System.out.println();
    }

    private void handleHelp() {

        System.out.println("""
            Available Commands:
    
            add [options]
                Create a new task
    
                Options:
                  -n, --name <text>             Task name
                  -p, --pic <person>            Person in charge
                  -d, --description <text>      Task description
                  -s, --start <date>            Start date (ISO 8601)
                  -e, --end <date>              End date (ISO 8601)
                  -u, --urgency <level>         Urgency (1–3 or low/medium/high)
                  -t, --status <status>         Status (new / ready / in_progress / blocked / on_hold / done / cancelled)
                  +<tag>                        Add tag
                  -<tag>                        Remove tag
    
            remove <task_id>
                Delete a task
                Alias: rm
    
            edit <task_id> [options]
                Update task fields
                Alias: update
    
                Options:
                  -n, --name <text>             Task name
                  -p, --pic <person>            Person in charge
                  -d, --description <text>      Task description
                  -s, --start <date>            Start date (ISO 8601)
                  -e, --end <date>              End date (ISO 8601)
                  -u, --urgency <level>         Urgency (1–3 or low/medium/high)
                  -t, --status <status>         Status (new / ready / in_progress / blocked / on_hold / done / cancelled)
                  +<tag>                        Add tag
                  -<tag>                        Remove tag
    
            list
                Display all tasks
                Alias: ls
    
            show <task_id>
                Display a specific task
                Alias: view
    
            help
                Show this help message
    
            exit
                Exit the application
            """);
    }

}
