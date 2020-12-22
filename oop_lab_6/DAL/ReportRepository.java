package DAL;

import java.util.ArrayList;
import java.util.function.Function;

public class ReportRepository implements IRepository<DALReport> {
    ArrayList<DALReport> DALReports;

    public ReportRepository() {
        DALReports = new ArrayList<>();
    }

    @Override
    public ArrayList<DALReport> GetAll() {
        return DALReports;
    }

    @Override
    public DALReport Get(int id) {
        return DALReports.get(id);
    }

    @Override
    public void Create(DALReport item) {
        item.setID(DALReports.size() + 1);
        DALReports.add(item);
    }

    @Override
    public void Update(DALReport item) {
        DALReports.set(item.getID() - 1, item);
    }

    @Override
    public void Delete(int id) {

    }

    @Override
    public DALReport Find() {
        return null;
    }

    @Override
    public void UpdateLeader(DALReport get, String name) {

    }
}
