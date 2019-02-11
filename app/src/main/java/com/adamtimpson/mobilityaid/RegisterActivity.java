package com.adamtimpson.mobilityaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton, clearButton;
    private EditText firstNameET, lastNameET, emailET, passwordET, passwordConfirmET;

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
                Toast.makeText(RegisterActivity.this,"TODO", Toast.LENGTH_LONG).show();
            }
        });

        clearButton = findViewById(R.id.registerClearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear(firstNameET);
                clear(lastNameET);
                clear(emailET);
                clear(passwordET);
                clear(passwordConfirmET);
            }
        });
    }

    private void clear(EditText e) {
        e.setText("");
    }
}
