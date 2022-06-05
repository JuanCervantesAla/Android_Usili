package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;
import android.window.SplashScreenView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
    String email,password2,passwordConfirmar2,edad2;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;


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


        btnRegistrar_Registro.setEnabled(false);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(registro.this);

        //LLamada al boton Volver (Registro a Login)
        btnVolver_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registro.this,iniciarSesion.class);
                startActivity(intent);
            }
        });



        //Inicializar la variable autenticadora
        mAuth = FirebaseAuth.getInstance();

        btnRegistrar_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarseRegistro();
            }
        });

        ocBTerminos_Registro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    materialAlertDialogBuilder.setTitle("Terminos y condiciones");
                    materialAlertDialogBuilder.setMessage("Terminos y condiciones USILI app.\n" +
                            "Este contrato estipula que al registrarse" +
                            "o hacer uso de la aplicación(Usili app.) y sitio web(UsiliWEB)" +
                            "tanto derivados desarrollados por los creadores," +
                            "aceptas los siguientes acuerdos enunciados." +
                            "Este contrato esta sujeto a las leyes digitales" +
                            "de la constitución de los Estados Unidos Mexicanos." +
                            "" +
                            "Enunciados:\n" +

                            "1°USILI y asociados, no se hacen responsables\n" +
                            "de de daños causados por el uso malintencionado\n" +
                            "de los articulos, imagenes, videos, material extra(Pdf)\n" +
                            "ni texto contenido en el mismo, ya que aquellos que no vengan registrados\n" +
                            "como parte de USILI no son creados por los mismos.\n" +
                            "\n" +
                            "2° El material compartido en los articulos sin\n" +
                            "marca de registro de USILI debe ser material didactico y escolar\n" +
                            "no se permite ningun otro material que incluya:\n" +
                            "*Pornografía de cualquier tipo\n" +
                            "*Material Sangriento\n" +
                            "*Campañas politicas o religiosas\n" +
                            "*No afines al tema escolar\n" +
                            "\n" +
                            "\n" +
                            "3°Al registrarse en la aplicación:\n" +
                            "-Al ser mayor de edad: No puedes demandar a USILI\n" +
                            "en ningun sentido, el uso de la aplicación y la información\n" +
                            "obtenida es por tu propia cuenta, no nos hacemos responsables\n" +
                            "de daños que estos puedan ocasionar de cualquier tipo.\n" +
                            "-Al ser menor de edad: Aceptas que tus padres estan\n" +
                            "de acuerdo al utilizar la aplicación y que al ser mayores de edad\n" +
                            "deben cumplir con la norma Al ser mayor de edad.\n" +
                            "\n" +
                            "4°Esta prohibida la distribución ilegal o modificación al\n" +
                            "software, inmediatamente se tomaran acciones en contra del\n" +
                            "perpetrador\n" +
                            "\n" +
                            "\n" +
                            "5°El Software es propiedad de:\n" +
                            "Cervantes Alatorre Juan José Emiliano (Nacionalidad Mexicana)\n" +
                            "Sánchez Valencia Leonardo Yael(Nacionalidad Mexicana)\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "6°Toda la información recabada del usuario es mantenida\n" +
                            "en total confidencialidad.\n" +
                            "\n" +
                            "@Proyecto hecho con el fin de Proyecto de Titulación\n" +
                            "de la escuela CENTRO DE ENSEÑANZA TECNICA INDUSTRIAL");
                    materialAlertDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            btnRegistrar_Registro.setEnabled(true);
                            dialogInterface.dismiss();
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            ocBTerminos_Registro.setChecked(false);
                        }
                    });
                    materialAlertDialogBuilder.show();
                }else{
                    btnRegistrar_Registro.setEnabled(false);
                }
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
            Toast.makeText(getApplicationContext(),"Ingresa un correo por favor",Toast.LENGTH_SHORT).show();
            return;
        }

        //Valida de la paqueteria patterns si el correo es un correo de verdad
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            edtEmail_Registro.setError("¡Por favor ingresa un correo autentico!");
            Toast.makeText(getApplicationContext(),"Por favor ingresa un correo autentico",Toast.LENGTH_SHORT).show();
            return;
        }

        if(edad.isEmpty()){
            Toast.makeText(getApplicationContext(),"Ingresa una edad por favor",Toast.LENGTH_SHORT).show();
            return;
        }

        if(edadNumero<=0 || edadNumero>90){
            Toast.makeText(getApplicationContext(),"Ingresa una edad real",Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Ingresa una contrasena por favor",Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordconfirm.isEmpty()){
            Toast.makeText(getApplicationContext(),"Vuelve a ingresar la misma contrasena",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6){
            Toast.makeText(getApplicationContext(),"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!passwordconfirm.equals(password)){
            Toast.makeText(getApplicationContext(),"Las contrasenas no coinciden",Toast.LENGTH_SHORT).show();
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