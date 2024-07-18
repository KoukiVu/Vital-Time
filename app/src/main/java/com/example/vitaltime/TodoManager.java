package com.example.vitaltime;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;
import java.util.List;

public class TodoManager {
    private List<ToDoItem> todos;
    private List<ToDoItem> todosHOLDER;
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

    public void addTodo(String todo, Date startTime, Date endTime, Context context) {
        ToDoItem item = new ToDoItem(todo, startTime, endTime, context);
        todos.add(item);
        sortTodos();
        adapter.notifyItemInserted(todos.size() - 1);
    }

    public void addFinishedTodo(String todo,Date startTime, Date endTime, Context context) {
        ToDoItem item = new ToDoItem(todo, startTime, endTime, context);
        finishedTodos.add(item);

        adapter.notifyItemInserted(finishedTodos.size() - 1);
    }

    public void removeTodo(int index) {
        if (index >= 0 && index < todos.size()) {
            todos.remove(todos.get(index));
            adapter.notifyItemRemoved(index);
        }
    }
    public void DeleteTodo(int index){

        finishedTodos.remove(finishedTodos.get(index));
        adapter.notifyItemRemoved(index);

    }
    public void markItemComplete(int index) {
        if (index >= 0 && index < todos.size()) {
            ToDoItem item = todos.get(index);
            item.setCompleted(true);
            adapter.notifyItemChanged(index);
        }
    }
    public  void sortTodos(){
        Collections.sort(todos, new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                return  o1.getEndTime().compareTo(o2.getEndTime());
            }
        });
    }

    public void displayTodos() {
        for (int i = 0; i < todos.size(); i++) {
            System.out.println((i + 1) + ". " + todos.get(i));
        }
    }

    public List<ToDoItem> getTodos() {
        return todos;
    }
    public List<ToDoItem> getFinishedTodos() {return finishedTodos;}
    public void CheckTodos(){
        for (ToDoItem item : finishedTodos) {
            item.setCompleted(true);
        }
    }
    public void SetAdapter(TodoAdapter adapter) {
        this.adapter = adapter;
    }

    public void setFinishedTodoFormat(List<ToDoItem> finishedTodos){
        for (ToDoItem item : finishedTodos) {
            item.setCompleted(true);
        }
    }

}


