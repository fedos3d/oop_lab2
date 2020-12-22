package BLL;

import DAL.DALReport;

import java.util.ArrayList;

public class ReportService implements IReportService{
    private DAL.IRepository<DALReport> repo;

    public ReportService(DAL.IRepository<DAL.DALReport> repo) {
        this.repo = repo;
    }

    @Override
    public void addReport(ReportDTO item) {
        var newReport = new DAL.DALReport();
        newReport.setText(item.getText());
        newReport.setAuthor(item.getAuthor());
        newReport.setStatus(item.getStatus());
        newReport.setType(item.getType());
        newReport.setSolvedtasks(item.getSolvedtasks());
        repo.Create(newReport);
        item.setId(newReport.getID());
    }

    @Override
    public ArrayList<DAL.DALReport> Get() { //didnt really get what's happening here 39:12
        return repo.GetAll();
    }
    public ReportDTO get(int id) {
        var databasereport = repo.Get(id);
        if (databasereport == null) {
            throw new NoSuchReportExcetion();
        }
        var newReport = new ReportDTO();
        newReport.setStatus(databasereport.getStatus());
        newReport.setText(databasereport.getText());
        newReport.setAuthor(databasereport.getAuthor());
        newReport.setType(databasereport.getType());
        newReport.setSolvedtasks(databasereport.getSolvedtasks());
        return newReport;
    }
    public void updateInfo(ReportDTO rep) {
        var kek = repo.Get(rep.getId());
        kek.setStatus(rep.getStatus());
        kek.setText(rep.getText());
        kek.setSolvedtasks(rep.getSolvedtasks());
        repo.Update(kek);
    }
}
