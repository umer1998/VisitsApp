package com.example.visitsapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.visitsapp.ui.MainActivity;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "OfflineHandling.db";
    public static final String QUES_POST_FEED = "QuesPostFeedback";
    public static final String QUES_REPLACE_POST_FEED = "QuesReplacePostFeedback";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";

    public DBHelper(MainActivity context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table QuesPostFeedback " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
        db.execSQL(
                "create table QUES_REPLACE_POST_FEED " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
