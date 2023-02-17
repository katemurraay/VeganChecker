package com.kmm.vegancheckerapp.dao;

import android.util.Log;

import com.kmm.vegancheckerapp.model.User;
import com.kmm.vegancheckerapp.persistence.DBConnection;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String TABLE_NAME = "USERDATA";
    private static final String C_ID = "USERID";
    private static final String C_UNAME = "USERNAME";
    private static final String C_FNAME = "FIRSTNAME";
    private static final String C_LNAME = "SURNAME";
    private static final String C_EMAIL = "EMAIL";
    private static final String C_PASS = "PASSWORD";
    private static final String C_TYPE ="USERTYPE";

public UserDAO(){

}



    public void insertUser(User newUser)  {

        String stmtNewUser = "INSERT INTO " +TABLE_NAME +" (" + C_UNAME+ ", "+C_EMAIL + ", "+  C_PASS + ", " +C_FNAME  + ", " + C_LNAME  + ", " + C_TYPE + ")" + " VALUES(?,?,?,?,?,?)";




        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(stmtNewUser);
            ps.setString(1,newUser.getUserName());
            ps.setString(2, newUser.getEmail());
            ps.setString(3, newUser.getPassword());
            ps.setString(4, newUser.getFirstName());
            ps.setString(5, newUser.getSurName());
            ps.setString(6, newUser.getUserType());
            int status=ps.executeUpdate();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();

        }
    }

    public void updateUser(User user)  {


        String sql="UPDATE " + TABLE_NAME  + " SET " + C_EMAIL + "=?," + C_PASS +"=?, " + C_FNAME + "=?, " + C_LNAME + "=?, " +  C_TYPE + "=? " + " WHERE " +  C_ID + " =? ";
        try{  DBConnection db = new DBConnection();
            Connection con = db.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2,user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getSurName());
            ps.setString(5, user.getUserType());
            ps.setString(6, (String.valueOf(user.getUserID())));

            int status=ps.executeUpdate();



        }catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }


    }

    public StringBuffer getAllUsers()  {


        String query = "SELECT * FROM " + TABLE_NAME;
        StringBuffer buffer = new StringBuffer();
        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    buffer.append("Email: ").append(rs.getString(3)).append("\n");
                    buffer.append("First Name: ").append(rs.getString(5)).append("\n");
                    buffer.append("Surname: ").append(rs.getString(6)).append("\n\n");

                    }

                    Log.d("DB", IConstants.TAG + "SQL Success "+ IConstants.TAG );
                } catch (SQLException e) {
                    e.printStackTrace();
                     Log.d("DB", IConstants.TAG + "SQL PROBLEM "+ IConstants.TAG );
                }
            return buffer;
            }

    public User getUserByID(int id) {


        String strUserID = String.valueOf(id);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + C_ID + " = " + strUserID + ";";
        User user = new User();
        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user.setUserID(rs.getInt(1));
                user.setUserName(rs.getString(2));
            }

            Log.d("DB", IConstants.TAG + "SQL Success "+ IConstants.TAG );
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("DB", IConstants.TAG + "SQL PROBLEM "+ IConstants.TAG );

        }

        return user;
    }

    public void deleteUser(String email)  {
        int status =0;

        String strSQL = "DELETE FROM " + TABLE_NAME +" WHERE " + C_EMAIL +" = " +"'" +email + "'";

        try{  DBConnection db = new DBConnection();

            Connection con = db.getConnection();
            PreparedStatement ps=con.prepareStatement(strSQL);

            status=ps.executeUpdate();

        }  catch (SQLException e) {
            e.printStackTrace();
            Log.d("DB", IConstants.TAG + "SQL PROBLEM "+ IConstants.TAG );
        }

        }


    }


