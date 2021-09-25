package com.hui.tally.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hui.tally.R;
import com.hui.tally.db.AccountBean;
import com.hui.tally.db.DBManager;
import com.hui.tally.db.TypeBean;
import com.hui.tally.utils.KeyBoardUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BaseRecordFragment extends Fragment{
    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv,remarkTv,timeTv;
    GridView typeGv;
    List<TypeBean>typeList;
    public TypeBaseAdapter adapter;
    AccountBean accountBean;//将需要插入到记账本中的数据保存成对象的形式

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean=new AccountBean();//创建对象
        accountBean.setTypename("其他");//默认是"其他"
        accountBean.setsImageId(R.mipmap.ic_qita_fs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_outcome,container,false);
        initView(view);
        setInitTime();
        //给GridView填充数据的方法
        loadDataToGV();
        setGVListener();//GridView每一项的点击事件
        return view;
    }
    //获取当前时间，显示到timeTv上
    private void setInitTime() {
        Date date=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time=sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;//一月是0，二月是1
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    //GridView每一项的点击事件
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.selectPos=position;
                adapter.notifyDataSetInvalidated();//提示绘制发生变化了
                TypeBean typeBean=typeList.get(position);
                String typename=typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int simageId=typeBean.getSimageId();
                typeIv.setImageResource(simageId);
                accountBean.setsImageId(simageId);

            }
        });
    }

    //给GridView填充数据的方法
    public void loadDataToGV() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);

    }

    private void initView(View view) {
        keyboardView=view.findViewById(R.id.frag_record_keyboard);
        moneyEt=view.findViewById(R.id.frag_record_et_money);
        typeIv=view.findViewById(R.id.frag_record_iv);
        typeGv=view.findViewById(R.id.frag_record_gv);
        typeTv=view.findViewById(R.id.frag_record_tv_type);
        remarkTv=view.findViewById(R.id.frag_record_tv_remark);
        timeTv=view.findViewById(R.id.frag_record_tv_time);
        //显示自定义软键盘
        KeyBoardUtils boardUtils=new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();
        //设置接口，监听确定按钮被点击
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //获取输入
                String moneyStr=moneyEt.getText().toString();
                if(!TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")){
                    getActivity().finish();
                    return;
                }
                float money= Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                //获取记录的信息

                //返回上一级页面
                getActivity().finish();
            }
        });
    }
}