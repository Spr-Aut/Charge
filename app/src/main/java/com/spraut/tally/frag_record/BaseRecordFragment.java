package com.spraut.tally.frag_record;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.spraut.tally.R;
import com.spraut.tally.db.AccountBean;
import com.spraut.tally.db.TypeBean;
import com.spraut.tally.utils.KeyBoardUtils;
import com.spraut.tally.utils.RemarkDialog;
import com.spraut.tally.utils.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener{
    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv,remarkTv,timeTv;
    GridView typeGv;
    List<TypeBean>typeList;
    TypeBaseAdapter adapter;
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
            /*private Vibrator vibrator;
            private Context context;
            public void Adapter(Context context, List<TypeBean>items){
                this.context=context;
            }*/
            @RequiresApi(api = Build.VERSION_CODES.Q)
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

                //震动

                /*vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(30);*/
                Vibrator vibrator=(Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK));
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
        remarkTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        //显示自定义软键盘
        KeyBoardUtils boardUtils=new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();
        //设置接口，监听确定按钮被点击
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //获取输入钱数
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float money= Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                //获取记录的信息
                saveAccountToDB();
                //返回上一级页面
                getActivity().finish();
            }

        });
    }
    //让子类重写此方法
    public abstract void saveAccountToDB();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_record_tv_time:
            showTimeDialog();
                break;
            case R.id.frag_record_tv_remark:
                showRMDialog();
                break;
        }
        Vibrator vibrator=(Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK));
    }

    //弹出显示时间的对话框
    private void showTimeDialog(){
        SelectTimeDialog dialog = new SelectTimeDialog(getContext());
        dialog.show();
        //设定确定按钮被点击的监听器
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }

    //弹出备注对话框
    public void showRMDialog(){
        RemarkDialog dialog = new RemarkDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new RemarkDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = dialog.getEditText();
                if (!TextUtils.isEmpty(msg)) {
                    remarkTv.setText(msg);
                    accountBean.setRemark(msg);
                }
                dialog.cancel();
            }
        });
    }
}