package com.xzl.demo1.citys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xzl.demo1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CitiesActivity extends AppCompatActivity {

    private final static int Type_title = 0;
    private final static int Type_item = 1;

    private class CommonBean{
        String data;
        int type;
    }

    private RecyclerView rvList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        rvList = findViewById(R.id.rv_list);

        List<CommonBean> datas = new ArrayList<>();

        for(int i = 0;i<50;i++){
            CommonBean cTitleBean = new CommonBean();
            cTitleBean.type = Type_title;
            cTitleBean.data = "第"+i+"组";
            datas.add(cTitleBean);
            int count = 1 + new Random().nextInt(9);
            for(int j = 0;j<count;j++){
                CommonBean cItemBean = new CommonBean();
                cItemBean.type = Type_item;
                cItemBean.data =  "第"+j+"个";
                datas.add(cItemBean);
            }
        }


        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
               CommonBean commonBean = datas.get(i);
               if(commonBean.type == Type_item)return 1;
               return 3;
            }
        });
        rvList.setLayoutManager(layoutManager);
        final LayoutInflater inflater = LayoutInflater.from(this);
        rvList.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
                if (type == Type_title) {
                    return new MyViewHolderTitle(inflater.inflate(R.layout.cell_title,viewGroup,false));
                }else if(type == Type_item){
                    return new MyViewHolderItem(inflater.inflate(R.layout.cell_items,viewGroup,false));
                }
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int type = getItemViewType(i);
                if(type == Type_title){
                    MyViewHolderTitle titleHolder = (MyViewHolderTitle)viewHolder;
                    CommonBean cTitleBean = datas.get(i);
                    titleHolder.bindData(cTitleBean);
                }else if(type == Type_item){
                    MyViewHolderItem itemHolder = (MyViewHolderItem) viewHolder;
                    CommonBean cItemBean = datas.get(i);
                    itemHolder.bindData(cItemBean);
                }
            }

            @Override
            public int getItemViewType(int position) {
               return datas.get(position).type;
            }

            @Override
            public int getItemCount() {
                return datas.size();
            }
        });
    }



    private final class MyViewHolderItem extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolderItem(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView;
        }

        public void bindData(CommonBean itemBean){
            textView.setText(itemBean.data);
        }
    }

    private final class MyViewHolderTitle extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolderTitle(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView;
        }
        public void bindData(CommonBean titleBean){
            textView.setText(titleBean.data);
        }

    }
}
