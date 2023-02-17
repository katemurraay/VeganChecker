package com.kmm.vegancheckerapp.features.Scanning;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.NetworkReceiver;
import com.kmm.vegancheckerapp.features.NetworkService;
import com.kmm.vegancheckerapp.features.Searching.SearchFragment;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.features.Upload.UserFragment;
import com.kmm.vegancheckerapp.utils.IConstants;


public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    String strResult=" ";
    NetworkReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myReceiver = new NetworkReceiver();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SearchFragment()).commit();
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_search);






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
                    break;

                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_user:
                    selectedFragment = new UserFragment();

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
    IntentIntegrator integrator = new IntentIntegrator(NavigationActivity.this);
    integrator.setCaptureActivity(CameraActivity.class);
    integrator.setOrientationLocked(true);
    integrator.setBeepEnabled(false);
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


                strResult = result.getContents();
                if (!strResult.equals("")) {
                   try{
                       Intent intent = new Intent(NavigationActivity.this, ResultActivity.class);
                       intent.putExtra("LOADERID", IConstants.RESULTLOADERID);
                       intent.putExtra("RESULT", strResult);
                       startActivity(intent);

                    } catch(Exception e){
                        ShowToast activity = new ShowToast();
                      activity.makeImageToast(NavigationActivity.this, R.drawable.ic_error, R.string.no_results, Toast.LENGTH_LONG);
              }}


                   else {
                    ShowToast activity = new ShowToast();
               activity.makeImageToast(NavigationActivity.this, R.drawable.ic_error, R.string.no_results, Toast.LENGTH_LONG);


                }
            }}
             else {
                super.onActivityResult(requestCode, resultCode, data);
            }
    } //END






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










