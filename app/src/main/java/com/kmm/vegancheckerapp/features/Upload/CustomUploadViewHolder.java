package com.kmm.vegancheckerapp.features.Upload;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kmm.vegancheckerapp.R;

public class CustomUploadViewHolder extends RecyclerView.ViewHolder {

    TextView tvName;


    public CustomUploadViewHolder(View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvProductName);


    }
}