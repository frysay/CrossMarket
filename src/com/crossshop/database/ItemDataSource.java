package com.crossshop.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.crossshop.R;

public class ItemDataSource {

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	private String[] columns;

	public static final String[] categories = {"Bevande",
		"Colazione",
		"Igiene",
		"Pasta/pane",
		"Scatolette",
		"Cucina",
	"Snacks"};

	public static final int[] categories_icons = { R.drawable.bevande, R.drawable.colazione, R.drawable.cosmetici, R.drawable.pasta, R.drawable.scatolette, R.drawable.cucina, R.drawable.snacks };

	public ItemDataSource(Context context, String[] columns) {
		if(columns[0].equals(DictionaryOpenHelperCrossDB.ITEM_TABLE)) {
			dbhelper = new DictionaryOpenHelperCrossDB(context);
		}else if(columns[0].equals(DictionaryOpenHelperCoop.ITEM_TABLE)) {
			dbhelper = new DictionaryOpenHelperCoop(context);
		}else if(columns[0].equals(DictionaryOpenHelperConad.ITEM_TABLE)) {
			dbhelper = new DictionaryOpenHelperConad(context);
		}else if(columns[0].equals(DictionaryOpenHelperCrai.ITEM_TABLE)) {
			dbhelper = new DictionaryOpenHelperCrai(context);
		}else{
			Toast.makeText(context, "Error: Unkown database", Toast.LENGTH_LONG).show();
		}
		this.columns = Arrays.copyOf(columns, columns.length+1);
		this.columns[columns.length] = "( replace(name, ltrim(name, \'1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM!#$@^&*()_+-=`~[]\\/{}|;:,.<>?\'), \'\')) AS justname";
	}

	public void open() {	
		database = dbhelper.getWritableDatabase();
	}	

	public void close() {	
		dbhelper.close();		
	}

	public ItemDB create(ItemDB item) {
		ContentValues values = new ContentValues();
		values.put(columns[2], item.getItemId());
		values.put(columns[3], item.getCategory());
		values.put(columns[4], item.getNameAndMarket());
		values.put(columns[5], item.getBrand());
		values.put(columns[6], item.getImage());
		values.put(columns[7], item.getPrize());
		values.put(columns[8], item.getDiscount());
		values.put(columns[9], item.getDesciption());
		long insertid = database.insert(columns[0], null, values);
		item.setId(insertid);
		return item;	
	}

	public boolean removeItem(ItemDB item) {
		String where = columns[1] + "=" + item.getId();
		int result = database.delete(columns[0], where, null);
		return (result == 1);
	}

	public List<ItemDB> findAll() {		
		Cursor cursor = database.query(columns[0], Arrays.copyOfRange(columns, 1, columns.length-1), null, null, null, null, null);
		return cursorToList(cursor);
	}

	public List<ItemDB> findFilteredNameFull(String selection, String[] selectionArgs, String orderBy) {		
		Cursor cursor = database.query(columns[0], Arrays.copyOfRange(columns, 1, columns.length-1), selection, selectionArgs, null, null, orderBy);
		return cursorToList(cursor);
	}

	public List<ItemDB> findFilteredName(String selection, String[] selectionArgs) {		
		Cursor cursor = database.query(columns[0], Arrays.copyOfRange(columns, 1, columns.length), selection, selectionArgs, null, null, "10 ASC, prize ASC");
		return cursorToList(cursor);
	}

	public double findPrizeFromName(String name) {
		Cursor cursor = database.query(columns[0], Arrays.copyOfRange(columns, 1, columns.length), columns[4] + " LIKE ?", new String[] { name+'%' }, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		try {
			return cursor.getDouble(cursor.getColumnIndex(columns[7]));			
		} catch (Exception e) {
			return 0.0;
		}
	}

	public ItemDB findItemFromId(long itemId) {
		Cursor cursor = database.query(columns[0], Arrays.copyOfRange(columns, 1, columns.length), columns[1] + "=?", new String[] { String.valueOf(itemId) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		ItemDB item = new ItemDB();
		item.setId(cursor.getLong(cursor.getColumnIndex(columns[1])));
		item.setItemId(cursor.getString(cursor.getColumnIndex(columns[2])));
		item.setCategory(cursor.getString(cursor.getColumnIndex(columns[3])));
		item.setName(cursor.getString(cursor.getColumnIndex(columns[4])));
		item.setBrand(cursor.getString(cursor.getColumnIndex(columns[5])));
		item.setImage(cursor.getInt(cursor.getColumnIndex(columns[6])));
		item.setPrize(cursor.getDouble(cursor.getColumnIndex(columns[7])));
		item.setDiscount(cursor.getInt(cursor.getColumnIndex(columns[8])));
		item.setDesciption(cursor.getString(cursor.getColumnIndex(columns[9])));

		return item;
	}

	private List<ItemDB> cursorToList(Cursor cursor) {
		List<ItemDB> items = new ArrayList<ItemDB>();
		if(cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				ItemDB item = new ItemDB();
				item.setId(cursor.getLong(cursor.getColumnIndex(columns[1])));
				item.setItemId(cursor.getString(cursor.getColumnIndex(columns[2])));
				item.setCategory(cursor.getString(cursor.getColumnIndex(columns[3])));
				item.setName(cursor.getString(cursor.getColumnIndex(columns[4])));
				item.setBrand(cursor.getString(cursor.getColumnIndex(columns[5])));
				item.setImage(cursor.getInt(cursor.getColumnIndex(columns[6])));
				item.setPrize(cursor.getDouble(cursor.getColumnIndex(columns[7])));
				item.setDiscount(cursor.getInt(cursor.getColumnIndex(columns[8])));
				item.setDesciption(cursor.getString(cursor.getColumnIndex(columns[9])));
				items.add(item);
			}	
		}

		return items;
	}

	public int updateItem(ItemDB item) {
		ContentValues values = new ContentValues();
		values.put(columns[2], item.getItemId());
		values.put(columns[3], item.getCategory());
		values.put(columns[4], item.getNameAndMarket());
		values.put(columns[5], item.getBrand());
		values.put(columns[6], item.getImage());
		values.put(columns[7], item.getPrize());
		values.put(columns[8], item.getDiscount());
		values.put(columns[9], item.getDesciption());
		return database.update(columns[0], values, columns[1] + " = ?",
				new String[] { String.valueOf(item.getId()) });
	}

	public String printCategory() {
		List<ItemDB> tmp = findAll();
		String print = "";
		for(int i = 0; i < tmp.size(); i++) {
			print = print + tmp.get(i).getCategory() + " ";
		}
		return print;
	}
}
