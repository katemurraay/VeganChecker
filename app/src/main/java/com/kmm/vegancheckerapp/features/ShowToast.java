package com.kmm.vegancheckerapp.features;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kmm.vegancheckerapp.R;


public class ShowToast {


        public  Toast makeImageToast( Context context, int imageResId, int stringId, int length) {
            /* Code below is based on:
            StakeOverflow Answer to Question: "Custom toast on Android: a simple example",
            Dipak Keshariya,
            https://stackoverflow.com/a/11288522
             */



            Toast toast = new Toast(context);
            LayoutInflater inflater = LayoutInflater.from(context);

            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.custom_toast, null);
            LinearLayout linearLayout = new LinearLayout(context);

            view.setBackground(getDrawableWithRadius(imageResId));


            TextView text =  view.findViewById(R.id.tvToast);
            text.setText(stringId);


            ImageView imageView = view.findViewById(R.id.ivToast);
            imageView.setImageResource(imageResId);



            toast.setGravity(Gravity.TOP, 0, 0);
            toast.setDuration(length);
            linearLayout.removeAllViews();

            linearLayout.addView(view);

            toast.setView(linearLayout);
            toast.show();
            return toast;
        } //END

   // https://stackoverflow.com/a/52648275
    private Drawable getDrawableWithRadius(int imageID) {

        GradientDrawable gradientDrawable   =   new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{40, 40, 30, 30, 30, 30, 40, 40});
        if( imageID== R.drawable.ic_check) gradientDrawable.setColor(Color.parseColor("#33cc33"));
        else if( imageID== R.drawable.ic_help) gradientDrawable.setColor(Color.parseColor("#666666"));
        else gradientDrawable.setColor(Color.parseColor("#ff0000"));

        return gradientDrawable;
    }
}
