package com.kmm.vegancheckerapp.features.Upload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.model.Product;

import java.util.ArrayList;

public class UserUploadsRecyclerViewAdapter extends RecyclerView.Adapter<CustomUploadViewHolder> implements View.OnClickListener {
    /*
       Code Below is based on:
       Tutorial 12/11/2020: SQLite 2 Table App,
       Created By Michael Gleeson
       Copyright (c) 2020 | gleeson.io
       * */
    private final Context context;
    private final ArrayList<Product> products;

    public UserUploadsRecyclerViewAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public CustomUploadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_uploads, parent, false);

        return new CustomUploadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomUploadViewHolder holder, int position) {
        Product product = products.get(position);
        String name = product.getProductBrand() + " " + product.getProductName();
        holder.tvName.setText(name);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return products.size();
    } //END


    @Override
    public void onClick(View v) {
        ShowToast toast = new ShowToast();

        toast.makeImageToast(context, R.drawable.ic_help, R.string.delete_help, Toast.LENGTH_LONG);
    }
}
