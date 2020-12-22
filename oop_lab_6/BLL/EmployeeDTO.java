package BLL;

import DAL.DALEmployee;

import java.util.ArrayList;

public class EmployeeDTO {
    private int id;
    private String name;
    private String leader;
    private ArrayList<EmployeeDTO> subordinates;



    public EmployeeDTO(String name) {
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeader() {
        return leader;
    }

    public String getName() {
        return name;
    }


}
