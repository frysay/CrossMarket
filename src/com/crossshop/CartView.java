package com.crossshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crossshop.arrayadapter.CheckOutArrayAdapter;
import com.crossshop.database.DictionaryOpenHelperConad;
import com.crossshop.database.DictionaryOpenHelperCoop;
import com.crossshop.database.DictionaryOpenHelperCrai;
import com.crossshop.database.DictionaryOpenHelperCrossDB;
import com.crossshop.database.ItemDB;
import com.crossshop.database.ItemDataSource;

public class CartView extends ListActivity {

	ItemDataSource datasource;

	LinearLayout ll;

	List<ItemDB> items;

	ImageView ib_conad;
	ImageView ib_coop;
	ImageView ib_crai;

	TextView tv_conad;
	TextView tv_coop;
	TextView tv_crai;
	TextView tv_prize;
	TextView tv_discount;

	int itemsInMarket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_view_layout);

		ll = (LinearLayout) findViewById(R.id.ll_checkout);

		datasource = new ItemDataSource(this, DictionaryOpenHelperCrossDB.getColumns());
		datasource.open();

		Bundle b = getIntent().getExtras();
		List<Integer> tmp = b.getIntegerArrayList("com.smartshop.CartView.cart");
		items = new ArrayList<ItemDB>();
		for(int i = 0; i < tmp.size(); i++) {
			items.add(datasource.findItemFromId(tmp.get(i)));
		}

		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		final CheckOutArrayAdapter adapter = new CheckOutArrayAdapter(this, items, size);
		getListView().setAdapter(adapter);			

		ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Ordine effettuato", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.putIntegerArrayListExtra("com.smartshop.CartView.selectedItems", (ArrayList<Integer>) new ArrayList<Integer>());
				setResult(-2, intent);
				finish();
			}
		});

		double myPrize, conadPrize, coopPrize, craiPrize = 0;

		ib_conad = (ImageView) findViewById(R.id.ib_conad);
		ib_coop = (ImageView) findViewById(R.id.ib_coop);
		ib_crai = (ImageView) findViewById(R.id.ib_crai);

		ib_conad.setImageResource(R.drawable.conad);
		ib_coop.setImageResource(R.drawable.coop);
		ib_crai.setImageResource(R.drawable.crai);

		tv_conad = (TextView) findViewById(R.id.tv_conad);
		tv_coop = (TextView) findViewById(R.id.tv_coop);
		tv_crai = (TextView) findViewById(R.id.tv_crai);

		conadPrize = Math.floor(prizeByMarket(DictionaryOpenHelperConad.getColumns()) * 100) / 100;
		tv_conad.setText("Totale: " + normalize(conadPrize) + "€\nN.  " + itemsInMarket + "/" + items.size() + "   +" + (int)(100-(100*tot()/conadPrize)) + "%");
		if(items.size() > itemsInMarket) {
			String first = "Totale: " + normalize(conadPrize) + "€<br>N.  ";
			String next = "<font color='#EE0000'>" + itemsInMarket + "</font>/" + items.size() + "&nbsp;&nbsp;&nbsp;+" + (int)(100-(100*tot()/conadPrize)) + "%";
			tv_conad.setText(Html.fromHtml(first + next));
		}
		coopPrize = Math.floor(prizeByMarket(DictionaryOpenHelperCoop.getColumns()) * 100) / 100;
		tv_coop.setText("Totale: " + normalize(coopPrize) + "€\nN. " + itemsInMarket + "/" + items.size() + "   +" + (int)(100-(100*tot()/coopPrize)) + "%");	
		if(items.size() > itemsInMarket) {
			String first = "Totale: " + normalize(coopPrize) + "€<br>N.  ";
			String next = "<font color='#EE0000'>" + itemsInMarket + "</font>/" + items.size() + "&nbsp;&nbsp;&nbsp;+" + (int)(100-(100*tot()/coopPrize)) + "%";
			tv_coop.setText(Html.fromHtml(first + next));
		}
		craiPrize = Math.floor(prizeByMarket(DictionaryOpenHelperCrai.getColumns()) * 100) / 100;
		tv_crai.setText("Totale: " + normalize(craiPrize) + "€\nN. " + itemsInMarket + "/" + items.size() + "   +" + (int)(100-(100*tot()/craiPrize)) + "%");	
		if(items.size() > itemsInMarket) {
			String first = "Totale: " + normalize(craiPrize) + "€<br>N.  ";
			String next = "<font color='#EE0000'>" + itemsInMarket + "</font>/" + items.size() + "&nbsp;&nbsp;&nbsp;+" + (int)(100-(100*tot()/craiPrize)) + "%";
			tv_crai.setText(Html.fromHtml(first + next));
		}

		List<Double> prizes = new ArrayList<Double>();
		prizes.add(conadPrize);
		prizes.add(coopPrize);
		prizes.add(craiPrize);
		myPrize = tot() - prizes.get(prizes.indexOf(Collections.min(prizes)));

		tv_prize = (TextView) findViewById(R.id.tv_prize);
		tv_discount = (TextView) findViewById(R.id.tv_discount);

		tv_prize.setText("Totale: " + normalize(tot()) + "€");
		tv_discount.setText("Risparmio: " + normalize(myPrize) + "€");
	}

	private double tot() {
		double tmp = 0;
		for (int i = 0; i < items.size(); i++) {
			tmp = tmp + items.get(i).getPrize();
		}
		return Math.floor(tmp * 100) / 100;
	}

	private double prizeByMarket(String[] columns) {
		itemsInMarket = 0;
		ItemDataSource tmpDataSource = new ItemDataSource(this, columns);
		tmpDataSource.open();
		double tot = 0;
		double single = 0;
		for(int i = 0; i < items.size(); i++) {
			single = tmpDataSource.findPrizeFromName(items.get(i).getName());
			if(single > 0) {
				tot = tot + single;
				itemsInMarket++;
			} else {
				tot = tot + items.get(i).getPrize();
			}
		}
		tmpDataSource.close();
		return tot;

	}

	private String normalize(double notnorm) {
		int dot = String.valueOf(notnorm).indexOf(".")+3;
		return (notnorm + "0").substring(0, dot);
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
