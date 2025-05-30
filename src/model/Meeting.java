package model;

import company.Company;
import employee.Employee;
import manager.Manager;
import java.util.Date;
import java.util.List;

public class Meeting {
    private int id;
    private String title;
    private Date date;
    private Date time;
    private String description;
    private Company company;
    private Manager manager;
    private List<Employee> participants;
    private Room room; // If null: remote meeting

    public Meeting(int id, String title, Date date, Date time, String description,
                   Company company, Manager manager, List<Employee> participants, Room room) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.company = company;
        this.manager = manager;
        this.participants = participants;
        this.room = room;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Date getDate() { return date; }
    public Date getTime() { return time; }
    public String getDescription() { return description; }
    public Company getCompany() { return company; }
    public Manager getManager() { return manager; }
    public List<Employee> getParticipants() { return participants; }
    public Room getRoom() { return room; }
}