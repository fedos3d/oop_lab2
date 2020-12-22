package BLL;

import DAL.DALTask;

import java.util.ArrayList;

public class TaskService implements ITaskService{
    DAL.IRepository<DALTask> repo; // didnt get how to use classes full path names? it should be just Report from DAL

    public TaskService(DAL.IRepository<DAL.DALTask> repo) {
        this.repo = repo;
    }

    public void addTask(TaskDTO item) {
        var newTask = new DAL.DALTask();
        newTask.setTaskName((item.getTaskText()));
        newTask.setTaskDesciption(item.getDescription());
        newTask.setExecutor(item.executor);
        newTask.setStatus(item.status);
        repo.Create(newTask);
        item.setId(newTask.getID());
    }

    public ArrayList<DALTask> Get() { //didnt really get what's happening here 39:12
        return repo.GetAll();
    }
    public void updateTaskStatus(TaskDTO task) {
        var kek = repo.Get(task.getId());
        kek.setStatus(task.status);
        kek.setTaskDesciption(task.getDescription());
        repo.Update(kek);
    }
    public TaskDTO get(int id) {
        var databasetask = repo.Get(id);
        var newTask = new TaskDTO();
        newTask.setDescription(databasetask.getTaskDesciption());
        newTask.setTaskText(databasetask.getTaskName());
        newTask.setExecutor(databasetask.getExecutor());
        return newTask;
    }
}
