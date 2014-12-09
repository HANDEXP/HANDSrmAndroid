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
import android.widget.ImageButton;
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
import com.hand.srm.model.CloseModel;
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

public class EnableToReceiveActivity extends SherlockActivity implements
		LMModelDelegate, OnClickListener {
	private int pageNum = 1;
	private List<List<String>> group;

	private List<List<EnableToReceiveModel>> child;

	private EnableToReceiveAdapter adapter;

	private ExpandableListView shopPoListView;

	private PullToRefreshExpandableListView mPullRefreshListView;
	private EnableToReceiveSvcModel model;
	private CloseModel closemodel;

	private ProgressDialog dialog;

	public static int RETURN_PARAMETER = 1;
	private HashMap<String, String> searchParm;
	private Boolean reloadFlag = true;
	// ////////////////是否只查待收货//////////////////
	private Boolean toReceiveFlag;

	// /////action bar
	private ActionMode mActionMode;
	private ActionMode.Callback actionModeCallback = new ActionModeOfApproveCallback();
	private Boolean actionModeFlag = false;
	private TextView titleTextView;
	private ImageButton returnBtn;
	private ImageButton searchBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_po_list);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.navigation_header);

		toReceiveFlag = getIntent().getBooleanExtra("to_receive_flag", false);

		bindAllViews();

		model = new EnableToReceiveSvcModel(this);
		closemodel = new CloseModel(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (reloadFlag == true) {
			searchParm.put("page_num", String.valueOf(pageNum++));
			if (toReceiveFlag) {
				searchParm.put("to_receive", "YES");
			}
			model.search(searchParm);
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

			if (toReceiveFlag) {

				searchParm.put("to_receive", "YES");
			}
			model.search(searchParm);

			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.return_btn:
			finish();
			break;
		case R.id.search_btn:
			Intent searchIntent = new Intent(getApplicationContext(),
					SearchForDeliveryActivity.class);
			startActivityForResult(searchIntent, RETURN_PARAMETER);
			overridePendingTransition(R.anim.move_right_in_activity,
					R.anim.move_left_out_activity);
			break;
		default:
			break;
		}
	}

	// ////////////////////////////private////////////////////
	/**
	 * 绑定View
	 * 
	 */
	private void bindAllViews() {
		// ActionBar
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		titleTextView.setText("送货单列表");
		returnBtn = (ImageButton) findViewById(R.id.return_btn);
		returnBtn.setOnClickListener(this);
		searchBtn = (ImageButton) findViewById(R.id.search_btn);
		searchBtn.setVisibility(View.VISIBLE);
		searchBtn.setOnClickListener(this);

		if (searchParm == null) {
			searchParm = new HashMap<String, String>();
		}
		searchParm = new HashMap<String, String>();
		mPullRefreshListView = (PullToRefreshExpandableListView) findViewById(R.id.shopPoListView);
		mPullRefreshListView.setMode(Mode.BOTH);
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
						// new GetDataTaskForLoad().execute();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						// TODO 自动生成的方法存根
						searchParm.put("page_num", String.valueOf(pageNum++));
						model.search(searchParm);
						// new GetDataTaskForSearch().execute();
					}

				});
		shopPoListView = mPullRefreshListView.getRefreshableView();
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
							.get(childPosition).getAsnHeaderId();
					Intent intent = new Intent(getApplicationContext(),
							EnableToReceiveDetailActivity.class);
					intent.putExtra("purHeaderId", headerId);
					startActivity(intent);
					overridePendingTransition(R.anim.move_right_in_activity,
							R.anim.move_left_out_activity);
					return false;
				}
			}
		});

		// 只有当to_receive为 true的时候才能开启关闭功能
		if (toReceiveFlag) {
			shopPoListView
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {

							mActionMode = getSherlock().startActionMode(
									actionModeCallback);
							mActionMode.setTitle("关闭");

							return false;
						}
					});
		}
		shopPoListView.setGroupIndicator(null);
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
			child = new ArrayList<List<EnableToReceiveModel>>();
		}
		String[] groupInfo = new String[2];
		List<EnableToReceiveModel> childInfo = new ArrayList<EnableToReceiveModel>();
		String topDate = null;
		Boolean flag = false;
		int length = jsonArr.length();
		for (int i = 0; i < length; i += 1) {
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
						data.getString("ship_time"), 
						data.getString("ship_day"),
						data.getString("process_status_name"),
						data.getString("cancel_process_status_name"));
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

	// //////////////////////////////////删除被关闭的订单//////////////////
	private void closeSelectList() {
		List<HashMap<String, Integer>> selectList = adapter.getSelectList();

		for (int i = 0; i < selectList.size(); i++) {

			int groupPosition = selectList.get(i).get("groupPosition");
			int childPosition = selectList.get(i).get("childPosition");
			child.get(groupPosition).remove(childPosition);
			if (child.get(groupPosition).size() == 0) {

				group.remove(groupPosition);
			}
		}

	}

	// ////////////////////////////model delegate//////////////////////////
	@Override
	public void modelDidFinshLoad(LMModel _model) {
		// TODO 自动生成的方法存根
		// Toast.makeText(getApplicationContext(), "modelDidFinshLoad",
		// Toast.LENGTH_SHORT).show ga();
		AsHttpRequestModel reponseModel = (AsHttpRequestModel) _model;

		if (_model instanceof CloseModel) {
			String json = null;
			JSONObject jsonObj = null;
			try {

				json = new String(reponseModel.mresponseBody);
				jsonObj = new JSONObject(json);
				String code = ((JSONObject) jsonObj.get("head")).get("code")
						.toString();
				if (code.equals("ok")) {

					closeSelectList();

				} else if (code.equals("failure")) {
					Toast.makeText(getApplicationContext(), "服务器返回错误代码",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// String msg =
				boolean flag = json.replaceAll("\r\n", "").matches(".*error.*");
				if (flag) {
					try {

						String message = ((JSONObject) jsonObj.get("error"))
								.get("message").toString();
						Toast.makeText(getApplicationContext(), message,
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
				e.printStackTrace();

			} finally {
				mActionMode.finish();
				dialog.dismiss();
				adapter.removeAllRecords();
			}

		} else if (_model instanceof EnableToReceiveSvcModel) {

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
						adapter = new EnableToReceiveAdapter(group, child,
								getApplicationContext(),toReceiveFlag);
						shopPoListView.setAdapter(adapter);
					} else {
						new AddDataTask().execute();
					}
					// ExpandableListView下标越界
					int groupCount = adapter.getGroupCount();
					for (int i = 0; i < groupCount; i++) {
						// 打开每一个Group
						shopPoListView.expandGroup(i);

					}
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
				if (mPullRefreshListView.isRefreshing()) {
					mPullRefreshListView.onRefreshComplete();
				}
			}

		}
	}

	@Override
	public void modelDidStartLoad(LMModel model) {
		// TODO 自动生成的方法存根
		if (model instanceof EnableToReceiveSvcModel) {
			dialog = new ProgressDialog(this, "数据正在加载中，请稍后");
			dialog.show();
		} else if (model instanceof CloseModel) {
			dialog = new ProgressDialog(this, "正在提交关闭送货单");
			dialog.show();

		}
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

	// /////////////////////////refresh task//////////////////////////////
	private class GetDataTaskForLoad extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			model.load();
			// Thread.sleep(2000);
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
			model.load();
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
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

	// ////////////////////////////////////actionmode//////////////////

	class ActionModeOfApproveCallback implements ActionMode.Callback {
		private static final int MENU_ID_DENY = 1;

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			menu.add(0, MENU_ID_DENY, 1, "拒绝")
					.setIcon(R.drawable.ic_approve_agree_dark)
					.setTitle("拒绝")
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_ALWAYS
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

			actionModeFlag = true;
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

			List<HashMap<String, Integer>> selectList = adapter.getSelectList();

			JSONArray closeArray = new JSONArray();

			for (int i = 0; i < selectList.size(); i++) {

				JSONObject closeItem = new JSONObject();
				HashMap<String, Integer> record = selectList.get(i);
				Integer groupPosition = record.get("groupPosition");
				Integer childPosition = record.get("childPosition");

				EnableToReceiveModel data = adapter.getChildList()
						.get(groupPosition.intValue())
						.get(childPosition.intValue());
				String asn_header_id = data.getAsnHeaderId();

				try {
					closeItem.put("asn_header_id", asn_header_id);
					closeArray.put(closeItem);
				} catch (JSONException e) {

					e.printStackTrace();
					return false;

				}

			}

			closemodel.load(closeArray);

			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {

			actionModeFlag = false;
			adapter.removeAllRecords();
		}
	}

}
