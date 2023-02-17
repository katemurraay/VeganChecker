package com.kmm.vegancheckerapp.features;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.kmm.vegancheckerapp.dao.ProductDAO;
import com.kmm.vegancheckerapp.dao.UserDAO;
import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.model.User;
import com.kmm.vegancheckerapp.model.Vegan;

import java.util.ArrayList;

import static com.kmm.vegancheckerapp.features.LoaderManager.NameLoader.CheckVeganIngredients;

/* Code below is based on :
 StackOverflow Answer to Question: "AsyncTaskLoader basic example. (Android)",
 Sanjeev
https://stackoverflow.com/a/44473762
*/
public class LoaderManager {
    public static class TypeLoader extends AsyncTaskLoader<ArrayList<String>> {

        private final String strCategory;
    public TypeLoader(@NonNull Context context, String category) {
        super(context);
        this.strCategory = category;
    }
    @Nullable
    @Override
    public ArrayList<String> loadInBackground() {
        ArrayList<String> productTypes;
        try {
            ProductDAO productDAO = new ProductDAO();
            productTypes = productDAO.getProductTypes(strCategory);
            Log.d("Loader", "TYPE" );
        } catch (Exception e){
            productTypes = null;

        }
        return productTypes;
    }



    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.d("Type_Loader", "onStartCalled");
    } //END
}


public static class AlternativeLoader extends AsyncTaskLoader<ArrayList<Product>>{
String query;
    public AlternativeLoader(@NonNull Context context, String query) {
        super(context);
        this.query = query;
    }

    @Nullable
    @Override
    public ArrayList<Product> loadInBackground() {

        ProductDAO productDAO = new ProductDAO();
        ArrayList<Product> products;
        try{
            products = new ArrayList<>(productDAO.getVeganProductsByType(query));
            products.addAll(productDAO.getOtherProductsByType(query));
        } catch(Exception e){
            Log.d("Alternative_Loader", e.getMessage());
            return  null;
        }

        return products;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.d("Alternative_Loader", "onStartCalled");
    }
}

public static class BarcodeLoader extends AsyncTaskLoader<Product>{
String barcode;
    public BarcodeLoader(@NonNull Context context, String barcode) {
        super(context);
        this.barcode = barcode;
    }

    @Nullable
    @Override
    public Product loadInBackground() {
        Product product;
        ProductDAO productDAO = new ProductDAO();



        try {
            product = productDAO.getVeganFoodsByBarcode(barcode);
            if(product != null){
              product.setProductProduction("Vegan");

            }else {
                product =OtherProductsByBarcode(barcode);


            }
        } catch (Exception e) {
            Log.d("Details ERROR", "ERROR: " + e.getMessage());
            return null;

        }
        return product;
    }

    public Product OtherProductsByBarcode (String barcode)  {
        Product product = new Product();
        ProductDAO productDAO = new ProductDAO();
        UserDAO userDAO = new UserDAO();



        try {
            product = productDAO.getProductByBarcode(barcode);
            if(product != null){


                String[] ingredients = product.getProductIngredients();
                ArrayList<String> vegan = CheckVeganIngredients(ingredients);
                if(product.getUploadedBy().getUserID() != 0){
                    User user = userDAO.getUserByID(product.getUploadedBy().getUserID());
                    product.setUploadedBy(user);
                }
                if (vegan.size() ==0) {
                    String[] animalIngredients = {"Vegan"};
                    product.setProductIngredients(animalIngredients);
                } else {
                    String[] animalIngredients = {vegan.get(0)};
                    product.setProductIngredients(animalIngredients);
                    product.setProductProduction("No");
                }




            } else{
                return null;
            }}catch (Exception e){
            Log.d("Details ERROR", "ERROR: " + e.getMessage());
        }
        return product;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.d("Barcode_Loader", "onStartCalled");
    }
}

public static class NameLoader extends AsyncTaskLoader<Product>{
String name;
    public NameLoader(@NonNull Context context, String name) {
        super(context);
        this.name = name;
    }

