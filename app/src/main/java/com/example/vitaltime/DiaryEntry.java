package com.example.vitaltime;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.util.Date;
public class DiaryEntry implements Parcelable {

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

    protected DiaryEntry(Parcel in) {
        title = in.readString();
        content = in.readString();
        mood = in.readString();
    }

    public static final Creator<DiaryEntry> CREATOR = new Creator<DiaryEntry>() {
        @Override
        public DiaryEntry createFromParcel(Parcel in) {
            return new DiaryEntry(in);
        }

        @Override
        public DiaryEntry[] newArray(int size) {
            return new DiaryEntry[size];
        }
    };

    public Date getDate() { return date; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getMood() { return mood; }
    public DiaryEntry getEntry() { return this; }

    public void setDate(Date date) { this.date = date; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setMood(String mood) { this.mood = mood; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(mood);
    }
}
