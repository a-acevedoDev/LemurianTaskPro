package com.example.lemuriantaskpro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(intent);
        });

        loadTasks();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (adapter == null) {
            return;
        }

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

        if (adapter != null) {
            adapter.updateList(currentTasks);
        } else {
            adapter = new TaskAdapter(this, currentTasks, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onTaskClick(Task task) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("task_id", task.getId());
        startActivity(intent);
    }

    @Override
    public void onCheckBoxClick(Task task, int position) {

        taskManager.toggleCompleted(task.getId());

        recyclerView.post(() -> {
            int filterPosition = spinnerFilter.getSelectedItemPosition();
            List<Task> updatedList;
            switch (filterPosition) {
                case 0:
                    updatedList = taskManager.getAllTasks();
                    break;
                case 1:
                    updatedList = taskManager.getTasksByPriority(5);
                    break;
                case 2:
                    updatedList = taskManager.getTasksByPriority(3);
                    break;
                case 3:
                    updatedList = taskManager.getTasksByPriority(1);
                    break;
                default:
                    updatedList = taskManager.getAllTasks();
                    break;
            }
            currentTasks = updatedList;
            adapter.updateList(currentTasks);
        });
    }
}