package com.hand.srm.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hand.srm.R;
import com.hand.srm.model.PieChartModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PieChartActivity extends Activity implements LMModelDelegate{
	
	private PieChart chart;
	
	private PieChartModel model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pie_chart);
		buildAllView();
		
		
		model =  new PieChartModel(this);
		model.load();
	}

///////////////////private///////////////////
	private void buildAllView()
	{
		
		chart = (PieChart) findViewById(R.id.pie_chart1);
		
        // display percentage values
        chart.setUsePercentValues(true);
        
        TextView title =  (TextView) findViewById(R.id.titleTextView);
        title.setText("采购品供应商占比分析");
        
        ImageButton  returnBtn  =  (ImageButton) findViewById(R.id.return_btn);
        returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}
		
//////////////打包Json数据//////////////////	
	private void generateDataPie(JSONArray data) throws JSONException{
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < data.length(); i++) {
            yVals1.add(new Entry((float) data.getJSONObject(i).getDouble("total_amount"),i));
            xVals.add(data.getJSONObject(i).getString("vendor_name"));
        }
        
        
        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(3f);
        set1.setColors(ColorTemplate.createColors(getApplicationContext(),
                ColorTemplate.VORDIPLOM_COLORS));

        PieData d = new PieData(xVals, set1);
        
        chart.setData(d);

        // undo all highlights
        chart.highlightValues(null);

        // set a text for the chart center
        chart.setCenterText("总计\n" + (int) chart.getYValueSum());
        chart.invalidate();
		
	}

////////////////////////model delegate/////////////////////////
	@Override
	public void modelDidFinshLoad(LMModel model) {
		
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);

			try {
				JSONObject jsonobj = new JSONObject(json);
				String code =  jsonobj.getJSONObject("head").get("code").toString();
				
				if(code.equalsIgnoreCase("ok")){
					//成功逻辑
					generateDataPie(jsonobj.getJSONArray("body"));
					
					
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
