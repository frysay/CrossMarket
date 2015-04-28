package com.crossshop.arrayadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.crossshop.ListViewPrototype;
import com.crossshop.R;
import com.crossshop.database.ItemDataSource;

public class MainCategoryArrayAdapter extends BaseAdapter implements OnClickListener {  

	LayoutInflater inflater;
	Context context;

	private int clickCounter;

	String[] categories;

	public MainCategoryArrayAdapter(Context context) {  
		super();

		this.context = context;
		categories = ItemDataSource.categories;
		clickCounter = -1;

		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return categories.length;
	}

	@Override
	public Object getItem(int position) {
		return categories[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override  
	public View getView(final int position, View convertView, ViewGroup parent) {    

		String category = categories[position];

		View vi = convertView;

		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.main_array_adapter_inflate, parent, false);
		}

		ImageView ib_category = (ImageView) vi.findViewById(R.id.iv_category);
		ib_category.setImageResource(ItemDataSource.categories_icons[position]);
		ib_category.setContentDescription(category);
		ib_category.setTag(position);
		ib_category.setOnClickListener(this);

		return vi;  
	}

	@Override
	public void onClick(View v) {

		switch ((Integer)v.getTag()) {
		case 0:
			if(clickCounter==0) {
				startCategory(v.getContentDescription());
			} else {
				clickCounter = 0;
			}
			break;

		case 1:
			if(clickCounter==1) {
				startCategory(v.getContentDescription());
			} else {
				clickCounter = 1;
			}
			break;

		case 2:
			if(clickCounter==2) {
				startCategory(v.getContentDescription());
			} else {
				clickCounter = 2;
			}
			break;

		case 3:
			if(clickCounter==3) {
				startCategory(v.getContentDescription());
			} else {
				clickCounter = 3;
			}
			break;

		case 4:
			if(clickCounter==4) {
				startCategory(v.getContentDescription());
			} else {
				clickCounter = 4;
			}
			break;

		case 5:
			if(clickCounter==5) {
				startCategory(v.getContentDescription());
			} else {
				clickCounter = 5;
			}
			break;

		case 6:
			if(clickCounter==6) {
				startCategory(v.getContentDescription());
			} else {
				clickCounter = 6;
			}
			break;

		default:
			break;
		}
	}

	private void startCategory(CharSequence category) {
		Intent intent = new Intent(context, ListViewPrototype.class);
		intent.putExtra("com.smartshop.ListViewPrototype.filter", "category");
		intent.putExtra("com.smartshop.ListViewPrototype.category", category);
		context.startActivity(intent);
	}
}
