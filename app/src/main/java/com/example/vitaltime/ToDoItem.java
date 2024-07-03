package com.example.vitaltime;
import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
public class ToDoItem {
    private String toDoListName;
    private boolean isCompleted;
    private Button TodoButton;

    public ToDoItem(String toDoListName, Context context) {
        this.toDoListName = toDoListName;
        this.isCompleted = false;
        this.TodoButton = new Button(context);
        this.TodoButton.setBackgroundColor(Color.TRANSPARENT);
        this.TodoButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public String getToDoListName() {
        return toDoListName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
//hi
    public void setCompleted(boolean completed) {

        isCompleted = completed;


    }

    public void setToDoListName(String toDoListName) {
        this.toDoListName = toDoListName;
    }

    public Button getTodoButton() {
        return TodoButton;
    }

}
