package com.hui.tally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hui.tally.db.AccountBean;
import com.hui.tally.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hui.tally.adapter.AccountAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView todayLv;//今日收支情况
    ImageView searchIV,editBtn;
    ImageButton moreBtn;
    //声明数据源
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year, month, day;
    //头布局相关控件
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv;
    ImageView topShowIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        initView();
        //添加ListView的头布局
        addLVHeaderView();
        mDatas = new ArrayList<>();
        //设置适配器加载每一行数据到列表当中
        adapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(adapter);
    }
    //初始化自带View的方法
    private void initView() {
        todayLv = findViewById((R.id.main_lv));
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn=findViewById(R.id.main_btn_more);
        searchIV=findViewById(R.id.main_iv_search);
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIV.setOnClickListener(this);
    }

    //给ListView添加头布局
    private void addLVHeaderView() {
        //将布局转换成View对象
        headerView=getLayoutInflater().inflate(R.layout.item_mainlv_top,null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide);

        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topShowIv.setOnClickListener(this);
    }
    //获取今日的具体时间
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_iv_search:

                break;
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, RecordActivity.class);
                startActivity(it1);
                break;
            case R.id.main_btn_more:

                break;
            case R.id.item_mainlv_top_tv_budget:

                break;
            case R.id.item_mainlv_top_iv_hide:
                break;
        }
        if (v==headerView) {
            //头布局被点击了
        }
    }
}

