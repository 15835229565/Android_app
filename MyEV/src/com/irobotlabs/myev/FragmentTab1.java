package com.irobotlabs.myev;

import java.util.ArrayList;
import java.util.List;


import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.R.color;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;


public class FragmentTab1 extends SherlockFragment {
	
	DefaultRenderer donutRenderer;
	
	private GraphicalView mLineChartView;
	private GraphicalView mDonutChartView;
	
	private RelativeLayout mRLChargeLayout;
	private ImageView mImgvCharging;
	private TextView mTvCharging;
	private TextView mTvChargingHour;
	private TextView mTvKilometers;
	private TextView mTvAmperes;
	private TextView mTvDonutCenterPercentage;
	MultipleCategorySeries series ;
	MultipleCategorySeries mcdataset;
	XYMultipleSeriesDataset dataset;
	

	
	
	GraphicalView gvlchart;
	
	RelativeLayout lineChartRelativeLayout;
	LinearLayout doughnutchartlinearLayout;
	
	boolean isConnected=false;
	
	// Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab1.xml
		View view=null;
		
		view= inflater.inflate(R.layout.fragmenttab1, container, false);
		
		
		mTvChargingHour = (TextView) view.findViewById(R.id.tvChargingHour);
		mTvKilometers = (TextView) view.findViewById(R.id.tvKm);
		mTvAmperes = (TextView) view.findViewById(R.id.tvA);
		mTvDonutCenterPercentage=(TextView) view.findViewById(R.id.tvdonutcenterlayout);
		mRLChargeLayout=(RelativeLayout) view.findViewById(R.id.chargelayout);
		mTvCharging=(TextView) view.findViewById(R.id.tvCharging);
		mImgvCharging=(ImageView) view.findViewById(R.id.imgvCharging);
		lineChartRelativeLayout = (RelativeLayout) view.findViewById(R.id.linechart);
		doughnutchartlinearLayout=(LinearLayout) view.findViewById(R.id.doughnutchart);
		String fragment1Tag=getTag();
		Toast.makeText(getActivity(), fragment1Tag, Toast.LENGTH_LONG).show();
		 
		SharedPreferences Settings=getActivity().getSharedPreferences("PREFT1", 0);
        SharedPreferences.Editor editor=Settings.edit();
        editor.putString("fragment1TagValue", fragment1Tag);
        editor.commit();
        //bdchart = new BudgetDoughnutChart();
        
       
       
        
        series = new MultipleCategorySeries("BATTERY");
        
