package com.example.usiliv101;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class postInvitado extends AppCompatActivity {

    Button btnVolver_Post,btnParar_Post;
    TextView txtTitulo_Post,txtEscondido_Post,txtPasos_Post,txtMateriales_Post,txtAutor_Post;
    ImageView imgV_Post,img1_Post,img2_Post,img3_Post,imgBack_Post,imgFoto_Post,txtMensaje4_Post,imgPdf;
    ImageSlider imageSlider;
    VideoView video_Post;
    ScrollView scrollView;
    private FirebaseUser usuario;
    private String idUsuario;
    private StorageReference reference3;

    int videoState=0;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    private DatabaseReference childREFERENCE=reference.child("Articulos/").child("enlace");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_invitado);


        String ID = getIntent().getStringExtra("Identificador");
        String AUTOR = getIntent().getStringExtra("Autor");
        String PASOS = getIntent().getStringExtra("Pasos");
        String MATERIALES = getIntent().getStringExtra("Materiales");
        String TITULO = getIntent().getStringExtra("Titulo");
        String ENLACE = getIntent().getStringExtra("Enlace");
        String ENLACE2 = getIntent().getStringExtra("Enlace2");
        String ENLACE3 = getIntent().getStringExtra("Enlace3");
        String ENLACEVideo = getIntent().getStringExtra("EnlaceV");
        String ENLACEP = getIntent().getStringExtra("EnlaceP");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Relacion de los atributos y objetos xml
        imgBack_Post = findViewById(R.id.imgBack_Post);
        txtTitulo_Post = findViewById(R.id.txtTitulo_Post);
        txtEscondido_Post = findViewById(R.id.txtEscondido_Post);
        txtPasos_Post = findViewById(R.id.txtPasos_Post);
        txtMateriales_Post = findViewById(R.id.txtMateriales_Post);
        imageSlider = findViewById(R.id.slider);
        video_Post = findViewById(R.id.video_Post);
        txtMensaje4_Post = findViewById(R.id.txtMensaje4_Post);
        txtAutor_Post = findViewById(R.id.txtAutor_Post);
        img1_Post = findViewById(R.id.img1_Post);
        img2_Post = findViewById(R.id.img2_Post);
        img3_Post = findViewById(R.id.img3_Post);
        imgPdf = findViewById(R.id.imgPdf);
        scrollView = findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0,0);

        String referencia = childREFERENCE.toString().trim();
        txtEscondido_Post.setText(referencia);
        txtTitulo_Post.setText(TITULO);
        txtMateriales_Post.setText(MATERIALES);
        txtPasos_Post.setText(PASOS);
        txtAutor_Post.setText(AUTOR);


        //Evento para el boton regresar a front
        imgBack_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(postInvitado.this,modoInvitado.class);
                startActivity(intent);
            }
        });

        txtMensaje4_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiperVinculo(ENLACEP);
            }
        });

        Glide.with(getApplicationContext())
                .load(ENLACE)
                .into(img1_Post);

        Glide.with(getApplicationContext())
                .load(ENLACE2)
                .into(img2_Post);

        Glide.with(getApplicationContext())
                .load(ENLACE3)
                .into(img3_Post);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(ENLACE.toString(), ScaleTypes.FIT));
        slideModels.add(new SlideModel(ENLACE2.toString(),ScaleTypes.FIT));
        slideModels.add(new SlideModel(ENLACE3.toString(),ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);
        video_Post.setVideoURI(Uri.parse(ENLACEVideo.toString()));
        MediaController mediaController = new MediaController(this);
        video_Post.setMediaController(mediaController);
        mediaController.setAnchorView(video_Post);

        //Generar PDF
        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions();
        }

        imgPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarPdf(TITULO,PASOS,MATERIALES,AUTOR,ID,ENLACE,ENLACE2,ENLACE3);
                Intent intent = new Intent(postInvitado.this,pdfVista.class);
                intent.putExtra("Ruta",TITULO);
                startActivity(intent);
            }
        });

    }
    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 200) {
            if(grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }


    //Para generar el PDF
    public void generarPdf(String Titulo,String Descripcion,String Materiales,String Autor,String Id,String Enlace,String Enlace2, String Enlace3) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint paint1 = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint descripcion = new TextPaint();
        TextPaint materiales = new TextPaint();
        TextPaint autor = new TextPaint();
        TextPaint id = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
        PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

        Canvas canvas = pagina1.getCanvas();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
        canvas.drawBitmap(bitmapEscala, 368, 0, paint);

        bitmap = getBitmapFromURL(Enlace);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        canvas.drawBitmap(bitmapEscala, 50, 600, paint);

        bitmap = getBitmapFromURL(Enlace2);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        canvas.drawBitmap(bitmapEscala, 300, 600, paint);

        bitmap = getBitmapFromURL(Enlace3);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        canvas.drawBitmap(bitmapEscala, 550, 600, paint);


        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(35);
        canvas.drawText(Titulo, 10, 178, titulo);

        autor.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titulo.setTextSize(20);
        canvas.drawText(Autor, 500, 210, titulo);


        materiales.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        materiales.setTextSize(18);

        String[] arrMats = Materiales.split("\n");
        int u = 250;
        for(int i = 0 ; i < arrMats.length ; i++) {
            canvas.drawText(arrMats[i], 10, u, materiales);
            u += 15;
        }

        descripcion.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        descripcion.setTextSize(18);

        String[] arrDescripcion = Descripcion.split("\n");
        int y = 450;

        for(int i = 0 ; i < arrDescripcion.length ; i++) {
            canvas.drawText(arrDescripcion[i], 10, y, descripcion);
            y += 15;
        }


        pdfDocument.finishPage(pagina1);


        //File file = new File(Environment.getExternalStorageDirectory(), "Archivo"+Titulo+".pdf");
        File ruta = new File(getExternalFilesDir(null).toString()+"/"+Titulo+".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(ruta));
            Toast.makeText(this, "Guardado en: "+ruta, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pdfDocument.close();
    }



    public void hiperVinculo(String link){
        Uri uri = Uri.parse(link);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    //Evito que pueda volver con los botones del sistema
    @Override
    public void onBackPressed(){

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}