package com.crossshop;

import android.app.ListActivity;
import android.os.Bundle;

import com.crossshop.arrayadapter.UsersshopArrayAdapter;

public class UsersShop extends ListActivity {
	
	//NOTA: da modificare
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		
		UsersshopArrayAdapter adapter = new UsersshopArrayAdapter(this);
		getListView().setAdapter(adapter);
	}

}
