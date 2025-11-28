import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;


public class taskManager {
    private HashMap<String, Task> tasks;

    public taskManager() {
        tasks = new HashMap<String, Task>();
    }

    public taskManager(HashMap<String, Task> tasks) {
        this.tasks = tasks;
    }

    public void printTasks(){
        for(Map.entry<String, Task> entry: tasks.entrySet())
        {
            System.out.println(entry.getKey() + tasks.get(entry.getKey()).printTask());
        }
    }

    public void createTask(Task new_task){
        tasks.put(new_task.getName(), new_task);
    }

    public void readTask(String task_name){
        System.out.println(task_name + tasks.get(task_name).printTask());
    }

    public void updateTask(String name, Task new_task){
        put(name, new_task);
    }

    public void deleteTask(String task_name){
        tasks.remove(task_name);
    }
}
