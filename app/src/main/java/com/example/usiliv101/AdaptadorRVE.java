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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdaptadorRVE extends RecyclerView.Adapter<AdaptadorRVE.MyViewHolder> implements View.OnClickListener,Interfaz{
    //Variable para contener todo lo que tenga en interfaz
    private final Interfaz interfaz;
    Context context;
    ArrayList<trabajador> list;
    ArrayList<trabajador> list2;


    private View.OnClickListener listener;


    public AdaptadorRVE(Context context, ArrayList<trabajador> list,Interfaz interfaz) {
        this.context = context;
        this.list = list;
        //Creo el this para tomar los datos de la interfaz
        this.interfaz=interfaz;
        list2  =new ArrayList<>();
        list2.addAll(list);
    }


    @NonNull
    @Override
    public AdaptadorRVE.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  =  LayoutInflater.from(context).inflate(R.layout.iteme,parent,false);
        v.setOnClickListener(this);
        return new AdaptadorRVE.MyViewHolder(v,interfaz);

    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRVE.MyViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
        trabajador tra = list.get(position);
        holder.txtNombre_enRV.setText("Nombre: "+tra.getNombre());
        holder.txtEleccion_enRV.setText("\nProfesión: "+tra.getEleccion());
        holder.txtEmail_enRV.setText("\nCorreo: "+tra.getEmail());
        Glide.with(context).load(list.get(position).getFoto()).apply(requestOptions).into(holder.imgVE_enRV);
    }

    /*
    @Override
    public void onBindViewHolder(@NonNull AdaptadorRV.MyViewHolder holder, int position) {

        //apoyoPost pollo = new apoyoPost();
        Articulos art = list.get(position);
        holder.txtTitulo_enRV.setText(art.getTitulo());
        holder.txtID_enRV.setText(art.getId());
        holder.txtAutor_enRV.setText(art.getAutor());
        //holder.txtMateriales_enRV.setText(art.getMateriales());
        //holder.txtPasos_enRV.setText(art.getPasos());
        //pollo.setTitulo(art.autor);

        Glide.with(context).load(list.get(position).getEnlace()).into(holder.imgV_enRV);

    }*/


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

        TextView  txtNombre_enRV,txtEleccion_enRV,txtEmail_enRV;
        ImageView imgVE_enRV;

        //Agrego el atributo de interfaz
        public MyViewHolder(@NonNull View itemeView,Interfaz interfaz) {
            super(itemeView);

            txtNombre_enRV = itemeView.findViewById(R.id.txtNombre_enRV);
            txtEleccion_enRV = itemeView.findViewById(R.id.txtEleccion_enRV);
            txtEmail_enRV = itemeView.findViewById(R.id.txtEmail_enRV);
            imgVE_enRV = itemeView.findViewById(R.id.imgVE_enRV);
            /*
            txtTitulo_enRV = itemView.findViewById(R.id.txtTitulo_enRV);
            txtAutor_enRV = itemView.findViewById(R.id.txtAutor_enRV);
            txtID_enRV = itemView.findViewById(R.id.txtID_enRV);
            imgV_enRV = itemView.findViewById(R.id.imgV_enRV);*/
            //txtMateriales_enRV = itemView.findViewById(R.id.txtMateriales_enRV);
            //txtPasos_enRV = itemView.findViewById(R.id.txtPasos_enRV);

            itemeView.setOnClickListener(new View.OnClickListener() {
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
