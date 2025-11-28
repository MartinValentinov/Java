import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

public class TaskManager {
    private HashMap<String, Task> tasks;

    public TaskManager() {
        tasks = new HashMap<String, Task>();
    }

    public TaskManager(HashMap<String, Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            throw new CustomException("tasks cannot be null or empty");
        }
        this.tasks = tasks;
    }

    public void printTasks(){
        if (tasks == null || tasks.isEmpty()) {
            throw new CustomException("tasks cannot be null or empty");
        }
        for(Map.entry<String, Task> entry: tasks.entrySet())
        {
            System.out.println(entry.getKey() + tasks.get(entry.getKey()).printTask());
        }
    }

    public void createTask(Task new_task){
        if (new_task == null) {
            throw new CustomException("New task cannot be null");
        }
        tasks.put(new_task.getName(), new_task);
    }

    public void readTask(String task_name){
        if (task_name == null) {
            throw new CustomException("Task name cannot be null");
        }
        System.out.println(task_name + tasks.get(task_name).printTask());
    }

    public void updateTask(String name, Task new_task){
        if (new_task == null || name == null) {
            throw new CustomException("New task name or task name cannot be null");
        }
        put(name, new_task);
    }

    public void deleteTask(String task_name){
        if (task_name == null) {
            throw new CustomException("Task name cannot be null");
        }
        tasks.remove(task_name);
    }
}