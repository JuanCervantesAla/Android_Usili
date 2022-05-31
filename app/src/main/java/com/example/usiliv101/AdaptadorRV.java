package com.example.usiliv101;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdaptadorRV extends RecyclerView.Adapter<AdaptadorRV.MyViewHolder> implements View.OnClickListener,Interfaz{

    //Variable para contener todo lo que tenga en interfaz
    private final Interfaz interfaz;
    Context context;
    ArrayList<Articulos> list;
    ArrayList<Articulos> list2;


    private View.OnClickListener listener;


    public AdaptadorRV(Context context, ArrayList<Articulos> list,Interfaz interfaz) {
        this.context = context;
        this.list = list;
        //Creo el this para tomar los datos de la interfaz
        this.interfaz=interfaz;
        list2  =new ArrayList<>();
        list2.addAll(list);
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
        Articulos art = list.get(position);
        holder.txtTitulo_enRV.setText(art.getTitulo());
        holder.txtID_enRV.setText(art.getId());
        holder.txtAutor_enRV.setText(art.getAutor());
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
        ImageView imgV_enRV;

        //Agrego el atributo de interfaz
        public MyViewHolder(@NonNull View itemView,Interfaz interfaz) {
            super(itemView);

            txtTitulo_enRV = itemView.findViewById(R.id.txtTitulo_enRV);
            txtAutor_enRV = itemView.findViewById(R.id.txtAutor_enRV);
            txtID_enRV = itemView.findViewById(R.id.txtID_enRV);
            imgV_enRV = itemView.findViewById(R.id.imgV_enRV);
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
