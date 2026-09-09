package com.example.lemuriantaskpro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lemuriantaskpro.R;
import com.example.lemuriantaskpro.model.User;
import com.example.lemuriantaskpro.utils.UserManager;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvUsername;
    private Button btnStart;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        userManager = UserManager.getInstance();

        tvUsername = findViewById(R.id.tv_username);
        btnStart = findViewById(R.id.btn_start);

        User currentUser = userManager.getCurrentUser();
        if (currentUser != null) {
            tvUsername.setText(currentUser.getUsername());
        }

        btnStart.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        });
    }
}