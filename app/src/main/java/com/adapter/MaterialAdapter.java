package com.adapter;

import java.util.ArrayList;
import com.activity.R;
import com.bean.MixtureInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MaterialAdapter extends BaseAdapter{
	
	ArrayList<MixtureInfo> mixtureList;
	
	public MaterialAdapter(ArrayList<MixtureInfo> mixtureList) {
		super();
		this.mixtureList = mixtureList;
		
	}

	public int getCount() {
		return mixtureList.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_adapter, parent, false);
		TextView txt_name = convertView.findViewById(R.id.txt_name);
		MixtureInfo  mixtureInfo = mixtureList.get(position);
		txt_name.setText(mixtureInfo.getMixturename());
		txt_name.setTag(mixtureInfo);
		return convertView;
	}

}