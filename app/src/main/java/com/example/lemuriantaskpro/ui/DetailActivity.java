package com.example.lemuriantaskpro.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lemuriantaskpro.R;
import com.example.lemuriantaskpro.model.Task;
import com.example.lemuriantaskpro.utils.TaskManager;

public class DetailActivity extends AppCompatActivity {

    // Vistas
    private TextView tvTitle;
    private EditText etTitle, etDescription;
    private RadioGroup rgPriority;
    private RadioButton rbLow, rbMedium, rbHigh;
    private CheckBox cbCompleted;
    private Button btnSave, btnCancel;

    // Managers y datos
    private TaskManager taskManager;
    private Task editingTask;
    private int editingTaskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        taskManager = TaskManager.getInstance();

        // Inicializar vistas
        tvTitle = findViewById(R.id.tv_title);
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        rgPriority = findViewById(R.id.rg_priority);
        rbLow = findViewById(R.id.rb_low);
        rbMedium = findViewById(R.id.rb_medium);
        rbHigh = findViewById(R.id.rb_high);
        cbCompleted = findViewById(R.id.cb_completed);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        // Verificar si estamos editando una tarea existente
        if (getIntent().hasExtra("task_id")) {
            editingTaskId = getIntent().getIntExtra("task_id", -1);
            if (editingTaskId != -1) {
                loadTaskForEditing(editingTaskId);
            }
        }

        setupListeners();
    }

    private void loadTaskForEditing(int taskId) {
        // Buscar la tarea en TaskManager
        for (Task task : taskManager.getAllTasks()) {
            if (task.getId() == taskId) {
                editingTask = task;
                break;
            }
        }

        if (editingTask != null) {
            // Cambiar título de la pantalla
            tvTitle.setText(R.string.detail_title_edit);

            // Cargar datos
            etTitle.setText(editingTask.getName());
            etDescription.setText(editingTask.getDescription());

            // Prioridad
            switch (editingTask.getPriority()) {
                case 1:
                    rbLow.setChecked(true);
                    break;
                case 3:
                    rbMedium.setChecked(true);
                    break;
                case 5:
                    rbHigh.setChecked(true);
                    break;
                default:
                    rbMedium.setChecked(true);
                    break;
            }

            cbCompleted.setChecked(editingTask.getCompleted());
        }
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveTask());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveTask() {
        try {
            // Obtener datos de los campos
            String title = etTitle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            // Validar título
            if (title.isEmpty()) {
                Toast.makeText(this, R.string.detail_error_title, Toast.LENGTH_SHORT).show();
                etTitle.requestFocus();
                return;
            }

            if (rgPriority.getCheckedRadioButtonId() == -1) {
                rbMedium.setChecked(true); // Seleccionar Media por defecto
            }

            // Obtener prioridad del RadioGroup
            int priority = getPriorityFromRadioGroup();

            // Si es una tarea nueva (sin editar)
            if (editingTask == null) {
                // Crear nueva tarea
                Task newTask = new Task(title, description, priority);
                newTask.setCompleted(cbCompleted.isChecked()); // ← Importante
                taskManager.addTask(newTask);
                Toast.makeText(this, "Tarea agregada", Toast.LENGTH_SHORT).show();
            } else {
                // Editar tarea existente
                editingTask.setName(title);
                editingTask.setDescription(description);
                editingTask.setPriority(priority);
                editingTask.setCompleted(cbCompleted.isChecked()); // ← Aquí estaba el problema

                // Verificar que la tarea existe antes de actualizar
                boolean exists = false;
                for (Task t : taskManager.getAllTasks()) {
                    if (t.getId() == editingTask.getId()) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    taskManager.updateTask(editingTask);
                    Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error: Tarea no encontrada", Toast.LENGTH_SHORT).show();
                }
            }

            // Volver a MainActivity
            setResult(RESULT_OK);
            finish();

        } catch (Exception e) {
            // Capturar cualquier error y mostrarlo
            Toast.makeText(this, "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private int getPriorityFromRadioGroup() {
        try {
            int selectedId = rgPriority.getCheckedRadioButtonId();
            if (selectedId == R.id.rb_low) {
                return 1;
            } else if (selectedId == R.id.rb_high) {
                return 5;
            } else {
                return 3; // Media por defecto
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 3; // Default
        }
    }
}