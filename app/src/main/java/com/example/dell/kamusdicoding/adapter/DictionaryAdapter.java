package com.example.dell.kamusdicoding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.kamusdicoding.R;
import com.example.dell.kamusdicoding.model.DictionaryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryHolder>{

    private ArrayList<DictionaryModel> arrayList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public DictionaryAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    public void replaceAll(ArrayList<DictionaryModel> list){
        arrayList = list;
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<DictionaryModel> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public DictionaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word_list, parent, false);
        return new DictionaryHolder(view);
    }

    @Override
    public void onBindViewHolder(DictionaryHolder holder, int position) {
        holder.tvWord.setText(arrayList.get(position).getWord());
        holder.tvMean.setText(arrayList.get(position).getMean());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class DictionaryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_word)
        TextView tvWord;
        @BindView(R.id.tv_mean)
        TextView tvMean;

        public DictionaryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
