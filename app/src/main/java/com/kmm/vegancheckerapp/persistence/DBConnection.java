package com.kmm.vegancheckerapp.persistence;

import android.os.StrictMode;
import android.util.Log;

import com.kmm.vegancheckerapp.utils.IConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    /*
     Code below is based on:
     Youtube Video: "How to connect to MS SQL Server database from your Android App? - complete steps",
     Programmer World,
     https://www.youtube.com/watch?v=MnmEXqfV5BU
    */

    private static final String ip = "vcdb.crz248hs3dlx.eu-west-1.rds.amazonaws.com";
    private static final String port = "1433";
    private static final String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static final String DB = "VC_DB";
    private static final String username = "admin";
    private static final String password = "117349221";
    private static final String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + DB;
    private static DBConnection dbConnection;


    public DBConnection() {

           }


    public static DBConnection getInstance() {
        if(dbConnection == null){
            synchronized (DBConnection.class) {
                if(dbConnection ==null)
                    dbConnection = new DBConnection();
            }
        }
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        Connection dbconn =null;
        try {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    Class.forName(Classes);

    dbconn = DriverManager.getConnection(url, username, password);

    Log.d("DB", IConstants.TAG + "SUCCESS" + IConstants.TAG);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
             Log.d("DB", IConstants.TAG + "FAIL" + IConstants.TAG);
}

return dbconn;
    } //END

}





