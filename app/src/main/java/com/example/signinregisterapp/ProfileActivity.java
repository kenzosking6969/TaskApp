package com.example.signinregisterapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvProfile = findViewById(R.id.tvProfile);
        String username = getIntent().getStringExtra("username");
        if (username == null) {
            username = "User";
        }
        tvProfile.setText("Profile of " + username);
    }
}
