package com.example.project.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project.Model.todoItem;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static  final String DATABASE_NAME="TODOLIST_DB";
    private static  final String TABLE_NAME="TODO_TABLE";
    private static  final String COL1_NAME="ID";
    private static  final String COL2_NAME="NAME";
    private static  final String COL3_NAME="STATUS";
    public DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, STATUS INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void insertItem(todoItem item){
        db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2_NAME,item.getItem());
        values.put(COL3_NAME,0);
        db.insert(TABLE_NAME, null, values);
    }

    public void updateItemName(int id, String itemName){
        db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2_NAME,itemName);
        db.update(TABLE_NAME,values,"ID=?", new String[]{String.valueOf(id)});
    }
    public void updateItemStatus(int id, int itemStatus){
        db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL3_NAME,itemStatus);
        db.update(TABLE_NAME,values,"ID=?", new String[]{String.valueOf(id)});
    }
    public void deleteItem(int id){
        db= this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(id)});
    }
    public List<todoItem> getAllItem(){
        db= this.getWritableDatabase();
        Cursor cr = null;
        List<todoItem> itemList =new ArrayList<>();

        db.beginTransaction();
        try{
            cr = db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cr != null){
                if(cr.moveToFirst()){
                    do{
                        todoItem i = new todoItem();
                        i.setId(cr.getInt(0));
                        i.setItemName(cr.getString(1));
                        i.setStatus(cr.getInt(2));
                        itemList.add(i);
                    }
                    while (cr.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cr.close();
        }
        return itemList;
        }
}
