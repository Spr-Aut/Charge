package com.hui.tally.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.hui.tally.R;

public class RemarkDialog extends Dialog implements View.OnClickListener{
    EditText editText;
    Button cancelBtn,ensureBtn;
    OnEnsureListener onEnsureListener;
    //设置接口回调的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remark);//设置备注对话框显示布局
        editText=findViewById(R.id.dialog_remark_et);
        cancelBtn=findViewById(R.id.dialog_remark_btn_cancel);
        ensureBtn=findViewById(R.id.dialog_remark_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }
    public interface OnEnsureListener{
        public void onEnsure();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_remark_btn_cancel:
                cancel();
                break;
            case R.id.dialog_remark_btn_ensure:
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure();//接口回调
                }
                break;
        }
    }
    //获取输入数据的方法
    public String getEditText(){
        return editText.getText().toString().trim();//.trim()去掉前后的空格和回车等
    }
    //设置Dialog的尺寸
    public void setDialogSize(){
        //获取当前窗口对象
        Window window = getWindow();
        //获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
        //获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width=(int)(d.getWidth());//使对话框的窗口和屏幕一样宽
        wlp.gravity=Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);

        handler.sendEmptyMessageDelayed(1,100);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //自动弹出键盘的方法
            InputMethodManager inputMethodManager=(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}
