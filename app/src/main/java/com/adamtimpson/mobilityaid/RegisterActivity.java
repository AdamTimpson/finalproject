package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.database.entry.UserEntry;
import com.adamtimpson.mobilityaid.database.model.User;
import com.adamtimpson.mobilityaid.util.ActivityUtils;
import com.adamtimpson.mobilityaid.util.LogInUtils;

public class RegisterActivity extends AppCompatActivity {

    private ActivityUtils activityUtils = new ActivityUtils(this);

    private UserEntry userEntry = new UserEntry(this);

    private Button registerButton;
    private Button clearButton;

    private EditText firstNameET;
    private EditText lastNameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText passwordConfirmET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initTextFields();
        initButton();
    }

    private void initTextFields() {
        firstNameET = findViewById(R.id.registerFirstNameEditText);
        lastNameET = findViewById(R.id.registerLastNameEditText);
        emailET = findViewById(R.id.registerEmailEditText);
        passwordET = findViewById(R.id.registerPasswordEditText);
        passwordConfirmET = findViewById(R.id.registerConfirmPasswordEditText);
    }

    private void initButton() {
        registerButton = findViewById(R.id.registerConfirmButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addNewUser())
                    activityUtils.moveToMainMenu();
            }
        });

        clearButton = findViewById(R.id.registerClearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllFields();
            }
        });
    }

    private Boolean addNewUser() {
        Integer id = 0;
        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordConfirmET.getText().toString();

        if(firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("") || passwordConfirm.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please fill out all fields!", Toast.LENGTH_LONG).show();

            return false;
        } else {
            if(password.equals(passwordConfirm)) {
                if(userEntry.doesContain(email)) {
                    Toast.makeText(RegisterActivity.this, "That email is already registered!", Toast.LENGTH_LONG).show();

                    return false;
                } else {
                    User user = new User(id, firstName, lastName, email, password);

                    clearAllFields();

                    userEntry.addUser(user);

                    LogInUtils.getInstance().setCurrentUser(new User(userEntry.getIdByEmail(email), userEntry));

                    Toast.makeText(RegisterActivity.this, "You have been registered!", Toast.LENGTH_LONG).show();

                    return true;
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();

                return false;
            }
        }
    }

    private void clear(EditText e) {
        e.setText("");
    }

    private void clearAllFields() {
        clear(firstNameET);
        clear(lastNameET);
        clear(emailET);
        clear(passwordET);
        clear(passwordConfirmET);
    }

}
