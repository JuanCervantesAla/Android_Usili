package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.UUID;

public class Agregar extends AppCompatActivity {

    //Atributos en Java
    Button btnVolver_Agregar,btnContinuar_Agregar;
    EditText edtETitulo_Agregar,edtEMateriales_Agregar,edtEPasos_Agregar;
    TextView txtEscondido_Agregar;
    ImageView imgVImagen_Agregar;
    public Uri imagenUri;

    private DatabaseReference reference;//Ruta para la tabla Usuarios
    private String idUsuario;
    private FirebaseUser usuario;//Autenticación del usuario, obtengo al usuario en sesion
    private FirebaseStorage storage;//Atributo contenedor de referencia al almacenamiento
    private StorageReference storageReference;//Atributo de contenedor de referencia al almacenamiento
    private DatabaseReference reference2;//Ruta para la tabla perfiles, se guarda la fecha y la URL vease linea 230
    private DatabaseReference dbApoyo;
    private DatabaseReference  referenciaURL;

    final String randomkey  = UUID.randomUUID().toString();//Creo una llave random para almacenar las imagenes en una carpeta
    private Object ValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        //Subida de foto
        storage =  FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        reference2= FirebaseDatabase.getInstance().getReference("Apoyo");//Tomo la referencia de mi base de datos root para subir fecha y url en tabla perfiles

        //Relacion JAVA - XML
        btnVolver_Agregar = findViewById(R.id.btnVolver_Agregar);
        btnContinuar_Agregar = findViewById(R.id.btnContinuar_Agregar);
        edtETitulo_Agregar = findViewById(R.id.edtETitulo_Agregar);
        edtEMateriales_Agregar  =findViewById(R.id.edtEMateriales_Agregar);
        edtEPasos_Agregar = findViewById(R.id.edtEPasos_Agregar);
        txtEscondido_Agregar = findViewById(R.id.txtEscondido_Agregar);
        imgVImagen_Agregar = findViewById(R.id.imgVImagen_Agregar);

        //Select * from Apoyo where

        dbApoyo = FirebaseDatabase.getInstance().getReference("Apoyo");




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

        imgVImagen_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escogerFoto();
            }
        });

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

    //Cuando se realizo la apertura de la app de archivos y se escogió una foto... en resultado se hace....
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Si el codigo de resultado es 1 como en la linea 210 indiqué, y el resultado es un ok y si el dato que se selecciono no esta vacio(seleccione una imagen)
        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            //Obten la ruta del fichero de la imagen la cual seleccione
            imagenUri = data.getData();
            //Setea en el imageView la imagen que selecciones desde su ruta apuntada
            imgVImagen_Agregar.setImageURI(imagenUri);
            //Creo el metodo subir foto para subida a BD
//            subirFoto(imagenUri);
        }
    }

    //************METODO PARA SUBIDA DE FOTO
    private void subirFoto(Uri uri,String nuevoId) {
        //Creo un cuadro de dialogo parecido a un JOptionPane en Java normal
        final ProgressDialog pd = new ProgressDialog(this);
        //Asigno un titulo al dialogo
        pd.setTitle("Subiendo imagen...");
        //Muestro el cuadro de dialogo
        pd.show();



        //Obtengo la referencia de la imagen
        final StorageReference riversRef = storageReference.child("articulos/"+idUsuario+"/"+randomkey);
        //Obtengo la referencia de mi almacenamiento guardado en la rama(child) images/(carpeta) dentro de la carpeta del Id del usuario y
        // asu vez quiero que la imagen se llame profile.jpeg esto permite que no se repita mas imagenes de perfil y todas las imagenes subidas se reemplazen por una nueva
        riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//Guardo la ruta de la imagen en la rama, la ruta que obtuvimos en el Activity OnResult
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//obtengo de la referencia la Url con la cual se sube el archivo
                    @Override
                    public void onSuccess(Uri uri) {//Si esto es exitoso

                        Perfiles p = new Perfiles();//Creo un objeto p de las clase perfiles
                        String G = uri.toString();//Ingreso en un atributo la url subida en String
                        String pId = reference2.push().getKey();//Obtengo la llave de referencia con la cual se sube los datos a la BD
                        //Ambos datos se guardarian en la llave obtenida algo como Woa743sdFDwas/Url y Woa743sdFDwas/Fecha por decir un ejemplo
                        reference2.child(pId).child("Url").setValue(G);//Subo en la rama de la llave un child que diga url
                        reference2.child(pId).child("Id").setValue(nuevoId);//Subo en la rama de la llave un child que diga url
                        txtEscondido_Agregar.setText(G);
                        Glide.with(getApplicationContext()).load(uri).into(imgVImagen_Agregar);
                        //reference2.child(pId).child("IdUsuario").setValue(idUsuario);
                        pd.dismiss();//Cierro el cuadro de dialogo abierto de subiendo imagen
                        Toast.makeText(Agregar.this, "Se subió correctamente", Toast.LENGTH_SHORT).show();//Muestro un mensaje de que se subió correctamente
                        Consulta(nuevoId);
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

    private void CargarBase(String titulo, String pasos, String materiales,String autor, String nuevoId, String enlace){
        Articulos articulo =  new Articulos(titulo,pasos,materiales,autor,nuevoId,enlace);
        FirebaseDatabase.getInstance().getReference("Articulos")
                .child(randomkey).setValue(articulo).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private void Consulta(String nuevoId){
        String titulo =  edtETitulo_Agregar.getText().toString().trim();
        String materiales = edtEMateriales_Agregar.getText().toString().trim();
        String pasos = edtEPasos_Agregar.getText().toString().trim();
        String autor = "Juan Cervantes";
        String enlace=" ";
        Query query = FirebaseDatabase.getInstance().getReference("Apoyo")
                .orderByChild("Id")
                .equalTo(nuevoId);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Apoyo poyo = snapshot.getValue(Apoyo.class);//Creamos un objeto de tipo usuario de la clase usuario
                    //Creamos objeto snapshot que obtenga los valores almacenados del constructor de la clase usuario
                    //Crea variables para almacenar los que encontraste de correo y edad del constructor apuntando hacia la BD
                    String url = poyo.Url;
                    txtEscondido_Agregar.setText(url);
                    Toast.makeText(Agregar.this, "Se encontraron Datos", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Agregar.this, "Nada we", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Agregar.this, "NONO", Toast.LENGTH_SHORT).show();
            }
        });
        enlace = txtEscondido_Agregar.getText().toString().trim();
        CargarBase(titulo,pasos,materiales,autor,nuevoId,enlace);

    }

    private void subirDatos() {
        String titulo =  edtETitulo_Agregar.getText().toString().trim();
        String materiales = edtEMateriales_Agregar.getText().toString().trim();
        String pasos = edtEPasos_Agregar.getText().toString().trim();
        String autor = "Juan Cervantes";
        String enlace=" ";
        String pId = reference2.push().getKey();
        //final String randomkey  = UUID.randomUUID().toString();//Creo una llave random para almacenar las imagenes en una carpeta
        final int min = 1000;
        final int max = 2000;
        final int id = new Random().nextInt((max - min) + 1) + min;
        final String nuevoId = String.valueOf(id);


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

        subirFoto(imagenUri,nuevoId);

    }
}

