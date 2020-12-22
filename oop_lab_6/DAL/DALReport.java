package DAL;

import PL.ReportStatus;
import PL.ReportType;

import java.util.ArrayList;

public class DALReport implements IEntity {
    int ID;
    String Text;
    String author;
    ReportStatus status;
    ReportType type;
    ArrayList<Integer> solvedtasks;
    @Override
    public int getID() {
        return ID;
    }
    public void setID(int id) {
        this.ID = id;
    }
    public String getText() {
        return Text;
    }
    public void setText(String txt) {
        this.Text = txt;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public ArrayList<Integer> getSolvedtasks() {
        return solvedtasks;
    }

    public void setSolvedtasks(ArrayList<Integer> solvedtasks) {
        this.solvedtasks = solvedtasks;
    }
}
