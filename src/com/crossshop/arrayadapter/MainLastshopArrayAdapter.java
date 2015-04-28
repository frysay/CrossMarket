package com.crossshop.arrayadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.crossshop.R;
import com.crossshop.database.ItemDB;

public class MainLastshopArrayAdapter extends BaseAdapter {  

	LayoutInflater inflater;
	Context context;

	List<ItemDB> items;

	public MainLastshopArrayAdapter(Context context, List<ItemDB> items) {  
		super();

		this.context = context;
		this.items  = items;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override  
	public View getView(final int position, View convertView, ViewGroup parent) {    

		View vi = convertView;

		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.main_array_adapter_inflate, parent, false);
		}

		ImageView ib_item = (ImageView) vi.findViewById(R.id.iv_category);
		ib_item.setImageResource(items.get(position).getImage());

		return vi;  
	}
}
