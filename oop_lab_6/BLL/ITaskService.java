package BLL;

import DAL.DALReport;
import DAL.DALTask;

import java.util.ArrayList;

public interface ITaskService {
    void addTask(TaskDTO rep);
    ArrayList<DALTask> Get();

    public void updateTaskStatus(TaskDTO task);
    public TaskDTO get(int id);
}
