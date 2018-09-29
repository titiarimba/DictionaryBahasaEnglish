package com.example.dell.kamusdicoding.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.dell.kamusdicoding.db.DatabaseContract.DictionaryColumns.FIELD_MEAN;
import static com.example.dell.kamusdicoding.db.DatabaseContract.DictionaryColumns.FIELD_WORD;
import static com.example.dell.kamusdicoding.db.DatabaseContract.TABLE_ENG_TO_IND;
import static com.example.dell.kamusdicoding.db.DatabaseContract.TABLE_IND_TO_ENG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbdictionary";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENG_IND = "create table "+
            TABLE_ENG_TO_IND+ " ("+_ID+" integer primary key autoincrement, "
            +FIELD_WORD+" text not null, "
            +FIELD_MEAN+" text not null);"
            ;
    public static String CREATE_TABLE_IND_ENG = "create table "+
            TABLE_IND_TO_ENG+ " ("+_ID+" integer primary key autoincrement, "
            +FIELD_WORD+" text not null, "
            +FIELD_MEAN+" text not null);"
            ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENG_IND);
        db.execSQL(CREATE_TABLE_IND_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_ENG_TO_IND);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_IND_TO_ENG);
        onCreate(db);
    }
}
