package com.example.signinregisterapp;

import androidx.appcompat.app.AppCompatActivity;  // Make sure this is present
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTitle, etDescription;
    private Button btnSave;
    private String userId; // To store passed username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        // Retrieve user identifier from Intent
        userId = getIntent().getStringExtra("username");
        if (userId == null) {
            userId = "defaultUser"; // Handle as needed
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new task using the passed userId
                Task newTask = new Task(userId, title, description, System.currentTimeMillis(), 1, false);

                // Insert the task into the database (for demo, using allowMainThreadQueries)
                AppDatabase.getDatabase(AddTaskActivity.this).taskDao().insertTask(newTask);
                Toast.makeText(AddTaskActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
