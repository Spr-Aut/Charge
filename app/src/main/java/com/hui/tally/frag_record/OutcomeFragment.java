package com.hui.tally.frag_record;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hui.tally.R;
import com.hui.tally.db.AccountBean;
import com.hui.tally.db.DBManager;
import com.hui.tally.db.TypeBean;

import java.util.List;

public class OutcomeFragment extends BaseRecordFragment{

    //重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("餐饮");
        typeIv.setImageResource(R.mipmap.ic_canyin_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.insertItemToAccounttb(accountBean);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean=new AccountBean();//创建对象
        accountBean.setTypename("餐饮");//默认是"其他"
        accountBean.setsImageId(R.mipmap.ic_canyin_fs);
    }
}
