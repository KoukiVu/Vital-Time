package com.example.vitaltime;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<ToDoItem> todos;
    private List<ToDoItem> FINTODOS;
    private OnTodoItemListener TodoListener;

    public TodoAdapter(List<ToDoItem> todos, List<ToDoItem> fintodos, OnTodoItemListener TodoListener) {
        this.todos = todos;
        FINTODOS = fintodos;
        this.TodoListener = TodoListener;
    }
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        return new TodoViewHolder(view,TodoListener);
    }
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder,int position) {
        ToDoItem todoItem = todos.get(position);
        holder.todoTitle.setText(todoItem.getToDoListName());
        holder.todocheckBox.setChecked(todoItem.isCompleted());
        holder.todocheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todoItem.setCompleted(isChecked);
            holder.deleteButton.setVisibility(isChecked ? View.VISIBLE : View.GONE);

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

        TodoViewHolder(View itemView, OnTodoItemListener OnTodoItemListener) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            todocheckBox = itemView.findViewById(R.id.todo_checkbox);
            deleteButton = itemView.findViewById(R.id.todo_delete);
        }

//HI

    }
    public interface OnTodoItemListener {
        void onDelete(int position);
    }
    public List<ToDoItem> getFinishedTodos() {return FINTODOS;}
    public List<ToDoItem> getTodos() {
        return todos;
    }
}
