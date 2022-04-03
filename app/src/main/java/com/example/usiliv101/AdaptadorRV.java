package com.example.usiliv101;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorRV extends RecyclerView.Adapter<AdaptadorRV.MyViewHolder> {

    Context context;
    ArrayList<Articulos> list;


    public AdaptadorRV(Context context, ArrayList<Articulos> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  =  LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Articulos art = list.get(position);
        holder.txtTitulo_enRV.setText(art.getTitulo());
        holder.txtID_enRV.setText(art.getId());
        holder.txtAutor_enRV.setText(art.getAutor());
        //holder.txtMateriales_enRV.setText(art.getMateriales());
        //holder.txtPasos_enRV.setText(art.getPasos());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitulo_enRV,txtAutor_enRV,txtID_enRV,txtMateriales_enRV,txtPasos_enRV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo_enRV = itemView.findViewById(R.id.txtTitulo_enRV);
            txtAutor_enRV = itemView.findViewById(R.id.txtAutor_enRV);
            txtID_enRV = itemView.findViewById(R.id.txtID_enRV);
            //txtMateriales_enRV = itemView.findViewById(R.id.txtMateriales_enRV);
            //txtPasos_enRV = itemView.findViewById(R.id.txtPasos_enRV);

        }
    }

}
