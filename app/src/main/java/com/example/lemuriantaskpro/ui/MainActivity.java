package com.example.lemuriantaskpro.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemuriantaskpro.R;
import com.example.lemuriantaskpro.adapter.TaskAdapter;
import com.example.lemuriantaskpro.model.Task;
import com.example.lemuriantaskpro.utils.TaskManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private RecyclerView recyclerView;
    private Spinner spinnerFilter;
    private TaskAdapter adapter;
    private TaskManager taskManager;
    private List<Task> currentTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskManager = TaskManager.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        spinnerFilter = findViewById(R.id.spinner_filter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupSpinner();
        loadTasks();
    }

    private void setupSpinner() {
        String[] filters = {"Todas", "Alta (5)", "Media (3-4)", "Baja (1-2)"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, filters
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                filterTasks(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private void filterTasks(int filterPosition) {
        List<Task> allTasks = taskManager.getAllTasks();
        switch (filterPosition) {
            case 0:
                currentTasks = allTasks;
                break;
            case 1:
                currentTasks = taskManager.getTasksByPriority(5);
                break;
            case 2:
                currentTasks = taskManager.getTasksByPriority(3);
                break;
            case 3:
                currentTasks = taskManager.getTasksByPriority(1);
                break;
        }
        adapter.updateList(currentTasks);
    }

    private void loadTasks() {
        currentTasks = taskManager.getAllTasks();
        adapter = new TaskAdapter(this, currentTasks, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTaskClick(Task task) {
        Toast.makeText(this, "Tarea: " + task.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckBoxClick(Task task, int position) {
        taskManager.toggleCompleted(task.getId());
        adapter.notifyItemChanged(position);
    }
}