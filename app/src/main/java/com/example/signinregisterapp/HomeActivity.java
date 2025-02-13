package com.example.signinregisterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private RecyclerView rvTasks;
    private LinearLayout emptyStateView;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabAddTask;
    private TaskAdapter taskAdapter;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve username from Intent
        String usernameFromIntent = getIntent().getStringExtra("username");
        final String username = (usernameFromIntent == null) ? "User" : usernameFromIntent;

        // Link UI elements
        tvWelcome = findViewById(R.id.tvWelcome);
        rvTasks = findViewById(R.id.rvTasks);
        emptyStateView = findViewById(R.id.emptyStateView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fabAddTask = findViewById(R.id.fabAddTask);

        // Set welcome message
        tvWelcome.setText("Welcome, " + username + "!");

        // Set up RecyclerView
        taskAdapter = new TaskAdapter(this);  // Pass context here
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(taskAdapter);

        // Initialize ViewModel and observe tasks
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.setUserId(username); // Use appropriate user identifier.
        taskViewModel.getTasks().observe(this, tasks -> {
            if (tasks == null || tasks.isEmpty()) {
                rvTasks.setVisibility(RecyclerView.GONE);
                emptyStateView.setVisibility(LinearLayout.VISIBLE);
            } else {
                rvTasks.setVisibility(RecyclerView.VISIBLE);
                emptyStateView.setVisibility(LinearLayout.GONE);
                taskAdapter.setTaskList(tasks);
            }
        });

        // Floating Action Button listener to launch AddTaskActivity, passing username
        fabAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Bottom Navigation listener using new API
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_profile) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.navigation_settings) {
                    Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
