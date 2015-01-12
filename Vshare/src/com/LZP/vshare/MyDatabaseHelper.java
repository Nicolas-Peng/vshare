package com.LZP.vshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "Register.db";
	private static final String TABLE_NAME = "Register";
	private static final int DB_VERSION = 1;

	public MyDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String SQL_CREATE_TABLE = "create table " + TABLE_NAME
				+ " (_id integer primary key autoincrement,"+
				" name nchar(30),password nchar(20));";
		Log.d("Database Oncreate","OnCreate!!!!!!!!");
		db.execSQL(SQL_CREATE_TABLE);
	}
/*
	public void insert1(String ssn, String name, String _class, String phone) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into Register values(ssn,name,_class,phone);");
		db.close();
		Log.d("Insert1111","Now in Insert111111");
	}
*/
	public void insert2(String name, String password) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("password", password);
		long rid = db.insert(TABLE_NAME, null, values);
		db.close();
	}

	public void update(String name,String new_password) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("password", new_password);
		String whereClause = "name=?";
		String[] whereArgs = { name };
		int rows = db.update(TABLE_NAME, values, whereClause, whereArgs);
		db.close();
	}

	public void deleteById(String ssn ){
		SQLiteDatabase db=getWritableDatabase();
		String whereClause = "ssn=?";
		String[] whereArgs = {ssn};
		int row = db.delete(TABLE_NAME, whereClause, whereArgs);
		db.close();
	}
	
	public Cursor query(){
		SQLiteDatabase db = getReadableDatabase();
		//获取所有行
		Cursor c= db.query(TABLE_NAME,null,null,null,null,null,null);
		//Cursor c2=db.rawQuery("select * from TABLE_NAME",null);
		/*
		while(c.moveToNext()){
			Log.d("query","query");
			System.out.println(c.getString(1)); //,返回本行第二列数据
			System.out.println(c.getString(2));
		}
		*/
		return c;
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
