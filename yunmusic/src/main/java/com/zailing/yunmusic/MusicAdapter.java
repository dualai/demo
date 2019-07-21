package com.zailing.yunmusic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.NormalTextViewHolder>{
    private final LayoutInflater mLayoutInflater;
//    private String[] mTitles = null;
    private List<String> mTitles = null;

    public MusicAdapter(Context context) {
//        mTitles = context.getResources().getStringArray(R.array.music);
        mTitles = new ArrayList<>();
        for(int i = 0;i<10;i++){
            String[] arrays = context.getResources().getStringArray(R.array.music);
            mTitles.addAll(Arrays.asList(arrays));
        }
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.music_name.setText(mTitles.get(position));
        holder.item_postion.setText(position+"");
        Log.d("test","bind:"+position);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    class NormalTextViewHolder extends RecyclerView.ViewHolder {
        TextView music_name;
        TextView item_postion;
        NormalTextViewHolder(View view) {
            super(view);
            music_name = (TextView) view.findViewById(R.id.music_name);
            item_postion = view.findViewById(R.id.item_postion);

        }
    }
}
