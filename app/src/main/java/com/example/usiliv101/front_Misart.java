package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class front_Misart extends AppCompatActivity implements Interfaz{

    //Variables de firebase
    RecyclerView rvArt;
    AdapatadorMis adapatadorMis;
    ArrayList<Articulos> list;

    SearchView searchView;
    DrawerLayout drawerLayout;
    NavigationView menuNavegacion;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView imgVFoto_Front,imageView4;
    private DatabaseReference referenceRV;
    private String idUsuario;
    private StorageReference reference3;//Referencia hacia el almacenamiento, la imagen de perfil

    //Variables del recycle
    RecyclerView rvPrincipal1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_misart);
        imgVFoto_Front = findViewById(R.id.imgVFoto_Front);
        rvArt = findViewById(R.id.rvArt);
        searchView = findViewById(R.id.search);
        drawerLayout = findViewById(R.id.drawerLayout);
        menuNavegacion = findViewById(R.id.menuNavegacion);
        imageView4 = findViewById(R.id.imageView4);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(front_Misart.this,front_activity.class);
                startActivity(intent);
            }
        });

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
                        Intent intent = new Intent(front_Misart.this,front_activity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Perfil_Menu:
                        Intent intent2 = new Intent(front_Misart.this,perfil.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Agregar_Menu:
                        Intent intent3 = new Intent(front_Misart.this,Agregar.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Especialistas_Menu:
                        Intent intent4 = new Intent(front_Misart.this,frontExpertos.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Cerrar_Menu:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent5 = new Intent(front_Misart.this,iniciarSesion.class);
                        startActivity(intent5);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Mis_Menu:
                        Intent intent6 = new Intent(front_Misart.this,front_Misart.class);
                        startActivity(intent6);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }
                return true;
            }
        });
        rvArt.setHasFixedSize(true);
        rvArt.setLayoutManager(new LinearLayoutManager(this ));

        list = new ArrayList<>();
        adapatadorMis= new AdapatadorMis(this,list,this);
        rvArt.setAdapter(adapatadorMis);
        referenceRV= FirebaseDatabase.getInstance().getReference("Articulos");

        referenceRV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Articulos art = dataSnapshot.getValue(Articulos.class);
                    list.add(art);
                }
                adapatadorMis.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void clickEnItem(int posicion) {
        Intent intent = new Intent(front_Misart.this,Post.class);
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