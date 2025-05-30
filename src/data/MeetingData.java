package data;

import model.Meeting;
import java.util.ArrayList;
import java.util.List;

public class MeetingData {
    private static final List<Meeting> meetings = new ArrayList<>();
    private static int nextId = 1;

    public static void saveMeeting(Meeting meeting) {
        meetings.add(meeting);
        nextId++;
    }

    public static List<Meeting> getMeetings() {
        return meetings;
    }

    public static int getNextId() {
        return nextId;
    }
}