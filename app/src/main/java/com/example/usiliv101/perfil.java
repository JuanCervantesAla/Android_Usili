package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;



public class perfil extends AppCompatActivity {

    //Atributos de Java
    ImageView imgVFoto_Perfil,imgVFoto_Front;
    public TextView txtCorreo_enPerfil,txtEdad_enPerfil;
    ImageView btnVolver_Perfil;
    Button btnCerrarsesion_perfil, btnCambiarontrasena_perfil;
    //String para guardar fecha y hora en la que se guardo una foto de perfil
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

    DrawerLayout drawerLayout;
    NavigationView menuNavegacion;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private Switch switch1;

    //Atibutos de la base de datos
    private FirebaseUser usuario;//Autenticación del usuario, obtengo al usuario en sesion
    private DatabaseReference reference;//Ruta para la tabla Usuarios
    private DatabaseReference reference2;//Ruta para la tabla perfiles, se guarda la fecha y la URL vease linea 230
    private StorageReference reference3;//Referencia hacia el almacenamiento, la imagen de perfil
    public String idUsuario;//Atributo que contiene el id del usuario actual
    private FirebaseStorage storage;//Atributo contenedor de referencia al almacenamiento
    private StorageReference storageReference;//Atributo de contenedor de referencia al almacenamiento

    //Atributo extra
    public Uri imagenUri;//Atributo contenedor de la URL de una imagen en particular

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Relacion entre los items xml y atributos de Java
        btnVolver_Perfil = findViewById(R.id.btnVolver_Perfil);
        btnCerrarsesion_perfil = findViewById(R.id.btnCerrarSesion_Perfil);
        btnCambiarontrasena_perfil = findViewById(R.id.btnCambiarContrasena_Perfil);
        imgVFoto_Perfil = findViewById(R.id.imgVFoto_Perfil);
        final TextView txtCorreo_enPerfil = findViewById(R.id.txtEmailRecipiente_Perfil);
        final TextView txtEdad_enPerfil = findViewById(R.id.txtPasswordRecipiente_Perfil);

        drawerLayout = findViewById(R.id.drawerLayout);
        menuNavegacion = findViewById(R.id.menuNavegacion);

