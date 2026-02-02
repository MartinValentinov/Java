package service;
import java.io.*;
import java.util.*;
import model.Task;
import model.Status;
import util.TaskException;
import util.TaskException.CannotDeleteTask;
import util.TaskException.TaskNotFound;

public class TaskStorage {
    private final String tasksDirectory;
    private final Map<String, Task> loadedTasks = new HashMap<>();

    public TaskStorage(String tasksDirectory) throws TaskException {
        this.tasksDirectory = tasksDirectory;
        File dir = new File(tasksDirectory);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new TaskException("Cannot create tasks directory");
        }
        loadAllTasks();
    }

    private String generateId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    private void loadAllTasks() throws TaskException {
        File dir = new File(tasksDirectory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null) return;
        for (File f : files) {
            String id = f.getName().substring(0, f.getName().length() - 4);
            try {
                Task t = readTaskFromFile(f.getAbsolutePath());
                loadedTasks.put(id, t);
            } catch (Exception _) {}
        }
    }

    public String create(Task task) throws TaskException {
        String id = generateId();
        String file = tasksDirectory + File.separator + id + ".txt";
        try {
            writeTaskToFile(file, id, task);
            loadedTasks.put(id, task);
            return id;
        } catch (IOException e) {
            throw new TaskException("Cannot create task file: " + e.getMessage());
        }
    }

    public Task read(String id) throws TaskException {
        if (loadedTasks.containsKey(id)) return loadedTasks.get(id);
        String file = tasksDirectory + File.separator + id + ".txt";
        if (!new File(file).exists()) throw new TaskException("Task not found");
        try {
            Task t = readTaskFromFile(file);
            loadedTasks.put(id, t);
            return t;
        } catch (IOException e) {
            throw new TaskException("Cannot read task file: " + e.getMessage());
        }
    }

    public void update(String id, Task updated) throws TaskException {
        String file = tasksDirectory + File.separator + id + ".txt";
        if (!new File(file).exists()) throw new TaskException("Task not found");
        try {
            writeTaskToFile(file, id, updated);
            loadedTasks.put(id, updated);
        } catch (IOException e) {
            throw new TaskException("Cannot update: " + e.getMessage());
        }
    }

    public void delete(String id) throws TaskException {
        String file = tasksDirectory + File.separator + id + ".txt";
        File f = new File(file);
        if (!f.exists()) throw new TaskNotFound(id);
        if (!f.delete()) throw new CannotDeleteTask(id);
        loadedTasks.remove(id);
    }

    public Map<String, Task> list() {
        return Collections.unmodifiableMap(loadedTasks);
    }

    private void writeTaskToFile(String path, String id, Task t) throws IOException {
        try (PrintWriter w = new PrintWriter(new FileWriter(path))) {
            w.println("id=" + id);
            w.println("name=" + safe(t.name));
            w.println("pic=" + safe(t.pic));
            w.println("status=" + (t.status != null ? t.status.label() : ""));
            w.println("desc=" + safe(t.desc));
            w.println("startDate=" + safe(t.startDate));
            w.println("endDate=" + safe(t.endDate));
            w.println("urgency=" + t.urgency);
            w.println("tags=" + String.join(",", t.tags));
        }
    }

    private Task readTaskFromFile(String path) throws IOException, TaskException {
        Map<String, String> m = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("=", 2);
                if (p.length == 2) m.put(p[0], p[1]);
            }
        }
        Status st = m.get("status").isEmpty() ? null : Status.from(m.get("status"));
        Set<String> tags = new HashSet<>();
        String tg = m.getOrDefault("tags", "");
        if (!tg.isEmpty()) tags.addAll(Arrays.asList(tg.split(",")));
        return new Task(
                m.get("name"),
                m.get("pic"),
                st,
                m.get("desc"),
                m.get("startDate"),
                m.get("endDate"),
                Integer.parseInt(m.getOrDefault("urgency", "0")),
                tags
        );
    }

    private String safe(String s) { return s == null ? "" : s; }
}
