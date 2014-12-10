package com.hand.srm.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Camera.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hand.srm.R;
import com.hand.srm.model.EnableToReceiveModel;
import com.hand.srm.model.ShopPoListModel;

public class ShopPoListAdapter extends BaseExpandableListAdapter {
	private List<List<String>> group;
	private List<List<ShopPoListModel>> child;
	private Context context;

	// ////////////选中列表
	private List<HashMap<String, Integer>> selectList;

	public ShopPoListAdapter(List<List<String>> group,
			List<List<ShopPoListModel>> child, Context context) {
		this.group = group;
		this.child = child;
		this.context = context;
		this.selectList = new ArrayList<HashMap<String, Integer>>();
	}

	@Override
	public int getGroupCount() {
		// TODO 自动生成的方法存根
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO 自动生成的方法存根
		return child.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO 自动生成的方法存根
		return group.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO 自动生成的方法存根
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		List<String> groupInfo = group.get(groupPosition);
		String dateString = groupInfo.get(0);
		String weekString = groupInfo.get(1);
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(context).inflate(
					R.layout.activity_shop_po_list_group, null);

		}
		TextView date = (TextView) convertView
				.findViewById(R.id.groupDateTextView);
		TextView week = (TextView) convertView
				.findViewById(R.id.groupWeekTextView);
		date.setText(dateString);
		week.setText(weekString);
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_shio_po_list_child, null);
		}
		ShopPoListModel childInfo = child.get(groupPosition).get(childPosition);
		String PoNumString = childInfo.getNum();
		String ReleaseTimeString = childInfo.getReleaseTime();
		String VendorNameString = childInfo.getVendorName();
		String SrmStatusString = childInfo.getSrmStatusName();
		String TotalAmountString = childInfo.getTotalAmount();
		String CurrencySymbol = childInfo.getCurrencySymbol();
		String urageFlag = childInfo.getUrgentStatusName();
		TextView poNum = (TextView) convertView.findViewById(R.id.po_num);
		TextView releaseTime = (TextView) convertView
				.findViewById(R.id.release_time);
		TextView vendorName = (TextView) convertView
				.findViewById(R.id.vendor_name);
		TextView srmStatus = (TextView) convertView
				.findViewById(R.id.srm_status);
		TextView totalAmount = (TextView) convertView
				.findViewById(R.id.total_amount);
		poNum.setText(PoNumString);
		releaseTime.setText(ReleaseTimeString);
		vendorName.setText(VendorNameString);
		srmStatus.setText(SrmStatusString);
		if (urageFlag.equals("已加急")) {
			srmStatus.setTextColor(Color.RED);
		} else {
			srmStatus.setTextColor(Color.parseColor("#00A0DF"));
		}
		totalAmount.setText(CurrencySymbol + TotalAmountString);

		HashMap<String, Integer> record = new HashMap<String, Integer>() {
			{
				put("groupPosition", Integer.valueOf(groupPosition));
				put("childPosition", Integer.valueOf(childPosition));
			}

		};

		if (selectList.contains(record)) {
			convertView.setBackgroundResource(R.drawable.grey);

		} else {

			convertView.setBackgroundResource(R.drawable.white);
		}

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return true;
	}

	public List<List<ShopPoListModel>> getChildList() {
		return child;
	}

	/*
	 * 选择记录
	 */
	public List<HashMap<String, Integer>> getSelectList() {

		return selectList;
	}

	public void selectRecord(final int groupPosition, final int childPosition) {

		if (child.get(groupPosition).get(childPosition).getSrmStatusName()
				.equals("已确认")) {
			Toast.makeText(context, "采购订单状态错误或未发布", Toast.LENGTH_SHORT).show();
			return;
		}
//		if (child.get(groupPosition).get(childPosition).getSrmStatusName()
//				.equals("已发布")
//				&& child.get(groupPosition).get(childPosition)
//						.getUrgentStatusName().equals("已加急")) {
//			Toast.makeText(context, "已加急订单不可重复加急", Toast.LENGTH_SHORT).show();
//			return;
//		}
		HashMap<String, Integer> record = new HashMap<String, Integer>() {
			{
				put("groupPosition", new Integer(groupPosition));
				put("childPosition", new Integer(childPosition));
			}

		};

		if (selectList.contains(record)) {

			selectList.remove(record);

		} else {

			selectList.add(record);

		}
		notifyDataSetChanged();

	}

	/**
	 * 获得选中的个数
	 */
	public int getRecordsCount() {

		return selectList.size();
	}

	/**
	 * 取所所有选择
	 */
	public void removeAllRecords() {
		selectList.clear();
		notifyDataSetChanged();

	}
}
