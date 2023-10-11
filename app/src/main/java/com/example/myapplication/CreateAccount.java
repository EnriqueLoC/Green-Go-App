package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        EditText edtName, edtLastName, edtEmail, edtPass, edtConfPass;

        Button btnCreateAcc;

        edtName = findViewById(R.id.edtName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmailAdd);
        edtPass = findViewById(R.id.edtPass);
        edtConfPass = findViewById(R.id.edtConfPass);
        btnCreateAcc = findViewById(R.id.btnCreate);

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPass.getText().toString().equals("") && edtConfPass.getText().toString().equals("")){
                    Toast.makeText(CreateAccount.this, "The fields are empty", Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().toString().equals("") || edtConfPass.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "The password doesn't match", Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().toString().equals(edtConfPass.getText().toString())) {
                    Toast.makeText(CreateAccount.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccount.this, "The password deasn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}