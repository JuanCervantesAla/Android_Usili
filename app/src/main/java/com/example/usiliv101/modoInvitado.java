package com.example.usiliv101;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
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
import android.os.Bundle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class modoInvitado extends AppCompatActivity implements Interfaz {


    //Variables de firebase
    ImageView btnVolver_Invitado;
    RecyclerView recyclerView;
    AdaptadorRV adaptadorRV;
    ArrayList<Articulos> list;

    Button btnRegistrar_Invitado;
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
        setContentView(R.layout.activity_modo_invitado);
        FirebaseAuth.getInstance().signOut();

        btnRegistrar_Invitado = findViewById(R.id.btnRegistrar_Invitado);
        recyclerView = findViewById(R.id.rvArticulos_Front);
        btnVolver_Invitado = findViewById(R.id.btnVolver_Invitado);
        //rvPrincipal1 = findViewById(R.id.rvPrincipal1);

        //INICIO DE RECUPERAR EL CORREO DE LA BASE DE DATOS


        btnRegistrar_Invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(modoInvitado.this,registro.class);
                startActivity(intent);
            }
        });

        btnVolver_Invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(modoInvitado.this,iniciarSesion.class);
                startActivity(intent);
            }
        });

        //Inicio del recycle View

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ));

        list = new ArrayList<>();
        adaptadorRV= new AdaptadorRV(this,list,this);
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
    }

    @Override
    public void clickEnItem(int posicion) {
        Intent intent = new Intent(modoInvitado.this,postInvitado.class);
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
}