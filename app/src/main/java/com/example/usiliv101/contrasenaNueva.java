package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class contrasenaNueva extends AppCompatActivity {

    Button btncontrasenaNueva;
    ImageButton btnVolver_contrasenaNueva;
    EditText edtEmail_contrasenaNueva;

    //Variable para la autenticacion de firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_nueva);

        btnVolver_contrasenaNueva = findViewById(R.id.btnVolver_contrasenaNueva);
        btncontrasenaNueva = findViewById(R.id.btncontrasenNueva);
        edtEmail_contrasenaNueva = findViewById(R.id.edtEmail_contrasenaNueva);

        //Obtengo la ruta de la base de datos
        auth = FirebaseAuth.getInstance();

        btncontrasenaNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LLamada a metodo
                resetearContrasena();
            }
        });


        btnVolver_contrasenaNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contrasenaNueva.this,perfil.class);
                startActivity(intent);
            }
        });


    }

    //Metodo del reseteo de contrasena
    private void resetearContrasena() {

        //Obtengo el valor del EdiText y lo asigno a la variable correo
        String correo = edtEmail_contrasenaNueva.getText().toString().trim();

        if(correo.isEmpty()){
            edtEmail_contrasenaNueva.setError("Ey correo equivocado");
            edtEmail_contrasenaNueva.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(contrasenaNueva.this, "Revisa el mensaje que llegó a tu correo", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(contrasenaNueva.this,iniciarSesion.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(contrasenaNueva.this, "Se produjo un error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}