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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class iniciarSesion extends AppCompatActivity {

    //Variables en Java
    Button btnVolver_iniciar;
    Button btnbutton2;
    Button btnContinuar_iniciar_sesion;
    TextView txtOlvidada_iniciar_sesion;
    private EditText edtEmail_iniciar_sesion, edtPassword_iniciar_sesion;

    //Variables en Java implementacion Firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //Relacion de las View xml y variables Java
        txtOlvidada_iniciar_sesion = findViewById(R.id.txtOlvidada_iniciar_sesion);
        btnVolver_iniciar = findViewById(R.id.btnVolver_iniciar_sesion);
        btnbutton2 = findViewById(R.id.button2);
        btnContinuar_iniciar_sesion = findViewById(R.id.btnContinuar_iniciar_sesion);
        edtEmail_iniciar_sesion = findViewById(R.id.edtEmail_iniciar_sesion);
        edtPassword_iniciar_sesion = findViewById(R.id.edtPassword_iniciar_sesion);

        //Inicializar la variable ruta de firebase
        mAuth = FirebaseAuth.getInstance();

        btnContinuar_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsuario();
            }
        });
        //Boton "Volver" al inicio
        btnVolver_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(iniciarSesion.this,login.class);
                startActivity(intent);
            }
        });

        txtOlvidada_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(iniciarSesion.this,contrasenaNueva.class);
                startActivity(intent);
            }
        });

        //Boton ir a registrar al usuario
        btnbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(iniciarSesion.this,registro.class);
                startActivity(intent);
            }
        });

    }


    private void loginUsuario() {
        String correo = edtEmail_iniciar_sesion.getText().toString().trim();
        String contrasena = edtPassword_iniciar_sesion.getText().toString().trim();

        if(correo.isEmpty()){
            edtEmail_iniciar_sesion.setError("¡Ingresa un correo por favor!");
            edtEmail_iniciar_sesion.requestFocus();
        }

        if(contrasena.isEmpty()){
            edtPassword_iniciar_sesion.setError("¡Ingresa una contrasena por favor!");
            edtPassword_iniciar_sesion.requestFocus();
        }

        if(contrasena.length()<6){
            edtPassword_iniciar_sesion.setError("¡La contrasena debe tener 6 caracteres minimo!");
            edtPassword_iniciar_sesion.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(correo,contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){
                                Intent intent = new Intent(iniciarSesion.this,front_activity.class);
                                startActivity(intent);
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(iniciarSesion.this, "Verifica tu cuenta en el correo ingresado", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(iniciarSesion.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}