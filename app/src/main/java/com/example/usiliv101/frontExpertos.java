package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class frontExpertos extends AppCompatActivity implements Interfaz{

    RecyclerView recyclerView;
    AdaptadorRVE adaptadorRVE;
    ArrayList<trabajador> list;
    private DatabaseReference referenceRV;
    ImageView imageView4;

    DrawerLayout drawerLayout;
    NavigationView menuNavegacion;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_expertos);
        recyclerView = findViewById(R.id.recycleE);
        drawerLayout = findViewById(R.id.drawerLayout);
        menuNavegacion = findViewById(R.id.menuNavegacion);
        imageView4 = findViewById(R.id.imageView4);


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
                    Intent intent = new Intent(frontExpertos.this,front_activity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.Perfil_Menu:
                    Intent intent2 = new Intent(frontExpertos.this,perfil.class);
                    startActivity(intent2);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.Agregar_Menu:
                    Intent intent3 = new Intent(frontExpertos.this,Agregar.class);
                    startActivity(intent3);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.Especialistas_Menu:
                    Intent intent4 = new Intent(frontExpertos.this,frontExpertos.class);
                    startActivity(intent4);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.Cerrar_Menu:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent5 = new Intent(frontExpertos.this,iniciarSesion.class);
                    startActivity(intent5);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.Mis_Menu:
                    Intent intent6 = new Intent(frontExpertos.this,front_Misart.class);
                    startActivity(intent6);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

            }
            return true;
        }
    });



        imageView4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(frontExpertos.this,front_activity.class);
            startActivity(intent);
        }
    });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ));

    list = new ArrayList<>();
    adaptadorRVE= new AdaptadorRVE(this,list,this);
        recyclerView.setAdapter(adaptadorRVE);
    referenceRV= FirebaseDatabase.getInstance().getReference("Trabajadores");

        referenceRV.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                trabajador tra = dataSnapshot.getValue(trabajador.class);
                list.add(tra);
            }
            adaptadorRVE.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}

    @Override
    public void clickEnItem(int posicion) {
        Intent intent = new Intent(frontExpertos.this,perfilTrabajador.class);
        //String nombre,telefono,email,eleccion,descripcion,zona,foto,diferencia=" "
        intent.putExtra("Nombre",list.get(posicion).getNombre());
        intent.putExtra("Telefono",list.get(posicion).getTelefono());
        intent.putExtra("Email",list.get(posicion).getEmail());
        intent.putExtra("Eleccion",list.get(posicion).getEleccion());
        intent.putExtra("Descripcion",list.get(posicion).getDescripcion());
        intent.putExtra("Zona",list.get(posicion).getZona());
        intent.putExtra("Foto",list.get(posicion).getFoto());

        startActivity(intent);
    }
}