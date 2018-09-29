package com.example.dell.kamusdicoding.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.dell.kamusdicoding.R;
import com.example.dell.kamusdicoding.adapter.DictionaryAdapter;
import com.example.dell.kamusdicoding.helper.DictionaryHelper;
import com.example.dell.kamusdicoding.model.DictionaryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BahasaFragment extends Fragment {

    @BindView(R.id.search_bar)
    SearchView searchView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    DictionaryAdapter adapter;
    DictionaryHelper helper;

    ArrayList<DictionaryModel> list = new ArrayList<>();

    public BahasaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.ind_eng));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bahasa, container, false);
        ButterKnife.bind(this, view);

        helper = new DictionaryHelper(getActivity());
        adapter = new DictionaryAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

//        initAllData();

        searchView.setQueryHint(getString(R.string.search_bar));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initDataById(newText);
                return true;
            }
        });

        return view;
    }

    private void initDataById(String query){
        helper.open();
        list = helper.getByWord(query, false);
        helper.close();
        Log.d("dataa:", String.valueOf(list.size()));
        adapter.replaceAll(list);
    }

//    private void initAllData() {
//        helper.open();
//        list = helper.getAllData(false);
//        helper.close();
//        Log.d("dataa:", String.valueOf(list.size()));
//        adapter.addAll(list);
//    }


}
