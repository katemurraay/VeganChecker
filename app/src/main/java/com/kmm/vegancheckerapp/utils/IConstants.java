package com.kmm.vegancheckerapp.utils;

import com.kmm.vegancheckerapp.model.Product;

import java.util.ArrayList;

public class IConstants {
    public static final String TAG = "***********";
    public static final String USER_SUBSCRIBED = "1";
    public static boolean isInternetConnected ;
    public static volatile boolean isNotConnected;
    public static int USER_ID = 1;
    public volatile static ArrayList<String> FOOD_TYPES, ALCOHOL_TYPES, PRODUCT_BARCODES;
    public volatile static ArrayList<Product> USER_UPLOADS;

    public static final int FOODTYPELOADERID = 1;
    public static final int NAMELOADERID = 2;
     public static final int ALLBARCODELOADERID = 4;
    public static final int UPLOADLOADERID = 5;
    public static final int ALTERNATIVELOADERID = 6;
    public static final int RESULTLOADERID = 7;
    public static final int USERUPLOADLOADERID = 8;
    public static final int DELETEPRODUCTLOADERID = 9;
    public static final int ALCOHOLTYPELOADERID = 10;



}


