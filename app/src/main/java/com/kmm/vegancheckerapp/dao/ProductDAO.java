package com.kmm.vegancheckerapp.dao;

import android.util.Log;

import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.model.User;
import com.kmm.vegancheckerapp.persistence.DBConnection;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {



        private static final String P_TABLE = "PRODUCTDATA";
        private static final String P_CODE = "BARCODE";
        private static final String P_NAME = "PRODUCTNAME";
        private static final String V_TABLE ="VEGANPRODUCT";
        private  static final String P_TYPE = "PRODUCTTYPE";
        private static final String P_BRAND = "BRAND";
        private static final String P_INGREDS = "INGREDIENTS";
        private static final String P_CAT = "CATEGORY";
        private  static  final String P_PROD = "PRODUCTION";
        private  static  final String P_AVAILABLE= "AVAILABLEFROM";

public Product getVeganFoodsByBarcode(String barcode) {


                Product tempP =new Product();
                String pname=null;
                boolean found =false;
                String brand = "";
                String type ="";
                String validatedBy ="";
                String category ="";

                String sqlQuery = "SELECT BARCODE, BRAND, PRODUCTNAME, PRODUCTTYPE, VALIDATEDBY, CATEGORY FROM  "+ V_TABLE + "  WHERE " + P_CODE +" = ? ;";

                try{
                    DBConnection db= DBConnection.getInstance();
                    Connection objconn = db.getConnection();
                    if(db==null|| objconn==null){
                        return null;
                    }
                    PreparedStatement ps= objconn.prepareStatement(sqlQuery);
                    ps.setString(1, barcode);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        barcode = rs.getString(1);
                        brand= rs.getString(2);
                        pname= rs.getString(3);
                        type = rs.getString(4);
                        validatedBy = rs.getString(5);
                        category = rs.getString(6);
                        found=true;
                    }
                    ps.close();
                    Log.d("DB", IConstants.TAG + "PRODUCTFOUND");



                }catch (SQLException e) {
                    e.printStackTrace();
                    Log.d("DB Error", IConstants.TAG+ "Exception: "+ e.getMessage());
                } if (found){
                    tempP.setProductName(pname);
                    tempP.setBarcode(barcode);
                    tempP.setProductBrand(brand);
                    tempP.setProductType(type);
                    tempP.setValidatedBy(validatedBy);
                    tempP.setCategory(category);

                  Log.d("DB", "FOUND PRODUCT");
                }




                else{

                    return null;



                }
                return tempP;




            }


    public Product getVeganFoodsByName(String name)  {

        String pname=null;
        boolean found =false;
        String brand = "";
        String type ="";
        String validatedBy ="";
        String barcode = "";
        String category ="";


        Product tempP= new Product();

        String sqlQuery = "SELECT BARCODE, BRAND, PRODUCTNAME, PRODUCTTYPE, VALIDATEDBY, CATEGORY FROM  "+ V_TABLE + "  WHERE LOWER("+P_NAME+") LIKE " +" (?)  OR LOWER(BRAND) LIKE  (?) ;";

        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db == null || objconn == null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
            String strInput = "%" +name.toLowerCase() + "%";
            ps.setString(1, strInput);
            ps.setString(2, strInput);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                barcode = rs.getString(1);
                brand= rs.getString(2);
                pname= rs.getString(3);
                type = rs.getString(4);
                validatedBy = rs.getString(5);
                category = rs.getString(6);
                found=true;
            }
            ps.close();




        }catch (SQLException e) {
            e.printStackTrace();
        } if (found){
            tempP.setBarcode(barcode);
            tempP.setProductName(pname);
            tempP.setProductBrand(brand);
            tempP.setProductType(type);
            tempP.setValidatedBy(validatedBy);
            tempP.setCategory(category);




        }




        else{
            return null;



        }
        return tempP;




    }
    public Product getProductByName(String name) {

        String productName = "";
        String barcode = "";
        boolean found =false;
        String pIngredients = "";
        String production ="";
        int userid=0;
        String brand = "";
        String category ="";
        String[] productIngredients = null;
        String type = "";
        Product tempP= new Product();
        User user = new User();

        String sqlQuery = "SELECT BARCODE, BRAND, PRODUCTNAME, PRODUCTTYPE, INGREDIENTS, PRODUCTION, FK_USERID, CATEGORY FROM  "+ P_TABLE + "  WHERE LOWER("+P_NAME+") LIKE (?) OR LOWER(BRAND) LIKE  (?) ;";

        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db == null || objconn == null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
            String strInput = "%" +name.toLowerCase() + "%";
            ps.setString(1, strInput);
            ps.setString(2, strInput);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                barcode = rs.getString(1);
                brand = rs.getString(2);
                productName= rs.getString(3);
                type = rs.getString(4);
                pIngredients= rs.getString(5);
                production = rs.getString(6);
                userid = rs.getInt(7);
                category = rs.getString(8);
                found=true;
            }
            ps.close();

            productIngredients = pIngredients.split(",");



        }catch (SQLException e) {
            e.printStackTrace();
        } if (found){
            tempP.setProductName(productName);
            tempP.setBarcode(barcode);
            tempP.setProductBrand(brand);
            tempP.setProductType(type);
            tempP.setProductProduction(production);
            tempP.setProductIngredients(productIngredients);
            user.setUserID(userid);
            tempP.setUploadedBy(user);
            tempP.setCategory(category);

        }
        else{
            return null;
        }
        return tempP;
    }

    public Product getProductByBarcode(String barcode)  {

        String productName = "";
        boolean found =false;
        String pIngredients = "";
        String brand ="";
        String type ="";
        String production ="";
        String[] productIngredients = null;
        Product tempP= new Product();
        User user = new User();
        int userID = 0;
        String category ="";

        String sqlQuery = "SELECT BARCODE, BRAND, PRODUCTNAME, PRODUCTTYPE, INGREDIENTS, PRODUCTION, FK_USERID, CATEGORY FROM  "+ P_TABLE + "  WHERE "+P_CODE+" = ?;";

        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db == null || objconn == null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
            ps.setString(1, barcode);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                barcode = rs.getString(1);
                brand =rs.getString(2);
                productName= rs.getString(3);
                type = rs.getString(4);
                pIngredients= rs.getString(5);
                production =rs.getString(6);
                userID = rs.getInt(7);
                category = rs.getString(8);

                found=true;
            }
            ps.close();

            productIngredients = pIngredients.split(",");



        }catch (SQLException e) {
            e.printStackTrace();
        } if (found){
            Log.d("DB", IConstants.TAG + "PRODUCTFOUND");
            tempP.setProductName(productName);
            tempP.setBarcode(barcode);
            tempP.setProductIngredients(productIngredients);
            tempP.setProductType(type);
            tempP.setProductProduction(production);
            tempP.setProductBrand(brand);
            user.setUserID(userID);
            tempP.setUploadedBy(user);
            tempP.setCategory(category);
        }
        else{
            return null;
        }
        return tempP;
    }

    public List<Product> getVeganProductsByType(String type)  {



        int id;
        String pname, barcode, brand, validatedBy, availableFrom, category;



        List<Product> productList = new ArrayList<>();

        String sqlQuery = "SELECT * FROM  "+ V_TABLE + "  WHERE "+P_TYPE+" = ? ;";

        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db == null || objconn == null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                id = rs.getInt(1);
                barcode = rs.getString(2);
                brand= rs.getString(3);
                pname= rs.getString(4);
                type = rs.getString(5);
                validatedBy = rs.getString(6);
                availableFrom =rs.getString(7);
                category =rs.getString(8);
                Product tempP= new Product();
                tempP.setProductID(id);
                tempP.setBarcode(barcode);
                tempP.setProductName(pname);
                tempP.setProductBrand(brand);
                tempP.setProductType(type);
                tempP.setValidatedBy(validatedBy);
                tempP.setAvailableFrom(availableFrom);
                tempP.setCategory(category);


                productList.add(tempP);

            }



            ps.close();




        }catch (Exception e) {
            e.printStackTrace();
            productList = null;
        }




       return productList;




    }
    public List<Product> getOtherProductsByType(String type) {

        int id,  uploadedBy;
        String pname, barcode, brand, availableFrom, category;



        List<Product> productList = new ArrayList<>();
        UserDAO userDAO = new UserDAO();


        String sqlQuery = "SELECT * FROM  "+ P_TABLE + "  WHERE PRODUCTION = 'Vegan' AND "+P_TYPE+" = ? ;";

        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db == null || objconn == null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                id = rs.getInt(1);
                barcode = rs.getString(2);
                brand= rs.getString(3);
                pname= rs.getString(4);
                type = rs.getString(5);
                uploadedBy = rs.getInt(8);
                availableFrom =rs.getString(9);
                category = rs.getString(10);
                Product tempP= new Product();
                tempP.setProductID(id);
                tempP.setBarcode(barcode);
                tempP.setProductName(pname);
                tempP.setProductBrand(brand);
                tempP.setProductType(type);
                tempP.setUploadedBy(userDAO.getUserByID(uploadedBy));
                tempP.setAvailableFrom(availableFrom);
                tempP.setCategory(category);


                productList.add(tempP);

            }



            ps.close();




        }catch (SQLException e) {
            e.printStackTrace();
            productList = null;
        }



        return productList;




    }



    public ArrayList<String> getProductTypes(String category) {

        String type ,sqlQuery;
        ArrayList<String> typeList = new ArrayList<>();


       sqlQuery = "SELECT " +P_TYPE + "\n" +
               "FROM " +V_TABLE + "\n" +
               "WHERE " +P_CAT+ " = ?"+ "\n" +
               "GROUP BY " +P_TYPE +"\n" +
               "ORDER BY " +P_TYPE +" ASC;";



try{
    DBConnection db= DBConnection.getInstance();
    Connection objconn = db.getConnection();
    if(db==null|| objconn==null){
        return null;
    }
    PreparedStatement ps= objconn.prepareStatement(sqlQuery);
    ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                type = rs.getString(1);
                typeList.add(type);
            }

            ps.close();

    }
