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

        btnRegistrar_Invitado = findViewById(R.id.btnRegistrar_Invitado);
        recyclerView = findViewById(R.id.rvArticulos_Front);
        //rvPrincipal1 = findViewById(R.id.rvPrincipal1);

        //INICIO DE RECUPERAR EL CORREO DE LA BASE DE DATOS

        //Obtengo al usuario actual
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        //Tomo la referencia de mi base de datos root
        reference= FirebaseDatabase.getInstance().getReference("Usuarios");
        //Tomo el id proveniente de usuario
        idUsuario = usuario.getUid();
        reference3 = FirebaseStorage.getInstance().getReference("images/").child(idUsuario).child("profile.jpeg");//Referencia a la imagen de perfil del usuario

        btnRegistrar_Invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(modoInvitado.this,registro.class);
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

    }
}