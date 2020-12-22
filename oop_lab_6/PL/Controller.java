package PL;

import BLL.*;

import java.util.ArrayList;

public class Controller {
    //fields
    EmployeeDTO curuser;
    BLL.IReportService reportService;
    BLL.ITaskService taskService;
    IEmployeeService employeeService;
    public Controller(BLL.IReportService service, IEmployeeService src, ITaskService taskService) {
        this.reportService = service;
        this.employeeService = src;
        this.taskService = taskService;
    }

    //report part ------------------------------------------------------------------------------------------------
    void addReport(ReportViewModel item) {
        var rep = new ReportDTO();
        rep.setText(item.getReportText());
        rep.setAuthor(item.getAuthor());
        rep.setStatus(item.getStatus());
        rep.setType(item.getType());
        reportService.addReport(rep);
    }
    void updateReport(ReportViewModel item) {
        var lol = getReport(item.getID());
        lol.setText(item.getReportText());
        lol.setStatus(item.getStatus());
        lol.setAuthor(item.getAuthor());
        reportService.updateInfo(lol);
    }
    ArrayList<ReportViewModel> showReports() {
        var mem = reportService.Get();
        var kek = new ArrayList<ReportViewModel>();
        for (int i = 0; i < mem.size(); i++) {
            var lol = new ReportViewModel();
            lol.setReportText(mem.get(i).getText());
            lol.setStatus(mem.get(i).getStatus());
            lol.setAuthor(mem.get(i).getAuthor());
            lol.setType(mem.get(i).getType());
            kek.add(lol);
        }
        return kek;
    }
    private ReportDTO getReport(int id) {
        return reportService.get(id);
    }
    //public ReportViewModel createFinalReport() {
    //    //here we calculate our finalReport in BLL
    //}

    //tasks part ---------------------------------------------------------------------------------------------------

    void addTask(TaskViewModel task) {
        var rep = new TaskDTO();
        rep.setTaskText(task.getTaskName());
        rep.setDescription(task.getTaskdescription());
        rep.setExecutor(task.getExecutor());
        taskService.addTask(rep);
    }
    ArrayList<TaskViewModel> showTasks() {
        var kek = taskService.Get();
        var lol = new ArrayList<TaskViewModel>();
        for (int i = 0; i < kek.size(); i++) {
            var cur = new TaskViewModel(kek.get(i).getTaskName(), kek.get(i).getTaskDesciption(), kek.get(i).getExecutor());
            lol.add(cur);
        }
        return lol;
    }
    public void startTask(TaskViewModel task){
        var lol = getTask(task.getId());
        lol.setStatus(TaskStatus.ACTIVE);
        taskService.updateTaskStatus(lol);
    }
    public void finishTask(TaskViewModel task) {
        var lol = getTask(task.getId());
        lol.setStatus(TaskStatus.RESOLVED);
        taskService.updateTaskStatus(lol);
    }
    public void updateTaskInfo(TaskViewModel task) {
        var lol = getTask(task.id);
        lol.setDescription(task.getTaskdescription());
        taskService.updateTaskStatus(lol);

    }
    private TaskDTO getTask(int id) {
        return taskService.get(id);
    }

    //employee part --------------------------------------------------------------------------------------------------
    void createEmployee(EmployeeViewModel en) {
        var emp = new EmployeeDTO(en.getName());
        if (en.getLeader() != null) {
            emp.setLeader(en.getLeader());
        }
        employeeService.addEmployee(emp);
    }
    private EmployeeDTO getEmployee(String lel) {
        return employeeService.get(lel);
    }
    void changeLeader(EmployeeViewModel emp, EmployeeViewModel newLeader) {
        var kek = getEmployee(emp.getName());
        kek.setLeader(newLeader.getName());
        employeeService.changeLeader(kek, getEmployee(newLeader.getName()));
    }
    public ArrayList<EmployeeViewModel> getListOfEmployess() {
        var mem = employeeService.Get();
        var kek = new ArrayList<EmployeeViewModel>();
        for (int i = 0; i < mem.size(); i++) {
            var cur = new EmployeeViewModel(mem.get(i).getName(), Roles.REGULAR);
            cur.setLeader(mem.get(i).getLeader());
            kek.add(cur);
        }
        return kek;
    }
    public void upadateEmpInfo(EmployeeViewModel emp) {
        var kek = getEmployee(emp.getName());
        kek.setLeader(emp.getLeader());
        employeeService.updateInfo(kek);
    }
}
