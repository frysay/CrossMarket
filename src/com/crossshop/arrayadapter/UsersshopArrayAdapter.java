package com.crossshop.arrayadapter;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crossshop.R;

public class UsersshopArrayAdapter extends BaseAdapter {  

	LayoutInflater inflater;
	Context context;

	List<String> categories;

	public UsersshopArrayAdapter(Context context) {  
		super();

		this.context = context;
		categories  = Arrays.asList("Usersshop #1",
				"Usersshop #2",
				"Usersshop #3",
				"Usersshop #4",
				"Usersshop #5",
				"Usersshop #6",
				"Usersshop #7");

		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override  
	public View getView(final int position, View convertView, ViewGroup parent) {    

		String category = categories.get(position);

		View vi = convertView;

		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.usersshop_array_adapter_inflate, parent, false);
		}

		ImageView iv_category = (ImageView) vi.findViewById(R.id.iv_category1);
		TextView tv_category = (TextView) vi.findViewById(R.id.tv_category1);
		iv_category.setImageResource(R.drawable.ic_launcher);
		tv_category.setText(category);
		iv_category = (ImageView) vi.findViewById(R.id.iv_category2);
		tv_category = (TextView) vi.findViewById(R.id.tv_category2);
		iv_category.setImageResource(R.drawable.ic_launcher);
		tv_category.setText(category);
		iv_category = (ImageView) vi.findViewById(R.id.iv_category3);
		tv_category = (TextView) vi.findViewById(R.id.tv_category3);
		iv_category.setImageResource(R.drawable.ic_launcher);
		tv_category.setText(category);
		iv_category = (ImageView) vi.findViewById(R.id.iv_category4);
		tv_category = (TextView) vi.findViewById(R.id.tv_category4);
		iv_category.setImageResource(R.drawable.ic_launcher);
		tv_category.setText(category);
		iv_category = (ImageView) vi.findViewById(R.id.iv_category5);
		tv_category = (TextView) vi.findViewById(R.id.tv_category5);
		iv_category.setImageResource(R.drawable.ic_launcher);
		tv_category.setText(category);

		return vi;  
	}
}
