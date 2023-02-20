package com.spraut.tally.frag_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spraut.tally.R;
import com.spraut.tally.db.TypeBean;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {
    Context context;
    List<TypeBean>mDatas;
    int selectPos=0;//被点击的位置

    public TypeBaseAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
//此适配器不考虑复用问题
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv,parent,false);
        //查找布局中的控件
        ImageView imageView=convertView.findViewById(R.id.item_recordfrag_iv);
        TextView textView=convertView.findViewById(R.id.item_recordfrag_tv);
        //获取指定位置的数据源
        TypeBean typeBean=mDatas.get(position);
        textView.setText(typeBean.getTypename());
        if(selectPos==position){
            imageView.setImageResource(typeBean.getSimageId());
        }else {
            imageView.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }
}
