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
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.hand.srm.R;
import com.hand.srm.adapter.ShopPoListAdapter;
import com.hand.srm.model.SearchForDeliverySvcModel;
import com.hand.srm.model.ShopPoListModel;
import com.hand.srm.model.ShopPoListSvcModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.littlemvc.model.LMModel;
import com.littlemvc.model.LMModelDelegate;
import com.littlemvc.model.request.AsHttpRequestModel;
import com.mas.customview.ProgressDialog;

public class ShopPoListActivity extends SherlockActivity implements
		OnClickListener, LMModelDelegate {
	private int pageNum = 1;
	private View header;  //顶部布局文件
	private List<List<String>> group;
	private List<List<ShopPoListModel>> child;
	private ShopPoListAdapter adapter;
	private ExpandableListView shopPoListView;
	private PullToRefreshExpandableListView mPullRefreshListView;
	private ShopPoListSvcModel model;
//	private SearchForDeliverySvcModel model;
	private TextView backTextView;
	private TextView searchTextView;
	private ProgressDialog dialog;
	private Boolean reloadFlag = true;
	public static int RETURN_PARAMETER = 1;
	private HashMap<String, String> searchParm;
	private Boolean searchFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ship_po_list);
		model = new ShopPoListSvcModel(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		bindAllViews();
		if(reloadFlag == true){
			searchParm.put("page_num", String.valueOf(pageNum++));
			model.search(searchParm);
			searchFlag = false;
		}
		
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		reloadFlag = false;
	}

	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (resultCode) {
		case 1:
			group = null;
			child = null;
			adapter = null;
			searchParm = (HashMap<String, String>) data.getSerializableExtra("searchParm");
			pageNum = 1;
			searchParm.put("page_num", String.valueOf(pageNum++));	
			model.search(searchParm);
			break;

		default:
			break;
		}
	}	
	
	/**
	 * 绑定View
	 * 
	 */
	private void bindAllViews() {
		//上拉刷新

		if(searchParm == null){
			searchParm = new HashMap<String, String>();
		}
		mPullRefreshListView = (PullToRefreshExpandableListView) findViewById(R.id.shopPoListView);
		mPullRefreshListView.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
		shopPoListView = mPullRefreshListView.getRefreshableView();
		
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ExpandableListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				// TODO 自动生成的方法存根
				group = null;
				child = null;
				adapter = null;
				pageNum = 1;
				searchParm.put("page_num", String.valueOf(pageNum++));	
				model.search(searchParm);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				// TODO 自动生成的方法存根
				searchParm.put("page_num", String.valueOf(pageNum++));	
				model.search(searchParm);
			}

		});
		
		shopPoListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO 自动生成的方法存根
				
				String headerId = child.get(groupPosition).get(childPosition).getPurHeaderId();
				Intent intent = new Intent(getApplicationContext(),ShopPoListDetailActivity.class);
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
		searchTextView = (TextView) findViewById(R.id.searchTextView);
		searchTextView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent searchIntent = new Intent(getApplicationContext(),SearchForDeliveryActivity.class);
				startActivityForResult(searchIntent, RETURN_PARAMETER);
				overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);				
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
			child = new ArrayList<List<ShopPoListModel>>();			
		}
		String[] groupInfo = new String[2];
		List<ShopPoListModel> childInfo = new ArrayList<ShopPoListModel>();
		String topDate = null;
		Boolean flag = false;
		int length = jsonArr.length();
		for (int i = 0; i < length; i += 1) {
			JSONObject data = new JSONObject();
			data = (JSONObject) jsonArr.get(i);
			try {
				if (topDate == null) {
					topDate = data.getString("release_date");
					groupInfo = new String[] { topDate,
							data.getString("release_day") };
				}
				flag = dateCompare(data.getString("release_date"), topDate);
				if (flag) {
					addInfo(groupInfo, childInfo);
					childInfo.clear();
					topDate = data.getString("release_date");
					groupInfo = new String[] { topDate,
							data.getString("release_day") };
				}

				ShopPoListModel item = new ShopPoListModel(data
						.getString("pur_header_id"), data.getString("po_num"),
						data.getString("vendor_id"), data
								.getString("vendor_name"), data
								.getString("srm_status"), data
								.getString("total_amount"), data
								.getString("release_date"), data
								.getString("release_time"), data
								.getString("release_day"));
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

	private void addInfo(String[] g, List<ShopPoListModel> c) {
		List<String> groupitem = new ArrayList<String>();
		List<ShopPoListModel> childitem = new ArrayList<ShopPoListModel>();
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
				if(adapter == null){
					adapter = new ShopPoListAdapter(group, child,
							getApplicationContext());
					shopPoListView.setAdapter(adapter);				
				}else{
					new AddDataTask().execute();
				}
				
				// ExpandableListView下标越界
				int groupCount = shopPoListView.getCount();
				for(int i =0; i<groupCount-1;i++){
					shopPoListView.expandGroup(i);
					
				}	
			} else if (code.equals("failure")) {
				Toast.makeText(getApplicationContext(), "服务器错误",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
//			Toast.makeText(getApplicationContext(), "网络繁忙请稍后再试",
//					Toast.LENGTH_SHORT).show();
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
	
	private class AddDataTask extends AsyncTask<Void, Integer, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO 自动生成的方法存根
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {

			// Call onRefreshComplete when the list has been refreshed.
			adapter.notifyDataSetChanged();

			super.onPostExecute(result);
		}
		
	}
}
