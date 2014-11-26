package com.hand.srm.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.hand.srm.R;
import com.hand.srm.adapter.EnableToReceiveAdapter;
import com.hand.srm.model.EnableToReceiveModel;
import com.hand.srm.model.EnableToReceiveSvcModel;
import com.hand.srm.model.SearchForDeliverySvcModel;
import com.hand.srm.model.ShopPoListModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.mas.customview.ProgressDialog;

public class EnableToReceiveActivity extends SherlockActivity implements LMModelDelegate {
	private int pageNum = 1;
	private List<List<String>> group;
	
	private List<List<EnableToReceiveModel>> child;
	
	private EnableToReceiveAdapter adapter;
	
	private ExpandableListView shopPoListView;
	
	private PullToRefreshExpandableListView mPullRefreshListView;
	private EnableToReceiveSvcModel model;
	private TextView backTextView;
	private TextView searchTextView;
	private ProgressDialog dialog;

	public static int RETURN_PARAMETER = 1;
	private HashMap<String, String> searchParm;

		
	///////action bar
	private ActionMode mActionMode;
	private ActionMode.Callback actionModeCallback = new ActionModeOfApproveCallback();
	private Boolean actionModeFlag = false;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_po_list);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.navigation_header);
		
		bindAllViews();
		
		model = new EnableToReceiveSvcModel(this);
	}
	

	
	@Override
	protected void onResume() {
		super.onResume();
		searchParm.put("page_num", String.valueOf(pageNum++));
		model.search(searchParm);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		
	
	}

	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (resultCode) {
		case 1:
			searchParm = (HashMap<String, String>) data.getSerializableExtra("searchParm");
			model.search(searchParm);
//			Toast.makeText(getApplicationContext(), "RETURN", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

//////////////////////////////private////////////////////	
	/**
	 * 绑定View
	 * 
	 */
	private void bindAllViews() {
		
		
		searchParm = new HashMap<String, String>();
		mPullRefreshListView = (PullToRefreshExpandableListView) findViewById(R.id.shopPoListView);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ExpandableListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				// TODO 自动生成的方法存根
				group = null;
				child = null;
				pageNum = 1;
				searchParm.put("page_num", String.valueOf(pageNum++));	
				model.search(searchParm);
//				new GetDataTaskForLoad().execute();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				// TODO 自动生成的方法存根
				searchParm.put("page_num", String.valueOf(pageNum++));	
				model.search(searchParm);
//				new GetDataTaskForSearch().execute();
			}

		});
		shopPoListView = mPullRefreshListView.getRefreshableView();
		shopPoListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO 自动生成的方法存根
				
				if(actionModeFlag){
					
					adapter.selectRecord(groupPosition,childPosition);
					if(adapter.getRecordsCount() != 0){
						mActionMode.setSubtitle(String.valueOf(adapter.getRecordsCount()));
					}else {
						
						mActionMode.finish();
						
					}
					return  true;
				}else {
				
					String headerId = child.get(groupPosition).get(childPosition)
							.getAsnHeaderId();
					Intent intent = new Intent(getApplicationContext(),
							EnableToReceiveDetailActivity.class);
					intent.putExtra("purHeaderId", headerId);
					startActivity(intent);
					overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);			
					return false;
				}
			}
		});
		
		shopPoListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mActionMode = getSherlock().startActionMode(actionModeCallback);
				mActionMode.setTitle("关闭");

				
				
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
		searchTextView = (TextView) findViewById(R.id.searchTextView);
		searchTextView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
//				Intent searchIntent = new Intent(getApplicationContext(),SearchForPurchasingActivity.class);
//				startActivityForResult(searchIntent, RETURN_PARAMETER);
//				overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);				
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
		if(group == null || child == null){
			group = new ArrayList<List<String>>();
			child = new ArrayList<List<EnableToReceiveModel>>();			
		}
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

	
//////////////////////////////model delegate//////////////////////////	
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
			if(mPullRefreshListView.isRefreshing()){
				mPullRefreshListView.onRefreshComplete();
			}
		}
		;
	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		// TODO 自动生成的方法存根
		dialog = new ProgressDialog(this, "数据正在加载中，请稍后");
		dialog.show();
	}

	@Override
	public void modelDidFaildLoadWithError(LMModel model) {
		// TODO 自动生成的方法存根
		Toast.makeText(getApplicationContext(), "modelDidFaildLoadWithError",
				Toast.LENGTH_SHORT).show();
		dialog.dismiss();
		if(mPullRefreshListView.isRefreshing()){
			mPullRefreshListView.onRefreshComplete();
		}
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

	
///////////////////////////refresh  task//////////////////////////////	
	private class GetDataTaskForLoad extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	private class GetDataTaskForSearch extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	
	
	
//////////////////////////////////////actionmode//////////////////	
	class ActionModeOfApproveCallback implements ActionMode.Callback {
		private static final int MENU_ID_DENY = 1;

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			menu.add(0, MENU_ID_DENY, 1, "拒绝")
			        .setIcon(R.drawable.ic_approve_agree_dark).setTitle("拒绝")
			        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			
			actionModeFlag = true;
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {

			actionModeFlag = false;
			adapter.removeAllRecords();
		}
	}
}
