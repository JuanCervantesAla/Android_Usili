package com.example.usiliv101;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.util.ArrayList;

public class AdaptadorRV extends RecyclerView.Adapter<AdaptadorRV.MyViewHolder> implements View.OnClickListener,Interfaz{

    //Variable para contener todo lo que tenga en interfaz
    private final Interfaz interfaz;
    Context context;
    ArrayList<Articulos> list;
    ArrayList<Articulos> list2;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    FirebaseUser userUID;
    private DatabaseReference databaseRef;


    private View.OnClickListener listener;


    public AdaptadorRV(Context context, ArrayList<Articulos> list,Interfaz interfaz) {
        this.context = context;
        this.list = list;
        //Creo el this para tomar los datos de la interfaz
        this.interfaz=interfaz;
        list2  =new ArrayList<>();
        list2.addAll(list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  =  LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        v.setOnClickListener(this);
        return new MyViewHolder(v,interfaz);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //apoyoPost pollo = new apoyoPost();
        int newPosition = holder.getAdapterPosition();
        Articulos art = list.get(position);
        Articulos art2 = list.get(position);
        String Key = art.getKey();
        String Id = art.getId();
        holder.txtTitulo_enRV.setText(art.getTitulo());
        holder.txtID_enRV.setText(art.getId());
        holder.txtAutor_enRV.setText(art.getAutor());
        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                userUID = FirebaseAuth.getInstance().getCurrentUser();
                //String comparador = userUID;

                if (userUID == null) {
                    // No user is signed in
                    Toast.makeText(context.getApplicationContext(), "NO REGISTRADO", Toast.LENGTH_SHORT).show();
                } else {
                    //String usuarioBase = (String) userUID;
                    if(Uid.equals(Key)){
                        list.remove(newPosition);
                        notifyItemRemoved(newPosition);
                        notifyItemRangeChanged(newPosition, list.size());

                        Query query= FirebaseDatabase.getInstance().getReference("Articulos")
                                .orderByChild("id")
                                .equalTo(Id);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChildren()) {
                                    DataSnapshot firstChild = snapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(context.getApplicationContext(), "Usted no subió este articulo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //holder.txtMateriales_enRV.setText(art.getMateriales());
        //holder.txtPasos_enRV.setText(art.getPasos());
        //pollo.setTitulo(art.autor);

        Glide.with(context).load(list.get(position).getEnlace()).into(holder.imgV_enRV);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    @Override
    public void clickEnItem(int posicion) {

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitulo_enRV,txtAutor_enRV,txtID_enRV,txtMateriales_enRV,txtPasos_enRV;
        ImageView imgV_enRV,btnBorrar;


        //Agrego el atributo de interfaz
        public MyViewHolder(@NonNull View itemView,Interfaz interfaz) {
            super(itemView);

            txtTitulo_enRV = itemView.findViewById(R.id.txtTitulo_enRV);
            txtAutor_enRV = itemView.findViewById(R.id.txtAutor_enRV);
            txtID_enRV = itemView.findViewById(R.id.txtID_enRV);
            imgV_enRV = itemView.findViewById(R.id.imgV_enRV);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);
            //txtMateriales_enRV = itemView.findViewById(R.id.txtMateriales_enRV);
            //txtPasos_enRV = itemView.findViewById(R.id.txtPasos_enRV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(interfaz !=null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            interfaz.clickEnItem(pos);
                        }
                    }
                }
            });
        }
    }

}