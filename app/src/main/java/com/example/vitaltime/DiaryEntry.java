package com.example.vitaltime;
import java.util.Date;
public class DiaryEntry {

    private Date date;
    private String title;
    private String content;
    private String mood;

    public DiaryEntry(Date date, String title, String mood, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.mood = mood;
    }

    public Date getDate() { return date; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getMood() { return mood; }
    public DiaryEntry getEntry() { return this; }

    public void setDate(Date date) { this.date = date; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setMood(String mood) { this.mood = mood; }




}
