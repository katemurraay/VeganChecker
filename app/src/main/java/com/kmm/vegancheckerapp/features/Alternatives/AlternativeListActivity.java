package com.kmm.vegancheckerapp.features.Alternatives;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.NetworkReceiver;
import com.kmm.vegancheckerapp.features.NetworkService;
import com.kmm.vegancheckerapp.features.Scanning.CameraActivity;
import com.kmm.vegancheckerapp.features.Scanning.NavigationActivity;
import com.kmm.vegancheckerapp.features.Scanning.ResultActivity;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.features.Upload.UserFragment;
import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.util.ArrayList;

import static com.kmm.vegancheckerapp.utils.IConstants.USER_SUBSCRIBED;

public class AlternativeListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Product>> {


    private RecyclerView recyclerView;
    Toolbar tbAlternatives;
    public String type = null;
    LottieAnimationView lottieAnimationView, lottieAlcohol;
    NetworkReceiver myReceiver;
    AlternativeRecyclerViewAdapter Adapter;
    BottomNavigationView bottomNav;
LinearLayout layoutAlternatives;
    String strLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myReceiver = new NetworkReceiver();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_list_alternatives);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_search);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        layoutAlternatives = findViewById(R.id.layoutAlternatives);

        recyclerView =  findViewById(R.id.rcAlternatives);
        recyclerView.setAdapter(Adapter);
        tbAlternatives = findViewById(R.id.tbAlternatives);
        lottieAnimationView = findViewById(R.id.lottie);
        lottieAlcohol = findViewById(R.id.lottieAlcohol);


        String strActivity = getIntent().getExtras().getString("Activity");
         strLottie = getIntent().getExtras().getString("Lottie");
        if(USER_SUBSCRIBED.equals("0")){

            SharedPreferences prefs= getPreferences(Context.MODE_PRIVATE);
            int c;
       switch(strActivity){
           case("Search"):
               {
/* Code below is based on:
StakeOverflow Answer to Question: "Counting how many times my Android app has been opened"
 Mourice,
 https://stackoverflow.com/a/20964721
 */

               c = prefs.getInt("numRun",0);
               c++;
               Log.d("PREFERENCES", String.valueOf(c));
               prefs.edit().putInt("numRun",c).apply();
               if(c >= 6){
                   ShowToast activity = new ShowToast();
                   activity.makeImageToast(this, R.drawable.ic_error, R.string.free_result_limit, Toast.LENGTH_LONG);
                   finish();
               }


       }  break;
           case("Result"):
                    c = prefs.getInt("num",0);
                   c++;
                   Log.d("PREFERENCES", String.valueOf(c));
                   prefs.edit().putInt("num",c).apply(); //END
                   if(c >= 4){
                       ShowToast activity = new ShowToast();
                       activity.makeImageToast(this, R.drawable.ic_error, R.string.free_result_limit, Toast.LENGTH_LONG);
                       finish();
                   }


               break;
           }

        } //END


        type= getIntent().getExtras().getString("PRODUCT_TYPE");
        if(checkInternet(this)){
            LoaderManager.getInstance(this).initLoader(IConstants.ALTERNATIVELOADERID, null, this);
        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
            finish();
        }




    }


    @NonNull
    @Override
    public Loader<ArrayList<Product>> onCreateLoader(int id, final Bundle args) {
if(strLottie.equals("Alcohol")){
    lottieAlcohol.setVisibility(View.VISIBLE);
} else{
    lottieAnimationView.setVisibility(View.VISIBLE);
}

        tbAlternatives.setVisibility(View.GONE);
        return new com.kmm.vegancheckerapp.features.LoaderManager.AlternativeLoader(this, type);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Product>> loader, ArrayList<Product> data) {


        if(data.isEmpty()){
            ShowToast toast = new ShowToast();
            toast.makeImageToast(getApplicationContext(), R.drawable.ic_error, R.string.no_alterntives, Toast.LENGTH_LONG);
            finish();


        } else{

            Adapter = new AlternativeRecyclerViewAdapter(this, data);
            recyclerView.setAdapter(Adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            String productType = data.get(0).getProductType();
            String title = "Vegan " + productType+ " Options";



            tbAlternatives.setTitle(title);
            tbAlternatives.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
            lottieAlcohol.setVisibility(View.GONE);

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Product>> loader) {

    }


    /*
Code Below is based on:
Youtube Video: "BottomNavigationView with Fragments - Android Studio Tutorial",
Coding in Flow,
https://www.youtube.com/watch?v=tPV8xA7m-iw
*/
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment selectedFragment = null;
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.nav_scan:
                    startScanningActivity();
                   return true;
                case R.id.nav_search:
                    Intent intent = new Intent(AlternativeListActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.nav_user:
                    selectedFragment = new UserFragment();
                    break;

            }


            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

            return true;
        }}; //END


    /*
    Code below is based on:
    Youtube Video: "QR & Barcode scanner with Android Studio",
    E.A.Y team,
    https://www.youtube.com/watch?v=wfucGSKngq4
     */
    public void startScanningActivity() {
        if(checkInternet(this)) {
            IntentIntegrator integrator = new IntentIntegrator(AlternativeListActivity.this);
            integrator.setCaptureActivity(CameraActivity.class);
            integrator.setOrientationLocked(false);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
            integrator.initiateScan();
        } else{
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);

        if(result != null) {
            if (result.getContents() != null) {


                String strResult = result.getContents();
                if (!strResult.equals("")) {
                    try{
                        Intent intent = new Intent(this, ResultActivity.class);
                        intent.putExtra("LOADERID", IConstants.RESULTLOADERID);
                        intent.putExtra("RESULT", strResult);
                        startActivity(intent);

                    } catch(Exception e){
                        ShowToast activity = new ShowToast();
                        activity.makeImageToast(this, R.drawable.ic_error, R.string.no_results, Toast.LENGTH_LONG);
                    }}


                else {
                    ShowToast activity = new ShowToast();
                    activity.makeImageToast(this, R.drawable.ic_error, R.string.no_results, Toast.LENGTH_LONG );


                }
            }}
        else {
            super.onActivityResult(requestCode, resultCode, data);
        } //END
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

