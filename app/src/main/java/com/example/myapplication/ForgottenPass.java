package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ForgottenPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_pass);

        EditText forgottenPass;

        forgottenPass = findViewById(R.id.edtEmailAdd);
    }
    @Override
    public void onBackPressed() {
        // Cuando se presiona el botón "Atrás", inicia MainActivity
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Elimina las actividades de la parte superior
        startActivity(intent);
        finish(); // Cierra la actividad actual
        overridePendingTransition(R.anim.anim_slide_right_in, R.anim.anim_slide_right_out); // Aplica la animación
    }
}