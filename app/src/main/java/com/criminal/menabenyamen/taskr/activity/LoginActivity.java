package com.criminal.menabenyamen.taskr.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.criminal.menabenyamen.taskr.R;
import com.criminal.menabenyamen.taskr.swipe.SwipeActivity;


public class LoginActivity extends AppCompatActivity {

    private Button logButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logButton = (Button) findViewById(R.id.sign_in_button);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SwipeActivity.class);
                startActivity(intent);

            }
        });
    }
}
