package com.hui.tally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hui.tally.db.AccountBean;
import com.hui.tally.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hui.tally.adapter.AccountAdapter;

public class MainActivity extends AppCompatActivity {
    ListView todayLv;//今日收支情况
    //声明数据源
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        todayLv = findViewById((R.id.main_lv));
        mDatas = new ArrayList<>();
        //设置适配器加载每一行数据到列表当中
        adapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(adapter);
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //当Activity获取焦点时会调用
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();
    }

    private void loadDBData() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }
    public void onCLick(View v) {
        switch (v.getId()) {
            case R.id.main_iv_search:
                break;
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, RecordActivity.class);
                startActivity(it1);
                break;
            case R.id.main_btn_more:
                break;
        }
    }


}

