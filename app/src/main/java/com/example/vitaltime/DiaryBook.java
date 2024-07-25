package com.example.vitaltime;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.google.firebase.database.DataSnapshot;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DiaryBook {
    private Map<Date, DiaryEntry> Diary;

    public DiaryBook() {
        Diary = new HashMap<Date, DiaryEntry>();

    }

    public Map<Date, DiaryEntry> getDiary() {
        return Diary;
    }

    public void addDiaryEntry(DiaryEntry diaryEntry) {
        Diary.put(diaryEntry.getDate(), diaryEntry);
    }

    public void removeDiaryEntry(DiaryEntry diaryEntry) {
        Diary.remove(diaryEntry.getDate());
    }

    public DiaryEntry getEntryKey(Date date) {
        for (Map.Entry<Date, DiaryEntry> entry : Diary.entrySet()) {
            Date EntryDate = entry.getKey();
            if (EntryDate.getMonth() == date.getMonth() && EntryDate.getYear() == date.getYear() && EntryDate.getDate() == date.getDate()) {
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

    public void updateFromFirebase(DataSnapshot dataSnapshot) {
        Map<String, Object> diaryBookMap = (Map<String, Object>) dataSnapshot.getValue();
        if (diaryBookMap != null) {
            Diary.clear(); // Clear existing entries
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            for (Map.Entry<String, Object> entry : diaryBookMap.entrySet()) {
                try {
                    Date date = sdf.parse(entry.getKey());
                    Map<String, Object> entryMap = (Map<String, Object>) entry.getValue();
                    DiaryEntry diaryEntry = new DiaryEntry(
                            date,
                            (String) entryMap.get("title"),
                            (String) entryMap.get("mood"),
                            (String) entryMap.get("content")
                    );
                    addDiaryEntry(diaryEntry);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
