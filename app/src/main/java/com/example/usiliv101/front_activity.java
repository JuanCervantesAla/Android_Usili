package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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

import com.firebase.ui.database.FirebaseRecyclerOptions;


import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class front_activity extends AppCompatActivity implements Interfaz {

    //Variables de firebase
    RecyclerView recyclerView;
    AdaptadorRV adaptadorRV;
    ArrayList<Articulos> list;

    SearchView searchView;
    DrawerLayout drawerLayout;
    NavigationView menuNavegacion;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView iconoperfil,imgVFoto_Front;
    Button btnAgregar_front,btnMis_front,btnExpertos_front;
    private FirebaseUser usuario;
    private DatabaseReference referenceRV;
    private DatabaseReference reference;
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
        searchView = findViewById(R.id.search);

        btnExpertos_front = findViewById(R.id.btnExpertos_front);
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
                        Intent intent = new Intent(front_activity.this,front_activity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Perfil_Menu:
                        Intent intent2 = new Intent(front_activity.this,perfil.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Agregar_Menu:
                        Intent intent3 = new Intent(front_activity.this,Agregar.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Especialistas_Menu:
                        Intent intent4 = new Intent(front_activity.this,frontExpertos.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Cerrar_Menu:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent5 = new Intent(front_activity.this,iniciarSesion.class);
                        startActivity(intent5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Mis_Menu:
                        Intent intent6 = new Intent(front_activity.this,front_Misart.class);
                        startActivity(intent6);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Ayudo_Menu:
                        String url = "http://proyectosinformaticatnl.ceti.mx/usiliweb/";
                        Uri enlace = Uri.parse(url);
                        Intent intent7 = new Intent(Intent.ACTION_VIEW,enlace);
                        startActivity(intent7);

                }
                return true;
            }
        });
        //rvPrincipal1 = findViewById(R.id.rvPrincipal1);

        btnExpertos_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(front_activity.this,frontExpertos.class);
                startActivity(intent);
            }
        });

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



    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this ));

    list = new ArrayList<>();
    list.clear();
    adaptadorRV= new AdaptadorRV(this,list,this);
    recyclerView.setAdapter(adaptadorRV);
    referenceRV= FirebaseDatabase.getInstance().getReference("Articulos");

    referenceRV.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                Articulos art = dataSnapshot.getValue(Articulos.class);
                list.add(art);
                adaptadorRV.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                buscar(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return false;
            }
        });



    }//Fin de Create


    private void buscar(String s){
        ArrayList<Articulos> listaBusqueda = new ArrayList<>();
        for(Articulos objetito: list){
            if(objetito.getTitulo().toLowerCase().contains(s.toLowerCase())){
                listaBusqueda.add(objetito);
            }
            if(objetito.getAutor().toLowerCase().contains(s.toLowerCase())){
                listaBusqueda.add(objetito);
            }
        }
        AdaptadorRV adaptadorRV = new AdaptadorRV(this,listaBusqueda,this);
        recyclerView.setAdapter(adaptadorRV);

    }

    //Impido que el usuario regrese a la ventana anterior
    @Override
    public void onBackPressed(){

    }

    @Override
    public void clickEnItem(int posicion) {
        Intent intent = new Intent(front_activity.this,Post.class);
        intent.putExtra("Identificador",list.get(posicion).getId());
        intent.putExtra("Titulo",list.get(posicion).getTitulo());
        intent.putExtra("Autor",list.get(posicion).getAutor());
        intent.putExtra("Pasos",list.get(posicion).getPasos());
        intent.putExtra("Materiales",list.get(posicion).getMateriales());
        intent.putExtra("Enlace",list.get(posicion).getEnlace());
        intent.putExtra("Enlace2",list.get(posicion).getEnlace2());
        intent.putExtra("Enlace3",list.get(posicion).getEnlace3());
        intent.putExtra("EnlaceV",list.get(posicion).getVideo());
        intent.putExtra("EnlaceP",list.get(posicion).getPdf());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
            FirebaseAuth.getInstance().signOut();
    }

}