package com.example.vitaltime;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<ToDoItem> todos;
    private OnTodoItemListener TodoListener;
    private Handler handler = new Handler();
    boolean TodoVision = true;
    private ScheduledExecutorService executorService;

    public TodoAdapter(List<ToDoItem> todos, List<ToDoItem> fintodos, OnTodoItemListener TodoListener) {
        this.todos = todos;
        this.TodoListener = TodoListener;
        this.handler = new Handler(Looper.getMainLooper());
        this.executorService = Executors.newScheduledThreadPool(1);
        ProgressUpdater();
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view, TodoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        ToDoItem todoItem = todos.get(position);
        holder.todoTitle.setText(todoItem.getToDoListName());
        holder.todocheckBox.setChecked(todoItem.isCompleted());

        holder.progressBar.setMax(todoItem.getMaxProgress());
        holder.progressBar.setProgress(todoItem.getProgress());

        if (todoItem.isCompleted()) {
            holder.progressBar.setVisibility(View.GONE);
            holder.minutesTextView.setVisibility(View.GONE);
            holder.hoursTextView.setVisibility(View.GONE);
            holder.todoTitle.setTextColor(Color.GRAY);
            if (!TodoVision) {
                holder.todocheckBox.setClickable(false);
                holder.deleteButton.setText("Delete");
            }
        }

        if (!todoItem.isCompleted()) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.minutesTextView.setVisibility(View.VISIBLE);
            holder.hoursTextView.setVisibility(View.VISIBLE);
            holder.todoTitle.setTextColor(Color.BLACK);
ProgressUpdater();
        }
        long currentTime = System.currentTimeMillis();
        long remainingTime = todoItem.getEndTime().getTime() - currentTime;
        long hours = (remainingTime / 3600000);
        long minutes = (remainingTime / 60000) % 60;

        String hoursString = String.format("%02d", hours);
        String minutesString = String.format("%02d", minutes);
        holder.hoursTextView.setText(hoursString);
        holder.minutesTextView.setText(minutesString);

        holder.todocheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todoItem.setCompleted(isChecked);

            holder.deleteButton.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            holder.hoursTextView.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            holder.minutesTextView.setVisibility(isChecked ? View.GONE : View.VISIBLE);

        });
        holder.deleteButton.setOnClickListener(view -> {

            if (TodoListener != null) {
                TodoListener.onDelete(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView todoTitle;
        CheckBox todocheckBox;
        Button deleteButton;
        ProgressBar progressBar;
        TextView hoursTextView;
        TextView minutesTextView;

        TodoViewHolder(View itemView, OnTodoItemListener OnTodoItemListener) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            todocheckBox = itemView.findViewById(R.id.todo_checkbox);
            deleteButton = itemView.findViewById(R.id.todo_delete);
            progressBar = itemView.findViewById(R.id.seekBar2);
            hoursTextView = itemView.findViewById(R.id.todo_hours);
            minutesTextView = itemView.findViewById(R.id.todo_minutes);
        }


    }

    public void setTodos(List<ToDoItem> todos) {
        this.todos = todos;
        SOrtToDoList();
        notifyDataSetChanged();
    }

    public void SOrtToDoList() {
        Collections.sort(todos, new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                return o1.getEndTime().compareTo(o2.getEndTime());
            }
        });
    }

    public interface OnTodoItemListener {
        void onDelete(int position);
    }

    public List<ToDoItem> getTodos() {
        return todos;
    }

    private void ProgressUpdater() {
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                long currentTime = System.currentTimeMillis();
                boolean dataChanged = false;
                for (ToDoItem ItEms : todos) {
                    if (ItEms.getEndTime() != null && ItEms.getStartTime() != null) {
                        ItEms.setProgress(ItEms.getProgress());
                        long remainingTime = ItEms.getEndTime().getTime() - currentTime;
                        long hours = (remainingTime / 3600000);
                        long minutes = (remainingTime / 60000) % 60;
                        String hoursString = String.format("%02d", hours);
                        String minutesString = String.format("%02d", minutes);

                        if (!hoursString.equals(ItEms.getHoursRemaining()) || !minutesString.equals(ItEms.getMinutesRemaining())) {

                            ItEms.setHoursRemaining(hoursString);
                            ItEms.setMinutesRemaining(minutesString);
                            dataChanged = true;
                        }
                    }

                }

                if (dataChanged) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }

                    });
                }
            }
        }, 1, 10, TimeUnit.SECONDS);
    }


    public boolean showFinishedTodos(boolean show) {
        TodoVision = show;
        if (!show) {
            for (ToDoItem ItEms : todos) {
                ItEms.setCompleted(true);
            }
        }
        return show;
    }


    public void ShutDownExecutorService() {

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}

