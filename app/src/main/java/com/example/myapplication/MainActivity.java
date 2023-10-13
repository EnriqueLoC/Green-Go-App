package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn;
    TextView btnCreateAcc, btnForgotPass;
    EditText edtUser, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btn_signin);
        btnCreateAcc = findViewById(R.id.btn_newacc);
        btnForgotPass = findViewById(R.id.btn_forgot);
        edtUser = findViewById(R.id.edtxt_email);
        edtPass = findViewById(R.id.edtxt_pass);

        String url = "http://192.168.100.28:80/greengo/add_user.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if (edtUser.getText().toString().equals("email@email.com") && edtPass.getText().toString().equals("contra")){
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Your Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ForgottenPass.class);
                startActivity(intent);
            }
        });
    }
}