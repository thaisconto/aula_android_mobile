package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private BancoHelper dbHelper;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new BancoHelper(this);
        listView = findViewById(R.id.listViewTasks);
        Button btnAddTask = findViewById(R.id.btnAddTask);

        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivity(intent);
        });

        setupListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTaskList();
    }

    private void setupListView() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            intent.putExtra("task_id", (int) id);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            dbHelper.deleteTask((int) id);
            updateTaskList();
            Toast.makeText(this, "Tarefa removida", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void updateTaskList() {
        Cursor cursor = dbHelper.getAllTasks();

        String[] fromColumns = {
                BancoHelper.COLUMN_TITLE,
                BancoHelper.COLUMN_DESCRIPTION
        };

        int[] toViews = {
                R.id.textViewTaskTitle,
                R.id.textViewTaskDescription
        };

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.task_list_item,
                cursor,
                fromColumns,
                toViews,
                0);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}