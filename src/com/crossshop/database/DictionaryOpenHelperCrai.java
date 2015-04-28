package com.crossshop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryOpenHelperCrai extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "crai.db";
	private static final int DATABASE_VERSION = 1;

	public static final String ITEM_TABLE = "Crai";
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

	DictionaryOpenHelperCrai(Context context) {
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

	public static final String[] getColumns() {
		String[] columns = new String[10];
		columns[0] = DictionaryOpenHelperCrai.ITEM_TABLE;
		columns[1] = DictionaryOpenHelperCrai.COLUMN_ID;
		columns[2] = DictionaryOpenHelperCrai.COLUMN_ITEM_ID;
		columns[3] = DictionaryOpenHelperCrai.COLUMN_CATEGORY;
		columns[4] = DictionaryOpenHelperCrai.COLUMN_NAME;
		columns[5] = DictionaryOpenHelperCrai.COLUMN_BRAND;
		columns[6] = DictionaryOpenHelperCrai.COLUMN_IMAGE;
		columns[7] = DictionaryOpenHelperCrai.COLUMN_PRIZE;
		columns[8] = DictionaryOpenHelperCrai.COLUMN_DISCOUNT;
		columns[9] = DictionaryOpenHelperCrai.COLUMN_DESCRIPTION;
		return columns;
	}
}
