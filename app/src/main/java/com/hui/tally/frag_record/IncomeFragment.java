package com.hui.tally.frag_record;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hui.tally.R;
import com.hui.tally.db.AccountBean;
import com.hui.tally.db.DBManager;
import com.hui.tally.db.TypeBean;

import java.util.List;


public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.in_qt_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.insertItemToAccounttb(accountBean);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean=new AccountBean();//创建对象
        accountBean.setTypename("其他");//默认是"其他"
        accountBean.setsImageId(R.mipmap.in_qt_fs);
    }
}