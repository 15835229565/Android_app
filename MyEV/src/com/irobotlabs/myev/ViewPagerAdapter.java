package com.irobotlabs.myev;



import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class ViewPagerAdapter extends FragmentPagerAdapter  {
	
	private ArrayList<View> views = new ArrayList<View>();

		
	boolean isConnected = false;
	// Declare the number of ViewPager pages
		final int PAGE_COUNT = 3;

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		public void onCreate() {
	        
	       
	        
	     
	    }
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			int index = views.indexOf (object);
		    if (index == -1)
		      return POSITION_NONE;
		    else
		      return index;
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {

				// Open FragmentTab1.java
			case 0:
				
					FragmentTab1 fragmenttab1 = new FragmentTab1();
					return fragmenttab1;
				
				

				// Open FragmentTab2.java
			case 1:
				FragmentTab2 fragmenttab2 = new FragmentTab2();
				return fragmenttab2;

				// Open FragmentTab3.java
			case 2:
				FragmentTab3 fragmenttab3 = new FragmentTab3();
				
				return fragmenttab3;
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return PAGE_COUNT;
		}
		
		public void setIsConnected(boolean value){
			isConnected = value;
		}

	}
