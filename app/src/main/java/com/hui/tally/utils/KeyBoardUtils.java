package com.hui.tally.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.hui.tally.R;

public class KeyBoardUtils {
    private KeyboardView keyboardView;
    private EditText editText;
    private final Keyboard k1;//自定义键盘

    public interface OnEnsureListener{
        public void onEnsure();
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }


    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);//防止弹出系统键盘
        k1 = new Keyboard(this.editText.getContext(), R.xml.key);

        this.keyboardView.setKeyboard(k1);//设置要显示键盘的样式
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);//设置键盘按钮被点击的监听
    }
    KeyboardView.OnKeyboardActionListener listener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {
        }
        @Override
        public void onRelease(int i) {
        }
        @Override
        public void onKey(int primaryCode, int[] KeyCodes) {
            Editable editable=editText.getText();
            int start=editText.getSelectionStart();
            switch (primaryCode){
                case Keyboard.KEYCODE_DELETE:
                    if(editable!=null&&editable.length()>0){
                        if(start>0){
                            editable.delete(start-1,start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    editable.clear();
                    break;
                case Keyboard.KEYCODE_DONE:
                    onEnsureListener.onEnsure();//通过接口回调的方法，点击确定时，调用此方法
                    break;
                default:
                    editable.insert(start,Character.toString((char)primaryCode));
                    break;
            }
        }
        @Override
        public void onText(CharSequence text) {
        }
        @Override
        public void swipeLeft() {
        }
        @Override
        public void swipeRight() {
        }
        @Override
        public void swipeDown() {
        }
        @Override
        public void swipeUp() {
        }
    };

    //显示键盘
    public void showKeyboard(){
        int visibility=keyboardView.getVisibility();
        if (visibility== View.INVISIBLE||visibility==View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    //隐藏键盘
    public void hideKeyboard(){
        int visibility=keyboardView.getVisibility();
        if(visibility==View.VISIBLE||visibility==View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }

}
