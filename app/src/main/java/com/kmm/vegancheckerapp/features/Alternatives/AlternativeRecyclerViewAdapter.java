package com.kmm.vegancheckerapp.features.Alternatives;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.model.Product;

import java.util.ArrayList;

public class AlternativeRecyclerViewAdapter extends RecyclerView.Adapter<CustomAlternativeViewHolder> {
    /*
     Code Below is based on:
     Tutorial 12/11/2020: SQLite 2 Table App,
     Created By Michael Gleeson
     Copyright (c) 2020 | gleeson.io
     * */

    private final Context context;
    private final ArrayList<Product> products;


    public AlternativeRecyclerViewAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;

    }

    @NonNull
    @Override
    public CustomAlternativeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_alternatives, parent, false);
        return new CustomAlternativeViewHolder(view);
    }



    @Override
    public void onBindViewHolder( CustomAlternativeViewHolder holder, int position) {
       
        Product product = products.get(position);
        String name = product.getProductBrand() + " " + product.getProductName();
        holder.tvName.setText(name);
        String strAvailableFrom = "Available From: " + product.getAvailableFrom().toUpperCase();
        holder.tvAvailableFrom.setText(strAvailableFrom);
        String category = product.getCategory();
        if(!category.equals("Food")){
            holder.imageView.setImageResource(R.drawable.ic_cheers);


         } else{
            holder.imageView.setImageResource(R.drawable.ic_cutlery);
        }


        if(product.getUploadedBy().getUserID() != 0) {
            String strUploadedBy = "Uploaded By: @" + product.getUploadedBy().getUserName();
            holder.tvUploadedBy.setText(strUploadedBy);
            holder.tvUploadedBy.setVisibility(View.VISIBLE);
        } else{
            holder.tvUploadedBy.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

   //END
}
