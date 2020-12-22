package DAL;

import PL.TaskStatus;

public class DALTask implements IEntity{
    String taskName;
    String taskDesciption;
    String executor;
    TaskStatus status;
    int id;
    @Override
    public int getID() {
        return id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskDesciption(String taskDesciption) {
        this.taskDesciption = taskDesciption;
    }

    public String getTaskDesciption() {
        return taskDesciption;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
