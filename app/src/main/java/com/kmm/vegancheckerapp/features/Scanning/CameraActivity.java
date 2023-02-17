package com.kmm.vegancheckerapp.features.Scanning;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.utils.IConstants;


public class CameraActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {

    private CaptureManager captureManager;
    private DecoratedBarcodeView barcodeView;
    ImageButton btnFlash;
    boolean flashOn;
    Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        barcodeView = findViewById(R.id.zxing_barcode_scanner);
        barcodeView.setTorchListener(this);
        btnSearch = findViewById(R.id.btnSearch);
        btnFlash =  findViewById(R.id.btnFlash);
        flashOn = false;


        btnFlash.setColorFilter(getResources().getColor(R.color.colour_white));
        captureManager = new CaptureManager(this, barcodeView);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();

        if (!hasFlash()) {
            btnFlash.setVisibility(View.GONE);
        }

        btnFlash.setOnClickListener(v -> {
            if (!flashOn) {
                barcodeView.setTorchOn();

            } else {
                barcodeView.setTorchOff();
            }
        });

        btnSearch.setOnClickListener(v -> {
            Dialog dialog = new Dialog(CameraActivity.this);
            dialog.setContentView(R.layout.custom_search_dialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
             Button btnBack = dialog.findViewById(R.id.btnBack);
            Button btnContinue = dialog.findViewById(R.id.btnContinue);
            EditText etInput = dialog.findViewById(R.id.etSearchName);

            btnBack.setOnClickListener((view)-> dialog.dismiss());
            btnContinue.setOnClickListener((view)->{
                String name = etInput.getText().toString();

                if(!name.isEmpty()){
                      try{
                          Intent intent = new Intent(this, ResultActivity.class);
                          intent.putExtra("LOADERID", IConstants.NAMELOADERID);
                          intent.putExtra("RESULT" , name);
                          startActivity(intent);
                          dialog.dismiss();
                    }
                      catch(Exception e){
                         ShowToast activity = new ShowToast();
                            activity.makeImageToast(this, R.drawable.ic_error, R.string.no_results, Toast.LENGTH_LONG);

                           }} else {
                    ShowToast activity = new ShowToast();
                    activity.makeImageToast(this, R.drawable.ic_error, R.string.no_results, Toast.LENGTH_LONG);


                }});

            dialog.show();
        });



    }
    /*
    Code below is based on:
    StackOverflow Answer to Question:
    "While using the IntentIntegrator from the ZXing library, can I add a flash button to the barcode scanner via the Intent?",
    Ashin Mandal,
    https://stackoverflow.com/a/36122230
     */
    @Override
    public void onTorchOn() {
        btnFlash.setImageResource(R.drawable.ic_flash_on);
        flashOn = true;
    }

    @Override
    public void onTorchOff() {
        btnFlash.setImageResource(R.drawable.ic_flash_off);
        flashOn = false;

    }
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    } //END

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }




}
