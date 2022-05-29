package com.example.usiliv101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.denzcoskun.imageslider.constants.ScaleTypes;

public class Post extends AppCompatActivity {

    Button btnVolver_Post;
    TextView txtTitulo_Post,txtEscondido_Post,txtPasos_Post,txtMateriales_Post;
    ImageView imgV_Post;
    ImageSlider imageSlider;
    float x1,x2,y1,y2;
    private StorageReference reference3;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    private DatabaseReference childREFERENCE=reference.child("Articulos/").child("enlace");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        String ID = getIntent().getStringExtra("Identificador");
        String AUTOR = getIntent().getStringExtra("Autor");
        String PASOS = getIntent().getStringExtra("Pasos");
        String MATERIALES = getIntent().getStringExtra("Materiales");
        String TITULO = getIntent().getStringExtra("Titulo");
        String ENLACE = getIntent().getStringExtra("Enlace");
        String ENLACE2 = getIntent().getStringExtra("Enlace2");
        String ENLACE3 = getIntent().getStringExtra("Enlace3");


        //Relacion de los atributos y objetos xml
        btnVolver_Post = findViewById(R.id.btnVolver_Post);
        txtTitulo_Post = findViewById(R.id.txtTitulo_Post);
        txtEscondido_Post = findViewById(R.id.txtEscondido_Post);
        txtPasos_Post = findViewById(R.id.txtPasos_Post);
        txtMateriales_Post = findViewById(R.id.txtMateriales_Post);
        imageSlider = findViewById(R.id.slider);

        String referencia = childREFERENCE.toString().trim();
        txtEscondido_Post.setText(referencia);
        txtTitulo_Post.setText(TITULO);
        txtMateriales_Post.setText(MATERIALES);
        txtPasos_Post.setText(PASOS);

        //Evento para el boton regresar a front
        btnVolver_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Post.this,front_activity.class);
                startActivity(intent);
            }
        });

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(ENLACE.toString(),ScaleTypes.FIT));
        slideModels.add(new SlideModel(ENLACE2.toString(),ScaleTypes.FIT));
        slideModels.add(new SlideModel(ENLACE3.toString(),ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

    }


    //Evito que pueda volver con los botones del sistema
    @Override
    public void onBackPressed(){

    }



    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
            }else if(x1 > x2){
                Intent i = new Intent(Post.this, postVideo.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }

}