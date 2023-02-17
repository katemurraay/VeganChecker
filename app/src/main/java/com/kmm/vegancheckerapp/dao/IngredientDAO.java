package com.kmm.vegancheckerapp.dao;

import android.util.Log;

import com.kmm.vegancheckerapp.persistence.DBConnection;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IngredientDAO {

    private static final String I_TABLE = "INGREDIENTS";
    private static final String I_NAME = "INGREDIENTNAME";
    private static final String I_VEGAN = "ISVEGAN";

    public ArrayList<String> getNonVeganIngredients(){
        String sqlQuery = "SELECT LOWER(" + I_NAME + ") FROM "+ I_TABLE + "  WHERE " + I_VEGAN +" = 0 ;";
        ArrayList<String> ingredients = new ArrayList<>();

        try{

            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db==null|| objconn==null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ingredients.add(rs.getString(1));
            }

    } catch (Exception e){
            e.printStackTrace();
            Log.d("DB Error", IConstants.TAG+ "Exception: "+ e.getMessage());
            ingredients = null;
        }
        return ingredients;
    }

}
