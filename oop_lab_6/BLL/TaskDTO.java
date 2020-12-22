package BLL;

import PL.TaskStatus;

public class TaskDTO {
    String taskText;
    int id;
    String executor;
    String description;
    TaskStatus status;

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getExecutor() {
        return executor;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
