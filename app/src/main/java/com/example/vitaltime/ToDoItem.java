package com.example.vitaltime;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.CountDownTimer;
public class ToDoItem {

    private String toDoListName;
    private boolean isCompleted;
    private Button TodoButton;
    private ProgressBar ProgressBar;
    private Date startTime;
    private Date endTime;
    private CountDownTimer timer;
    private String HoursRemaining;
    private String MinutesRemaining;
    private String secondsRemaining;
    public ToDoItem(String toDoListName, Date startTime, Date endTime, Context context) {
        this.toDoListName = toDoListName;
        this.isCompleted = false;
        this.TodoButton = new Button(context);
        this.TodoButton.setBackgroundColor(Color.TRANSPARENT);
        this.startTime = startTime;
        this.endTime = endTime;
        this.ProgressBar = new ProgressBar(context);
        this.HoursRemaining = "00";
        this.MinutesRemaining = "00";
        secondsRemaining = "00";
        this.TodoButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }




    public String getToDoListName() {
        return toDoListName;
    }

    public ProgressBar getProgressBar() {return ProgressBar;}

    public Date getStartTime() {return startTime;}

    public void setStartTime(Date startTime) {this.startTime = startTime;}

    public Date getEndTime() {return endTime;}

    public void setEndTime(Date endTime) {this.endTime = endTime;}

    public int getProgress() {
        long currentTime = (System.currentTimeMillis()- startTime.getTime())/60000;
        return(int)(currentTime);

    }
    public int getMaxProgress() {
        long endTimE = (endTime.getTime()- startTime.getTime())/60000;
        return (int)(endTimE);
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {

        isCompleted = completed;


    }
    public void setProgress(int progress) {
        ProgressBar.setProgress(progress);
    }

    public void setToDoListName(String toDoListName) {
        this.toDoListName = toDoListName;
    }

    public Button getTodoButton() {
        return TodoButton;
    }

    public String getHoursRemaining() {
        return HoursRemaining;
    }

    public void setHoursRemaining(String hoursRemaining) {
        this.HoursRemaining = hoursRemaining;
    }

    public String getMinutesRemaining() {
        return MinutesRemaining;
    }

    public void setMinutesRemaining(String minutesRemaining) {
        this.MinutesRemaining = minutesRemaining;
    }
    public String getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setSecondsRemaining(String SecondsRemaining) {
        this.secondsRemaining = SecondsRemaining;
    }
}