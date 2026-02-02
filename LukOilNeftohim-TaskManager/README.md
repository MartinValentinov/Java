# Task Manager 

A simple command-line task manager written in Java. Manage tasks with ease - create, edit, view, and delete - all stored as individual files locally.

---

## Features

- Create tasks with:
  - Name
  - Person in charge
  - Description
  - Start and end dates
  - Status (`new`, `ready`, `in_progress`, `blocked`, `on_hold`, `done`, `cancelled`)
  - Urgency (1–3 or `low`, `medium`, `high`)
  - Tags
- Edit and update existing tasks
- Delete tasks
- List all tasks with color-coded urgency
- View detailed task information with colored status
- Each task is saved as a `.txt` file in a local folder

---

## Quick Start

1. Clone the repository:
```bash
git clone <repository_url>
````

2. Compile:
```bash
 make all
```
3. Run:
```bash
make run
```

Tasks are stored by default in `./tasks`.

---

## Commands

- **Add task**
```bash
add -n "Task Name" -p "John Doe" -d "Description" -s 2025-11-26 -e 2025-12-01 -u 2 -t new +tag1
```

- **Edit task**
```bash
edit <task_id> -n "New Name" +urgent
```

- **Remove task**
```bash
remove <task_id>
# alias: rm
```

- **List all tasks**
```bash
list
# alias: ls
```

- **Show task details**
```bash
show <task_id>
# alias: view
```

- **Help**
```bash
help
```

- **Exit**
```bash
exit
```

---

## Task Colors

- Urgency:
    - Low (1–3) → Green
    - Medium (4–6) → Yellow
    - High (7–8) → Red
    - Critical (9–10) → Dark Red
- Status colors indicate task state for quick identification.

---

## Project Structure

```
src/
├── Main.java
├── model/Task.java
├── service/
│   ├── TaskStorage.java
│   └── CommandLineInterface.java
├── util/InterfaceException.java
tasks/  # Folder for .txt task files
```
