package com.example.dell.kamusdicoding.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_ENG_TO_IND = "eng_ind";
    public static String TABLE_IND_TO_ENG = "ind_eng";

    public static final class DictionaryColumns implements BaseColumns{
        //word
        public static String FIELD_WORD = "word";

        //mean
        public static String FIELD_MEAN = "mean";
    }
}
