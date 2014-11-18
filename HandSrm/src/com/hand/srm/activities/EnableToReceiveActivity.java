package com.hand.srm.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.hand.srm.R;
import com.hand.srm.adapter.EnableToReceiveAdapter;
import com.hand.srm.model.EnableToReceiveModel;
import com.hand.srm.model.EnableToReceiveSvcModel;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.mas.customview.ProgressDialog;

public class EnableToReceiveActivity extends SherlockActivity implements
		OnClickListener, LMModelDelegate {
	private List<List<String>> group;
	private List<List<EnableToReceiveModel>> child;
	private EnableToReceiveAdapter adapter;
	private ExpandableListView shopPoListView;
	private EnableToReceiveSvcModel model;
	private TextView backTextView;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ship_po_list);
		model = new EnableToReceiveSvcModel(this);
		dialog = new ProgressDialog(this, "数据正在加载中，请稍后");
		dialog.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindAllViews();
		model.load();
	}

	/**
	 * 绑定View
	 * 
	 */
	private void bindAllViews() {
		shopPoListView = (ExpandableListView) findViewById(R.id.shopPoListView);
		shopPoListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO 自动生成的方法存根

				String headerId = child.get(groupPosition).get(childPosition)
						.getAsnHeaderId();
				Intent intent = new Intent(getApplicationContext(),
						EnableToReceiveDetailActivity.class);
				intent.putExtra("purHeaderId", headerId);
				startActivity(intent);
				overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);			
				return false;
			}
		});
		shopPoListView.setGroupIndicator(null);
		backTextView = (TextView) findViewById(R.id.backTextView);
		backTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
	}

	/**
	 * 初始化数据
	 * 
	 * @param bodyObj
	 * @throws JSONException
	 * @throws ParseException
	 * 
	 * 
	 */
	private void initializeData(JSONArray jsonArr) throws JSONException,
			ParseException {
		group = new ArrayList<List<String>>();
		child = new ArrayList<List<EnableToReceiveModel>>();
		String[] groupInfo = new String[2];
		List<EnableToReceiveModel> childInfo = new ArrayList<EnableToReceiveModel>();
		String topDate = null;
		Boolean flag = false;
		int length = jsonArr.length();
		for (int i = 0; i < length; i += 1) {
			// if(i == 50){
			// break;
			// }
			JSONObject data = new JSONObject();
			data = (JSONObject) jsonArr.get(i);
			try {
				if (topDate == null) {
					topDate = data.getString("ship_date");
					groupInfo = new String[] { topDate,
							data.getString("ship_day") };
				}
				flag = dateCompare(data.getString("ship_date"), topDate);
				if (flag) {
					addInfo(groupInfo, childInfo);
					childInfo.clear();
					topDate = data.getString("ship_date");
					groupInfo = new String[] { topDate,
							data.getString("ship_day") };

				}

				EnableToReceiveModel item = new EnableToReceiveModel(
						data.getString("asn_header_id"),
						data.getString("asn_num"),
						data.getString("asn_type_name"),
						data.getString("vendor_id"),
						data.getString("vendor_name"),
						data.getString("status_name"),
						data.getString("expected_date"),
						data.getString("ship_date"),
						data.getString("ship_time"), data.getString("ship_day"));
				childInfo.add(item);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				continue;
			}
			// String foo = data.getString("vendor_name");
			// Log.d("FOO",foo);
		}
		if (childInfo.size() != 0) {
			addInfo(groupInfo, childInfo);
		}
	}

	private void addInfo(String[] g, List<EnableToReceiveModel> c) {
		List<String> groupitem = new ArrayList<String>();
		List<EnableToReceiveModel> childitem = new ArrayList<EnableToReceiveModel>();
		for (int i = 0; i < g.length; i += 1) {
			groupitem.add(g[i]);
		}
		group.add(groupitem);
		for (int j = 0; j < c.size(); j += 1) {
			childitem.add(c.get(j));
		}
		child.add(childitem);
	}

	@Override
	public void modelDidFinshLoad(LMModel model) {
		// TODO 自动生成的方法存根
//		Toast.makeText(getApplicationContext(), "modelDidFinshLoad",
//				Toast.LENGTH_SHORT).show();
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) model;
		String json = new String(reponseModel.mresponseBody);
		try {
			JSONObject jsonObj = new JSONObject(json);
			String code = ((JSONObject) jsonObj.get("head")).get("code")
					.toString();
			if (code.equals("ok")) {
				JSONArray bodyArr = (JSONArray) jsonObj.get("body");
				try {
					initializeData(bodyArr);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

				}
				adapter = new EnableToReceiveAdapter(group, child,
						getApplicationContext());
				shopPoListView.setAdapter(adapter);
				int groupCount = shopPoListView.getCount();
				for (int i = 0; i < groupCount; i++) {
					shopPoListView.expandGroup(i);

				}
				// 打开每一个Group
			} else if (code.equals("failure")) {
				Toast.makeText(getApplicationContext(), "服务器错误",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "网络繁忙请稍后再试",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} finally {
			dialog.dismiss();
		}
		;
	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		// TODO 自动生成的方法存根
		Toast.makeText(getApplicationContext(), "modelDidFaildLoadWithError",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根

	}

	/**
	 * 比较日期,
	 * 
	 * @throws ParseException
	 *             b < e return true
	 * 
	 */
	public static boolean dateCompare(String bDateString, String eDateString)
			throws ParseException {
		boolean flag = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date bDate = format.parse(bDateString);
		Date eDate = format.parse(eDateString);
		try {
			if (bDate.before(eDate)) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