        openPowerChart(new double[]{0,0,0,0,0,0,0});
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
			
		
    	
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	

	
	 
public void updateUI(ArrayList<Double> uiparams){
	//tvTimetoCharge.invalidate();
		mTvChargingHour.setText(String.valueOf(uiparams.get(1)).replaceFirst("\\.0+$", "")+"h");
		mTvKilometers.setText(String.valueOf(uiparams.get(2)).replaceFirst("\\.0+$", "")+"Km");
		mTvAmperes.setText(String.valueOf(uiparams.get(3)).replaceFirst("\\.0+$", "")+"A");
		int mode=uiparams.get(4).intValue();
		if(mode==1){
			mRLChargeLayout.setBackgroundColor(Color.parseColor("#f8aa33"));
			mTvCharging.setText("Charging");
			Drawable d=getResources().getDrawable(R.drawable.battery);
			mImgvCharging.setBackgroundDrawable(d);
		}else if(mode==2){
			mRLChargeLayout.setBackgroundColor(color.transparent);
			mTvCharging.setText("Driving");
			Drawable d=getResources().getDrawable(R.drawable.driving);
			mImgvCharging.setBackgroundDrawable(d);
			
		}if(mode==3){
			mRLChargeLayout.setBackgroundColor(color.transparent);
			mTvCharging.setText("Vehicle Off");
			Drawable d=getResources().getDrawable(R.drawable.vehicleoff);
			mImgvCharging.setBackgroundDrawable(d);
		}if(mode==4){
			mRLChargeLayout.setBackgroundColor(color.transparent);
			mTvCharging.setText("Not Connected");
			Drawable d=getResources().getDrawable(R.drawable.notconnected);
			mImgvCharging.setBackgroundDrawable(d);
		}
	}	
public void updatePowerChart(ArrayList<Double> uiparams){
		Log.d("uiparams", uiparams.toString());
		double x[] = new double[uiparams.size()];
		for(int i = 0; i < uiparams.size(); i++){
	     x[i] = uiparams.get(i);
	}
	if (dataset != null) {
		dataset.clear();
	}
	openPowerChart(x);
}
public void updateDonutChart(double uiparams){
	int[] colors = new int[2];
	int donutBackgroundColor;
	double total=100.0;
	double reamining=total-uiparams;
	double x[] = new double[2];
	x[0]=uiparams;
	x[1]=reamining;
	mTvDonutCenterPercentage.setText(String.valueOf(uiparams).replaceFirst("\\.0+$", "")+"%");
	

	if(x[0]==100){
		
		
    	colors[0]=Color.parseColor("#89ff7d");
	    colors[1]=Color.parseColor("#3a8dce");
	    donutBackgroundColor=Color.parseColor("#2BB044");
    }
    else if(x[0]==0){
    	
    	colors[0]=Color.parseColor("#FF7D7D");
	    colors[1]=Color.parseColor("#FF7D7D");
	    donutBackgroundColor=Color.parseColor("#B02B2B");
	    
    	
    }else{
    	colors[0]=Color.parseColor("#7dc6ff");
	    colors[1]=Color.parseColor("#3a8dce");
	    donutBackgroundColor=Color.parseColor("#2B76B0");
    }
	
		
	
		mcdataset = getDonutMultiipleCategorySeries(x);
		donutRenderer = getDonutRenderer(colors,donutBackgroundColor);
		openDonutChart();
	
	
	
}



private void openPowerChart(double[] data){
	XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
	
	
	 multiRenderer.addSeriesRenderer(makeSeries( new XYSeriesRenderer(),new XYSeries("Today"),data,multiRenderer));
	 multiRenderer.setChartTitle("POWER");
	 multiRenderer.setChartTitleTextSize(30);multiRenderer.setXAxisMin(0);
     multiRenderer.setYAxisMin(-50);
     multiRenderer.setYAxisMax(150);
     multiRenderer.setXAxisMin(0.5);
     multiRenderer.setXAxisMax(12.5);
     multiRenderer.setAxesColor(color.transparent);
     multiRenderer.setLabelsColor(color.white);
     multiRenderer.setPointSize(5f);
     multiRenderer.setYLabelsPadding(5);multiRenderer.setXTitle("1HOUR");
	 multiRenderer.setClickEnabled(true);
	 multiRenderer.setPanEnabled(false,false);
	 multiRenderer.setZoomEnabled(true);
	 multiRenderer.setShowLegend(false);
	 multiRenderer.setXLabels(0);
	 multiRenderer.setYLabels(6);
	 multiRenderer.setShowGrid(true);
	 multiRenderer.setShowGridY(false);
	 multiRenderer.setXAxisMin(0);	 
	 multiRenderer.setXLabelsColor(Color.TRANSPARENT);
	 multiRenderer.setApplyBackgroundColor(true);
	 multiRenderer.setBackgroundColor(Color.TRANSPARENT);
	 multiRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
	 multiRenderer.setLabelsColor(Color.WHITE);
	 multiRenderer.setInitialRange(null);
	 
	
	 mLineChartView = ChartFactory.getLineChartView(getActivity(), dataset, multiRenderer);
	
	

		
	Drawable d = getResources().getDrawable(R.drawable.chartbg);
    d.setAlpha(50);
    lineChartRelativeLayout.removeAllViews();
    lineChartRelativeLayout.setBackgroundDrawable(d);
    
    lineChartRelativeLayout.addView(mLineChartView);

		
	
	
}

private XYSeriesRenderer makeSeries(XYSeriesRenderer seriesRenderer,XYSeries series,double[] data,XYMultipleSeriesRenderer multiRenderer){
	
	for(int i=0;i<data.length;i++){
		
		series.add(i,data[i]);
	
	}
	dataset = new XYMultipleSeriesDataset();
	dataset.addSeries(series); 
	
	seriesRenderer = new XYSeriesRenderer();
	seriesRenderer.setChartValuesTextSize(15);
	seriesRenderer.setPointStyle(PointStyle.CIRCLE);seriesRenderer.setFillPoints(true);
	
	int color = Color.argb(255, 255, 255, 255);
	seriesRenderer.setColor(color);
	seriesRenderer.setLineWidth(5f);
	seriesRenderer.setDisplayChartValues(true); 
	////////////////////////////////////////////////////
	
	
	return seriesRenderer;
	
}


public void setIsConnected(boolean value){
	isConnected = value;
}



private void openDonutChart(){
	   
	   mDonutChartView= ChartFactory.getDoughnutChartView(getActivity(), mcdataset, donutRenderer);
	   
	   mDonutChartView.repaint();
	   doughnutchartlinearLayout.removeAllViews();
	   doughnutchartlinearLayout.addView(mDonutChartView);
	   
	   
	  
	  
}

	public DefaultRenderer getDonutRenderer(int[] colors ,int b){
	 
		 DefaultRenderer renderer = new DefaultRenderer();
		 
		 
		 renderer.setLabelsTextSize(15);
		 renderer.setLegendTextSize(15);
		 renderer.setMargins(new int[] { 20, 30, 15, 0 });
		    
		   
		   renderer.setApplyBackgroundColor(true);
		   	
		   renderer.setBackgroundColor(b);
		   
		   Log.d("colors", ""+colors[0]);
		    for (int color : colors) {
		    	SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			      r.setColor(color);
			      renderer.addSeriesRenderer(r);
			}
		    
		    renderer.setLabelsColor(Color.GRAY);
		    
		    renderer.setZoomEnabled(false);
		    renderer.setZoomButtonsVisible(false);
		    renderer.setShowLabels(false);
		    renderer.setPanEnabled(false);
		    renderer.setShowLegend(false);
		    renderer.setApplyBackgroundColor(false);
		    renderer.setMargins(new int[]{0,0,0,0});
		    renderer.setInScroll(true);
		    return renderer;
	 
 }
 
 	private MultipleCategorySeries getDonutMultiipleCategorySeries(double[] a){
	 
		 MultipleCategorySeries series = new MultipleCategorySeries("");
		 List<double[]> values  = new ArrayList<double[]>();
		 
		    values.add(a);
		    values.add(new double[] { 0,0 });
		    
		    List<String[]>  titles = new ArrayList<String[]>();
		    titles.clear();
		    titles.add(new String[] {  "P1","p" });
		    titles.add(new String[] { "P2","p" });
		    
		   
		    series.clear();
		    int k = 0;
		    for (double[] value : values) {
		      series.add(titles.get(k), value);
		      k++;
		    }
		 
		 return series;
		 
 	}


 
	
	
}

