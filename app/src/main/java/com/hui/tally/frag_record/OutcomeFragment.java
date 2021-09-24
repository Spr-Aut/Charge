package com.hui.tally.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.hui.tally.R;
import com.hui.tally.utils.KeyBoardUtils;

public class OutcomeFragment extends Fragment{
    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv,remarkTv,timeTv;
    GridView typeGv;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_outcome,container,false);
        initView(view);
        return view;
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
                //点击了确定按钮
                //获取记录的信息
                //返回上一级页面
            }
        });
    }
}