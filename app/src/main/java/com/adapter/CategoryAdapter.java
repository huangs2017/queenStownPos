package com.adapter;

import com.bean.CategoryInfo;
import com.activity.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    ArrayList<CategoryInfo> categoryList;

    public CategoryAdapter(ArrayList<CategoryInfo> categoryList) {
        super();
        this.categoryList = categoryList;

    }

    public int getCount() {
        return categoryList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter, parent, false);
        TextView txt_name = convertView.findViewById(R.id.txt_name);
        CategoryInfo category = categoryList.get(position);
        String categories2name = category.getCategories2name();
        String categories2Id = category.getCategories2id();
        txt_name.setText(categories2name);
        txt_name.setTag(categories2Id);
        return convertView;
    }

}