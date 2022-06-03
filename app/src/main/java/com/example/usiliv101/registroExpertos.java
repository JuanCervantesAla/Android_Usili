package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.UUID;

public class registroExpertos extends AppCompatActivity {

    EditText edtNombre_Expertos,edtEmail_Expertos,edtTelefono_Expertos,edtInfor_Expertos,edtUbicacion_Expertos;
    ImageView btnVolver_Expertos;
    Button btnradio_Expertos,btnRegistrar_Expertos,btnEleccion_Expertos;
    RadioButton rTelefono,rEmail,rReparador,rElectricista,rMecanico,rFontanero,rDos,rTodos;
    CheckBox ocBTerminos_Expertos;
    final String randomkey  = UUID.randomUUID().toString();
    ImageView imgV_Expertos;
    Uri uri;
    String eleccion="No profesion";
    private FirebaseAuth mAuth;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference reference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_expertos);

        //Vinculo xml con Java
        edtNombre_Expertos = findViewById(R.id.edtNombre_Expertos);
        edtEmail_Expertos = findViewById(R.id.edtEmail_Expertos);
        edtTelefono_Expertos = findViewById(R.id.edtTelefono_Expertos);
        btnVolver_Expertos = findViewById(R.id.btnVolver_Expertos);
        ocBTerminos_Expertos = findViewById(R.id.ocBTerminos_Expertos);
        btnRegistrar_Expertos = findViewById(R.id.btnRegistrar_Expertos);
        btnEleccion_Expertos = findViewById(R.id.btnEleccion_Expertos);
        edtUbicacion_Expertos = findViewById(R.id.edtUbicacion_Expertos);
        edtInfor_Expertos = findViewById(R.id.edtInfor_Expertos);
        imgV_Expertos = findViewById(R.id.imgV_Expertos);
        rReparador = findViewById(R.id.rReparador);
        rElectricista= findViewById(R.id.rElectricista);
        rMecanico=findViewById(R.id.rMecanico);
        rFontanero=findViewById(R.id.rFontanero);
        rDos=findViewById(R.id.rDos);
        rTodos = findViewById(R.id.rTodos);

        storage =  FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        reference2= FirebaseDatabase.getInstance().getReference("Apoyo");


        btnRegistrar_Expertos.setEnabled(false);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(registroExpertos.this);
        ocBTerminos_Expertos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                            btnRegistrar_Expertos.setEnabled(true);
                            dialogInterface.dismiss();
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            ocBTerminos_Expertos.setChecked(false);
                        }
                    });
                    materialAlertDialogBuilder.show();
                }else{
                    btnRegistrar_Expertos.setEnabled(false);
                }
            }
        });

        //Intents de activities
        btnVolver_Expertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(registroExpertos.this,iniciarSesion.class);
                startActivity(intent);
            }
        });

        //Inicializar la variable autenticadora
        mAuth = FirebaseAuth.getInstance();

        //Boton para registrar a los expertos
        btnRegistrar_Expertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirFoto(uri);
            }
        });
        imgV_Expertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escogerFoto();
            }
        });

    }

    private void registrarseRegistro(String x) {
        String nombre = edtNombre_Expertos.getText().toString().trim();
        String correo = edtEmail_Expertos.getText().toString().trim();//.trim elimina los espacios en blanco al inicio y final de la cadena
        String telefono = edtTelefono_Expertos.getText().toString().trim();
        String descripcion = edtInfor_Expertos.getText().toString().trim();
        String zona = edtUbicacion_Expertos.getText().toString().trim();
        String foto = x;
        String diferencia ="Esto es un correo";

        //Validaciones de cada editText o caja de texto
        if(telefono.isEmpty()){
            edtTelefono_Expertos.setError("¡Ingresa un correo o un telefono por favor!");
            edtEmail_Expertos.requestFocus();
            return;
        }
        if(correo.isEmpty()){
            edtEmail_Expertos.setError("¡Ingresa un correo o un telefono por favor!");
            edtEmail_Expertos.requestFocus();
            return;
        }

        //Valida de la paqueteria patterns si el correo es un correo de verdad
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            edtEmail_Expertos.setError("¡Por favor ingresa un correo autentico!");
            edtEmail_Expertos.requestFocus();
            return;
        }

        if(!ocBTerminos_Expertos.isChecked()){
            Toast.makeText(registroExpertos.this, "Acepta los terminos y condiciones", Toast.LENGTH_SHORT).show();
        }

        if(rReparador.isChecked()){
            eleccion="Reparador";
        }
        if(rElectricista.isChecked()){
            eleccion="Electricista";
        }

        if(rMecanico.isChecked()){
            eleccion="Mecanico";
        }

        if(rFontanero.isChecked()){
            eleccion="Fontanero";
        }

        if(rDos.isChecked()){
            eleccion="Domina dos";
        }

        if(rTodos.isChecked()){
            eleccion="Domina todos";
        }

        if(!correo.isEmpty()) {
            trabajador tra = new trabajador(nombre,telefono,eleccion,zona,foto,descripcion,correo);
                            /*
                            Obtiene la base de datos directamente de la principal,
                            solo tengo una asi que no especifico la ruta de la DB
                            Y obtengo la referencia, que es mas bien para crear un apartado llamado Usuarios
                             */
            FirebaseDatabase.getInstance().getReference("Trabajadores")
                    //Toma de la base de datos principal el id del usuario
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    //Cuando obtiene el valor lo asigna al objeto usu, se usa un complete listener para verificar el exito del mismo
                    .setValue(tra).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                //Crea un metodo de task
                public void onComplete(@NonNull Task<Void> task) {
                    //Si el objeto de tipo usuario se le asigno el valor correspondiente
                    if(task.isSuccessful()){
                        Toast.makeText(registroExpertos.this, "El usuario se registró", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(registroExpertos.this,iniciarSesion.class);
                        startActivity(intent);
                    }
                    //Si no
                    else{
                        Toast.makeText(registroExpertos.this, "Fallo en registrar al usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void escogerFoto() {
        //Creo la intencion
        Intent intent = new Intent();
        //Defino de que tipo es la intencion y el tipo de imagenes a guardar, el /* indica que todo tipo de imagenes
        intent.setType("image/*");
        //Declaro el tipo de accion para la intencion, es decir deseo traer datos
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //Inicio la actividad para retraer info, parecido a cuando abro la camara, solo que abre la aplicacion de archivos por defecto
        startActivityForResult(intent,1);//Guardo el numero de request para identificar que accion se hizo
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Si el codigo de resultado es 1 como en la linea 210 indiqué, y el resultado es un ok y si el dato que se selecciono no esta vacio(seleccione una imagen)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Obten la ruta del fichero de la imagen la cual seleccione
            uri = data.getData();
            //Setea en el imageView la imagen que selecciones desde su ruta apuntada
            imgV_Expertos.setImageURI(uri);
            //Creo el metodo subir foto para subida a BD
//            subirFoto(imagenUri);
        }
    }

    //************METODO PARA SUBIDA DE VIDEO y FOTO
    private void subirFoto(Uri uri) {
        //Creo un cuadro de dialogo parecido a un JOptionPane en Java normal
        final ProgressDialog pd = new ProgressDialog(this);
        //Asigno un titulo al dialogo
        pd.setTitle("Subiendo imagen y video...");
        //Muestro el cuadro de dialogo
        pd.show();

        //Obtengo la referencia de la imagen
        final StorageReference riversRef = storageReference.child("articulos"+"/"+"trabajadores/"+randomkey);
        //Obtengo la referencia de mi almacenamiento guardado en la rama(child) images/(carpeta) dentro de la carpeta del Id del usuario y
        // asu vez quiero que la imagen se llame profile.jpeg esto permite que no se repita mas imagenes de perfil y todas las imagenes subidas se reemplazen por una nueva

        riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//Guardo la ruta de la imagen en la rama, la ruta que obtuvimos en el Activity OnResult
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//obtengo de la referencia la Url con la cual se sube el archivo
                    @Override
                    public void onSuccess(Uri uri) {//Si esto es exitoso
                        final int min = 1000;
                        final int max = 2000;
                        final int id = new Random().nextInt((max - min) + 1) + min;
                        final String nuevoId = String.valueOf(id);
                        Perfiles p = new Perfiles();//Creo un objeto p de las clase perfiles
                        String G = uri.toString();//Ingreso en un atributo la url subida en String
                        String pId = reference2.push().getKey();//Obtengo la llave de referencia con la cual se sube los datos a la BD
                        //Ambos datos se guardarian en la llave obtenida algo como Woa743sdFDwas/Url y Woa743sdFDwas/Fecha por decir un ejemplo
                        reference2.child(pId).child("UrlTrabajador").setValue(G);//Subo en la rama de la llave un child que diga url
                        reference2.child(pId).child("Id").setValue(nuevoId);//Subo en la rama de la llave un child que diga url
                        //txtEscondido_Agregar.setText(G);
                        Glide.with(getApplicationContext()).load(uri).into(imgV_Expertos);
                        //reference2.child(pId).child("IdUsuario").setValue(idUsuario);
                        Toast.makeText(registroExpertos.this, "Se subió correctamente", Toast.LENGTH_SHORT).show();//Muestro un mensaje de que se subió correctamente
                        registrarseRegistro(G);

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {//Mientras se hace el meotod Succes
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());//Obten los milisegundos en que tarda la imagen en cargar
                pd.setMessage("Progress: "+(int)progressPercent + "%");//Conviertelo a un porcentaje del 0 al 100 porciento y muestralo en el caudro de dialogo
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {//Si falla
                pd.dismiss();//Cierra el cuadro de dialogo
                Toast.makeText(getApplicationContext(),"No se pudo subir la imagen",Toast.LENGTH_LONG).show();//Muestra un mensaje de eror
            }
        });
    }

}