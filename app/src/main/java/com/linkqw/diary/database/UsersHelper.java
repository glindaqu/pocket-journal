package com.linkqw.diary.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class UsersHelper extends SQLiteOpenHelper {

    private final Context context;

    public static final String DATABASE_NAME = "Journal.db";
    public static final int DATABASE_VERSION = 4;

    public static final String ID_COLUMN = "_id";

    public UsersHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + "persons" + "(" +
                ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "firstname" + " NVARCHAR(50) NOT NULL," +
                "lastname" + " NVARCHAR(50) NOT NULL);";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + "heap" + "(" +
                ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "personID" + " INTEGER NOT NULL," +
                "status" + " NVARCHAR(50) NOT NULL," +
                "pairNumber INTEGER NOT NULL," +
                "subjectID INTEGER NOT NULL," +
                "dateStamp" + " DATE NOT NULL);";
        db.execSQL(query2);

        String query3 = "CREATE TABLE subjects(" +
                ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title nvarchar(100) NOT NULL);";
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "persons");
        db.execSQL("DROP TABLE IF EXISTS " + "heap");
        db.execSQL("DROP TABLE IF EXISTS subjects");
        this.onCreate(db);
    }

    public void removeAllFromHeap() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM heap;");
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
    }

    public void addNewPerson(String fname, String lname) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("firstname", fname);
        cv.put("lastname", lname);

        long res = db.insert("persons", null, cv);

        if (res == -1) {
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllDataFrom(String table) {
        String query = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cr = null;

        if (db != null) {
            cr = db.rawQuery(query, null);
        }

        return cr;
    }

    public ArrayList<String> getAllSubjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT title FROM subjects;";
        ArrayList<String> res = new ArrayList<>();

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor c = db.rawQuery(query, null);

            while (c.moveToNext()) {
                res.add(c.getString(0));
            }
        }
        return res;
    }

    public ArrayList<String> getAllSkippedBySubject(String subject, String date1, String date2) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query1 = "SELECT personID, status FROM heap WHERE " +
                "subjectID = " + getSubjectByName(subject) + "" +
                " AND dateStamp BETWEEN " + "'" + date1 + "'" + " AND " + "'" + date2 + "'" + ";";

        ArrayList<String> res = new ArrayList<>();

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor c = db.rawQuery(query1, null);

            while (c.moveToNext()) {
                res.add(c.getString(0) + "," + c.getString(1));
            }
        }

        return res;
    }

    public ArrayList<String> getAllSkipped(String date1, String date2) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query1 = "SELECT personID, status FROM heap" +
        " WHERE dateStamp BETWEEN " + "'" + date1 + "'" + " AND "
                + "'" + date2 + "'" + ";";

        ArrayList<String> res = new ArrayList<>();

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor c = db.rawQuery(query1, null);

            while (c.moveToNext()) {
                res.add(c.getString(0) + "," + c.getString(1));
            }
        }

        return res;
    }

    public void addToHeap(int userId, String state, String subject, int pairNumber, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("personID", userId);
        contentValues.put("status", state);
        contentValues.put("dateStamp", date);
        contentValues.put("subjectID", getSubjectByName(subject));
        contentValues.put("pairNumber", pairNumber);

        long res = db.insert("heap", null, contentValues);

        if (res == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Recycle")
    public int getCurrentPairNum(String dateStamp) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT MAX(pairNumber) FROM heap " +
                "WHERE dateStamp = " + "'" + dateStamp + "'";

        if (db != null) {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        }

        return 0;
    }

    public int getSubjectByName(String name) {
        String query = "SELECT _id FROM subjects " +
                "WHERE title = " + "'" + name + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                return id;
            }
        }

        return 0;
    }

    public String getSubjectByID(int id) {
        String query = "SELECT title FROM subjects " +
                "WHERE _id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
        }

        return null;
    }

    @SuppressLint("Recycle")
    public Cursor getAllFromHeapByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        String query = "SELECT * FROM heap " +
                "WHERE dateStamp = " + "'" + date + "'";

        if (db != null) {
            c = db.rawQuery(query, null);
        }

        return c;
    }

    public void addSubject(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("title", name);

        long res = db.insert("subjects", null, cv);

        if (res == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> getAllPerformedPais(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> res = new ArrayList<>();

        String query = "SELECT subjectID, pairNumber FROM heap WHERE dateStamp = " + "'" + date + "'"
                + " GROUP BY pairNumber";

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor c = db.rawQuery(query, null);

            while (c.moveToNext()) {
                res.add(getSubjectByID(c.getInt(0)) + "," + c.getInt(1));
            }
        }
        return res;
    }

    public ArrayList<String> getAllByPairAndDate(String subject, String date) {

        ArrayList<String> res = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT personID, status FROM heap " +
                "WHERE pairNumber = " + subject + " " +
                "AND dateStamp = " + "'" + date + "'";

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor c = db.rawQuery(query, null);

            while (c.moveToNext()) {
                res.add(c.getString(0) + "," + c.getString(1));
            }
        }
        return res;
    }

    public String getFirstname(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT firstname FROM persons " +
                "WHERE _id = " + id;

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor c = db.rawQuery(query, null);

            if (c.moveToNext()) {
                return c.getString(0);
            }
        }
        return null;
    }

    public String getLastname(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT lastname FROM persons " +
                "WHERE _id = " + id;

        if (db != null) {
            @SuppressLint("Recycle")
            Cursor c = db.rawQuery(query, null);

            if (c.moveToNext()) {
                return c.getString(0);
            }
        }
        return null;
    }

    public ArrayList<String> getAllNames() {
        Cursor cursor = getAllDataFrom("persons");
        ArrayList<String> names = new ArrayList<>();

        while (cursor.moveToNext()) {
            names.add(cursor.getString(0) + "," +
                    cursor.getString(1) + " " + cursor.getString(2));
        }

        return names;
    }
}
