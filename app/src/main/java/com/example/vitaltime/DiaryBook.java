package com.example.vitaltime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiaryBook {
    private Map<Date,DiaryEntry> Diary;
    public DiaryBook() {
        Diary = new HashMap<Date,DiaryEntry>();

    }
    public Map<Date,DiaryEntry> getDiary() {
        return Diary;
    }
    public void addDiaryEntry(DiaryEntry diaryEntry) {
        Diary.put(diaryEntry.getDate(),diaryEntry);
    }
    public void removeDiaryEntry(DiaryEntry diaryEntry) {
        Diary.remove(diaryEntry.getDate());
    }

    public DiaryEntry getEntryKey(Date date){
        for (Map.Entry<Date,DiaryEntry> entry : Diary.entrySet()){
            Date EntryDate = entry.getKey();
            if (EntryDate.getMonth() == date.getMonth() && EntryDate.getYear() == date.getYear() && EntryDate.getDate() == date.getDate()){
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> diaryBookMap = new HashMap<>();
        for (Map.Entry<Date, DiaryEntry> entry : Diary.entrySet()) {
            diaryBookMap.put(entry.getKey().toString(), entry.getValue().toMap());
        }
        return diaryBookMap;
    }
}
