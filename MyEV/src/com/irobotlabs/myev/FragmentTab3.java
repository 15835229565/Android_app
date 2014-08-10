package com.irobotlabs.myev;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.actionbarsherlock.app.SherlockFragment;

public class FragmentTab3 extends SherlockFragment {
	
	OnHeadlineSelectedListener mCallback;
	private Button mConnectBT;
	private EditText mMAc;
	private EditText mName;
	
	
	
	
	// Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(String Mac,String name);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab3.xml
		View view = inflater.inflate(R.layout.fragmenttab3, container, false);
		String strFragment3Tag=getTag();
		
		LinearLayout rLayout = (LinearLayout) view.findViewById (R.id.fr3);
		Resources res = getResources(); //resource handle
		Drawable drawable = res.getDrawable(R.drawable.bg); //new Image that was added to the res folder
		rLayout.setBackgroundDrawable(drawable);
		
		
		
		SharedPreferences Settings=getActivity().getSharedPreferences("PREFT3", 0);
        SharedPreferences.Editor editor=Settings.edit();
        editor.putString("fragment3TagValue", strFragment3Tag);
        editor.commit();
		
		mConnectBT=(Button)view.findViewById(R.id.connectBT);
		mMAc=(EditText)view.findViewById(R.id.etDeviceName);
		mName=(EditText)view.findViewById(R.id.etPassword);
		
		
		
		mConnectBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mCallback.onArticleSelected( mMAc.getText().toString(),mName.getText().toString());
			}
		});
	
		
		
			
		return view;
	}
	
	 @Override
	public void onStop() {
	    	
	    	super.onStop();
	    	SharedPreferences settings = getActivity().getSharedPreferences("PREFBLC", 0);
	    	SharedPreferences.Editor editor = settings.edit();
	    	editor.putString("macValue", mMAc.getText().toString());
	    	editor.putString("deviceName", mName.getText().toString());
	    	editor.commit();
	    	
	    	
	    }
	 @Override
	public void onStart() {
		
		super.onStart();
		SharedPreferences settings = getActivity().getSharedPreferences("PREFBLC", 0);
		mMAc.setText(settings.getString("macValue", ""));
		mName.setText(settings.getString("deviceName", ""));
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		// This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
	}

}
