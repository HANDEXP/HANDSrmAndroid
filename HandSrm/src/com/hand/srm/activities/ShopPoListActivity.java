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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.hand.srm.R;
import com.hand.srm.activities.EnableToReceiveActivity.ActionModeOfApproveCallback;
import com.hand.srm.adapter.ShopPoListAdapter;
import com.hand.srm.model.EnableToReceiveModel;
import com.hand.srm.model.SearchForDeliverySvcModel;
import com.hand.srm.model.ShopPoListModel;
import com.hand.srm.model.ShopPoListSvcModel;
import com.hand.srm.model.UrgentModel;
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
	private View header; // 顶部布局文件
	private List<List<String>> group;
	private List<List<ShopPoListModel>> child;
	private ShopPoListAdapter adapter;
	private ExpandableListView shopPoListView;
	private PullToRefreshExpandableListView mPullRefreshListView;
	private ShopPoListSvcModel model;
	// private SearchForDeliverySvcModel model;
	private TextView backTextView;
	private TextView searchTextView;
	private ProgressDialog dialog;
	private Boolean reloadFlag = true;
	public static int RETURN_PARAMETER = 1;
	private HashMap<String, String> searchParm;
	private Boolean searchFlag = false;

	// //////是否只查待发货/////////////////
	private Boolean toShipFlag;

	private UrgentModel urgentmodel;

	// /////action bar/////////////////
	private ActionMode mActionMode;
	private ActionMode.Callback actionModeCallback = new ActionModeOfApproveCallback();
	private Boolean actionModeFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.navigation_header);

		toShipFlag = getIntent().getBooleanExtra("to_ship_flag", false);

		setContentView(R.layout.activity_ship_po_list);

		bindAllViews();

		model = new ShopPoListSvcModel(this);

		urgentmodel = new UrgentModel(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (reloadFlag == true) {
			searchParm.put("page_num", String.valueOf(pageNum++));
			if (toShipFlag) {
				searchParm.put("to_ship", "true");
			}
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 1:
			group = null;
			child = null;
			adapter = null;
			searchParm = (HashMap<String, String>) data
					.getSerializableExtra("searchParm");
			pageNum = 1;
			searchParm.put("page_num", String.valueOf(pageNum++));
			if (toShipFlag) {
				searchParm.put("to_ship", "true");
			}
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
		// 上拉刷新

		if (searchParm == null) {
			searchParm = new HashMap<String, String>();
		}

		mPullRefreshListView = (PullToRefreshExpandableListView) findViewById(R.id.shopPoListView);
		mPullRefreshListView
				.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
		shopPoListView = mPullRefreshListView.getRefreshableView();

		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ExpandableListView>() {

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

		// 只有当to_receive为 true的时候才能开启关闭功能
		if (toShipFlag) {
			shopPoListView
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {

							mActionMode = getSherlock().startActionMode(
									actionModeCallback);
							mActionMode.setTitle("加急处理");
							return false;
						}
					});
		}

		shopPoListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO 自动生成的方法存根
				if (actionModeFlag) {

					adapter.selectRecord(groupPosition, childPosition);
					if (adapter.getRecordsCount() != 0) {
						mActionMode.setSubtitle(String.valueOf(adapter
								.getRecordsCount()));
					} else {

						mActionMode.finish();

					}

					return true;
				} else {
					String headerId = child.get(groupPosition)
							.get(childPosition).getPurHeaderId();
					Intent intent = new Intent(getApplicationContext(),
							ShopPoListDetailActivity.class);
					intent.putExtra("purHeaderId", headerId);
					startActivity(intent);
					overridePendingTransition(R.anim.move_right_in_activity,
							R.anim.move_left_out_activity);
					return false;

				}
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
				Intent searchIntent = new Intent(getApplicationContext(),
						SearchForDeliveryActivity.class);
				startActivityForResult(searchIntent, RETURN_PARAMETER);
				overridePendingTransition(R.anim.move_right_in_activity,
						R.anim.move_left_out_activity);
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
		if (group == null || child == null) {
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

				ShopPoListModel item = new ShopPoListModel(
						data.getString("pur_header_id"),
						data.getString("po_num"), data.getString("vendor_id"),
						data.getString("vendor_name"),
						data.getString("srm_status"),
						data.getString("total_amount"),
						data.getString("release_date"),
						data.getString("release_time"),
						data.getString("release_day"));
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

	
	////////////////////////////////////删除被关闭的订单//////////////////
	private void closeSelectList()
	{
		List<HashMap<String, Integer>>  selectList  =   adapter.getSelectList();
		
		for(int i = 0;i< selectList.size();i++){
			
			int groupPosition   =  selectList.get(i).get("groupPosition");
			int childPosition  =  selectList.get(i).get("childPosition");
			child.get(groupPosition).remove(childPosition);	
			if(child.get(groupPosition).size() == 0){
				
				group.remove(groupPosition);
			}
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

	private JSONArray packSelectList() {
		List<HashMap<String, Integer>> selectList = adapter.getSelectList();

		JSONArray closeArray = new JSONArray();

		for (int i = 0; i < selectList.size(); i++) {

			JSONObject closeItem = new JSONObject();
			HashMap<String, Integer> record = selectList.get(i);
			Integer groupPosition = record.get("groupPosition");
			Integer childPosition = record.get("childPosition");

			ShopPoListModel data = adapter.getChildList()
					.get(groupPosition.intValue())
					.get(childPosition.intValue());
			String asn_header_id = data.getPurHeaderId();

			try {
				closeItem.put("pur_header_id", asn_header_id);
				closeArray.put(closeItem);
			} catch (JSONException e) {

				e.printStackTrace();
				return closeArray;

			}

		}
		return closeArray;
	}

	@Override
	public void modelDidFinshLoad(LMModel _model) {
		// TODO 自动生成的方法存根
		// Toast.makeText(getApplicationContext(), "modelDidFinshLoad",
		// Toast.LENGTH_SHORT).show();
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) _model;

		if (_model instanceof ShopPoListSvcModel) {

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
					if (adapter == null) {
						adapter = new ShopPoListAdapter(group, child,
								getApplicationContext());
						shopPoListView.setAdapter(adapter);
					} else {
						new AddDataTask().execute();
					}

					// ExpandableListView下标越界
					int groupCount = shopPoListView.getCount();
					for (int i = 0; i < groupCount - 1; i++) {
						shopPoListView.expandGroup(i);

					}
				} else if (code.equals("failure")) {
					Toast.makeText(getApplicationContext(), "服务器错误",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				// Toast.makeText(getApplicationContext(), "网络繁忙请稍后再试",
				// Toast.LENGTH_SHORT).show();
				e.printStackTrace();

			} finally {
				dialog.dismiss();
				if (mPullRefreshListView.isRefreshing()) {
					mPullRefreshListView.onRefreshComplete();
				}
			}
		}else if(_model instanceof UrgentModel){

			try {
				String json = new String(reponseModel.mresponseBody);			
				JSONObject jsonObj = new JSONObject(json);
				String code = ((JSONObject) jsonObj.get("head")).get("code")
						.toString();
				if (code.equals("ok")) {
					
					 
					closeSelectList();

				} else if (code.equals("failure")) {
					Toast.makeText(getApplicationContext(), "请求失败",
							Toast.LENGTH_SHORT).show();
				}
				
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "服务器返回数据格式错误",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} finally {
				mActionMode.finish();
				dialog.dismiss();
				adapter.removeAllRecords();
			}



		}
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
		if (mPullRefreshListView.isRefreshing()) {
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

	private class AddDataTask extends AsyncTask<Void, Integer, Boolean> {

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

	private class ActionModeOfApproveCallback implements ActionMode.Callback {

		private static final int add_urgent_id = 1;

		private static final int cancel_urgent_id = 2;

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			menu.add(0, add_urgent_id, 1, "加急")
					.setIcon(R.drawable.ic_approve_agree_dark)
					.setTitle("加急")
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_ALWAYS
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

			menu.add(0, cancel_urgent_id, 2, "取消加急")
					.setIcon(R.drawable.ic_approve_agree_dark)
					.setTitle("取消加急")
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_ALWAYS
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

			actionModeFlag = true;
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			if (item.getItemId() == add_urgent_id) {
				urgentmodel.AddUrgent(packSelectList());
				return true;
			} else if (item.getItemId() == cancel_urgent_id) {
				packSelectList();
				urgentmodel.CancelUrgent(packSelectList());
				return true;
			}

			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			actionModeFlag = false;
			adapter.removeAllRecords();
		}
	}

}
