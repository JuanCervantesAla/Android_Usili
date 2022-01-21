package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registro extends AppCompatActivity  {

    //Java objetos
    private FirebaseAuth mAuth;

    Button btnVolver_registro;
    Button btnLogin_registro;
    Button btnRegistrarse_registro;
    private EditText edtEmail_registro, edtEdad_registro,edtPassword_registro,edtPasswordConfirm_registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Relacion Java xml
        btnRegistrarse_registro = findViewById(R.id.btnRegistrarse_registro);
        btnLogin_registro = findViewById(R.id.btnLogin_registro);
        btnVolver_registro = findViewById(R.id.btnVolver_registro);
        edtEmail_registro = findViewById(R.id.edtEmail_registro);
        edtEdad_registro = findViewById(R.id.edtEdad_registro);
        edtPassword_registro = findViewById(R.id.edtPassword_registro);
        edtPasswordConfirm_registro = findViewById(R.id.edtPasswordConfirm_registro);

        //final EditText Edt_Email_registro = findViewById(R.id.edtEmail_registro);
        //final EditText Edt_Password_registro = findViewById(R.id.edtPassword_registro);
        //btnRegistrarse_registro = findViewById(R.id.btnRegistrarse_registro);
        //usuarioDataB usuarioDataB = new usuarioDataB();


        //Llamada al boton de registro a inicio de sesion
        btnLogin_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registro.this,iniciarSesion.class);
                startActivity(intent);
            }
        });

        //LLamada al boton Volver (Registro a Login)
        btnVolver_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registro.this,login.class);
                startActivity(intent);
            }
        });

        //Inicializar la variable autenticadora
        mAuth = FirebaseAuth.getInstance();

        /*
        //Inicio de Firebase
        //Boton registrarse para conexión a Base de datos
        btnRegistrarse_registro.setOnClickListener(v ->
        {
            usuario usu = new usuario(Edt_Email_registro.getText().toString(),Edt_Password_registro.getText().toString());
            usuarioDataB.add(usu).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(this, "Usuario exitosamente registrado", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(er ->
            {
                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

         */

        btnRegistrarse_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarseRegistro();
            }
        });
    }


    //Metodo para el boton registrarse, validacion
    private void registrarseRegistro() {
        String correo = edtEmail_registro.getText().toString().trim();//.trim elimina los espacios en blanco al inicio y final de la cadena
        String edad = edtEdad_registro.getText().toString().trim();
        String password = edtPassword_registro.getText().toString().trim();
        String passwordconfirm = edtPasswordConfirm_registro.getText().toString().trim();


        //Validaciones de cada editText o caja de texto
        if(correo.isEmpty()){
            edtEmail_registro.setError("¡Ingresa un correo por favor!");
            edtEmail_registro.requestFocus();
            return;
        }
        /*
        //Valida de la paqueteria patterns si el correo es un correo de verdad
        if(Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            edtEmail_registro.setError("¡Por favor ingresa un correo autentico!");
            edtEmail_registro.requestFocus();
            return;
        }*/

        if(edad.isEmpty()){
            edtEdad_registro.setError("¡Ingresa una edad por favor!");
            edtEdad_registro.requestFocus();
            return;
        }
        /*
        if(edadNumero<=0){
            edtEdad_registro.setError("!Ingresa una edad real¡");
            edtEdad_registro.requestFocus();
            return;
        }*/

        if (password.isEmpty()){
            edtPassword_registro.setError("¡Ingresa una contrasena por favor!");
            edtPassword_registro.requestFocus();
            return;
        }
        if(passwordconfirm.isEmpty()){
            edtPasswordConfirm_registro.setError("¡Vuelve a ingresar la misma contrasena!");
            edtPasswordConfirm_registro.requestFocus();
            return;
        }
        if(password.length()<6){
            edtPassword_registro.setError("¡La contraseña debe tener al menos 6 caracteres!");
            edtPassword_registro.requestFocus();
        }
        if(!passwordconfirm.equals(password)){
            edtPassword_registro.setError("¡Las contrasenas no coinciden!");
            edtPassword_registro.requestFocus();
            return;
        }

        //Crea al usuario en base a una contraseña y un correo
        mAuth.createUserWithEmailAndPassword(correo,password)
                /*En caso de que la accion se haya ejecutado agrega al constructor de la clase usuario
                los datos de correo y edad.*/
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            usuario usu = new usuario(correo,edad);
                            /*
                            Obtiene la base de datos directamente de la principal,
                            solo tengo una asi que no especifico la ruta de la DB
                            Y obtengo la referencia, que es mas bien para crear un apartado llamado Usuarios
                             */
                            FirebaseDatabase.getInstance().getReference("Usuarios")
                                    //Toma de la base de datos principal el id del usuario
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    //Cuando obtiene el valor lo asigna al objeto usu, se usa un complete listener para verificar el exito del mismo
                                    .setValue(usu).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                //Crea un metodo de task
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Si el objeto de tipo usuario se le asigno el valor correspondiente
                                    if(task.isSuccessful()){
                                        Toast.makeText(registro.this, "El usuario se registró", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(registro.this,iniciarSesion.class);
                                        startActivity(intent);
                                    }
                                    //Si no
                                    else{
                                        Toast.makeText(registro.this, "Fallo en registrar al usuario", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                    }
                });
    }

}