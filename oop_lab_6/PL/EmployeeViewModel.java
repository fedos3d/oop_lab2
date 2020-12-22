package PL;

import java.util.ArrayList;

public class EmployeeViewModel {
    private String name;
    private String leader;
    private ArrayList<EmployeeViewModel> subordinates;
    private Roles role;

    EmployeeViewModel(String name, Roles role) {
        this.name = name;
        this.role = role;

    }
    @Override
    public String toString() {
        return "Name: " + name + " " + "Leader: " + leader + " Role: " + role;
    }
    public String getName() {
        return name;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public ArrayList<EmployeeViewModel> getSubordinates() {
        return subordinates;
    }

    public String getLeader() {
        return leader;
    }
}
