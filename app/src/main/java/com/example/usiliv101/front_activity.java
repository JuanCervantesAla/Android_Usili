package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class front_activity extends AppCompatActivity {

    //Variables de firebase
    RecyclerView recyclerView;
    AdaptadorRV adaptadorRV;
    ArrayList<Articulos> list;

    ImageView iconoperfil,imgVFoto_Front;
    Button btnAgregar_front;
    private FirebaseUser usuario;
    private DatabaseReference reference;
    private DatabaseReference referenceRV;
    private String idUsuario;
    private StorageReference reference3;//Referencia hacia el almacenamiento, la imagen de perfil

    //Variables del recycle
    RecyclerView rvPrincipal1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        //Asocio variables Java con xml
        imgVFoto_Front = findViewById(R.id.imgVFoto_Front);
        btnAgregar_front = findViewById(R.id.btnAgregar_front);
        recyclerView = findViewById(R.id.rvArticulos_Front);
        //rvPrincipal1 = findViewById(R.id.rvPrincipal1);

        btnAgregar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(front_activity.this,Agregar.class);
                startActivity(intent);
            }
        });

        //Iconos principales de home
        imgVFoto_Front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(front_activity.this,perfil.class);
                startActivity(intent);
            }
        });

        //INICIO DE RECUPERAR EL CORREO DE LA BASE DE DATOS

        //Obtengo al usuario actual
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        //Tomo la referencia de mi base de datos root
        reference= FirebaseDatabase.getInstance().getReference("Usuarios");
        //Tomo el id proveniente de usuario
        idUsuario = usuario.getUid();
        reference3 = FirebaseStorage.getInstance().getReference("images/").child(idUsuario).child("profile.jpeg");//Referencia a la imagen de perfil del usuario

        //**********************TRY PARA RECUPERAR LA IMAGEN**************************
        //Try catch en el metodo Oncreate de la activity perfil
        try {//Intenta
            final File localFile= File.createTempFile("profile","jpeg");//Crea un archivo temporal con este nombre,profile jpeg
            reference3.getFile(localFile)//trae el archivo de la referencia y almacenalo en el objeto temporal localFile
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {//Si el proceso fue exitoso
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(front_activity.this, "Imagen cargada", Toast.LENGTH_SHORT).show();//Manda un mensaje de imagen cargada
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

                            imgVFoto_Front.setImageBitmap(bitmap);

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






/*
        final TextView txtEmail_home = findViewById(R.id.txtEmail_home);

        //Recibir datos mediante Datasnapshot
        reference.child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            //Este sirve para cuando se pase los datos 
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 usuario perfilUsuario = snapshot.getValue(usuario.class);

                 if(perfilUsuario !=null){
                     String correo = perfilUsuario.correo;
                     //La edad esta comentada para utilizarlo en la activity del perfil
                     //String edad = perfilUsuario.edad;

                     txtEmail_home.setText(correo);

                 }
            }
            //Este si es que hay un error al momento de recibir datos
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(front_activity.this, "No pudimos recuperar los datos", Toast.LENGTH_SHORT).show();
            }
        });//Fin de el correo en textView*/

        //Inicio del recycle View

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this ));

    list = new ArrayList<>();
    adaptadorRV= new AdaptadorRV(this,list);
    recyclerView.setAdapter(adaptadorRV);
    referenceRV= FirebaseDatabase.getInstance().getReference("Articulos");

    referenceRV.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                Articulos art = dataSnapshot.getValue(Articulos.class);
                list.add(art);
            }
            adaptadorRV.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });



    }//Fin de Create


    //Impido que el usuario regrese a la ventana anterior
    @Override
    public void onBackPressed(){

    }
}