package com.crossshop;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.crossshop.arrayadapter.LastshopListArrayAdapter;
import com.crossshop.database.DictionaryOpenHelperCrossDB;
import com.crossshop.database.ItemDB;
import com.crossshop.database.ItemDataSource;

public class ListViewPrototype extends ListActivity {

	List<ItemDB> itemsFiltered;

	String filter;

	ItemDataSource datasource;

	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_prototype);

		btn = (Button) findViewById(R.id.add_to_cart);

		datasource = new ItemDataSource(this, DictionaryOpenHelperCrossDB.getColumns());
		datasource.open();

		itemsFiltered = new ArrayList<ItemDB>();

		Intent intent = getIntent();
		filter = intent.getStringExtra("com.smartshop.ListViewPrototype.filter");
		if(filter.equals("name")) {
			itemsFiltered = datasource.findFilteredName("name LIKE ?", new String[]{ '%' + intent.getStringExtra("com.smartshop.ListViewPrototype.name")+'%' }); 
			if(itemsFiltered.size() == 0) {
				itemsFiltered = datasource.findAll();
				Toast.makeText(getApplicationContext(), "No item found with that name...", Toast.LENGTH_LONG).show();
			}
		}else if(filter.equals("category")) {
			itemsFiltered = datasource.findFilteredName("category LIKE ?", new String[]{ intent.getStringExtra("com.smartshop.ListViewPrototype.category") });			
		}
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		final LastshopListArrayAdapter adapter = new LastshopListArrayAdapter(this, itemsFiltered, size, true);
		getListView().setAdapter(adapter);			

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//NOTA: MODIFICARE QUESTA PARTE
				Intent intent = new Intent();
				intent.putIntegerArrayListExtra("com.smartshop.ListViewPrototype.selectedItems", (ArrayList<Integer>) adapter.getSelectedItem());
				setResult(-1, intent);
				finish();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		datasource.close();
	}
}
