package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class front_activity extends AppCompatActivity {

    ImageView iconoperfil;
    private FirebaseUser usuario;
    private DatabaseReference reference;
    private String idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        iconoperfil = findViewById(R.id.imgVperfil_front);

        iconoperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(front_activity.this,perfil.class);
                startActivity(intent);
            }
        });

        //Obtengo al usuario actual
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        //Tomo la referencia de mi base de datos root
        reference= FirebaseDatabase.getInstance().getReference("Usuarios");
        //Tomo el id proveniente de usuario
        idUsuario = usuario.getUid();

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
        });
    }
    //Impido que el usuario regrese a la ventana anterior
    @Override
    public void onBackPressed(){

    }
}