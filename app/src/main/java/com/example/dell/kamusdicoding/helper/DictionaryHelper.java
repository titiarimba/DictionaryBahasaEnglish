package com.example.dell.kamusdicoding.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.dell.kamusdicoding.db.DatabaseContract;
import com.example.dell.kamusdicoding.db.DatabaseHelper;
import com.example.dell.kamusdicoding.model.DictionaryModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.dell.kamusdicoding.db.DatabaseContract.DictionaryColumns.FIELD_MEAN;
import static com.example.dell.kamusdicoding.db.DatabaseContract.DictionaryColumns.FIELD_WORD;
import static com.example.dell.kamusdicoding.db.DatabaseContract.TABLE_ENG_TO_IND;
import static com.example.dell.kamusdicoding.db.DatabaseContract.TABLE_IND_TO_ENG;

public class DictionaryHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private String field;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    private void choice(boolean choice){
        if (choice){
            field = TABLE_ENG_TO_IND;
        } else {
            field = TABLE_IND_TO_ENG;
        }
    }

    public DictionaryHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<DictionaryModel> getByWord(String word, boolean choice){
        choice(choice);
        Cursor cursor = database.rawQuery(" SELECT * FROM "+field+" "+
                "WHERE word LIKE '"+word+"%'", null);

        cursor.moveToFirst();
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0){
            do {
                dictionaryModel = new DictionaryModel(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_WORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.FIELD_MEAN)));

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;

    }

    public ArrayList<DictionaryModel> getAllData(boolean choice){
        choice(choice);
        Cursor cursor = database.query(field, null, null, null,
                null, null, _ID+ " ASC", null);

        cursor.moveToFirst();
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount()>0){
            do {
                dictionaryModel = new DictionaryModel(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_WORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.FIELD_MEAN)));

                arrayList.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(DictionaryModel dictionaryModel, boolean choice){
        choice(choice);

        ContentValues initialValues = new ContentValues();
        initialValues.put(FIELD_WORD, dictionaryModel.getWord());
        initialValues.put(FIELD_MEAN, dictionaryModel.getMean());
        return database.insert(field, null, initialValues);
    }

    public void insertTransaction(ArrayList<DictionaryModel> list, boolean choice){
        choice(choice);

        String sql = "INSERT INTO "+field+" ("+FIELD_WORD+", "+FIELD_MEAN+") VALUES(?,?)";
        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);
        for(int i = 0; i <list.size(); i++){
            stmt.bindString(1, list.get(i).getWord());
            stmt.bindString(2, list.get(i).getMean());
            stmt.execute();
            stmt.clearBindings();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
}
