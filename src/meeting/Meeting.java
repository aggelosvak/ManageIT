package meeting;

import java.util.List;

public class Meeting {
    private String title;
    private String date;
    private String time;
    private List<Employee> participants;
    private String description;
    private String location;

    public Meeting(String title, String date, String time, List<Employee> participants, String description, String location) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.participants = participants;
        this.description = description;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<Employee> getParticipants() {
        return participants;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }
}