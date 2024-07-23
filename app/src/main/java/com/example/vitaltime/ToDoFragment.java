package com.example.vitaltime;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.databinding.FragmentTodoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToDoFragment extends Fragment implements TodoAdapter.OnTodoItemListener {

    private FragmentTodoBinding binding;
    private TodoAdapter.TodoViewHolder T_binding;
    private TodoManager todoManager;
    private TodoAdapter todoAdapter;
    private boolean TodoSwitch;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTodoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TodoSwitch = true;
        todoManager = new TodoManager();
        todoAdapter = new TodoAdapter(todoManager.getTodos(),todoManager.getFinishedTodos(),this);
        todoManager.SetAdapter(todoAdapter);
        binding.recyclerview.setAdapter(todoAdapter);
        binding.buttonAddTodo.setVisibility(View.VISIBLE);
        binding.CompletedTodos.setVisibility(View.GONE);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager((getContext())));
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoSwitch = !TodoSwitch;
                if(TodoSwitch){displayTodos();
                    binding.CompletedTodos.setVisibility(View.GONE);
                    todoAdapter.showTodos(TodoSwitch);
                    binding.buttonAddTodo.setVisibility(View.VISIBLE);
                }
                if(!TodoSwitch){FinishedTodos();
                    binding.CompletedTodos.setVisibility(View.VISIBLE);
                    binding.buttonAddTodo.setVisibility(View.GONE);
                    todoAdapter.showFinishedTodos(TodoSwitch);}

            }
        });
        binding.buttonAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TodoSwitch){
                    showAddTodoDialog();}
                else{binding.buttonAddTodo.setClickable(false);}
            }
        });


    }







    private void showAddTodoDialog(){
        /* creates a dialog in order to add another todo */
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_todo, null);
        EditText editTextTodo = dialogView.findViewById(R.id.editTextTodo);
        Button buttonSetTime = dialogView.findViewById(R.id.buttonSetTime);
        Button buttonAdd = dialogView.findViewById(R.id.buttonAdd);
        int[] StartHour = new int[1];
        int[] StartMinute = new int[1];
        boolean[] StartTimeSet = new boolean[1];
        String[] ampm = new String[1];
        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ampm[0] = (hourOfDay < 12) ? "AM" : "PM";

                        StartHour[0] = hourOfDay;
                        StartMinute[0] = minute;
                        StartTimeSet[0] = true;
                    }
                },12,0,false);// defaults the time to 12:00 using 24hour format
                timePickerDialog.show();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setTitle("Add To-Do");
        android.app.AlertDialog alertDialog = builder.create();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = editTextTodo.getText().toString().trim();
                if (todoText.isEmpty()) {
                    editTextTodo.setError("To-Do cannot be empty");
                    return;
                }
                if(!StartTimeSet[0]){
                    Toast.makeText(requireContext(), "Please Enter a Time", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar startCalender = Calendar.getInstance();
                startCalender.set(Calendar.HOUR_OF_DAY, 0);
                startCalender.set(Calendar.MINUTE, 0);
                Date startDate = startCalender.getTime();

                Calendar EndCalender = Calendar.getInstance();
                EndCalender.set(Calendar.HOUR_OF_DAY, StartHour[0]);
                EndCalender.set(Calendar.MINUTE, StartMinute[0]);
                Date EndDate = EndCalender.getTime();

                if (ampm[0].equalsIgnoreCase("PM")){
                    startCalender.add(Calendar.HOUR_OF_DAY, 24);
                }
                int HourOfDay = StartHour[0];
                HourOfDay = (HourOfDay % 12 == 0) ? 12 : HourOfDay % 12;
                String time = String.format("%02d:%02d:%s", HourOfDay, StartMinute[0],ampm[0]);
                todoManager.addTodo(todoText + " at " + time,startDate,EndDate,requireContext());
                updateTodoList();
                alertDialog.dismiss();}
        });
        alertDialog.show();
    }




    private void updateTodoList() {
        if(TodoSwitch){
            displayTodos();
        }
        if(!TodoSwitch){FinishedTodos();}

    }




    private void displayTodos(){
        todoAdapter.setTodos(todoManager.getTodos());
        todoAdapter.notifyDataSetChanged();
        binding.buttonAddTodo.setText("Add Todos");
        binding.buttonAddTodo.setClickable(true);
        binding.buttonFirst.setText("Finished Todos");


    }



    private void FinishedTodos() {
        todoManager.sortTodos();
        todoAdapter.setTodos(todoManager.getFinishedTodos());
        todoAdapter.notifyDataSetChanged();
        binding.buttonAddTodo.setText("Completed Todos");
        binding.buttonFirst.setText("Unfinished Todos");
    }
    @Override
    public void onDelete(int position) {
        if(TodoSwitch) {
            if (todoManager.getTodos().size() == 1) {
                ToDoItem toDo = todoManager.getTodos().get(position);
                todoManager.addFinishedTodo(toDo.getToDoListName(), toDo.getStartTime(), toDo.getEndTime(), requireContext());
                todoManager.markItemComplete(0);
                todoManager.removeTodo(0);
                todoAdapter.notifyDataSetChanged();
            } else {
                ToDoItem toDo = todoManager.getTodos().get(position);
                todoManager.addFinishedTodo(toDo.getToDoListName(), toDo.getStartTime(), toDo.getEndTime(), requireContext());
                todoManager.markItemComplete(position);
                todoManager.removeTodo(position);
                todoAdapter.notifyDataSetChanged();
            }
        }
        if(!TodoSwitch){
            if (todoManager.getFinishedTodos().size() == 1) {
                ToDoItem toDo = todoManager.getFinishedTodos().get(position);
                todoManager.DeleteTodo(0);
                todoAdapter.notifyDataSetChanged();
            } else {
                ToDoItem toDo = todoManager.getFinishedTodos().get(position);
                todoManager.DeleteTodo(position);
                todoAdapter.notifyDataSetChanged();
            }
        }

        updateTodoList();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

