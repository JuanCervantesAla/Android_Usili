package com.example.usiliv101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class pdfVista extends AppCompatActivity {

    PDFView PDFView;
    ImageView imgVolver_P;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_vista);
        PDFView = findViewById(R.id.PDFView);
        imgVolver_P = findViewById(R.id.imgVolver_P);
        String TITULO = getIntent().getStringExtra("Ruta");
        String path = getExternalFilesDir(null).toString()+"/"+TITULO+".pdf";
        File file = new File(path);

        PDFView.fromFile(file)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(false)
                .defaultPage(0)
                .password(null)
                .scrollHandle(null)
                .load();

        imgVolver_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pdfVista.this,front_activity.class);
                startActivity(intent);
            }
        });
    }
}