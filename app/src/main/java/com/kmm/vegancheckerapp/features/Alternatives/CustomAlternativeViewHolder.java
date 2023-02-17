package com.kmm.vegancheckerapp.features.Alternatives;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kmm.vegancheckerapp.R;

public class CustomAlternativeViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,  tvAvailableFrom, tvUploadedBy;
        ImageView imageView;



        public CustomAlternativeViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPName);
            tvAvailableFrom = itemView.findViewById(R.id.tvAvailableFrom);
            tvUploadedBy = itemView.findViewById(R.id.tvUploadedBy);
            imageView = itemView.findViewById(R.id.ivImage);

}
}
