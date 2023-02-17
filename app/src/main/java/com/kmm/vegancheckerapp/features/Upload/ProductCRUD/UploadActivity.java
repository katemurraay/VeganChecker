package com.kmm.vegancheckerapp.features.Upload.ProductCRUD;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.model.User;
import com.kmm.vegancheckerapp.model.Vegan;
import com.kmm.vegancheckerapp.utils.IConstants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tiper.MaterialSpinner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class UploadActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Integer>{
    Button btnUploadIngredients, btnStep1, btnStep2, btnStep3, btnStep4;
    CheckBox cbOther, cbLidl, cbAldi, cbDunnes, cbTesco, cbHB, cbSupervalu, cbIceland;
    RadioButton rdoYes, rdoNo;
    EditText etBarcode, etResult, etBrand, etPName, etSpecify;
    TextView tvIngredients;
    ImageView ivPreview;
    MaterialSpinner spType, spCategory;
    Toolbar tbUpload;
    LottieAnimationView lottieAnimationView;
    ArrayList<String> barcodeList;
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> types;
    LinearLayout  layoutStep3, layoutStep1;
    ScrollView layoutStep4, layoutStep2;
    Dialog dialog;
    Translate translate;
    Product uploadedProduct;
    String strStep ="";

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;
    private static final int STORAGE_REQUEST_CODE = 400;

    String[] cameraPermission;
    String[] storagePermission;
    Uri image_uri;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        IConstants.USER_ID = 1;
        uploadedProduct = new Product();
        cameraPermission = new String [] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        super.onCreate(savedInstanceState);
        barcodeList = new ArrayList<>(IConstants.PRODUCT_BARCODES);
        setContentView(R.layout.activity_upload);
        // <editor-fold default state="collapsed" desc="UI Assignment">

        btnUploadIngredients = findViewById(R.id.btnUploadIngredients);
        etBarcode = findViewById(R.id.etBarcode);
        spCategory = findViewById(R.id.spCategory);
        etResult = findViewById(R.id.etResultIngredients);
        etBrand = findViewById(R.id.etBrand);
        etPName = findViewById(R.id.etName);

        lottieAnimationView = findViewById(R.id.lottieUploads);
        tbUpload = findViewById(R.id.tbUpload);
        spType =  findViewById(R.id.spType);
        ivPreview = findViewById(R.id.ivPreview);
        tvIngredients = findViewById(R.id.tvIngredients);
        btnStep1 = findViewById(R.id.btnStep1);
        btnStep2 = findViewById(R.id.btnStep2);
        btnStep3 = findViewById(R.id.btnStep3);
        layoutStep1 = findViewById(R.id.layoutStep1);
        layoutStep2 = findViewById(R.id.layoutStep2);
        layoutStep3 = findViewById(R.id.layoutStep3);
        layoutStep4 = findViewById(R.id.layoutStep4);
        btnStep4 = findViewById(R.id.btnStep4);
        cbDunnes = findViewById(R.id.cbDunnes);
        cbAldi = findViewById(R.id.cbAldi);
        cbLidl = findViewById(R.id.cbLidl);
        cbTesco = findViewById(R.id.cbTesco);
        cbHB = findViewById(R.id.cbHB);
        cbOther = findViewById(R.id.cbOther);
        cbSupervalu = findViewById(R.id.cbSupervalu);
        cbIceland = findViewById(R.id.cbIceland);
        rdoNo = findViewById(R.id.rdoNoVeganSign);
        rdoYes = findViewById(R.id.rdoYesVeganSign);
        etSpecify = findViewById(R.id.etSpecify);

//</editor-fold>
        strStep = "STEP 1";
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Alcohol");
        categories.add("Food");
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        tbUpload.setTitle(strStep);



        btnUploadIngredients.setOnClickListener((view)-> showImageImportDialog());
        cbOther.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(etSpecify.getVisibility() == View.VISIBLE){
                    etSpecify.setVisibility(View.GONE);
                 } else{
                    etSpecify.setVisibility(View.VISIBLE);
                }});

        btnStep1.setOnClickListener(this::onClick);
        btnStep2.setOnClickListener(this::onClick);
        btnStep3.setOnClickListener(this::onClick);
        btnStep4.setOnClickListener(this::onClick);


    }

    private void showImageImportDialog() {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_image_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        Button btnCamera = dialog.findViewById(R.id.btnCamera);
        Button btnGallery = dialog.findViewById(R.id.btnGallery);

           btnCamera.setOnClickListener((v)->{
               if(!checkCameraPermission()){
                   requestCameraPermission();
               } else{
                   pickCamera();
                   dialog.dismiss();
               }
           });

           btnGallery.setOnClickListener((View)->{
               if(!checkStoragePermission()){
                   requestStoragePermission();
               }else{
                   pickGallery();
                   dialog.dismiss();
               }
           });

             dialog.show();


    }
    /*Code below is sourced from
    Youtube Video : Text Recognition App - Android Studio - Java,
    Atif Pervaiz,
     https://www.youtube.com/watch?v=mmuz8qIWcL8
     */
    private void pickCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "NewPic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "TextRecognition");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);


    }
    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults [0] ==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeStorageAccepted){
                        pickCamera();
                        dialog.dismiss();
                    } else{
                        ShowToast toast = new ShowToast();
                        toast.makeImageToast(this, R.drawable.ic_error, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if(grantResults.length>0){

                    boolean writeStorageAccepted = grantResults [0] ==PackageManager.PERMISSION_GRANTED;
                    if(writeStorageAccepted){
                        pickGallery();
                        dialog.dismiss();
                    } else{
                        ShowToast toast = new ShowToast();
                        toast.makeImageToast(this, R.drawable.ic_error, R.string.permission_denied, Toast.LENGTH_SHORT).show();

                    }
                }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                ivPreview.setVisibility(View.VISIBLE);

                Uri resultUri = result.getUri();
                ivPreview.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPreview.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    etResult.setText(sb.toString());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                Log.d("Upload_Dialog_Fragment", error.toString());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    } //END

/*
Code below is based on:
Web Article: "How to use Google Translate API in Android Studio projects?",
Cansu Yeksan Aktaş,
Medium,
https://medium.com/@yeksancansu/how-to-use-google-translate-api-in-android-studio-projects-7f09cae320c7
 */

    public void getTranslateService() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try (InputStream is = getResources().openRawResource(R.raw.credentials)) {


            final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);
            TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
            translate = translateOptions.getService();

        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }
    public boolean translate() {
        String originalText = etResult.getText().toString();
        Translation translation = translate.translate(originalText, Translate.TranslateOption.targetLanguage("en"), Translate.TranslateOption.model("base"));
        String translatedText = translation.getTranslatedText();
        Log.d("UploadActivity", "Translated");
        return checkVegan(translatedText);
 }
    public boolean checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);


        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }// END

    public boolean checkVegan(String ingredients){
        Log.d("INGREDIENTS", ingredients);
        String[] results = ingredients.split(",");
        Vegan vegan = new Vegan();
        ArrayList<String> containsAnimal = vegan.containsAnimalIngredients(results);
        if (containsAnimal.size() > 0) {
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this,R.drawable.ic_error, R.string.contains_animal, Toast.LENGTH_LONG);
            return false;
        } else{
            uploadedProduct.setProductIngredients(results);
            return true;
        }


    }



    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch(view.getId()){
            case R.id.btnStep1:
                Step1(view);
                break;
            case R.id.btnStep2:
                Step2(view);
                break;
            case R.id.btnStep3:
                Step3(view);
                break;
            case R.id.btnStep4:
                Step4(view);
                break;

        }







    }

    private  void Step1(View view){
        String category;
        try {
            category= Objects.requireNonNull(spCategory.getSelectedItem()).toString();
        } catch (Exception e){
            category ="";
            e.printStackTrace();
        }

            if(barcodeList==null){
                ShowToast toast = new ShowToast();
                toast.makeImageToast(this, R.drawable.ic_error, R.string.no_db, Toast.LENGTH_LONG);


            } else{
            String barcode = etBarcode.getText().toString();

            if(barcode.isEmpty() || category.isEmpty()){
                ShowToast toast = new ShowToast();
                toast.makeImageToast(this, R.drawable.ic_error, R.string.please_enter, Toast.LENGTH_SHORT);

            } else{
                if(barcodeList.contains(barcode)){
                    ShowToast toast = new ShowToast();
                    toast.makeImageToast(this, R.drawable.ic_error, R.string.barcode_indb, Toast.LENGTH_LONG);


                } else{
                    uploadedProduct.setBarcode(barcode);
                    uploadedProduct.setCategory(category);
                    strStep = "STEP 2";
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    tbUpload.setTitle(strStep);
                    layoutStep1.setVisibility(View.GONE);
                    layoutStep2.setVisibility(View.VISIBLE);

                }

            }
            }
        }


    private void Step2(View view){
        String result;
        result = etResult.getText().toString();
        if(result.isEmpty()){
            ShowToast toast = new ShowToast();
            toast.makeImageToast(this, R.drawable.ic_error, R.string.please_enter, Toast.LENGTH_LONG);

        } else{
            if (checkInternetConnection()) {
                getTranslateService();
                if(translate()){

                    String productCategory = uploadedProduct.getCategory();
                if(productCategory.equals("Alcohol")){
                    types= new ArrayList<>(IConstants.ALCOHOL_TYPES);
                  } else{
                    types= new ArrayList<>(IConstants.FOOD_TYPES);
                        }
                    dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spType.setAdapter(dataAdapter);
                    spType.setPrompt("Select Type");
                    layoutStep2.setVisibility(View.GONE);
                    strStep = "STEP 3";
                    tbUpload.setTitle(strStep);
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    layoutStep3.setVisibility(View.VISIBLE);
                }

            } else {
                ShowToast toast = new ShowToast();
                toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
            }

        }
    }

        private void Step3(View view){

            String name = etPName.getText().toString();
            String brand = etBrand.getText().toString();
            String type;
            try {
                type= Objects.requireNonNull(spType.getSelectedItem()).toString();
            } catch (Exception e){
                type ="";
                e.printStackTrace();
            }


            if (brand.isEmpty() || name.isEmpty() || type.isEmpty()) {
                ShowToast toast = new ShowToast();
                toast.makeImageToast(this, R.drawable.ic_error, R.string.please_enter, Toast.LENGTH_LONG);
            } else {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String strStep = "STEP 4";
                User user = new User();
                user.setUserID(IConstants.USER_ID);
                tbUpload.setTitle(strStep);
                uploadedProduct.setProductName(name);
                uploadedProduct.setProductBrand(brand);
                uploadedProduct.setUploadedBy(user);
                uploadedProduct.setProductType(type);
                layoutStep3.setVisibility(View.GONE);
                btnStep4.setVisibility(View.VISIBLE);
                layoutStep4.setVisibility(View.VISIBLE);
        }
    }

 private void Step4(View view){
     ArrayList<String> list = new ArrayList<>();
               StringBuilder availablefrom = new StringBuilder();
                if(cbOther.isChecked()){
                  String string = etSpecify.getText().toString();
                  if(string.isEmpty()){
                       etSpecify.requestFocus();
                }else{
                      availablefrom = new StringBuilder(string);
                      list.add(availablefrom.toString());
                }
                }

                CheckBox[] checkboxes =  {cbIceland, cbSupervalu, cbTesco, cbLidl, cbAldi, cbHB, cbDunnes};

                 for(CheckBox ck: checkboxes){
                    if(ck.isChecked()){
                    list.add(ck.getText().toString());
                } }

                 if(list.isEmpty()){
                     ShowToast toast = new ShowToast();
                     toast.makeImageToast(this, R.drawable.ic_error, R.string.please_choose, Toast.LENGTH_LONG);

                 } else if(list.size()> 1){
                     int total = list.size() -1;
                     for(int i =0; i <= total;i ++){
                         String string = list.get(i);
                         if(i==total){
                              availablefrom.append(string);
                              } else{
                             availablefrom.append(string).append(", ");
                         }
                     }
                 } else{
                     availablefrom = new StringBuilder(list.get(0));
                 }



                  uploadedProduct.setAvailableFrom(availablefrom.toString());
                 if((rdoYes.isChecked()) || (rdoNo.isChecked())){
                     if(rdoYes.isChecked()){
                         uploadedProduct.setProductProduction("Vegan");
                     } else{
                         uploadedProduct.setProductProduction("Likely Vegan");
                     }

                 }else {
                     ShowToast toast = new ShowToast();
                     toast.makeImageToast(this, R.drawable.ic_error, R.string.please_choose, Toast.LENGTH_LONG);
                 }


     if(uploadedProduct.getProductProduction().isEmpty() || uploadedProduct.getAvailableFrom().isEmpty() ){
         ShowToast toast = new ShowToast();
         toast.makeImageToast(this, R.drawable.ic_error, R.string.please_choose, Toast.LENGTH_LONG);

     }
     else{

         if(checkInternetConnection()) {
             layoutStep4.setVisibility(View.GONE);
             androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.UPLOADLOADERID, null, this);
             InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
             inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

         } else{
             ShowToast toast = new ShowToast();
             toast.makeImageToast(this, R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
         }
     }


         }





    @NonNull
    @Override
    public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
        layoutStep4.setVisibility(View.GONE);
        etSpecify.setVisibility(View.GONE);
        btnStep4.setVisibility(View.GONE);

        lottieAnimationView.setVisibility(View.VISIBLE);

        return new com.kmm.vegancheckerapp.features.LoaderManager.InsertProductLoader(this, uploadedProduct);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
        lottieAnimationView.setVisibility(View.GONE);
        ShowToast toast = new ShowToast();
        if (data != 1) {
            toast.makeImageToast(this, R.drawable.ic_error, R.string.no_db, Toast.LENGTH_LONG);
        }
        finish();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Integer> loader) {

    }


}

