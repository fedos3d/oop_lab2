package DAL;

import java.util.ArrayList;
import java.util.function.Function;

public class EmployeeRepository implements IRepository<DALEmployee>{
    ArrayList<DALEmployee> DALEmployees = new ArrayList<>();
    @Override
    public ArrayList<DALEmployee> GetAll() {
        return DALEmployees;
    }

    @Override
    public DALEmployee Get(int id) {
        return DALEmployees.get(id);
    }

    @Override
    public void Create(DALEmployee item) {
        item.setId(DALEmployees.size() + 1);
        DALEmployees.add(item);
    }

    @Override
    public void Update(DALEmployee item) {

    }

    @Override
    public void Delete(int id) {

    }

    @Override
    public DALEmployee Find() {
        return null;
    }

    DALEmployee Find(String name) {
        for (DALEmployee dalEmployee : DALEmployees) {
            if (dalEmployee.getName().equals(name)) {
                return dalEmployee;
            }
        }
        return null;
    }
    public void UpdateLeader(DALEmployee lol, String name) {
        lol.setLeader(name);
    }
}
