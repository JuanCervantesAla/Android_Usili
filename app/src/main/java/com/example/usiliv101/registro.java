package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registro extends AppCompatActivity  {

    //Java objetos
    private FirebaseAuth mAuth;

    EditText edtEmail_Registro,edtPassword_Registro,edtPasswordConfirmar_Registro,edtEdad_Registro;
    Button btnRegistrar_Registro;
    ImageView btnVolver_Registro;
    CheckBox ocBTerminos_Registro;
    TextView txtTerminos_Registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Relacion Java xml
        btnRegistrar_Registro = findViewById(R.id.btnRegistrar_Registro);
        btnVolver_Registro = findViewById(R.id.btnVolver_Registro);
        edtEmail_Registro = findViewById(R.id.edtEmail_Registro);
        edtEdad_Registro = findViewById(R.id.edtEdad_Registro);
        edtPassword_Registro = findViewById(R.id.edtPassword_Registro);
        edtPasswordConfirmar_Registro = findViewById(R.id.edtPasswordConfirmar_Registro);
        ocBTerminos_Registro = findViewById(R.id.ocBTerminos_Registro);
        txtTerminos_Registro = findViewById(R.id.txtTerminos_Registro);

        //final EditText Edt_Email_registro = findViewById(R.id.edtEmail_registro);
        //final EditText Edt_Password_registro = findViewById(R.id.edtPassword_registro);
        //btnRegistrarse_registro = findViewById(R.id.btnRegistrarse_registro);
        //usuarioDataB usuarioDataB = new usuarioDataB();




        //LLamada al boton Volver (Registro a Login)
        btnVolver_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registro.this,iniciarSesion.class);
                startActivity(intent);
            }
        });

        txtTerminos_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registro.this,terminosCondiciones.class);
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

        btnRegistrar_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarseRegistro();
            }
        });
    }



    //Metodo para el boton registrarse, validacion
    private void registrarseRegistro() {
        String correo = edtEmail_Registro.getText().toString().trim();//.trim elimina los espacios en blanco al inicio y final de la cadena
        String edad = edtEdad_Registro.getText().toString().trim();
        String password = edtPassword_Registro.getText().toString().trim();
        String passwordconfirm = edtPasswordConfirmar_Registro.getText().toString().trim();
        int edadNumero = Integer.valueOf(edad);

        //Validaciones de cada editText o caja de texto
        if(correo.isEmpty()){
            edtEmail_Registro.setError("¡Ingresa un correo por favor!");
            edtEmail_Registro.requestFocus();
            return;
        }

        //Valida de la paqueteria patterns si el correo es un correo de verdad
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            edtEmail_Registro.setError("¡Por favor ingresa un correo autentico!");
            edtEmail_Registro.requestFocus();
            return;
        }

        if(edad.isEmpty()){
            edtEdad_Registro.setError("¡Ingresa una edad por favor!");
            edtEdad_Registro.requestFocus();
            return;
        }

        if(edadNumero<=0 || edadNumero>90){
            edtEdad_Registro.setError("!Ingresa una edad real¡");
            edtEdad_Registro.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPassword_Registro.setError("¡Ingresa una contrasena por favor!");
            edtPassword_Registro.requestFocus();
            return;
        }
        if(passwordconfirm.isEmpty()){
            edtPasswordConfirmar_Registro.setError("¡Vuelve a ingresar la misma contrasena!");
            edtPasswordConfirmar_Registro.requestFocus();
            return;
        }
        if(password.length()<6){
            edtPassword_Registro.setError("¡La contraseña debe tener al menos 6 caracteres!");
            edtPassword_Registro.requestFocus();
            return;
        }
        if(!passwordconfirm.equals(password)){
            edtPassword_Registro.setError("¡Las contrasenas no coinciden!");
            edtPassword_Registro.requestFocus();
            return;
        }
        if(!ocBTerminos_Registro.isChecked()){
            Toast.makeText(registro.this, "Acepta los terminos y condiciones", Toast.LENGTH_SHORT).show();
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