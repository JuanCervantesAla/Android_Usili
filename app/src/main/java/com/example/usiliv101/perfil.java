package com.example.usiliv101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class perfil extends AppCompatActivity {

    ImageButton imgbtnVolver_perfil;
    Button btnCerrarsesion_perfil, btnCambiarontrasena_perfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        imgbtnVolver_perfil = findViewById(R.id.imgbtnVolver_perfil);
        btnCerrarsesion_perfil = findViewById(R.id.btnCerrarsesion_perfil);
        btnCambiarontrasena_perfil = findViewById(R.id.btnCambiarcontrasena_perfil);

        imgbtnVolver_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this,front_activity.class);
                startActivity(intent);
            }
        });


        btnCerrarsesion_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(perfil.this,login.class);
                startActivity(intent);
            }
        });

        btnCambiarontrasena_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this,contrasenaNueva.class);
                startActivity(intent);
            }
        });
    }
}