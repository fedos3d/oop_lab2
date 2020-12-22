package PL;

import java.util.ArrayList;

public class ReportViewModel {
    private int id;
    private String reportText;
    private String author;
    private ReportStatus status;
    private ReportType type;
    public void setReportText(String reportText) {
        this.reportText = reportText;
    }
    ArrayList<Integer> solvedTasksIds = new ArrayList<>();

    @Override
    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Author: " + author + ", Status: " + status + "\n" + "Text:\n" + reportText;
    }

    public String getReportText() {
        return reportText;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public ReportType getType() {
        return type;
    }
    void addSolvedTask(int id) {
        solvedTasksIds.add(id);
    }
}
