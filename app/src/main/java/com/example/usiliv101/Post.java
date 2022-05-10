package com.example.usiliv101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Post extends AppCompatActivity {

    Button btnVolver_Post;
    TextView txtTitulo_Post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Relacion de los atributos y objetos xml
        btnVolver_Post = findViewById(R.id.btnVolver_Post);
        txtTitulo_Post = findViewById(R.id.txtTitulo_Post);


        //Evento para el boton regresar a front
        btnVolver_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Post.this,front_activity.class);
                startActivity(intent);
            }
        });

    }

    //Evito que pueda volver con los botones del sistema
    @Override
    public void onBackPressed(){

    }

}