    @Nullable
    @Override
    public Product loadInBackground() {
        ProductDAO productDAO = new ProductDAO();
        Product product;


        try {

            product = productDAO.getVeganFoodsByName(name);

            if(product != null){
                product.setProductProduction("Vegan");
                String[] ingredients = {"Vegan"};

                product.setProductIngredients(ingredients);

            }else {

                product =OtherProductsByName(name);

            } } catch (Exception e) {
            Log.d("Details ERROR", "ERROR: " + e.getMessage());
            return null;
        }
        return product;

    }
    public Product OtherProductsByName (String name) {
        Product product = new Product();
        ProductDAO productDAO = new ProductDAO();
        UserDAO userDAO = new UserDAO();


        try {

            product = productDAO.getProductByName(name);
            if(product != null){

                String[] ingredients = product.getProductIngredients();
                ArrayList<String> vegan = CheckVeganIngredients(ingredients);
                if(product.getUploadedBy().getUserID() != 0){
                    User user = userDAO.getUserByID(product.getUploadedBy().getUserID());
                    product.setUploadedBy(user);
                }

                if (vegan.size()==0) {
                    String[] animalIngredients = {"Vegan"};
                    product.setProductIngredients(animalIngredients);

                } else {
                    String[] animalIngredients = {vegan.get(0)};
                    product.setProductIngredients(animalIngredients);
                    product.setProductProduction("No");
                }



            } else{
                Log.d("Details ERROR", "ERROR WITH FINDPRODUCTDETAILS");
            }}catch (Exception e){
            Log.d("Details ERROR", "ERROR: " + e.getMessage());
        } return product;
    }

    public static ArrayList<String> CheckVeganIngredients(String[] ingredients){
        ArrayList<String> animalIngredients;
        Vegan vegan = new Vegan();
        animalIngredients = vegan.containsAnimalIngredients(ingredients);
        return animalIngredients;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.d("Name_Loader", "onStartCalled");
    }
    }

    public static class InsertProductLoader extends AsyncTaskLoader<Integer>{
Product product;
        public InsertProductLoader(@NonNull Context context, Product product) {
            super(context);
            this.product = product;
        }

        @Nullable
        @Override
        public Integer loadInBackground() {
            ProductDAO productDAO = new ProductDAO();
          int id;
            try {
                id = productDAO.uploadProduct(product);
            } catch (Exception e) {
                e.printStackTrace();
                id =0;
            }
            return id;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("Insert_Loader", "onStartCalled");
        }
    }

    public static class AllBarcodeLoaders extends AsyncTaskLoader<ArrayList<String>> {

        public AllBarcodeLoaders(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public ArrayList<String> loadInBackground() {

                ProductDAO productDAO = new ProductDAO();
                ArrayList<String> barcodeList =new ArrayList<>();
                try {
                    barcodeList.addAll(productDAO.getVeganBarcodes());
                    barcodeList.addAll(productDAO.getProductBarcodes());
                } catch (Exception e) {
                    e.printStackTrace();
                    barcodeList = null;

                }

                return barcodeList;


            }


        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("All_Barcode_Loader", "onStartCalled");
        }
    }

    public static class UserUploadLoader extends AsyncTaskLoader<ArrayList<Product>>{

        public UserUploadLoader(@NonNull Context context) {
            super(context);

        }

        @Nullable
        @Override
        public ArrayList<Product> loadInBackground() {

            ProductDAO productDAO = new ProductDAO();
            ArrayList<Product> products;
            try{
                products = new ArrayList<>(productDAO.getUserUploads());

                   }
            catch(Exception e){
                Log.d("UserUpload_Loader", e.getMessage());
                return  null;
            }

            return products;
        }
        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("UserUpload_Loader", "onStartCalled");
        }
    }


    public static class DeleteProductLoader extends AsyncTaskLoader<Integer>{
        Product product;

        public DeleteProductLoader(@NonNull Context context, Product product) {
            super(context);
            this.product = product;
        }

        @Nullable
        @Override
        public Integer loadInBackground() {
            ProductDAO productDAO = new ProductDAO();
            int id;
            try {
                String barcode = product.getBarcode();
                id = productDAO.deleteProduct(barcode);
            } catch (Exception e) {
                e.printStackTrace();
                id =0;
            }
            return id;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
            Log.d("Delete_Loader", "onStartCalled");
        }
    }
}

