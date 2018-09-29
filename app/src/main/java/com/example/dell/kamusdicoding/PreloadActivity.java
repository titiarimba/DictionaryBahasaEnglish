package com.example.dell.kamusdicoding;

import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.dell.kamusdicoding.helper.AppPreference;
import com.example.dell.kamusdicoding.helper.DictionaryHelper;
import com.example.dell.kamusdicoding.model.DictionaryModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreloadActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        ButterKnife.bind(this);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void>{

        DictionaryHelper helper;
        AppPreference preference;
        double progress;
        double maxprogress = 100;

        public void onPreExecute() {
            helper = new DictionaryHelper(getApplicationContext());
            preference = new AppPreference(getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = preference.getFirstRun();
            if (firstRun){
                ArrayList<DictionaryModel> english = preLoadRaw(R.raw.english_indonesia);
                ArrayList<DictionaryModel> bahasa = preLoadRaw(R.raw.indonesia_english);
                Log.d("length", String.valueOf(bahasa.size()));
                publishProgress((int) progress);

                try{
                    helper.open();
                } catch (SQLException e){
                    e.printStackTrace();
                }

                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) /
                        (english.size() + bahasa.size());

                helper.insertTransaction(english, true);
                progress += progressDiff;
                publishProgress((int) progress);

                helper.insertTransaction(bahasa, false);
                progress += progressDiff;
                publishProgress((int) progress);

                helper.close();
                preference.setFirstRun(false);

                publishProgress((int) maxprogress);
            }
            else {
                try{
                    synchronized (this){
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(300);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(PreloadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private ArrayList<DictionaryModel> preLoadRaw(int data) {
        ArrayList<DictionaryModel> list = new ArrayList<>();
        BufferedReader reader;
        try{
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line = null;
            do{
                line =  reader.readLine();
                String[] splitstr = line.split("\t");
                DictionaryModel dict;
                dict = new DictionaryModel(splitstr[0], splitstr[1]);
                list.add(dict);
            } while (line != null);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }
}
