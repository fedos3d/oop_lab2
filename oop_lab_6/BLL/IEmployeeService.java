package BLL;

import DAL.DALEmployee;
import DAL.DALReport;

import java.util.ArrayList;

public interface IEmployeeService {
    void addTask(TaskDTO task);
    void changeTask(int taskId);
    void fulfillTask(int taskId);
    void createDailyReport();
    void addFulfilledTasksToReport();
    void createFinalReport();
    EmployeeDTO get(String name);

    void addEmployee(EmployeeDTO emp);

    void changeLeader();

    void changeLeader(EmployeeDTO kek, EmployeeDTO employee);

    ArrayList<DALEmployee> Get();

    void updateInfo(EmployeeDTO employee);
}
