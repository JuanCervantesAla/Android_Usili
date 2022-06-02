package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
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
    //private EditText edtEmail_enIniciar,edtPassword_enIniciar;
    Button btnIniciarSesion_enIniciar;
    TextView txtContrasenaOlvidada_enIniciar,txtRegistrarse_enIniciar,txtInvitado;

    private EditText edtEmail_iniciar_sesion, edtPassword_iniciar_sesion;

    //Variables en Java implementacion Firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //Fondo animado


        //Relacion de las View xml y variables Java
        txtContrasenaOlvidada_enIniciar = findViewById(R.id.txtContrasenaOlvidada_enIniciar);
        txtRegistrarse_enIniciar = findViewById(R.id.txtRegistrarse_enIniciar);
        btnIniciarSesion_enIniciar = findViewById(R.id.btnIniciarSesion_enIniciar);
        edtEmail_iniciar_sesion = findViewById(R.id.edtEmail_enIniciar);
        edtPassword_iniciar_sesion = findViewById(R.id.edtPassword_enIniciar);
        txtInvitado = findViewById(R.id.txtInvitado);


        //Inicializar la variable ruta de firebase
        mAuth = FirebaseAuth.getInstance();

        /*
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();*/

        txtInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(iniciarSesion.this,modoInvitado.class);
                startActivity(intent);
            }
        });

        btnIniciarSesion_enIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsuario();
            }
        });



        txtContrasenaOlvidada_enIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(iniciarSesion.this,contrasenaNueva.class);
                startActivity(intent);
            }
        });

        //Boton ir a registrar al usuario
        txtRegistrarse_enIniciar.setOnClickListener(new View.OnClickListener() {
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
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            edtEmail_iniciar_sesion.setError("¡Por favor ingresa un correo autentico!");
            edtEmail_iniciar_sesion.requestFocus();
            return;
        }

        if(contrasena.isEmpty()){
            edtPassword_iniciar_sesion.setError("¡Ingresa una contrasena por favor!");
            edtPassword_iniciar_sesion.requestFocus();
            return;
        }

        if(contrasena.length()<6){
            edtPassword_iniciar_sesion.setError("¡La contrasena debe tener 6 caracteres minimo!");
            edtPassword_iniciar_sesion.requestFocus();
            return;
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
    @Override
    public void onBackPressed(){

    }
}