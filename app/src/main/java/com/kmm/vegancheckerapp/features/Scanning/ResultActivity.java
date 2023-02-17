package com.kmm.vegancheckerapp.features.Scanning;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.airbnb.lottie.LottieAnimationView;
import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.Alternatives.AlternativeListActivity;
import com.kmm.vegancheckerapp.features.NetworkReceiver;
import com.kmm.vegancheckerapp.features.NetworkService;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.utils.IConstants;

public class ResultActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Product> {

    TextView tvName, tvProductionVegan,  tvIngredientsVegan, tvResult, tvValidatedBy, tvContains;
    ImageView imgResult;
    Button btnAlternatives;
    String ingredients, name, contain, validatedBy, production, type;
RelativeLayout rlLayout;
String category;
String result;
Animation zoomAnim;
    NetworkReceiver myReceiver;
LottieAnimationView lottieAnimationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new NetworkReceiver();

        setContentView(R.layout.activity_result);


        tvName =  findViewById(R.id.tvName);
        tvProductionVegan = findViewById(R.id.tvProduction);
        tvIngredientsVegan = findViewById(R.id.tvIngredients);
        tvResult = findViewById(R.id.tvResult);
        tvValidatedBy = findViewById(R.id.tvValidatedBy);
        tvContains = findViewById(R.id.tvContains);
        imgResult =  findViewById(R.id.imgResult);
        rlLayout = findViewById(R.id.layoutResult);
        btnAlternatives =  findViewById(R.id.btnViewAlternatives);
        lottieAnimationView = findViewById(R.id.lottieLoading);
        zoomAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_out);


        Intent i = getIntent();
        int loaderId = i.getExtras().getInt("LOADERID");
        result= i.getExtras().getString("RESULT");

        if(checkInternet(this)) {
            category ="";
            LoaderManager.getInstance(this).initLoader(loaderId, null, this);
        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
            finish();
        }
        btnAlternatives.setOnClickListener((v)-> {

            if (!type.isEmpty()) {

                Intent intent = new Intent(ResultActivity.this, AlternativeListActivity.class);
                intent.putExtra("PRODUCT_TYPE", type);
                intent.putExtra("Lottie", category);
                intent.putExtra("Activity", "Result");
                finish();
                startActivity(intent);

            } else {
                ShowToast activity = new ShowToast();
                activity.makeImageToast(ResultActivity.this, R.drawable.ic_error, R.string.no_alterntives, Toast.LENGTH_LONG);


            }
        });
    }






    @NonNull
    @Override
    public Loader<Product> onCreateLoader(int id, @Nullable Bundle args) {

        if(id == IConstants.NAMELOADERID){
            return new com.kmm.vegancheckerapp.features.LoaderManager.NameLoader(this, result);
        } else{
            return new com.kmm.vegancheckerapp.features.LoaderManager.BarcodeLoader(this, result);
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Product> loader, Product product) {
        lottieAnimationView.setVisibility(View.GONE);

       if(product != null){
        rlLayout.setVisibility(View.VISIBLE);

        setProductResult(product);

       } else{
           ShowToast toast = new ShowToast();
           toast.makeImageToast(this, R.drawable.ic_error, R.string.no_results, Toast.LENGTH_LONG);
           finish();
       }

}

    public void setProductResult(Product product) {
            type = product.getProductType();
            String contains ="";
            String strProduction = product.getProductProduction();
            if(product.getProductIngredients()!= null){
                String[] ingredients1=product.getProductIngredients();
                contains = ingredients1[0];
            }

            name = product.getProductBrand() +" " +product.getProductName();

        switch (strProduction) {
            case "Vegan":
                production = "Production: " + product.getProductProduction() + "*";
                ingredients = "Ingredients: Vegan*";
                if (product.getUploadedBy().getUserID() != 0) {
                    validatedBy = "*Uploaded By: @" + product.getUploadedBy().getUserName();
                } else {
                    validatedBy = "*Validated By: " + product.getValidatedBy();
                }
                imgResult.setImageResource(R.drawable.ic_vegan);
                tvResult.setText(R.string.vegan_result);
                tvResult.setTextColor(getResources().getColor(R.color.vegan_colour));
                btnAlternatives.setVisibility(View.INVISIBLE);
                tvValidatedBy.setText(validatedBy);


                break;
            case "No":
                imgResult.setImageResource(R.drawable.ic_remove);
                contain = "Contains: " + contains.toUpperCase();
                ingredients = "Ingredients: Not Vegan";
                production = "Production: Not Vegan";
                tvResult.setText(R.string.not_vegan_result);
                tvResult.setTextColor(getResources().getColor(R.color.not_vegan_colour));
                tvContains.setTextColor(getResources().getColor(R.color.not_vegan_colour));
                tvContains.setText(contain.toUpperCase());


                break;
            case "Likely Vegan":
                ingredients = "Ingredients: " + contains;
                production = "Production: " + product.getProductProduction();
                if (product.getUploadedBy().getUserID() != 0) {
                    validatedBy = "*Uploaded By: @" + product.getUploadedBy().getUserName();
                } else {
                    validatedBy = "";
                }
                imgResult.setImageResource(R.drawable.ic_question);
                tvResult.setTextColor(getResources().getColor(R.color.likely_vegan_colour));
                tvResult.setText(R.string.likely_vegan_result);


                break;
            default:
                imgResult.setImageResource(R.drawable.ic_remove);
                contain = "Production: " + product.getProductProduction();
                tvResult.setText(R.string.not_vegan_result);
                tvResult.setTextColor(getResources().getColor(R.color.not_vegan_colour));
                tvContains.setTextColor(getResources().getColor(R.color.not_vegan_colour));
                tvContains.setText(contain.toUpperCase());
                ingredients = "Ingredients: Vegan";
                production = "Production: Not Vegan";
                break;
        }
        imgResult.setAnimation(zoomAnim);
        tvValidatedBy.setText(validatedBy);
        tvName.setText(name);
        category= product.getCategory();
        tvIngredientsVegan.setText(ingredients);
        tvProductionVegan.setText(production);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Product> loader) {

    }



    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, intentFilter);

    }


    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    boolean checkInternet(Context context) {
        NetworkService serviceManager = new NetworkService(context);
        return serviceManager.isNetworkAvailable();
    }

}