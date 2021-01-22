package com.example.mynotes;

import java.util.Date;

public class Note {

    private int id;
    private String title;
    private String description;
    private String date;
    private Priority priority;

    public Note(int id, String title, String description, String date, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

}
