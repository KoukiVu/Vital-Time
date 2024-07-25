package com.example.vitaltime;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vitaltime.databinding.FragmentTodoBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<ToDoItem> todos;
    private OnTodoItemListener TodoListener;
    private Handler handler = new Handler();
    boolean TodoVision = true;

    public TodoAdapter(List<ToDoItem> todos, List<ToDoItem> fintodos, OnTodoItemListener TodoListener) {
        this.todos = todos;
        this.TodoListener = TodoListener;

    }
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        return new TodoViewHolder(view,TodoListener);
    } //
    //hi
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder,int position) {
        ToDoItem todoItem = todos.get(position);
        holder.todoTitle.setText(todoItem.getToDoListName());
        holder.todocheckBox.setChecked(todoItem.isCompleted());

        holder.progressBar.setMax(todoItem.getMaxProgress());
        holder.progressBar.setProgress(todoItem.getProgress());

        if(todoItem.isCompleted()){
            holder.progressBar.setVisibility(View.GONE);
            holder.minutesTextView.setVisibility(View.GONE);
            holder.hoursTextView.setVisibility(View.GONE);
            holder.secondsTextView.setVisibility(View.GONE);
            holder.todoTitle.setTextColor(Color.GRAY);
            holder.Semi1.setVisibility(View.GONE);
            holder.Semi2.setVisibility(View.GONE);
            if(!TodoVision){holder.todocheckBox.setClickable(false);
                holder.deleteButton.setText("Delete");}
        }

        if(!todoItem.isCompleted()){
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.minutesTextView.setVisibility(View.VISIBLE);
            holder.hoursTextView.setVisibility(View.VISIBLE);
            holder.secondsTextView.setVisibility(View.VISIBLE);
            holder.todoTitle.setTextColor(Color.BLACK);
            holder.Semi1.setVisibility(View.VISIBLE);
            holder.Semi2.setVisibility(View.VISIBLE);
            ProgressUpdater();
        }
        long currentTime = System.currentTimeMillis();
        long remainingTime = todoItem.getEndTime().getTime()- currentTime;
        long hours = (remainingTime/3600000);
        long minutes = (remainingTime/60000)%60;
        long seconds = (remainingTime/1000)%60;

        String hoursString = String.format("%02d",hours);
        String minutesString = String.format("%02d",minutes);
        String secondsString = String.format("%02d",seconds);

        holder.hoursTextView.setText(hoursString);
        holder.minutesTextView.setText(minutesString);
        holder.secondsTextView.setText(secondsString);

        holder.todocheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todoItem.setCompleted(isChecked);

            holder.deleteButton.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            holder.hoursTextView.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            holder.minutesTextView.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            holder.secondsTextView.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        });
        holder.deleteButton.setOnClickListener(view -> {

            if (TodoListener != null) {
                TodoListener.onDelete(position);
            }
        });

    }
    @Override
    public int getItemCount(){
        return todos.size();
    }
    static class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView todoTitle;
        CheckBox todocheckBox;
        Button deleteButton;
        ProgressBar progressBar;
        TextView hoursTextView;
        TextView minutesTextView;
        TextView secondsTextView;
        TextView Semi1;
                TextView Semi2;
        TodoViewHolder(View itemView, OnTodoItemListener OnTodoItemListener) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            todocheckBox = itemView.findViewById(R.id.todo_checkbox);
            deleteButton = itemView.findViewById(R.id.todo_delete);
            progressBar = itemView.findViewById(R.id.seekBar2);
            hoursTextView = itemView.findViewById(R.id.todo_hours);
            minutesTextView = itemView.findViewById(R.id.todo_minutes);
            secondsTextView = itemView.findViewById(R.id.todo_seconds);
            Semi1 = itemView.findViewById(R.id.todo_semicolon);
            Semi2 = itemView.findViewById(R.id.todo_semicolon2);
        }



    }
    public void setTodos(List<ToDoItem> todos) {
        this.todos = todos;
        SOrtToDoList();
        notifyDataSetChanged();
    }
    public void SOrtToDoList(){
        Collections.sort(todos, new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                return  o1.getEndTime().compareTo(o2.getEndTime());
            }
        });
    }

    public interface OnTodoItemListener {
        void onDelete(int position);
    }

    public List<ToDoItem> getTodos() {
        return todos;
    }
    private void ProgressUpdater(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
boolean hasChanged = false;
int index = 0;
                for(ToDoItem ItEms: todos){
                    hasChanged = false;
                    if(ItEms.getEndTime() != null && ItEms.getStartTime() != null){
                        ItEms.setProgress(ItEms.getProgress());
                        long remainingTime = ItEms.getEndTime().getTime()- currentTime;
                        if(remainingTime < 0){remainingTime = 0;}
                        long hours = (remainingTime/3600000);
                        long minutes = (remainingTime/60000)%60;
                        long seconds = (remainingTime/1000)%60;


                        String hoursString = String.format("%02d",hours);
                        String minutesString = String.format("%02d",minutes);
                        String secondsString = String.format("%02d",seconds);


                        if (!hoursString.equals(ItEms.getHoursRemaining())) {
                            ItEms.setHoursRemaining(hoursString);
                            hasChanged = true;
                        }
                        if (!minutesString.equals(ItEms.getMinutesRemaining())) {
                            ItEms.setMinutesRemaining(minutesString);
                            hasChanged = true;
                        }
                        if (!secondsString.equals(ItEms.getSecondsRemaining())) {
                            ItEms.setSecondsRemaining(secondsString);
                            hasChanged = true;
                        }
                       index = todos.indexOf(ItEms);
                    }
                    if(hasChanged){
                        notifyDataSetChanged();
                    }

                }

                handler.postDelayed(this, 1000);
            }
        },1000);
    }


    private void animateFlip(TextView textView,String newText){
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f,1f,0f,1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(newText);
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1f,1f,0f,1f,
                        Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(300);
                scaleAnimation.setFillAfter(true);
                textView.startAnimation(scaleAnimation);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        textView.startAnimation(scaleAnimation);
    }
    public boolean showFinishedTodos(boolean show){
        TodoVision = show;
        if(!show){
            for(ToDoItem ItEms: todos){
                ItEms.setCompleted(true);
            }
        }
        return show;
    }

    public boolean showTodos(boolean show){
        TodoVision = show;
        if(show){
            for(ToDoItem ItEms: todos){
                ItEms.setCompleted(false);
            }
        }
        return show;
    }
}