package PL;

import BLL.EmployeeService;
import BLL.TaskService;
import DAL.EmployeeRepository;
import DAL.TaskRepository;

public class Program {
    public static void main(String[] args) {
        //reportservice
        var repo = new DAL.ReportRepository();
        var service = new BLL.ReportService(repo);
        //employeeservice
        var emprepo = new EmployeeRepository();
        var emsrv = new EmployeeService(emprepo);
        //taskservice
        var taskrepo = new TaskRepository();
        var tasksrv = new TaskService(taskrepo);

        //controller creation
        var controller = new Controller(service, emsrv, tasksrv);

        //create employees
        var emp1 = new EmployeeViewModel("Tomas", Roles.REGULAR);
        var emp2 = new EmployeeViewModel("Lolek", Roles.REGULAR);
        var emp3 = new EmployeeViewModel("Bolek", Roles.REGULAR);

        //add employees
        controller.createEmployee(emp1);
        controller.createEmployee(emp2);
        controller.createEmployee(emp3);

        //check if employess were added to our DAL layer
        var emps = controller.getListOfEmployess();
        for (EmployeeViewModel emp : emps) {
            System.out.println(emp.getName());
        }
        System.out.println();

        //lets try to set leader to a new employee
        var emp4 = new EmployeeViewModel("Mike", Roles.REGULAR);
        emp4.setLeader(emp1.getName());
        controller.createEmployee(emp4);

        //lets add leader to already created employee
        emp2.setLeader(emp1.getName());
        controller.upadateEmpInfo(emp2);

        //check our leader change
        var emps2 = controller.getListOfEmployess();
        for (EmployeeViewModel emp : emps2) {
            System.out.println(emp.getName());
            if (emp.getLeader() != null) {
                System.out.println(", Leader: " + emp.getLeader());
            }
        }

        System.out.println();
        //------------------------------------------------------------------------------------------------------------
        //lets test task system
        var task1 = new TaskViewModel("Fix bugs", "Fix bugs in module 2", emp1.getName());
        controller.addTask(task1);

        var tasks = controller.showTasks();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("Task: " + tasks.get(i).getTaskName() + ", Description: " +
                   tasks.get(i).getTaskdescription() + ", Excecutor: " + tasks.get(i).getExecutor() + ", Task status: " +
                    tasks.get(i).getTaskStatus());
        }

        System.out.println();

        controller.startTask(task1);

        tasks = controller.showTasks();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("Task: " + tasks.get(i).getTaskName() + ", Description: " +
                    tasks.get(i).getTaskdescription() + ", Excecutor: " + tasks.get(i).getExecutor() + ", Task status: " +
                    tasks.get(i).getTaskStatus());
        }
        //-------------------------------------------------------------------------------------------------------------
        //lets test report system
        var report1 = new ReportViewModel();
        report1.setAuthor(emp1.getName());
        report1.setReportText("Today we fixed this and this");
        report1.setStatus(ReportStatus.OPEN);
        report1.setType(ReportType.DAILY);
        controller.addReport(report1);

        var reports = controller.showReports();
        for (int i = 0; i < reports.size(); i++) {
            System.out.println(reports.get(i));
        }

        report1.setReportText("Darova darova");
        report1.setStatus(ReportStatus.FINISHED);
        controller.updateReport(report1);

        reports = controller.showReports();
        for (int i = 0; i < reports.size(); i++) {
            System.out.println(reports.get(i));
        }
    }
}