catch (SQLException e){
    typeList = null;

}

return typeList;
         }
    public ArrayList<String> getVeganBarcodes()  {


        String barcode ,sqlQuery;
        ArrayList<String> typeList = new ArrayList<>();


        sqlQuery = "SELECT " + P_CODE + "\n" +
                "FROM " +V_TABLE + "\n" +
                "GROUP BY " +P_CODE +"\n" +
                "ORDER BY " +P_CODE +" ASC;";



        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db==null|| objconn==null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                barcode = rs.getString(1);
                typeList.add(barcode);
            }

            ps.close();

        }
        catch (SQLException e){
            typeList = null;

        }

        return typeList;
    }
    public ArrayList<String> getProductBarcodes()  {


        String barcode,sqlQuery;
        ArrayList<String> typeList = new ArrayList<>();


        sqlQuery = "SELECT " + P_CODE + "\n" +
                "FROM " +P_TABLE + "\n" +
                "GROUP BY " +P_CODE +"\n" +
                "ORDER BY " +P_CODE +" ASC;";



        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db==null|| objconn==null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                barcode = rs.getString(1);
                typeList.add(barcode);
            }

            ps.close();

        }
        catch (Exception e){
            typeList = null;

        }

        return typeList;
    }


public int uploadProduct(Product product) {

    String sqlQuery;
    int status;
    StringBuilder strIngredients = new StringBuilder();
    for(String string: product.getProductIngredients()){
        strIngredients.append(string).append(", ");
    }
    sqlQuery = "INSERT INTO " + P_TABLE + " (" + P_CODE + ", " + P_NAME +", " + P_BRAND + ", "+  P_TYPE +", " + P_INGREDS +", " + P_PROD + ", FK_USERID, " + P_AVAILABLE + ", "+ P_CAT + " ) VALUES(?,?,?,?,?,?,?,?,?)" ;
try{
    DBConnection db= DBConnection.getInstance();
    Connection objconn = db.getConnection();
    if(db == null || objconn == null){
        return -1;
    }
    PreparedStatement ps = objconn.prepareStatement(sqlQuery);
    ps.setString(1,product.getBarcode());
    ps.setString(2, product.getProductName());
    ps.setString(3, product.getProductBrand());
    ps.setString(4, product.getProductType());
    ps.setString(5, strIngredients.toString());
    ps.setString(6, product.getProductProduction());
    ps.setInt(7, product.getUploadedBy().getUserID());
    ps.setString(8, product.getAvailableFrom());
    ps.setString(9, product.getCategory());

   status=ps.executeUpdate();

} catch (SQLException e){
    e.printStackTrace();
    status =0;

}
return status;
}

    public List<Product> getUserUploads() {

        int id, uploadedBy;
        String pname, barcode, brand, availableFrom, type;

        User user = new User();

        List<Product> productList = new ArrayList<>();

        String sqlQuery = "SELECT * FROM  "+ P_TABLE + "  WHERE FK_USERID  = ? ;";

        try{
            DBConnection db= DBConnection.getInstance();
            Connection objconn = db.getConnection();
            if(db == null || objconn == null){
                return null;
            }
            PreparedStatement ps= objconn.prepareStatement(sqlQuery);
             ps.setInt(1, IConstants.USER_ID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                id = rs.getInt(1);
                barcode = rs.getString(2);
                brand= rs.getString(3);
                pname= rs.getString(4);
                type = rs.getString(5);

                uploadedBy = rs.getInt(8);
                availableFrom =rs.getString(9);
                Product tempP= new Product();
                tempP.setProductID(id);
                tempP.setBarcode(barcode);
                tempP.setProductName(pname);
                tempP.setProductBrand(brand);
                tempP.setProductType(type);
                user.setUserID(uploadedBy);
                tempP.setUploadedBy(user);
                tempP.setAvailableFrom(availableFrom);


                productList.add(tempP);

            }



            ps.close();




        }catch (SQLException e) {
            e.printStackTrace();
            productList = null;
        }

   return productList;


    }


    public int deleteProduct(String barcode) {
        int status;

        String strSQL = "DELETE FROM " + P_TABLE +" WHERE " + P_CODE +" = ?;";

        try{
            DBConnection db = new DBConnection();
            Connection con = db.getConnection();
            if(db == null || con == null){
                return -1;
            }
            PreparedStatement ps=con.prepareStatement(strSQL);
            ps.setString(1, barcode);

            status=ps.executeUpdate();

        }  catch (SQLException e) {
            e.printStackTrace();
            Log.d("DB", IConstants.TAG + "SQL PROBLEM "+ IConstants.TAG );
            status = 0;
        }
        return status;
    }
}




