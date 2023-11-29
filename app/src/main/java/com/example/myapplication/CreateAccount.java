package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        String url = "http://192.168.100.28:80/greengo/add_user.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        EditText edtName, edtLastName, edtEmail, edtPass, edtConfPass;

        Button btnCreateAcc;

        edtName = findViewById(R.id.edtName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmailAdd);
        edtPass = findViewById(R.id.edtPass);
        edtConfPass = findViewById(R.id.edtConfPass);
        btnCreateAcc = findViewById(R.id.btnCreate);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesa la respuesta aquí
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja errores de la solicitud
                        Log.e("Error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                // Define los parámetros que deseas enviar en la solicitud POST
                Map<String, String> params = new HashMap<>();
                params.put("name", edtName.getText().toString());
                params.put("last_name", edtLastName.getText().toString());
                params.put("mail", edtEmail.getText().toString());
                params.put("passw", edtPass.getText().toString());
                params.put("user_points", "0");
                return params;
            }
        };

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPass.getText().toString().equals("") && edtConfPass.getText().toString().equals("")){
                    Toast.makeText(CreateAccount.this, "The fields are empty", Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().toString().equals("") || edtConfPass.getText().toString().equals("")) {
                    Toast.makeText(CreateAccount.this, "The password doesn't match", Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().toString().equals(edtConfPass.getText().toString())) {

                    queue.add(postRequest);
                    Toast.makeText(CreateAccount.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreateAccount.this, "The password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Cuando se presiona el botón "Atrás", inicia MainActivity
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Elimina las actividades de la parte superior
        startActivity(intent);
        finish(); // Cierra la actividad actual
        overridePendingTransition(R.anim.anim_slide_left_in, R.anim.anim_slide_left_out); // Aplica la animación
    }
}