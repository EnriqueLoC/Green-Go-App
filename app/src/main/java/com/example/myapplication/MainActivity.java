package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén los valores de email y contraseña desde los campos de entrada
                String email = edtUser.getText().toString();
                String password = edtPass.getText().toString();
                Log.d("Credentials", "Email: " + email + " Password: " + password);
                // Define la URL de tu API con los valores de email y contraseña
                String url = "http://192.168.100.28:80/greengo/get_user.php?email=" + email + "&password=" + password;

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    int id = -1;
                                    String nombre = "null";
                                    String apellido= "null";
                                    int puntos = -1;

                                    boolean found = false;
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        id = jsonObject.getInt("id_usuario");
                                        nombre = jsonObject.getString("nombre");
                                        apellido = jsonObject.getString("apellido");
                                        String email = jsonObject.getString("email");
                                        String password = jsonObject.getString("password");
                                        puntos = jsonObject.getInt("puntos");
                                        // Verifica si las credenciales coinciden
                                        if (email.equals(edtUser.getText().toString()) && password.equals(edtPass.getText().toString())) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (found) {
                                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                        intent.putExtra("id",id);
                                        intent.putExtra("nombre", nombre);
                                        intent.putExtra("apellido", apellido);
                                        intent.putExtra("puntos", puntos);
                                        startActivity(intent);
                                        finish();
                                    } else if(edtUser.getText().toString().equals("") && edtPass.getText().toString().equals("")){
                                        Toast.makeText(MainActivity.this, "The fields are empty", Toast.LENGTH_SHORT).show();
                                    } else if (edtUser.getText().toString().equals("") || edtPass.getText().toString().equals("")) {
                                        Toast.makeText(MainActivity.this, "Fill both fields", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Login failed. Invalid email or password.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Maneja errores de la solicitud
                                Log.e("Error", error.toString());
                            }
                        }
                );

                queue.add(getRequest);
            }
        });

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
                finish();
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgottenPass.class);
                startActivity(intent);
                finish();
            }
        });
    }
}