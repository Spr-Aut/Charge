package com.hui.tally.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}
