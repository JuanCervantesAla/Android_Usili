package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.UUID;

public class Agregar extends AppCompatActivity {

    //Atributos en Java
    Button btnVolver_Agregar,btnContinuar_Agregar;
    EditText edtETitulo_Agregar,edtEMateriales_Agregar,edtEPasos_Agregar;
    TextView txtEscondido_Agregar;

    private DatabaseReference reference;//Ruta para la tabla Usuarios
    private String idUsuario;
    private FirebaseUser usuario;//Autenticación del usuario, obtengo al usuario en sesion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        //Relacion JAVA - XML
        btnVolver_Agregar = findViewById(R.id.btnVolver_Agregar);
        btnContinuar_Agregar = findViewById(R.id.btnContinuar_Agregar);
        edtETitulo_Agregar = findViewById(R.id.edtETitulo_Agregar);
        edtEMateriales_Agregar  =findViewById(R.id.edtEMateriales_Agregar);
        edtEPasos_Agregar = findViewById(R.id.edtEPasos_Agregar);
        txtEscondido_Agregar = findViewById(R.id.txtEscondido_Agregar);


        //Boton para volver a la activity front
        btnVolver_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Agregar.this,front_activity.class);
                startActivity(intent);
            }
        });

        //Boton para continuar con la carga del articulo

        btnContinuar_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subirDatos();

            }
        });

    }


    private void subirDatos() {
        String titulo =  edtETitulo_Agregar.getText().toString().trim();
        String materiales = edtEMateriales_Agregar.getText().toString().trim();
        String pasos = edtEPasos_Agregar.getText().toString().trim();
        String autor = "Hola";
        final String randomkey  = UUID.randomUUID().toString();//Creo una llave random para almacenar las imagenes en una carpeta
        final int min = 1000;
        final int max = 2000;
        final int id = new Random().nextInt((max - min) + 1) + min;


        if(titulo.isEmpty()){
            edtETitulo_Agregar.setError("¡Ingresa un titulo por favor!");
            edtETitulo_Agregar.requestFocus();
            return;
        }
        if(titulo.length()<=10){
            edtETitulo_Agregar.setError("¡Ingresa un titulo mas largo!");
            edtETitulo_Agregar.requestFocus();
            return;
        }

        if(materiales.isEmpty()){
            edtEMateriales_Agregar.setError("¡Ingresa los materiales!");
            edtEMateriales_Agregar.requestFocus();
            return;
        }

        if(pasos.isEmpty()){
            edtEPasos_Agregar.setError("¡Ingresa los pasos a seguir!");
            edtEPasos_Agregar.requestFocus();
            return;
        }

        Articulos articulo =  new Articulos(titulo,pasos,materiales,autor,id);
        FirebaseDatabase.getInstance().getReference("Articulos")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomkey).setValue(articulo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Agregar.this, "Se agregó tu articulo :)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Agregar.this,front_activity.class);
                    startActivity(intent);
                }
                //Si no
                else{
                    Toast.makeText(Agregar.this, "Fallo en agregar el articulo", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

