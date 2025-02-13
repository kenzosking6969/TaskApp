package com.example.signinregisterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription, tvStatus;
    private Button btnMarkDone, btnEdit, btnDelete;
    private int taskId;
    private boolean isCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvStatus = findViewById(R.id.tvDetailStatus);
        btnMarkDone = findViewById(R.id.btnMarkDone);
        btnEdit = findViewById(R.id.btnEditTask);
        btnDelete = findViewById(R.id.btnDeleteTask);

        // Retrieve task details from Intent extras
        taskId = getIntent().getIntExtra("taskId", -1);
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        isCompleted = getIntent().getBooleanExtra("isCompleted", false);

        // Set task details to views
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvStatus.setText(isCompleted ? "Completed" : "Pending");

        // Set up button listeners
        btnMarkDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update task status to done in the database
                Task task = AppDatabase.getDatabase(TaskDetailActivity.this)
                        .taskDao().getTaskById(taskId);
                if (task != null && !task.isCompleted()) {
                    task.setCompleted(true);
                    AppDatabase.getDatabase(TaskDetailActivity.this).taskDao().updateTask(task);
                    Toast.makeText(TaskDetailActivity.this, "Task marked as done", Toast.LENGTH_SHORT).show();
                    tvStatus.setText("Completed");
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement edit functionality (e.g., open an edit screen or dialog)
                Toast.makeText(TaskDetailActivity.this, "Edit feature not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the task from the database
                Task task = AppDatabase.getDatabase(TaskDetailActivity.this)
                        .taskDao().getTaskById(taskId);
                if (task != null) {
                    AppDatabase.getDatabase(TaskDetailActivity.this).taskDao().deleteTask(task);
                    Toast.makeText(TaskDetailActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                    finish(); // Close detail activity after deletion
                }
            }
        });
    }
}
