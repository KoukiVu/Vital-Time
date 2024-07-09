package com.example.vitaltime;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.databinding.FragmentTodoBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;

public class ToDoFragment extends BaseFragment
        implements TodoAdapter.OnTodoItemListener {


private FragmentTodoBinding binding;
    private TodoManager todoManager;
    private TodoAdapter todoAdapter;
    private boolean TodoSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTodoBinding.inflate(inflater, container, false);
        bottomNavigationView = binding.bottomNavigationView;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView.setSelectedItemId(R.id.tasks);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        TodoSwitch = true;
        todoManager = new TodoManager();
        todoAdapter = new TodoAdapter(todoManager.getTodos(),todoManager.getFinishedTodos(),this);
        todoManager.SetAdapter(todoAdapter);
        binding.recyclerView.setAdapter(todoAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoSwitch = !TodoSwitch;
                if(TodoSwitch){displayTodos();}
                if(!TodoSwitch){FinishedTodos();}
                //NavHostFragment.findNavController(FirstFragment.this)
                //.navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.buttonAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TodoSwitch){
                    showAddTodoDialog();}
                else{FinishedTodos();}
            }
        });

    }


    private void addNewTODOS(){
        String todoText = "New To-Do";
        todoManager.addTodo(todoText,requireContext());
        updateTodoList();

    }



    private void showAddTodoDialog(){
        /* creates a dialog in order to add another todo */
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_todo, null);
        EditText editTextTodo = dialogView.findViewById(R.id.editTextTodo);
        Button buttonSetTime = dialogView.findViewById(R.id.buttonSetTime);
        Button buttonAdd = dialogView.findViewById(R.id.buttonAdd);
        int[] Hour = new int[1];
        int[] Minute = new int[1];
        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Hour[0] = hourOfDay;
                        Minute[0] = minute;
                    }
                },12,0,true);// defaults the time to 12:00 using 24hour format
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

                String time = String.format("%02d:%02d", Hour[0], Minute[0]);

                todoManager.addTodo(todoText + " at " + time,requireContext());
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

        binding.linearLayoutTodos.removeAllViews();
        binding.buttonAddTodo.setText("Add Todos");
        binding.buttonAddTodo.setClickable(true);
        for(ToDoItem items : todoAdapter.getTodos()){
            Button TodoButton = items.getTodoButton();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginInDp = 8;
            int marginInPx = (int)(marginInDp * requireContext().getResources().getDisplayMetrics().density);
            params.setMargins(marginInPx, marginInPx, marginInPx,marginInPx);
            TodoButton.setLayoutParams(params);
            TodoButton.setPadding(marginInPx, marginInPx, marginInPx, marginInPx);
            binding.linearLayoutTodos.addView(TodoButton);
        }
    }

    @Override
    public void onDelete(int position) {
        if (todoManager.getTodos().size()==1){

            todoManager.removeTodo(0);
            todoAdapter.notifyDataSetChanged();
        }
        else {
            todoManager.removeTodo(position);
            todoAdapter.notifyDataSetChanged();
        }

        updateTodoList();
    }

    private void FinishedTodos() {

        binding.linearLayoutTodos.removeAllViews();
        binding.buttonAddTodo.setText("Completed Todos");
        for(ToDoItem F_T : todoAdapter.getFinishedTodos()){
            Button FinishedT_odos = F_T.getTodoButton();
            FinishedT_odos.setClickable(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginInDp = 8;
            int marginInPx = (int)(marginInDp * requireContext().getResources().getDisplayMetrics().density);
            params.setMargins(marginInPx, marginInPx, marginInPx,marginInPx);
            FinishedT_odos.setLayoutParams(params);
            FinishedT_odos.setPadding(marginInPx, marginInPx, marginInPx, marginInPx);
            binding.linearLayoutTodos.addView(FinishedT_odos);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

