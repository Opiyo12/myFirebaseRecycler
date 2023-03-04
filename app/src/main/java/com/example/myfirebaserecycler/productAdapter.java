package com.example.myfirebaserecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.myViewHolder> {
    Context context;
    ArrayList<productModal> list;

    public productAdapter(Context context, List<productModal> list) {
        this.context = context;
        this.list = (ArrayList<productModal>) list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.productitem, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
      productModal model= list.get(position);
      holder.name.setText(model.getName());
      holder.description.setText(model.getName());
      holder.price.setText(model.getPrice());
      String imageUri;
      imageUri=model.getProductImage();
      Picasso.get().load(imageUri).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView name, description, price;
        ImageView image;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            description=itemView.findViewById(R.id.description);
            price=itemView.findViewById(R.id.price);
            image=itemView.findViewById(R.id.cardviewImg);

        }
    }
}
