package BLL;

import PL.ReportStatus;
import PL.ReportType;

import java.util.ArrayList;

public class ReportDTO {
    private int ID;
    private String text;
    private String author;
    private ReportStatus status;
    private ReportType type;
    private ArrayList<Integer> solvedtasks;

    public void setId(int ID) {
        this.ID = ID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }
    public void setSolvedtasks(ArrayList<Integer> solvedtasks) {
        this.solvedtasks = solvedtasks;
    }

    public ArrayList<Integer> getSolvedtasks() {
        return solvedtasks;
    }
}
