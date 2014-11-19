package com.hand.srm.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hand.srm.R;
import com.hand.srm.model.BarChartModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;

public class BarChartActivity extends Activity implements LMModelDelegate{
	  
	BarChart  barchart;
	BarChartModel model;
	
	TextView  title;
	ImageButton  returnBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bar_chart);
		
		buildAllViews();
		
		model = new BarChartModel(this);
		
		model.load();
	
	}

/////////////private////////////////////////////////////////////////////////
	private void buildAllViews()
	{
		
		barchart  = 	(BarChart)findViewById(R.id.chart1);
		
        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XLabels xl = barchart.getXLabels();
        xl.setPosition(XLabelPosition.BOTTOM);
        xl.setCenterXLabelText(true);
        xl.setSpaceBetweenLabels(0);
        xl.setTypeface(tf);
        
        title =  (TextView) findViewById(R.id.titleTextView);
        title.setText("供应商交易额榜单");
        
        returnBtn  =  (ImageButton) findViewById(R.id.return_btn);
        returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}
	
	
	private void generateDataBar(JSONArray data)
	{
		
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        //填充数据
        for (int i = 0; i < data.length(); i++) {
            try {
				entries.add(new BarEntry( (float) data.getJSONObject(i).getDouble("total_amount"),i));
				xVals.add(data.getJSONObject(i).getString("vendor_name"));
            
            } catch (JSONException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "请求数据格式错误错误"  , Toast.LENGTH_SHORT).show();

				e.printStackTrace();
			}
        }
        
        
        BarDataSet d = new BarDataSet(entries, "");
        d.setBarSpacePercent(10f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS, this);
        d.setHighLightAlpha(255);
        
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(d);
        
        BarData cd = new BarData(xVals, dataSets);

        barchart.setData(cd);
        barchart.invalidate();
        
        
	}
	
	
	
		

///////////////////////////model delegate////////////////////////////////////	
	@Override
	public void modelDidFinshLoad(LMModel model) {
		
		
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);

			try {
				JSONObject jsonobj = new JSONObject(json);
				String code =  jsonobj.getJSONObject("head").get("code").toString();
				
				if(code.equalsIgnoreCase("ok")){
					//成功逻辑
					generateDataBar(jsonobj.getJSONArray("body"));
					
					
				}else {
					
					Toast.makeText(getApplicationContext(), "请求数据返回错误消息" +((JSONObject) jsonobj.get("head")).get("message").toString() , Toast.LENGTH_SHORT).show();

					
				}
				
				
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "服务器返回数据格式错误", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				return;
			}
			
			
		
		
		
		
	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		
	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {

		
	}

}
