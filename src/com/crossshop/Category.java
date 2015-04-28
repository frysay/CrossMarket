package com.crossshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crossshop.database.ItemDataSource;

public class Category extends Activity implements OnClickListener {
	
	LinearLayout ll;
	ImageView iv;
	TextView tv;
	LayoutParams params;
	
	//NOTA: implementare la parete del cart anche da qui
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_layout);
		
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		int thirdScreenWidth = size.x / 3;
		int quarterScreenHeight = size.y / 4;
		
		ll = (LinearLayout) findViewById(R.id.ll_category1);
		iv = (ImageView) findViewById(R.id.ib_category1);
		tv = (TextView) findViewById(R.id.tv_category1);
		
		params = ll.getLayoutParams();
		params.width = thirdScreenWidth;
		params.height = quarterScreenHeight;
		
		iv.setOnClickListener(this);
		
		iv.setImageResource(ItemDataSource.categories_icons[0]);
		tv.setText(ItemDataSource.categories[0]);
		
		ll = (LinearLayout) findViewById(R.id.ll_category2);
		iv = (ImageView) findViewById(R.id.ib_category2);
		tv = (TextView) findViewById(R.id.tv_category2);
		
		params = ll.getLayoutParams();
		params.width = thirdScreenWidth;
		params.height = quarterScreenHeight;
		
		iv.setOnClickListener(this);
		
		iv.setImageResource(ItemDataSource.categories_icons[1]);
		tv.setText(ItemDataSource.categories[1]);
		
		ll = (LinearLayout) findViewById(R.id.ll_category3);
		iv = (ImageView) findViewById(R.id.ib_category3);
		tv = (TextView) findViewById(R.id.tv_category3);
		
		params = ll.getLayoutParams();
		params.width = thirdScreenWidth;
		params.height = quarterScreenHeight;
		
		iv.setOnClickListener(this);
		
		iv.setImageResource(ItemDataSource.categories_icons[2]);
		tv.setText(ItemDataSource.categories[2]);
		
		ll = (LinearLayout) findViewById(R.id.ll_category4);
		iv = (ImageView) findViewById(R.id.ib_category4);
		tv = (TextView) findViewById(R.id.tv_category4);

		params = ll.getLayoutParams();
		params.width = thirdScreenWidth;
		params.height = quarterScreenHeight;
		
		iv.setOnClickListener(this);

		iv.setImageResource(ItemDataSource.categories_icons[3]);
		tv.setText(ItemDataSource.categories[3]);
		
		ll = (LinearLayout) findViewById(R.id.ll_category5);
		iv = (ImageView) findViewById(R.id.ib_category5);
		tv = (TextView) findViewById(R.id.tv_category5);
		
		params = ll.getLayoutParams();
		params.width = thirdScreenWidth;
		params.height = quarterScreenHeight;
		
		iv.setOnClickListener(this);
		
		iv.setImageResource(ItemDataSource.categories_icons[4]);
		tv.setText(ItemDataSource.categories[4]);
		
		ll = (LinearLayout) findViewById(R.id.ll_category6);
		iv = (ImageView) findViewById(R.id.ib_category6);
		tv = (TextView) findViewById(R.id.tv_category6);
		
		params = ll.getLayoutParams();
		params.width = thirdScreenWidth;
		params.height = quarterScreenHeight;
		
		iv.setOnClickListener(this);
		
		iv.setImageResource(ItemDataSource.categories_icons[5]);
		tv.setText(ItemDataSource.categories[5]);
		
		ll = (LinearLayout) findViewById(R.id.ll_category7);
		iv = (ImageView) findViewById(R.id.ib_category7);
		tv = (TextView) findViewById(R.id.tv_category7);
		
		params = ll.getLayoutParams();
		params.width = thirdScreenWidth;
		params.height = quarterScreenHeight;
		
		iv.setOnClickListener(this);
		
		iv.setImageResource(ItemDataSource.categories_icons[6]);
		tv.setText(ItemDataSource.categories[6]);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, ListViewPrototype.class);
		intent.putExtra("com.smartshop.ListViewPrototype.filter", "category");
		intent.putExtra("com.smartshop.ListViewPrototype.category", v.getContentDescription());
		startActivity(intent);
	}

}
