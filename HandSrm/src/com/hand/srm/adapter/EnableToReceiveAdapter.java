package com.hand.srm.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.hardware.Camera.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hand.srm.R;
import com.hand.srm.model.EnableToReceiveModel;
import com.hand.srm.model.ShopPoListModel;

public class EnableToReceiveAdapter extends BaseExpandableListAdapter {
	private List<List<String>> group;
	private List<List<EnableToReceiveModel>> child;
	private Context context;
	
	//////////////选中列表
	private List<HashMap<String, Integer>> selectList;

	public EnableToReceiveAdapter(List<List<String>> group,
			List<List<EnableToReceiveModel>> child, Context context) {
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
		EnableToReceiveModel childInfo = child.get(groupPosition).get(
				childPosition);
		String AsnNumString = childInfo.getAsnNum();
		String AsnTypeName = childInfo.getAsnTypeName();
		String VendorNameString = childInfo.getVendorName();
		String StatusString = childInfo.getStatusName();
		String ExpectedDateString = childInfo.getExpectedDate();
		TextView asnNum = (TextView) convertView.findViewById(R.id.po_num);
		TextView asnTypeName = (TextView) convertView
				.findViewById(R.id.release_time);
		TextView asnVendorName = (TextView) convertView
				.findViewById(R.id.vendor_name);
		TextView statusName = (TextView) convertView
				.findViewById(R.id.srm_status);
		TextView expectedDate = (TextView) convertView
				.findViewById(R.id.total_amount);
		asnNum.setText(AsnNumString);
		asnTypeName.setText(AsnTypeName);
		asnVendorName.setText(VendorNameString);
		statusName.setText(StatusString);
		expectedDate.setText(ExpectedDateString);
		
		HashMap<String, Integer> record = new HashMap<String, Integer>(){
			{
				put("groupPosition", new Integer(groupPosition));
				put("childPosition", new Integer(childPosition));
			}
			
		};
		
		if(selectList.contains(record))
		{
			convertView.setBackgroundResource(R.drawable.grey);
			
		}else {
			
			convertView.setBackgroundResource(R.drawable.white);
		}
		
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return true;
	}
	
/**
 * 	
 * @return
 */
	public List<List<EnableToReceiveModel>> getChildList()
	{
		return child;
	}
	
/*
 * 选择记录
 *
 */
	public List<HashMap<String, Integer>>  getSelectList()
	{
		
		return selectList;
	}
	
	
	public  void selectRecord(final int groupPosition,final int childPosition)
	{
		
		HashMap<String, Integer> record = new HashMap<String, Integer>(){
			{
				put("groupPosition", new Integer(groupPosition));
				put("childPosition", new Integer(childPosition));
			}
			
		};
		
		if(selectList.contains(record)){
			
			selectList.remove(record);
			
		}else{
		
			selectList.add(record);
		
		}
		 notifyDataSetChanged();
		
	}
/**
 * 获得选中的个数	
 */
	public int getRecordsCount()
	{
		
		return selectList.size();
	}
	
/**
 * 取所所有选择	
 */
	public void removeAllRecords()
	{
		selectList.clear();
		 notifyDataSetChanged();
		
	}
	
}
