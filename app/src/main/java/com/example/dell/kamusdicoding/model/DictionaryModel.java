package com.example.dell.kamusdicoding.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DictionaryModel implements Parcelable{

    private int id;
    private String word;
    private String mean;

    public DictionaryModel(Parcel in){
        //this.id = in.readInt();
        this.word = in.readString();
        this.mean = in.readString();
    }

    public DictionaryModel(String word, String mean) {
        this.word = word;
        this.mean = mean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.mean);
    }

//    protected DictionaryModel(Parcel in){
//        this.id = in.readInt();
//        this.word = in.readString();
//        this.mean = in.readString();
//    }

    public static final Parcelable.Creator<DictionaryModel> CREATOR = new Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}
