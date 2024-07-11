package com.example.vitaltime;

import android.app.Application;

public class ApplicationData extends Application {
    private static ApplicationData instance;
    private DiaryBook diaryBook;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        diaryBook = new DiaryBook();
    }

    public static ApplicationData getInstance(){ return instance; }
    public DiaryBook getDiaryBook() { return diaryBook; }
    public void setDiaryBook(DiaryBook diaryBook) { this.diaryBook = diaryBook; }
    public void addDiaryEntry(DiaryEntry entry) { diaryBook.addDiaryEntry(entry); }






}
