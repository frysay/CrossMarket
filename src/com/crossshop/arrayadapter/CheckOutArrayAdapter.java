package com.crossshop.arrayadapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.crossshop.R;
import com.crossshop.database.ItemDB;

public class CheckOutArrayAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	List<ItemDB> items;
	int splitScreenHeight;
	List<ItemDB> checkedItems;
	double total;

	public CheckOutArrayAdapter(Context context, List<ItemDB> items, Point size) {  
		super();
		this.context = context;
		this.items = items;
		total = 0;
		for(int i = 0; i < items.size(); i++) {
			total = total + items.get(i).getPrize();
		}
		splitScreenHeight = size.y / 8;
		checkedItems = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public ItemDB getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ItemDB item = items.get(position);

		View vi = convertView;

		if(vi == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.lastshop_array_adapter_inflate, parent, false);
		} else {
			// NOTA: it's used to reset all data and avoid problem of override by the recycling principle of listview
			vi.setBackgroundColor(Color.TRANSPARENT);
			((CheckBox) vi.findViewById(R.id.cb_item)).setChecked(false);
		}

		final CheckBox cb_item = (CheckBox) vi.findViewById(R.id.cb_item);
		ImageView iv_icon = (ImageView) vi.findViewById(R.id.icon_item);
		TextView tv_name = (TextView) vi.findViewById(R.id.tv_name);
		TextView tv_shop = (TextView) vi.findViewById(R.id.tv_shop);
		TextView tv_price = (TextView) vi.findViewById(R.id.tv_prize);
		TextView tv_discount = (TextView) vi.findViewById(R.id.tv_discount);
		cb_item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(cb_item.isChecked()) {
					checkedItems.add(item);
					cb_item.setChecked(true);	
				} else {
					try {
						checkedItems.remove(checkedItems.indexOf(item));
					} catch (Exception e) {
						//First use: initialize to false by defualt
					} finally {
						cb_item.setChecked(false);
					}
				}
			}
		});

		iv_icon.setImageResource(item.getImage());

		tv_name.setText(item.getName());
		tv_shop.setText(item.getMarket());
		tv_price.setText(String.valueOf(item.getPrize()) + "€");
		tv_discount.setText("-" + String.valueOf(item.getDiscount()) + "%");
		if(checkedItems.contains(item)) {
			cb_item.setChecked(true);			
		}

		return vi;
	}

	public List<ItemDB> getSelectedItem() {
		return checkedItems;
	}
}
