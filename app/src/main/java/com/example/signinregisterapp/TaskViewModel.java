package com.example.signinregisterapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public void setUserId(String userId) {
        tasks = repository.getTasksForUser(userId);
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }
}
