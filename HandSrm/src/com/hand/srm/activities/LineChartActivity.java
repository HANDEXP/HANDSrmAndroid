package com.hand.srm.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.hand.srm.R;
import com.hand.srm.model.LineChartModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LineChartActivity  extends Activity implements LMModelDelegate
{
	private LineChartModel model;
	
	private LineChart  linechart;
	
	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_line_chart);
			
			model = new LineChartModel(this);
			
			buildAllViews();
			
			model.load();
			
		}
//////////////////////private////////////////////////
	private void buildAllViews()
	{
		linechart = (LineChart) findViewById(R.id.lineChart);
		
		TextView  title =  (TextView) findViewById(R.id.titleTextView);
        title.setText("供应商交易额榜单报表");
        
        ImageButton  returnBtn  =  (ImageButton) findViewById(R.id.return_btn);
        returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}
	
	//////////生成dataset
	private void generateLineDataSets(JSONObject data) throws JSONException
	{
		JSONArray datasets = data.getJSONArray("datasets");
		
	     ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
		
		for(int i =0;i<datasets.length();i++ ){
			
			ArrayList<Entry> ds = new ArrayList<Entry>();
			JSONObject dataset  = datasets.getJSONObject(i);
			JSONArray amounts = dataset.getJSONArray("total_amount");
			
			for(int j=0;j<amounts.length();j++){
				float amount = (float) amounts.getDouble(j);
				ds.add(new Entry(amount, j));
				
			}
			
	        LineDataSet dn = new LineDataSet(ds,dataset.getString("vendor_name") );
	        dn.setLineWidth(3f);
	        dn.setCircleSize(5f);
	        dn.setHighLightColor(Color.rgb(244, 117, 117));
	        
	        switch (i%5) {
			case 0:
				 dn.setColor(getResources().getColor(R.color.vordiplom_1));
				 dn.setCircleColor(getResources().getColor(R.color.vordiplom_1));

				break;			
			case 1:
				 dn.setColor(getResources().getColor(R.color.vordiplom_2));
				 dn.setCircleColor(getResources().getColor(R.color.vordiplom_2));

				break;
			case 2:
				 dn.setColor(getResources().getColor(R.color.vordiplom_3));
				 dn.setCircleColor(getResources().getColor(R.color.vordiplom_3));

				break;
			case 3:
				 dn.setColor(getResources().getColor(R.color.vordiplom_4));
				 dn.setCircleColor(getResources().getColor(R.color.vordiplom_4));

				break;
			case 4:
				 dn.setColor(getResources().getColor(R.color.vordiplom_5));
				 dn.setCircleColor(getResources().getColor(R.color.vordiplom_5));

				break;	
			default:
				break;
			}
	        
	        
	        sets.add(dn);
			
		}
		
		///////////////////获得横坐标////////////
        ArrayList<String> m = new ArrayList<String>();
        
        JSONArray periods =  data.getJSONArray("periods");
        
		for(int i =0;i<periods.length();i++){
			JSONObject obj  = periods.getJSONObject(i);
			m.add(obj.getString("period_name"));
		
		}
		
		//////////////////
        LineData cd = new LineData(m, sets);
        
        linechart.setData(cd);
        
        linechart.getLegend().setPosition(LegendPosition.BELOW_CHART_RIGHT);

        // undo all highlights
        linechart.highlightValues(null);
        
        linechart.invalidate();
	}
	
//////////////////////model delegate/////////////////	
	@Override
	public void modelDidFinshLoad(LMModel model) {
		
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);

			try {
				JSONObject jsonobj = new JSONObject(json);
				String code =  jsonobj.getJSONObject("head").get("code").toString();
				
				if(code.equalsIgnoreCase("ok")){
					//成功逻辑

					generateLineDataSets(jsonobj.getJSONObject("body"));
					
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
