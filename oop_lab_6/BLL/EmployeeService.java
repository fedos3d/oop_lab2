package BLL;

import DAL.DALEmployee;
import DAL.IRepository;

import java.util.ArrayList;

public class EmployeeService implements IEmployeeService{
    private IRepository<DALEmployee> repo;
    public EmployeeService(IRepository<DALEmployee> kek) {
        this.repo = kek;
    }

    @Override
    public void addTask(TaskDTO task) {

    }

    @Override
    public void changeTask(int taskId) {

    }

    @Override
    public void fulfillTask(int taskId) {

    }

    @Override
    public void createDailyReport() {

    }

    @Override
    public void addFulfilledTasksToReport() {

    }

    @Override
    public void createFinalReport() {

    }

    @Override
    public EmployeeDTO get(String name) {
        return new EmployeeDTO("lol");
    }
    public void addEmployee(EmployeeDTO emp) {
        var kek = new DALEmployee();
        kek.setName(emp.getName());
        if (emp.getLeader() != null) {
            kek.setLeader(emp.getLeader());
        }
        repo.Create(kek);
    }

    @Override
    public void changeLeader() {

    }

    public void changeLeader(EmployeeDTO e1, EmployeeDTO e2) {
        //var kek = repo.Get(e1.getID());
        repo.UpdateLeader(repo.Get(e1.getID()), e2.getName());
    }
    public ArrayList<DALEmployee> Get() {
        return repo.GetAll();
    }
    public void updateInfo(EmployeeDTO emp) {
        repo.Get(emp.getID()).setLeader(emp.getLeader());
    }
}
