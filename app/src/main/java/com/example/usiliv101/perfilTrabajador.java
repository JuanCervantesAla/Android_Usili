package com.example.usiliv101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class perfilTrabajador extends AppCompatActivity {

    ImageView imgVolver_E,imgFoto_E;
    TextView txtNombre_E,txtCorreo_E,txtEleccion_E,txtDescripcion_E,txtZona_E,txtTelefono_E;
    Context context;
    Button btnLlamar_E,btnEnviarC_E2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_trabajador);
        imgVolver_E=findViewById(R.id.imgVolver_E);
        imgFoto_E = findViewById(R.id.imgFoto_E);
        txtNombre_E = findViewById(R.id.txtNombre_E);
        txtCorreo_E = findViewById(R.id.txtCorreo_E);
        txtEleccion_E=findViewById(R.id.txtEleccion_E);
        txtDescripcion_E = findViewById(R.id.txtDescripcion_E);
        txtZona_E = findViewById(R.id.txtZona_E);
        txtTelefono_E = findViewById(R.id.txtTelefono_E);
        btnLlamar_E = findViewById(R.id.btnLlamar_E);
        btnEnviarC_E2 = findViewById(R.id.btnEnviarC_E2);


        String NOMBRE = getIntent().getStringExtra("Nombre");
        String TELEFONO = getIntent().getStringExtra("Telefono");
        String CORREO = getIntent().getStringExtra("Email");
        String DESCRIPCION = getIntent().getStringExtra("Descripcion");
        String ZONA = getIntent().getStringExtra("Zona");
        String ELECCION = getIntent().getStringExtra("Eleccion");
        String FOTO = getIntent().getStringExtra("Foto");


        Glide.with(getApplicationContext()).load(FOTO).into(imgFoto_E);
        txtNombre_E.setText(NOMBRE);
        txtCorreo_E.setText(CORREO);
        txtEleccion_E.setText(ELECCION);
        txtDescripcion_E.setText(DESCRIPCION);
        txtZona_E.setText(ZONA);
        txtTelefono_E.setText(TELEFONO);

        imgVolver_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfilTrabajador.this,frontExpertos.class);
                startActivity(intent);
            }
        });
        btnLlamar_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(TELEFONO)));
                startActivity(intent);
            }
        });
        btnEnviarC_E2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfilTrabajador.this,correo.class);
                //String nombre,telefono,email,eleccion,descripcion,zona,foto,diferencia=" "
                intent.putExtra("Correito",CORREO);
                startActivity(intent);
            }
        });


    }
}