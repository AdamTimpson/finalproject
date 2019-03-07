package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.database.entry.UserEntry;
import com.adamtimpson.mobilityaid.database.model.User;
import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.LogInUtils;

public class MainActivity extends AppCompatActivity {

    private final String LOGIN_SUCCESS_MESSAGE = "Login successful";
    private final String LOGIN_FAIL_MESSAGE = "Login failed";

    private ActivityUtils activityUtils = new ActivityUtils(this);

    private UserEntry userEntry = new UserEntry(this);

    Button loginButton;
    Button registerButton;
    Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initButtonListeners();
    }

    public void initButtonListeners() {
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        clearButton = findViewById(R.id.loginClearButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getText((EditText) findViewById(R.id.emailInput));
                String password = getText((EditText) findViewById(R.id.passwordInput));

                Boolean validEmail = false;
                Boolean correctPassword = false;

                if(userEntry.doesContain(email)) {
                    validEmail = true;

                    if(password.equals(userEntry.getPasswordByEmail(email))) {
                        correctPassword = true;
                    }
                }

                if(validEmail && correctPassword) {
                    Log.d("[DEBUG]", "Logged in: " + email + " with password: " + password);

                    LogInUtils.getInstance().setCurrentUser(new User(userEntry.getIdByEmail(email), userEntry));

                    Toast.makeText(MainActivity.this, LOGIN_SUCCESS_MESSAGE, Toast.LENGTH_LONG).show();
                    activityUtils.moveToMainMenu();
                } else {
                    Toast.makeText(MainActivity.this, LOGIN_FAIL_MESSAGE, Toast.LENGTH_LONG).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.moveToRegister();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText) findViewById(R.id.emailInput)).setText("");
                ((EditText) findViewById(R.id.passwordInput)).setText("");
            }
        });
    }

    private String getText(EditText e) {
        return e.getText().toString();
    }

}
