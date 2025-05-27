package hiring;

// Represents a Job Request
public class JobRequest {
    private final int requestId;
    private final int userId;
    private final int jobId;
    private final String message;

    public JobRequest(int requestId, int userId, int jobId, String message) {
        this.requestId = requestId;
        this.userId = userId;
        this.jobId = jobId;
        this.message = message;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getUserId() {
        return userId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getMessage() {
        return message;
    }
}
