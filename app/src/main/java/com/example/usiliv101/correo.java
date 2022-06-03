package com.example.usiliv101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class correo extends AppCompatActivity {
    EditText edtCorreoE_Correo,txtBody_Correo;
    ImageView imgVolver_Correo;
    Button btnEnviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo);
        btnEnviar = findViewById(R.id.btnEnviar);
        edtCorreoE_Correo =findViewById(R.id.edtCorreoE_Correo);
        txtBody_Correo = findViewById(R.id.txtBody_Correo);
        imgVolver_Correo = findViewById(R.id.imgVolver_Correo);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Enviar(view);
            }
        });

        imgVolver_Correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(correo.this,perfilTrabajador.class);
                startActivity(intent);
            }
        });

    }

    public void Enviar(View view){
        String CORREO = getIntent().getStringExtra("Correito");
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{CORREO});
        intent.putExtra(Intent.EXTRA_SUBJECT,edtCorreoE_Correo.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT,txtBody_Correo.getText().toString());
        startActivity(intent);
    }
}