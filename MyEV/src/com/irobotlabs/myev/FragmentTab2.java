package com.irobotlabs.myev;



import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;





public class FragmentTab2 extends SherlockFragment {
	
	
	private TextView mTvBatteryVoltage;
	private TextView mTvTemperatureValue;
	private TextView mTvChargerCurrentValue;
	private TextView mTvMaxVoltageValue;
	private TextView mTvMinVoltageValue;
	private TextView mTvAvgVoltageValue;
	private TextView mTvFirstText ;
	
	private ArrayList<HashMap<String,Double>> mListBatteryCells;
	private ArrayList<HashMap<String,int[]>> listimg;
	private listviewAdapter mListviewAdapter;
	

	private ArrayList<String> mTvValues;
	private int mTvID=0;
	
	public static final String FIRST_COLUMN = "First";
	public static final String SECOND_COLUMN = "Second";
	public static final String THIRD_COLUMN = "Third";
	public static final String FOURTH_COLUMN = "Fourth";
	
	public static final int FIRST_COLUMNi = 0;
	public static final int SECOND_COLUMNi = 0;
	public static final int THIRD_COLUMNi = 0;
	public static final int FOURTH_COLUMNi = 0;
	String[] a;
	// Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected();
    }
    
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab2.xml
		View view = inflater.inflate(R.layout.fragmenttab2, container, false);
		String strFragment2Tag=getTag();
		SharedPreferences Settings=getActivity().getSharedPreferences("PREFT2", 0);
        SharedPreferences.Editor editor=Settings.edit();
        editor.putString("fragment2TagValue", strFragment2Tag);
        editor.commit();
        
        mTvBatteryVoltage=(TextView) view.findViewById(R.id.tvBatteryVoltage);
        mTvTemperatureValue=(TextView) view.findViewById(R.id.tvTemperatureValue);
        mTvChargerCurrentValue=(TextView) view.findViewById(R.id.tvChargerCurrentValue);
        mTvMaxVoltageValue=(TextView) view.findViewById(R.id.tvMaxVoltageValue);
        mTvMinVoltageValue=(TextView) view.findViewById(R.id.tvMinVoltageValue);
        mTvAvgVoltageValue=(TextView) view.findViewById(R.id.tvAvgVoltageValue);
        mListBatteryCells = new ArrayList<HashMap<String,Double>>();
        listimg=new ArrayList<HashMap<String,int[]>>();
        ListView batteryCellsListView = (ListView)view.findViewById(R.id.listview);
        populateList();
        mListviewAdapter = new listviewAdapter(getActivity(), mListBatteryCells,listimg);
        batteryCellsListView.setAdapter(mListviewAdapter);
        LinearLayout var_x = (LinearLayout) mListviewAdapter.getView(0,null,null);
        mTvFirstText  =  (TextView) var_x.findViewById(R.id.FirstText);
        Log.d("TV", mTvFirstText.getText().toString());
        mListviewAdapter.notifyDataSetChanged();
        mTvValues = new ArrayList<String>();
        mListBatteryCells.clear();
        return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mTvFirstText.setBackgroundColor(Color.RED);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	public void updateUI(ArrayList<Double> uiparams){
			mTvBatteryVoltage.setText(""+uiparams.get(0)+" V");
			mTvTemperatureValue.setText(""+uiparams.get(2)+" T");
			mTvChargerCurrentValue.setText(""+uiparams.get(1)+" A");
			mTvMaxVoltageValue.setText(""+uiparams.get(3)+" V");
			mTvMinVoltageValue.setText(""+uiparams.get(4)+" V");
			mTvAvgVoltageValue.setText(""+uiparams.get(5)+" V");
		}
	public void updateBatteryCells(ArrayList<String> uiparams){
		
		populateList(uiparams);
		
	}
    private void populateList(ArrayList<String> uiparams) {
    	if(mListBatteryCells !=null){
			mListBatteryCells.clear();
		}
    	
    	
    	Log.d("tab2tvVales", mTvValues.toString());
    	int rows=uiparams.size()/4;
    	int cells=rows*4;
    	int remainingCells=uiparams.size()-cells;
    	
    	/**
    	 
    	if(remainingCells==1){
    		uiparams.add(uiparams.size(), "0.0");
    		uiparams.add(uiparams.size(), "0.0");
    		uiparams.add(uiparams.size(), "0.0");
    	}
    	else if(remainingCells==2){
    		uiparams.add(uiparams.size(), "0.0");
    		uiparams.add(uiparams.size(), "0.0");
    		
    	}
    	if(remainingCells==3){
    		uiparams.add(uiparams.size(), "0.0");
    		
    	}
    	
    	* 
    	 */
    	
    	mTvValues = uiparams;	
			
    		for(int j=0;j<uiparams.size();j++){
    			HashMap<String,Double> temp = new HashMap<String,Double>();
    			
    			
				
    			temp.put(FIRST_COLUMN,Double.parseDouble(uiparams.get(j)));
    			temp.put(SECOND_COLUMN, Double.parseDouble(uiparams.get(j+1)));
    			temp.put(THIRD_COLUMN,Double.parseDouble(uiparams.get(j+2)));
    			temp.put(FOURTH_COLUMN,Double.parseDouble(uiparams.get(j+3)));
    			Log.d("double", ""+Double.parseDouble(uiparams.get(j)));
    			
    			mListBatteryCells.add(temp);
    			
    			j+=3;
    		}
    	
    		
		
	
		mListviewAdapter.notifyDataSetChanged();
		
		
	}
 private void populateList() {
	
		HashMap<String,Double> temp = new HashMap<String,Double>();
		temp.put(FIRST_COLUMN,0.0);
		temp.put(SECOND_COLUMN, 0.0);
		temp.put(THIRD_COLUMN, 0.0);
		temp.put(FOURTH_COLUMN,0.0);
		
		mListBatteryCells.add(temp);
	
	
		
		
	}

  
   
  private class listviewAdapter extends BaseAdapter {

		public ArrayList<HashMap<String,Double>> listAdapter;
		public ArrayList<HashMap<String, int[]>> listAdapterimg;
		
		Activity activity;
		
		
		public static final String FIRST_COLUMN = "First";
		public static final String SECOND_COLUMN = "Second";
		public static final String THIRD_COLUMN = "Third";
		public static final String FOURTH_COLUMN = "Fourth";
		
		
		
		public listviewAdapter(Activity activity, ArrayList<HashMap<String,Double>> list, ArrayList<HashMap<String, int[]>> listAdapterimg) {
			super();
			
			this.activity = activity;
			
			this.listAdapter = list;
			this.listAdapterimg=listAdapterimg;
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
						
						Log.d("Textview",""+position);
						//for textview first
						
						mTvID = position * 4;
						
						
						convertView.setTag(holder);
					}
					else
					{
						holder = (ViewHolder) convertView.getTag();
					}

					HashMap<String, Double> map = listAdapter.get(position);
					//HashMap<String, int[]> map2=listAdapterimg.get(position);
					holder.txtFirst.setText(""+map.get(FIRST_COLUMN));
					holder.txtSecond.setText(""+map.get(SECOND_COLUMN));
					holder.txtThird.setText(""+map.get(THIRD_COLUMN));
					holder.txtFourth.setText(""+map.get(FOURTH_COLUMN));
					
					//firstview
					if(map.get(FIRST_COLUMN)<1.5){
						holder.txtFirst.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty12, 0, 0, 0);
					}else if(map.get(FIRST_COLUMN)<2 && map.get(FIRST_COLUMN)>=1.5){
						holder.txtFirst.setCompoundDrawablesWithIntrinsicBounds(R.drawable.low12, 0, 0, 0);
					}else if(map.get(FIRST_COLUMN)<3 && map.get(FIRST_COLUMN)>=2){
						holder.txtFirst.setCompoundDrawablesWithIntrinsicBounds(R.drawable.normal12, 0, 0, 0);
					}else if(map.get(FIRST_COLUMN)>=3){
						holder.txtFirst.setCompoundDrawablesWithIntrinsicBounds(R.drawable.full12, 0, 0, 0);
					}
					
					//secondview
					if(map.get(SECOND_COLUMN)<1.5){
						holder.txtSecond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty12, 0, 0, 0);
					}else if(map.get(SECOND_COLUMN)<2 && map.get(SECOND_COLUMN)>=1.5){
						holder.txtSecond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.low12, 0, 0, 0);
					}else if(map.get(SECOND_COLUMN)<3 && map.get(SECOND_COLUMN)>=2){
						holder.txtSecond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.normal12, 0, 0, 0);
					}else if(map.get(SECOND_COLUMN)>=3){
						holder.txtSecond.setCompoundDrawablesWithIntrinsicBounds(R.drawable.full12, 0, 0, 0);
					}
					//thirdview
					if(map.get(THIRD_COLUMN)<1.5){
						holder.txtThird.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty12, 0, 0, 0);
					}else if(map.get(THIRD_COLUMN)<2 && map.get(THIRD_COLUMN)>=1.5){
						holder.txtThird.setCompoundDrawablesWithIntrinsicBounds(R.drawable.low12, 0, 0, 0);
					}else if(map.get(THIRD_COLUMN)<3 && map.get(THIRD_COLUMN)>=2){
						holder.txtThird.setCompoundDrawablesWithIntrinsicBounds(R.drawable.normal12, 0, 0, 0);
					}else if(map.get(THIRD_COLUMN)>=3){
						holder.txtThird.setCompoundDrawablesWithIntrinsicBounds(R.drawable.full12, 0, 0, 0);
					}
					
					//fourthview
					if(map.get(FOURTH_COLUMN)<1.5){
						holder.txtFourth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty12, 0, 0, 0);
					}else if(map.get(FOURTH_COLUMN)<2 && map.get(FOURTH_COLUMN)>=1.5){
						holder.txtFourth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.low12, 0, 0, 0);
					}else if(map.get(FOURTH_COLUMN)<3 && map.get(FOURTH_COLUMN)>=2){
						holder.txtFourth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.normal12, 0, 0, 0);
					}else if(map.get(FOURTH_COLUMN)>=3){
						holder.txtFourth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.full12, 0, 0, 0);
					}
					
				return convertView;
		}

	}


}