        //Menu
        actionBarDrawerToggle =new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        menuNavegacion.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_Menu:
                        Intent intent = new Intent(perfil.this,front_activity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Perfil_Menu:
                        Intent intent2 = new Intent(perfil.this,perfil.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Agregar_Menu:
                        Intent intent3 = new Intent(perfil.this,Agregar.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Especialistas_Menu:
                        Intent intent4 = new Intent(perfil.this,frontExpertos.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Cerrar_Menu:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent5 = new Intent(perfil.this,iniciarSesion.class);
                        startActivity(intent5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Mis_Menu:
                        Intent intent6 = new Intent(perfil.this,front_Misart.class);
                        startActivity(intent6);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return true;
            }
        });

        //Inicialización de variables de firebase
        usuario = FirebaseAuth.getInstance().getCurrentUser();//Obtengo al usuario actual
        idUsuario = usuario.getUid();//Tomo el id proveniente de usuario
        reference= FirebaseDatabase.getInstance().getReference("Usuarios");//Tomo la referencia de mi base de datos root para autenticar el perfil
        reference2= FirebaseDatabase.getInstance().getReference("Perfiles");//Tomo la referencia de mi base de datos root para subir fecha y url en tabla perfiles
        String pId = reference2.push().getKey();//Obtengo la llave de referencia generada cuando se hace un push en Perfiles
        reference3 = FirebaseStorage.getInstance().getReference("images/").child(idUsuario).child("profile.jpeg");//Referencia a la imagen de perfil del usuario

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            switch1.setChecked(true);
        }
/*
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    reset();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    reset();
                }
            }
        });*/

        //Regresa a la activity Home
        btnVolver_Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this,front_activity.class);
                startActivity(intent);
            }
        });

        //Cierra la sesion y redirige a la activity Inicial para logear
        btnCerrarsesion_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(perfil.this,iniciarSesion.class);
                startActivity(intent);
            }
        });

        //Lleva del perfil hacia la activity para cambio de contrasena
        btnCambiarontrasena_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this,contrasenaNueva.class);
                startActivity(intent);
            }
        });

        //LLamada al metodo escoger foto cuando se ahce clic en el imageView
        imgVFoto_Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escogerFoto();

            }
        });

        //Try catch en el metodo Oncreate de la activity perfil
        try {//Intenta
            final File localFile= File.createTempFile("profile","jpeg");//Crea un archivo temporal con este nombre,profile jpeg
            reference3.getFile(localFile)//trae el archivo de la referencia y almacenalo en el objeto temporal localFile
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {//Si el proceso fue exitoso
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(perfil.this, "Imagen cargada", Toast.LENGTH_SHORT).show();//Manda un mensaje de imagen cargada
                            /*
                            Como no puedo mostrar una imagen a su resolución total
                            Hago una bitmap(una rescala) de la imagen
                            Acortando el uso de la memoria que tiene
                            Ajustandolo a la pantalla
                            y que solo muestra una fracción de la imagen
                            algo asi como cuando se muestran las imagenes en galeria
                            No se muestran a toda resolucion, solo una parte de ellas como unos pequeños iconos
                             */
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());/*
                                                                                                    Creo un bitmap para tomar solo una parte de los bits
                                                                                                    Despues obtengo la ruta donde se guardo el archivo temporal
                                                                                                    de tipo File que utilicé
                                                                                                   */

                            imgVFoto_Perfil.setImageBitmap(bitmap);

                            /*
                             Indico que al imageView le agregue la imagen reescalada que apunta al fichero
                             creado en memoria temporal que obtiene la ruta de mi base de datos
                             */

                        }
                    }).addOnFailureListener(new OnFailureListener() {//Si el proceso falla
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(perfil.this, "Error el cargar imagen", Toast.LENGTH_SHORT).show();//Muestra mensaje de error
                }
            });
        } catch (IOException e) { //Si de plano todo falla imprimer el error con la expcetion In Out
            e.printStackTrace();
        }

        //Evento que se ejcuta en el metodo OnCreate
        reference.child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {//Recupera la referencia de la rama del Ide del usuario de Usuarios en la BD
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//Cuando se recuperan los datos
                usuario perfilUsuario = snapshot.getValue(usuario.class);//Creamos un objeto de tipo usuario de la clase usuario
                //Creamos objeto snapshot que obtenga los valores almacenados del constructor de la clase usuario

                if(perfilUsuario !=null){//Si el constructor no esta vacio
                    //Crea variables para almacenar los que encontraste de correo y edad del constructor apuntando hacia la BD
                    String correo = perfilUsuario.correo;
                    String edad = perfilUsuario.edad;

                    //Ingresa esos datos en el label para mostrar la información del usuario
                    txtCorreo_enPerfil.setText(correo);
                    txtEdad_enPerfil.setText(edad);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {//Si no se pudo recuperar datos
                Toast.makeText(perfil.this, "No pudimos recuperar los datos", Toast.LENGTH_SHORT).show();//Muestra un mensaje de error
            }
        });

        //Subida de foto
        storage =  FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }//Fin del metodo OnCreate

    private void reset() {
        Intent intent = new Intent(getApplicationContext(),perfil.class);
        startActivity(intent);
        finish();
    }

    //********************************METODOS FUERA DEL ONCREATE************************************
    //Metodo para escoger la foto
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
            imgVFoto_Perfil.setImageURI(imagenUri);
            //Creo el metodo subir foto para subida a BD
            subirFoto(imagenUri);
        }
    }

    //************METODO PARA SUBIDA DE FOTO
    private void subirFoto(Uri uri) {
        //Creo un cuadro de dialogo parecido a un JOptionPane en Java normal
        final ProgressDialog pd = new ProgressDialog(this);
        //Asigno un titulo al dialogo
        pd.setTitle("Subiendo imagen...");
        //Muestro el cuadro de dialogo
        pd.show();


        //final String randomkey  = UUID.randomUUID().toString();//Creo una llave random para almacenar las imagenes en una carpeta
        //Obtengo la referencia de la imagen
        final StorageReference riversRef = storageReference.child("images/"+idUsuario+"/profile.jpeg");
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
                        reference2.child(pId).child("Fecha").setValue(timeStamp);//Subo en la rama de la llave un child que diga url
                        Glide.with(getApplicationContext()).load(uri).into(imgVFoto_Perfil);
                        //reference2.child(pId).child("IdUsuario").setValue(idUsuario);
                        pd.dismiss();//Cierro el cuadro de dialogo abierto de subiendo imagen
                        Toast.makeText(perfil.this, "Se subió correctamente", Toast.LENGTH_SHORT).show();//Muestro un mensaje de que se subió correctamente
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

    private String getFileExtension(Uri muri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }

}