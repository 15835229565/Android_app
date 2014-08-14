package com.irobotlabs.xmlParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class XMLParser  {
	
	
	public XMLParser(){
		
		
		
	}
	
	
	
	
		
	private String mHead_donut = "0";
	private String mHead_TimetoCharge = "0";
	private String mHead_RemainingDistance = "0";
	private String mHome_Current = "0";
	private String mHome_pw = "0";
	
	private String mBattery_voltage = "0.0";
	private String mBattery_currCap = "0.0";
	private String mBattery_temp = "0.0";
	private String mBattery_max="0.0";
	private String mBattery_min="0.0";
	private String mBattery_avg="0.0";
	private String mMode="0";
	private String mBattery_Cell = "0.0";
	
	
	
	private ArrayList<String> mBattery_Values = new ArrayList<String>();
	private ArrayList<Double> mLineChart_Values = new ArrayList<Double>();
	 
	
	
	public void parseXML(String str){
		String xmlstart = "<?xml version='1.0' encoding='utf-8'?>";
		String fullStr = xmlstart + str;
		mBattery_Values.clear();
		
		mLineChart_Values.clear();
		mLineChart_Values.add(0.0);
		//makeToast(str);
		XmlPullParser xpp = Xml.newPullParser();
		
			
			
			try {
				xpp.setInput(new StringReader(fullStr));
				
				xpp.next();
				int eventType = xpp.getEventType();
				
				while (eventType != XmlPullParser.END_DOCUMENT)
			    {
					 if(eventType == XmlPullParser.START_DOCUMENT){
						 
					 }
					 else if(eventType == XmlPullParser.START_TAG)
				     {
						if(xpp.getName().equalsIgnoreCase("donut")){
							mHead_donut = xpp.getAttributeValue(0);
						}
						else if(xpp.getName().equalsIgnoreCase("ttc")){
							mHead_TimetoCharge = xpp.getAttributeValue(0);
						}
						else if(xpp.getName().equalsIgnoreCase("rd")){
							mHead_RemainingDistance = xpp.getAttributeValue(0);
						}
						else if(xpp.getName().equalsIgnoreCase("amp")){
							mHome_Current = xpp.getAttributeValue(0);
						}
						
						else if(xpp.getName().equalsIgnoreCase("pw")){
							//Log.d("array", "haha");
							mLineChart_Values.add(Double.parseDouble(xpp.getAttributeValue(0))) ;
						} 
						else if(xpp.getName().equalsIgnoreCase("vol")){
							mBattery_voltage = xpp.getAttributeValue(0) ;
						}  
						else if(xpp.getName().equalsIgnoreCase("camp")){
							mBattery_currCap = xpp.getAttributeValue(0) ;
						}  
						else if(xpp.getName().equalsIgnoreCase("temp")){
							mBattery_temp = xpp.getAttributeValue(0) ;
						}
						else if(xpp.getName().equalsIgnoreCase("max")){
							mBattery_max = xpp.getAttributeValue(0) ;
						} 
						else if(xpp.getName().equalsIgnoreCase("min")){
							mBattery_min = xpp.getAttributeValue(0) ;
						} 
						else if(xpp.getName().equalsIgnoreCase("avg")){
							mBattery_avg = xpp.getAttributeValue(0) ;
						} 
						else if(xpp.getName().equalsIgnoreCase("mode")){
							mMode = xpp.getAttributeValue(0) ;
						} 
						else if(xpp.getName().equalsIgnoreCase("bt")){
							Log.d("array", "haha");
							mBattery_Values.add(xpp.getAttributeValue(1)) ;
						} 
						 
				     }
				     else if(eventType == XmlPullParser.END_TAG)
				     {
				    	 
				     }
				     else if(eventType == XmlPullParser.TEXT)
				     {
				    	 
				     }
				     eventType = xpp.next();
			    }
				 
				 
		  } catch (XmlPullParserException e) {
			   
				e.printStackTrace();
				
				Log.d("xml", "PPEerror");
		  } catch (IOException e) {
			    
				e.printStackTrace();
				
				Log.d("xml", "IOerror");
		  }
			
			
			
			
			
			
}
	
	public ArrayList<Double> getPowerchartvalues(){
		
		Log.d("xml", ""+mLineChart_Values.size());
		return mLineChart_Values;
	}
	
	public ArrayList<Double> getValuesforMain(){
		 ArrayList<Double> mainValues = new ArrayList<Double>();
		 mainValues.add(Double.parseDouble(mHead_donut));
		 mainValues.add(Double.parseDouble(mHead_TimetoCharge));
		 mainValues.add(Double.parseDouble(mHead_RemainingDistance));
		 mainValues.add(Double.parseDouble(mHome_Current));
		 mainValues.add(Double.parseDouble(mMode));
		
		 return mainValues;
	}
	
	
	
	public ArrayList<String> getBatteryValues(){
		Log.d("xmlb", ""+mBattery_Values.size());
		
		return mBattery_Values;
	}
	
	public ArrayList<Double> getValuesforSeconView(){
		 ArrayList<Double> mainValues = new ArrayList<Double>();
		 mainValues.add(Double.parseDouble(mBattery_voltage));
		 mainValues.add(Double.parseDouble(mBattery_temp));
		 mainValues.add(Double.parseDouble(mBattery_currCap));
		 mainValues.add(Double.parseDouble(mBattery_max));
		 mainValues.add(Double.parseDouble(mBattery_min));
		 mainValues.add(Double.parseDouble(mBattery_avg));
		 return mainValues;
	}
	
	
	
	
	
}
