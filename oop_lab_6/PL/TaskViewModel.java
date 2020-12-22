package PL;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskViewModel {
    String taskname;
    String taskdescription;
    String executor;
    TaskStatus taskStatus;
    String comment;
    Date taskCreationDate;
    int id;
    Map<Date, String> changes = new HashMap<Date, String>();
    public TaskViewModel(String taskname, String taskdescription, String executor){
        this.taskname = taskname;
        this.taskdescription = taskdescription;
        this.executor = executor;
        this.taskCreationDate = new Date();
        this.taskStatus = TaskStatus.OPEN;
    }
    public void setTaskName(String tasktext) {
        this.taskname = tasktext;
    }

    public String getTaskName() {
        return taskname;
    }

    @Override
    public String toString() {
        return "Task ID: " + "\n" + "Task name: " + taskname + "\n"
                + "Task status" + taskStatus + "\n"
                + "Task description " + taskdescription + "\n"
                + "Executor: " + executor.toString() + "\n"
                + "Creation Date: " + taskCreationDate.toString() + "\n"
                + "Comment: " + comment.toString() + "\n";
    }
    public String getTaskdescription() {
        return taskdescription;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void changeExecutor(EmployeeViewModel executor) {
        this.executor = executor.getName();
    }

    public int getId() {
        return id;
    }
    public void changeState(TaskStatus status) {
        taskStatus = status;
    }

    public String getExecutor() {
        return executor;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
}
