package com.example.babyneedsnew.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.database.CursorWindowCompat;

import com.example.babyneedsnew.model.Item;
import com.example.babyneedsnew.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;

    public DatabaseHandler(@Nullable Context context ) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
            this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE="CREATE TABLE "+Constants.TABLE_NAME+"("
                +Constants.KEY_ID+" INTEGER PRIMARY KEY,"
                +Constants.KEY_NAME+" TEXT,"
                +Constants.KEY_COLOR+" Text,"
                +Constants.KEY_QUANTITY+" INTEGER,"
                +Constants.KEY_SIZE+" INTEGER,"
                +Constants.KEY_DATE_ADDED+" LONG);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addItem(Item item){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Constants.KEY_NAME,item.getItemName());
        values.put(Constants.KEY_COLOR,item.getColor());
        values.put(Constants.KEY_QUANTITY,item.getQuantity());
        values.put(Constants.KEY_SIZE,item.getItem_size());
        values.put(Constants.KEY_DATE_ADDED,java.lang.System.currentTimeMillis());
        db.insert(Constants.TABLE_NAME,null,values);
        Log.d("Tokes", "ItemAddedSuccessFully ");
    }

    public Item getItem(int id){

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                            Constants.KEY_NAME,
                            Constants.KEY_COLOR,
                            Constants.KEY_QUANTITY,
                            Constants.KEY_SIZE,
                            Constants.KEY_DATE_ADDED},
                Constants.KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Item item=new Item();
        if (cursor!=null){
            item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
            item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME)));
            item.setColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
            item.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY)));
            item.setItem_size(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));

            DateFormat dateFormat=DateFormat.getDateInstance();
            String formateddate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)))
            .getTime());

            item.setDate_added(formateddate);
        }
        return item;
    }


    public ArrayList<Item> getAllItems()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Item> arrayList=new ArrayList<>();
        String SELECT_ALL="SELECT * FROM "+Constants.TABLE_NAME+" ORDER BY "+Constants.KEY_DATE_ADDED+ " DESC";

        Cursor cursor=db.rawQuery(SELECT_ALL,null);

        if (cursor.moveToFirst())
            do {
                Item item=new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME)));
                item.setColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY)));
                item.setItem_size(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));

                DateFormat dateFormat=DateFormat.getDateInstance();
                String formateddate=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_ADDED)))
                        .getTime());
                item.setDate_added(formateddate);

                arrayList.add(item);
            }while (cursor.moveToNext());

            return arrayList;
    }



    public int updateItem(Item item)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Constants.KEY_NAME,item.getItemName());
        values.put(Constants.KEY_COLOR,item.getColor());
        values.put(Constants.KEY_QUANTITY,item.getQuantity());
        values.put(Constants.KEY_SIZE,item.getItem_size());
        values.put(Constants.KEY_DATE_ADDED,item.getDate_added());

        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",
                new String[]{String.valueOf(item.getId())});

    }


    public void deleteItem(Item item){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",
                new String[]{String.valueOf(item.getId())});
    }

    public int getCount()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String COUNT_Q="SELECT * FROM "+Constants.TABLE_NAME;
        Cursor cursor=db.rawQuery(COUNT_Q,null);

        return cursor.getCount();

    }

}
