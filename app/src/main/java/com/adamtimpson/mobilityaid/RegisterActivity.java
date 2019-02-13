package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adamtimpson.mobilityaid.database.entry.UserEntry;
import com.adamtimpson.mobilityaid.database.model.User;
import com.adamtimpson.mobilityaid.helper.DatabaseHelper;
import com.adamtimpson.mobilityaid.util.ActivityUtils;

public class RegisterActivity extends AppCompatActivity {

    private ActivityUtils activityUtils = new ActivityUtils(this);

    private DatabaseHelper dbh = new DatabaseHelper(this);

    private UserEntry userEntry;

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

        userEntry = new UserEntry(this);

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
                addNewUser();
                activityUtils.moveToMain();
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

    private void addNewUser() {
        Integer id = 0;
        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordConfirmET.getText().toString();

        if(firstName == "" || lastName == "" || email == "" || password == "" || passwordConfirm == "") {
            Toast.makeText(RegisterActivity.this, "Please fill out all fields!", Toast.LENGTH_LONG).show();
        } else {
            if(password.equals(passwordConfirm)) {
                User user = new User(id, firstName, lastName, email, password);

                clearAllFields();

                userEntry.addUser(user);

                Toast.makeText(RegisterActivity.this, "You have been registered!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
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
