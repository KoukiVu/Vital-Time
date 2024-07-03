package com.example.vitaltime;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoManager {
    private List<ToDoItem> todos;
    private TodoAdapter adapter;
    private List<ToDoItem> finishedTodos;

    public TodoManager() {
        this.adapter = adapter;
        this.todos = new ArrayList<>();
        this.finishedTodos = new ArrayList<>();
    }

    public TodoManager(TodoAdapter adapter) {
        this();
        this.adapter = adapter;
    }

    public void addTodo(String todo, Context context) {
        ToDoItem item = new ToDoItem(todo, context);
        todos.add(item);
        adapter.notifyItemInserted(todos.size() - 1);
    }

    public void removeTodo(int index) {
        if (index >= 0 && index < todos.size()) {
            finishedTodos.add(todos.get(index));
            todos.remove(todos.get(index));
        }
    }

    public void markItemComplete(int index) {
        if (index >= 0 && index < todos.size()) {
            ToDoItem item = todos.get(index);
            item.setCompleted(true);
            adapter.notifyItemChanged(index);
        }
    }

    public void displayTodos() {
        for (int i = 0; i < todos.size(); i++) {
            System.out.println((i + 1) + ". " + todos.get(i));
        }
    }

    public List<ToDoItem> getTodos() {
        return todos;
    }

    public List<ToDoItem> getFinishedTodos() {
        return finishedTodos;
    }

    public void SetAdapter(TodoAdapter adapter) {
        this.adapter = adapter;
    }
}


