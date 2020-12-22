package BLL;

import java.util.ArrayList;

public interface IReportService {
    void addReport(ReportDTO item);
    ArrayList<DAL.DALReport> Get();

    ReportDTO get(int id);

    void updateInfo(ReportDTO lol);
}
