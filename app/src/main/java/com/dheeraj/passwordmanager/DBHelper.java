package com.dheeraj.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "passwordsDB.db";
    public static final String PASSWORD_TABLE_NAME = "passwordManagerTable";
    public static final String PASSWORD_COLUMN_ID = "id";
    public static final String PASSWORD_COLUMN_Appliction = "application";
    public static final String PASSWORD_COLUMN_USERNAME = "username";
    public static final String PASSWORD_COLUMN_PASSWORD = "password";
    public static final String PASSWORD_COLUMN_URL="url";
    public static final String PASSWORD_COLUMN_DOMAIN = "domain";
    public static final String PASSWORD_COLUMN_LASTUPDATED = "lastupdated";
    public static final String PASSWORD_CREATED = "created";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PASSWORD_TABLE_NAME + " (" +
                    PASSWORD_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    PASSWORD_COLUMN_Appliction + " TEXT," +
                    PASSWORD_COLUMN_USERNAME + " TEXT," +
                    PASSWORD_COLUMN_DOMAIN + " TEXT," +
                    PASSWORD_COLUMN_PASSWORD + " TEXT," +
                    PASSWORD_COLUMN_URL + " TEXT," +
                    PASSWORD_COLUMN_LASTUPDATED + " TEXT," +
                    PASSWORD_CREATED + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PASSWORD_TABLE_NAME;
    private static String secretKey = "kdjf*&jka7Tfh)(&^6sb,zio#)&EWklsdf10/;djuodufa";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_ENTRIES);

        } catch (Exception ex) {
            Log.d("Error", "onCreate: " + ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(d0b);
        //NOt sure
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean addData(String application, String username, String password, String domain, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        String encryptedPassword = EncryptPasswordData(password, secretKey);
        boolean app = AppExists(application);
        if (!app) {
            ContentValues contentValues = new ContentValues();
            //contentValues.put("id", "1");
            contentValues.put(PASSWORD_COLUMN_Appliction, application);
            contentValues.put(PASSWORD_COLUMN_USERNAME, username);
            contentValues.put(PASSWORD_COLUMN_PASSWORD, password);
            contentValues.put(PASSWORD_COLUMN_DOMAIN, domain);
            contentValues.put(PASSWORD_COLUMN_URL, url);
            //contentValues.put(PASSWORD_COLUMN_LASTUPDATED, "");
            java.util.Date date = new java.util.Date();
            contentValues.put(PASSWORD_CREATED, date.getTime());
            try {
                long id = db.insert(PASSWORD_TABLE_NAME, PASSWORD_COLUMN_LASTUPDATED, contentValues);

            } catch (Exception ex) {
                Log.d("Error", "addData: " + ex);
            } finally {
                db.close();
            }

            return true;
        } else {
            db.close();
            return false;
        }

    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from "+PASSWORD_TABLE_NAME+" where id=" + id + "", null);
            return res;
        } catch (Exception ex) {
            Log.d("Error", "getData: " + ex);
        } finally {
            res.close();
        }

        return null;

    }

    public boolean AppExists(String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from "+PASSWORD_TABLE_NAME +" where application=?", new String[]{appName});

            if ((int) res.getCount() > 0) {
                res.close();
                return true;
            } else {
                res.close();
                return false;
            }
        } catch (Exception ex) {
            Log.d("Error", "AppExists: " + ex);
        }
        return false;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PASSWORD_TABLE_NAME);
        return numRows;
    }

    public boolean updateData(String application, String username, String password,String domain,String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD_COLUMN_URL, url);
        contentValues.put(PASSWORD_COLUMN_USERNAME, username);
        contentValues.put(PASSWORD_COLUMN_PASSWORD, password);
        contentValues.put(PASSWORD_COLUMN_DOMAIN, domain);
        java.util.Date date = new java.util.Date();
        contentValues.put(PASSWORD_COLUMN_LASTUPDATED, date.getTime());

        db.update("PASSWORD_TABLE_NAME", contentValues, "application = ? ", new String[]{application});

        //db.close();
        return true;
    }

    public boolean deleteApplication(String application) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int i= db.delete("PASSWORD_TABLE_NAME",
                    "application = ? ", new String[]{application});
        }catch(Exception ex)
        {
            Log.d("Error", "deleteApplication: "+ex);
            return false;
        }
        return true;
    }

    public ArrayList<Model> getAllData() {
        ArrayList<Model> array_list = new ArrayList<Model>();
        SQLiteDatabase db = this.getReadableDatabase();
        ;
        try {
            //hp = new HashMap();

            Cursor res = db.rawQuery("select * FROM "+PASSWORD_TABLE_NAME, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                array_list.add(new Model( res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));
                res.moveToNext();
            }
            res.close();

        } catch (Exception ex) {
            Log.d("Error", "getAllData: " + ex);
            if (ex.getMessage().toString().contains("attempt to re-open an already-closed object")) {

            }
        }
        return array_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String EncryptPasswordData(String value, String secret) {
        String encrypted = null;
        try {
            encrypted = AESUtils.encrypt(value);
            Log.d("TEST", "encrypted:" + encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String strToDecrypt, String secret) {
        String decrypted = null;

        try {
            decrypted = AESUtils.decrypt(strToDecrypt);
            Log.d("TEST", "decrypted:" + decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;

    }
}
