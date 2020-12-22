package DAL;

import java.util.ArrayList;
import java.util.function.Function;

public interface IRepository<T extends IEntity> {
    ArrayList<T> GetAll();
    T Get(int id);

    void Create(T item);
    void Update(T item);
    void Delete(int id);
    T Find();

    void UpdateLeader(T get, String name);
}
