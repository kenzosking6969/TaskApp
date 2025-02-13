package com.example.signinregisterapp;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
    }

    public LiveData<List<Task>> getTasksForUser(String userId) {
        return taskDao.getTasksForUser(userId);
    }

    // Add insert, update, and delete methods if needed.
}
