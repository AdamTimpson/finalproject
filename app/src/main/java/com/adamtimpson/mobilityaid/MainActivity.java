package com.adamtimpson.mobilityaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String LOGIN_SUCCESS_MESSAGE = "Login successful";
    private final String LOGIN_FAIL_MESSAGE = "Login failed";

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initLoginButtonListener();
    }

    public void initLoginButtonListener() {
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getText((EditText) findViewById(R.id.emailInput));
                String password = getText((EditText) findViewById(R.id.passwordInput));

                Boolean validEmail = false;
                Boolean correctPassword = false;

                if(email.toLowerCase().equals("a@test.com")) {
                    validEmail = true;

                    if(password.equals("1234")) {
                        correctPassword = true;
                    }
                }

                if(validEmail && correctPassword) {
                    Toast.makeText(MainActivity.this, LOGIN_SUCCESS_MESSAGE, Toast.LENGTH_LONG).show();
                    launchMainMenuActivity();
                } else {
                    Toast.makeText(MainActivity.this, LOGIN_FAIL_MESSAGE, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void launchMainMenuActivity() {
        Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenuIntent);
    }

    private String getText(EditText e) {
        return e.getText().toString();
    }

}
