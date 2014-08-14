package com.irobotlabs.listview;


import java.util.ArrayList;
import java.util.HashMap;

import com.irobotlabs.myev.R;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class listviewAdapter extends BaseAdapter {

	public ArrayList<HashMap<String,String>> listAdapter;
	
	Activity activity;
	int a=0;
	
	public static final String FIRST_COLUMN = "First";
	public static final String SECOND_COLUMN = "Second";
	public static final String THIRD_COLUMN = "Third";
	public static final String FOURTH_COLUMN = "Fourth";
	
	public listviewAdapter(Activity activity, ArrayList<HashMap<String,String>> list) {
		super();
		
		this.activity = activity;
		
		this.listAdapter = list;
	}
	public listviewAdapter(){
		this.listAdapter.clear();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listAdapter.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listAdapter.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder {
	       TextView txtFirst;
	       TextView txtSecond;
	       TextView txtThird;
	       TextView txtFourth;
	  }

	  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
				ViewHolder holder;
				LayoutInflater inflater =  activity.getLayoutInflater();

				if (convertView == null)
				{
					convertView = inflater.inflate(R.layout.listview_row, null);
					holder = new ViewHolder();
					holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
					holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
					holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
					holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
					if(position==1){
						
						
						
						
						
					}
					holder.txtFirst.setCompoundDrawablesWithIntrinsicBounds(R.drawable.full12, 0, 0, 0);
					holder.txtSecond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty12, 0, 0, 0);
					holder.txtThird.setCompoundDrawablesWithIntrinsicBounds(R.drawable.low12, 0, 0, 0);
					holder.txtFourth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty12, 0, 0, 0);
					
					convertView.setTag(holder);
				}
				else
				{
					holder = (ViewHolder) convertView.getTag();
				}

				HashMap<String, String> map = listAdapter.get(position);
				holder.txtFirst.setText(map.get(FIRST_COLUMN));
				holder.txtSecond.setText(map.get(SECOND_COLUMN));
				holder.txtThird.setText(map.get(THIRD_COLUMN));
				holder.txtFourth.setText(map.get(FOURTH_COLUMN));

			return convertView;
	}

}
