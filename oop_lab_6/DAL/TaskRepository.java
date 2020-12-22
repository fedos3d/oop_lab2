package DAL;

import java.util.ArrayList;
import java.util.function.Function;

public class TaskRepository implements IRepository<DALTask>{
    ArrayList<DALTask> DALTasks = new ArrayList<>();
    @Override
    public ArrayList<DALTask> GetAll() {
        return DALTasks;
    }

    @Override
    public DALTask Get(int id) {
        return DALTasks.get(id);
    }

    @Override
    public void Create(DALTask item) {
        item.setId(DALTasks.size() + 1);
        DALTasks.add(item);
    }

    @Override
    public void Update(DALTask item) {
        DALTasks.set(item.getID() - 1, item);
    }

    @Override
    public void Delete(int id) {

    }

    @Override
    public DALTask Find() {
        return null;
    }

    @Override
    public void UpdateLeader(DALTask get, String name) {

    }
}
