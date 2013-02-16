package com.myphonemanager.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "myphonemanager.sqlite";
	private static final int DB_VERSION = 3;
	
	private static final String TB_BAD_PHONE = "bad_phone";
	private static final String TB_GOOD_PHONE = "good_phone";
	
	private static final String KEY_ID = "id";
	private static final String KEY_PHONE_NAME = "phone_name";
	private static final String KEY_PHONE_NUM = "phone_number";
	private static final String KEY_PHONE_MSG = "phone_msg";
	
	
	public MySQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_BAG_TABLE = "CREATE TABLE " + TB_BAD_PHONE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHONE_NAME + " TEXT," + KEY_PHONE_NUM + " TEXT" + ")";
		String CREATE_GOOD_TABLE = "CREATE TABLE " + TB_GOOD_PHONE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHONE_NAME + " TEXT," + KEY_PHONE_NUM + " TEXT," + KEY_PHONE_MSG + " TEXT" + ")";
	    db.execSQL(CREATE_BAG_TABLE);
	    db.execSQL(CREATE_GOOD_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TB_BAD_PHONE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_GOOD_PHONE);
 
        // Create tables again
        onCreate(db);
	}
	
	public void addBadPhone(BadPhone phone) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_PHONE_NAME, phone.getName());
	    values.put(KEY_PHONE_NUM, phone.getNumber());
	 
	    db.insert(TB_BAD_PHONE, null, values);
	    db.close();
	}
	
	public void addGoodPhone(GoodPhone phone) {
		SQLiteDatabase db = this.getWritableDatabase();
		
	    ContentValues values = new ContentValues();
	    values.put(KEY_PHONE_NAME, phone.getName());
	    values.put(KEY_PHONE_NUM, phone.getNumber());
	    values.put(KEY_PHONE_MSG, phone.getMsg());
	    
	    db.insert(TB_GOOD_PHONE, null, values);
	    db.close();
	}
	
	public List<BadPhone> getAllBadPhones() {
	    List<BadPhone> phoneList = new ArrayList<BadPhone>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TB_BAD_PHONE;
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if ( cursor != null ) {
	    	if (cursor.moveToFirst()) {
	    		do {
	    			BadPhone phone = new BadPhone(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
	    			// Adding phone to list
	    			phoneList.add(phone);
	    		} while (cursor.moveToNext());
	    	}
	    	cursor.close();
	    }
	    db.close();
	 
	    // return phone list
	    return phoneList;
	}
	
	public List<GoodPhone> getAllGoodPhones() {
	    List<GoodPhone> phoneList = new ArrayList<GoodPhone>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TB_GOOD_PHONE;
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if ( cursor != null ) {
		    if (cursor.moveToFirst()) {
		        do {
		        	GoodPhone phone = new GoodPhone(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
		            // Adding phone to list
		        	phoneList.add(phone);
		        } while (cursor.moveToNext());
		    }
		    cursor.close();
	    }
	    db.close();
	 
	    // return phone list
	    return phoneList;
	}
	
	public int getBadPhonesCount() {
		String countQuery = "SELECT  * FROM " + TB_BAD_PHONE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
	}
	
	public int getGoodPhonesCount() {
		String countQuery = "SELECT  * FROM " + TB_GOOD_PHONE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
	}
	
	public boolean hasBadPhone(String phone_num) {
		return (getBadPhone(phone_num) != null);
	}
	
	public boolean hasGoodPhone(String phone_num) {
		return (getGoodPhone(phone_num) != null);
	}
	
	public BadPhone getBadPhone(String phone_num) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = db.query(TB_BAD_PHONE, new String[] { KEY_ID,
	    		KEY_PHONE_NAME, KEY_PHONE_NUM }, KEY_PHONE_NUM + "=?",
	            new String[] { phone_num }, null, null, null, null);
	    if (cursor != null && cursor.getCount() != 0) {
	        cursor.moveToFirst();
	    } else {
	    	return null;
	    }
	    
	    BadPhone phone = new BadPhone(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
	    cursor.close();
	    db.close();
	    return phone;
	}
	
	public GoodPhone getGoodPhone(String phone_num) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = db.query(TB_GOOD_PHONE, new String[] { KEY_ID,
	    		KEY_PHONE_NAME, KEY_PHONE_NUM, KEY_PHONE_MSG }, KEY_PHONE_NUM + "=?",
	            new String[] { phone_num }, null, null, null, null);
	    if (cursor != null && cursor.getCount() != 0) {
	        cursor.moveToFirst();
	    } else {
	    	return null;
	    }
	    
	    GoodPhone phone = new GoodPhone(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
	    cursor.close();
	    db.close();
	    return phone;
	}
	
	public int updateBadPhone(BadPhone phone) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
		ContentValues values = new ContentValues();
	    values.put(KEY_PHONE_NAME, phone.getName());
	    values.put(KEY_PHONE_NUM, phone.getNumber());
	 
	    // updating row
	    return db.update(TB_BAD_PHONE, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(phone.getID()) });
	}

	public int updateGoodPhone(GoodPhone phone) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
		ContentValues values = new ContentValues();
	    values.put(KEY_PHONE_NAME, phone.getName());
	    values.put(KEY_PHONE_NUM, phone.getNumber());
	    values.put(KEY_PHONE_MSG, phone.getMsg());
	 
	    // updating row
	    return db.update(TB_GOOD_PHONE, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(phone.getID()) });
	}
	
	public void delBadPhone(BadPhone phone) {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TB_BAD_PHONE, KEY_ID + " = ?",
	            new String[] { String.valueOf(phone.getID()) });
	    db.close();
	}
	
	public void delGoodPhone(GoodPhone phone) {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TB_GOOD_PHONE, KEY_ID + " = ?",
	            new String[] { String.valueOf(phone.getID()) });
	    db.close();
	}
	
	public BadPhone getBadPhoneByPosition(int position) {
		List<BadPhone> phones = this.getAllBadPhones();
		return phones.get(position);
	}
	
	public GoodPhone getGoodPhoneByPosition(int position) {
		List<GoodPhone> phones = this.getAllGoodPhones();
		return phones.get(position);
	}
}
