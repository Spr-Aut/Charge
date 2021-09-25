package com.hui.tally.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.hui.tally.R;

import java.util.Calendar;

public class SelectTimeDialog extends Dialog implements View.OnClickListener{
    EditText hourEt,minuteEt;
    DatePicker datePicker;
    Button ensureBtn,cancelBtn;
    Calendar c;

    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        hourEt = findViewById(R.id.dialog_time_et_hour);
        minuteEt = findViewById(R.id.dialog_time_et_minute);
        datePicker = findViewById(R.id.dialog_time_dp);
        ensureBtn = findViewById(R.id.dialog_time_btn_ensuro);
        cancelBtn = findViewById(R.id.dialog_time_btn_cancel);
        ensureBtn.setOnClickListener(this);  //添加点击监听事件
        cancelBtn.setOnClickListener(this);
        //hideDatePickerHeader();
        initView();
    }
    //隐藏DatePicker头布局
    private void initView() {
        datePicker=(DatePicker)findViewById(R.id.dialog_time_dp);
        ((LinearLayout) ((ViewGroup)datePicker.getChildAt(0)).getChildAt(0)).setVisibility(View.GONE);
        c= Calendar.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
            case R.id.dialog_time_btn_ensuro:

                break;
        }
    }

    //隐藏DatePicker头部  失败
    /*private void hideDatePickerHeader(){
        ViewGroup rootView =(ViewGroup) datePicker.getChildAt(0);
        if (rootView==null) {
            return;
        }
        View headerView = rootView.getChildAt(0);
        if (headerView==null) {
            return;
        }
        int headerId = getContext().getResources().getIdentifier("day_picker_header", "id", "android");
        if (headerId==headerView.getId()) {
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParamsRoot=rootView.getLayoutParams();
            layoutParamsRoot.width=ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsRoot);

            ViewGroup animator=(ViewGroup)rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator=animator.getLayoutParams();
            layoutParamsAnimator.width=ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;
        }
    }*/
}
