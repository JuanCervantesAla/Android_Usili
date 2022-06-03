package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_expertos);
        recyclerView = findViewById(R.id.recycleE);


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