/*Clase comentada, no necesaria por el momento
package com.example.usiliv101;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adaptador extends RecyclerView.Adapter<adaptador.adaptadorholder>{

    Context context;
    ArrayList<articulo> list;

    public adaptador(Context context, ArrayList<articulo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public adaptadorholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new adaptadorholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorholder holder, int position) {
        articulo articulo = list.get(position);
        holder.titulo.setText(articulo.getTitulo());
        holder.descripcion.setText(articulo.getDescripcion());
        holder.id.setText(articulo.getId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class adaptadorholder extends RecyclerView.ViewHolder{

        TextView titulo, descripcion, id;

        public adaptadorholder(@NonNull View itemView) {
            super(itemView);

            titulo=itemView.findViewById(R.id.txtTitulo_Card);
            descripcion=itemView.findViewById(R.id.txtDescripción_Card);
            id=itemView.findViewById(R.id.txtID_Card);
        }
    }
}
*/