package com.hui.tally.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/*
* 管理数据库
* 对表中的内容进行增删查改
* */
public class DBManager {
    private static SQLiteDatabase db;
    //初始化数据库对象
    public static void initDB(Context context){
        DBOpenHelper helper=new DBOpenHelper(context);//得到帮助类对象
        db=helper.getWritableDatabase();//得到数据库对象
    }
    /*
     * 读取数据库当中的数据，写入内存集合里
     *   kind :表示收入或者支出
     * */
    public static List<TypeBean>getTypeList(int kind){
        List<TypeBean>list=new ArrayList<>();
        //读取typetb表中的数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor=db.rawQuery(sql,null);
        //循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind);
            list.add(typeBean);
        }
        return list;
    }
    //向记账表中插入一条元素
    public static void insertItemToAccounttb(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("sImageId",bean.getsImageId());
        values.put("remark",bean.getRemark());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        db.insert("accounttb",null,values);
    }
    /*
    * 获取记账表中某一天的所有支出或者收入情况
    * */
    public static List<AccountBean>getAccountListOneDayFromAccounttb(int year,int month,int day){
        List<AccountBean>list=new ArrayList<>();
        String sql="select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, remark, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    /*
     * 获取记账表中当年的所有支出或者收入情况
     * */
    public static List<AccountBean>getAccountListAllFromAccounttb(int year,int month){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "",month + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));

            AccountBean accountBean = new AccountBean(id, typename, sImageId, remark, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    /*
    * 获取某一天的支出或收入的总金额 支出kind==0 收入kind==1
    * */
    public static float getSumMoneyOneDay(int year,int month,int day,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
    /*
     * 获取某一月的支出或者收入的总金额   kind：支出==0    收入===1
     * */
    public static float getSumMoneyOneMonth(int year,int month,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
    /* 统计某月份支出或者收入情况有多少条  收入-1   支出-0*/
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        //遍历
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }
    /*
     * 获取某一年的支出或者收入的总金额   kind：支出==0    收入===1
     * */
    public static float getSumMoneyOneYear(int year,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

}
