package DAL;

public class DALEmployee implements IEntity{
    int id;
    String name;
    String leader;

    @Override
    public int getID() {
        return id;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLeader() {
        return leader;
    }
}
