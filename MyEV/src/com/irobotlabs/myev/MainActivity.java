package com.irobotlabs.myev;


import java.util.ArrayList;
import java.util.Map;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.irobotlabs.bluetooth.CommThread;
import com.irobotlabs.myev.FragmentTab3.OnHeadlineSelectedListener;
import com.irobotlabs.xmlParser.XMLParser;



public class MainActivity extends SherlockFragmentActivity implements TabListener,OnHeadlineSelectedListener {

	
	
		BroadcastReceiver  mReceiver;
	
		private Handler mHandler;
		private CommThread mThread;
	
		public ActionBar actionBar;
		public ViewPager viewPager;
		public Tab tab;
		boolean isconnected=false;
		
		
		private XMLParser mXmlParser;
		private ArrayList<Double> mGetPowerchartvalues;
		private ArrayList<Double> mGetMainView;
		private ArrayList<String> mGetBatteryVal;
		private ArrayList<Double> mGetSecondViewVal;
		
		private String[] mTabs = { "MAIN", "BATTERY", "SETTINGS" };

	
		
		

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Get the view from activity_main.xml
			setContentView(R.layout.activity_main);
			
			
			// Activate Navigation Mode Tabs
			
			actionBar = getSupportActionBar();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			
			// Locate ViewPager in activity_main.xml
			viewPager = (ViewPager) findViewById(R.id.pager);
			
			
			
			// Activate Fragment Manager
			FragmentManager fm = getSupportFragmentManager();
			

			// Capture ViewPager page swipes
			
			// Locate the adapter class called ViewPagerAdapter.java
			final ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
			
			// Set the View Pager Adapter into ViewPager
			viewPager.setAdapter(viewpageradapter);
			
			 // Adding Tabs
	        for (String tab_name : mTabs) {
	        	actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this).setTag(tab_name));
	        }
	        
	        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	        	 
	            @Override
	            public void onPageSelected(int position) {
	                // on changing the page
	                // make respected tab selected
	                actionBar.setSelectedNavigationItem(position);
	            }
	         
	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	            }
	         
	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	            }
	        });
	        
	        
	        mHandler = new Handler() {
	            @SuppressWarnings("unchecked")
	            @Override
	            public void handleMessage(Message msg) {
	                    super.handleMessage(msg);
	                    Map<String, String> params = (Map<String, String>)msg.obj;
	                   
	                    String state = params.get("state");
	                    getViews(state);
	    				updateMainView();
	                    Log.d("xml", state);
	                    
	                   
	                   
	            }
	    };
	    IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
       
        
        //The BroadcastReceiver that listens for bluetooth broadcasts
		mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                   //Device found
                	
                	
                }
                else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    //Device is now connected
                	viewpageradapter.setIsConnected(true);
                	viewpageradapter.notifyDataSetChanged();
                	isconnected=true;
                }
                else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //Done searching
                }
                 
                        
            }
        };
        
        this.registerReceiver(mReceiver, filter1);
        this.registerReceiver(mReceiver, filter2);
        this.registerReceiver(mReceiver, filter3);
	    

		}
		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			 viewPager.setCurrentItem(tab.getPosition());
	 	
		}
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
		

		@Override
		public void onArticleSelected(String Mac,String name) {
			// TODO Auto-generated method stub
			
			connectBT(Mac, name);
			
		}
		
		public void connectBT(String Mac,String deviceNameStr ){
			 mThread = new CommThread(BluetoothAdapter.getDefaultAdapter(),  mHandler, Mac,deviceNameStr);
             mThread.start();
		}

		public void getViews(String x){
	    	mXmlParser=new XMLParser();
	    	mXmlParser.parseXML(x);
	    	mGetMainView=mXmlParser.getValuesforMain();
	    	mGetPowerchartvalues=mXmlParser.getPowerchartvalues();
	    	mGetBatteryVal=mXmlParser.getBatteryValues();
	    	mGetSecondViewVal=mXmlParser.getValuesforSeconView();
	    }
	
	  @SuppressLint("NewApi")
	  public void  updateMainView(){
			  FragmentTab1 fr1 = null;
			  FragmentTab2 fr2 = null;
			  SharedPreferences Settings=this.getSharedPreferences("PREFT1", 0);
			  String Tab1=Settings.getString("fragment1TagValue", "");
			  SharedPreferences Settings2=this.getSharedPreferences("PREFT2", 0);
			  String Tab2=Settings2.getString("fragment2TagValue", "");
		      fr1 =(FragmentTab1) getSupportFragmentManager().findFragmentByTag(Tab1);
		      fr2 =(FragmentTab2) getSupportFragmentManager().findFragmentByTag(Tab2);
		      if(isconnected){
		    	  if(fr1 !=null){
			    		
			    		fr1.updateUI(mGetMainView);
			    		
			    		fr1.updatePowerChart(mGetPowerchartvalues);
			    		double x[] = new double[mGetMainView.size()];
			    		for(int i = 0; i < mGetMainView.size(); i++){
			    	     x[i] = mGetMainView.get(i);
			    		}
			    		fr1.updateDonutChart(x[0]);
			    		
			    	}else{
			    		
			    	}
			    	if(fr2 !=null){
			    		fr2.updateUI(mGetSecondViewVal);
			    		fr2.updateBatteryCells(mGetBatteryVal);
			    		
			    	}else{
			    		
			    	}
		      }
		    	
		  }
		 
	 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (mThread!=null) {
			mThread.cancel();
			
		}
		
		unregisterReceiver(mReceiver);
	}
	    }
	