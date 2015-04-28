package com.crossshop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryOpenHelperCrossDB extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "crossdb.db";
	private static final int DATABASE_VERSION = 1;

	public static final String ITEM_TABLE = "CrossDB";
	public static final String COLUMN_ID = "dbId";
	public static final String COLUMN_ITEM_ID = "itemId";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_BRAND = "brand";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_PRIZE = "prize";
	public static final String COLUMN_DISCOUNT = "discount";
	public static final String COLUMN_DESCRIPTION = "description";

	private static final String TABLE_CREATE = 
			"CREATE TABLE " + ITEM_TABLE + " (" + 
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_ITEM_ID + " TEXT, " +
					COLUMN_CATEGORY + " TEXT, " +
					COLUMN_NAME + " TEXT, " +
					COLUMN_BRAND + " TEXT, " +
					COLUMN_IMAGE + " TEXT, " +
					COLUMN_PRIZE + " TEXT, " +
					COLUMN_DISCOUNT + " TEXT, " +
					COLUMN_DESCRIPTION + " TEXT " +
					")";	

	DictionaryOpenHelperCrossDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);
		onCreate(db);
	}

	final static public String[] getColumns() {
		String[] columns = new String[10];
		columns[0] = DictionaryOpenHelperCrossDB.ITEM_TABLE;
		columns[1] = DictionaryOpenHelperCrossDB.COLUMN_ID;
		columns[2] = DictionaryOpenHelperCrossDB.COLUMN_ITEM_ID;
		columns[3] = DictionaryOpenHelperCrossDB.COLUMN_CATEGORY;
		columns[4] = DictionaryOpenHelperCrossDB.COLUMN_NAME;
		columns[5] = DictionaryOpenHelperCrossDB.COLUMN_BRAND;
		columns[6] = DictionaryOpenHelperCrossDB.COLUMN_IMAGE;
		columns[7] = DictionaryOpenHelperCrossDB.COLUMN_PRIZE;
		columns[8] = DictionaryOpenHelperCrossDB.COLUMN_DISCOUNT;
		columns[9] = DictionaryOpenHelperCrossDB.COLUMN_DESCRIPTION;
		return columns;
	}
}
