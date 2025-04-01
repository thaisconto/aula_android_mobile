package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditTaskActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextDescription;
    private BancoHelper dbHelper;
    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        dbHelper = new BancoHelper(this);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        if (getIntent().hasExtra("task_id")) {
            taskId = getIntent().getIntExtra("task_id", -1);
            loadTaskData(taskId);
        }
    }

    private void loadTaskData(int id) {
        Task task = dbHelper.getTask(id);
        if (task != null) {
            editTextTitle.setText(task.getTitle());
            editTextDescription.setText(task.getDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty()) {
            editTextTitle.setError("Título obrigatório");
            return;
        }

        Task task = new Task(title, description, 0);

        if (taskId == -1) {
            long id = dbHelper.addTask(task);
            if (id != -1) {
                Toast.makeText(this, "Tarefa salva", Toast.LENGTH_SHORT).show();
            }
        } else {
            task.setId(taskId);
            int rows = dbHelper.updateTask(task);
            if (rows > 0) {
                Toast.makeText(this, "Tarefa atualizada", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}