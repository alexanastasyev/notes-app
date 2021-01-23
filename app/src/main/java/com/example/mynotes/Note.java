package com.example.mynotes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String date;
    private int priority;

    public Note(int id, String title, String description, String date, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.date = date;
    }

    @Ignore
    public Note(String title, String description, String date, int priority) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
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

    public int getPriority() {
        return priority;
    }

}
