public class Report {
    private String reportId;            // Unique identifier for the report
    private Timestamp timestampReport; // Time when the report was generated
    private String reportedId;         // ID of the reported entity
    private String userId;             // ID of the user who created the report

    // Constructor
    public Report(String reportId, Timestamp timestampReport, String reportedId, String userId) {
        this.reportId = reportId;
        this.timestampReport = timestampReport;
        this.reportedId = reportedId;
        this.userId = userId;
    }

    // Getters and Setters
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Timestamp getTimestampReport() {
        return timestampReport;
    }

    public void setTimestampReport(Timestamp timestampReport) {
        this.timestampReport = timestampReport;
    }

    public String getReportedId() {
        return reportedId;
    }

    public void setReportedId(String reportedId) {
        this.reportedId = reportedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return "Report{" +
                "reportId='" + reportId + '\'' +
                ", timestampReport=" + timestampReport +
                ", reportedId='" + reportedId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}