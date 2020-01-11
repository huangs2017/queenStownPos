package com.view;

import java.util.ArrayList;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.activity.CollectMoney;
import com.activity.R;
import com.adapter.MaterialAdapter;
import com.bean.MixtureInfo;
import com.bean.ProductInfo;
import com.util.db.DBUtil;

public class MaterialPopWindow {
	
	PopupWindow popupWindow;
	GridView gv_material;
    int selectedCounter; //已选配料数
    TextView tv_yixuan;

    ArrayList<MixtureInfo> allMixtureList = new ArrayList<MixtureInfo>(); 		// 所有配料
	ArrayList<MixtureInfo> selectedMixtureList = new ArrayList<MixtureInfo>(); 	// 选中的配料
    
	//配料PopWindow
	public void materialPopWindow(final CollectMoney activity, final ProductInfo product){
		final View vPopupWindow = activity.getLayoutInflater().inflate(R.layout.collectmoney_mixture, null);
        popupWindow = new PopupWindow(vPopupWindow, 720, 640, true); // true获得焦点
        selectedCounter =0;
        final int totalNumber = product.getSalemodel();
        
        gv_material = vPopupWindow.findViewById(R.id.gv_material);
	    TextView tv_kexuan = vPopupWindow.findViewById(R.id.collectmoney_material_kexuan);
	    tv_yixuan = vPopupWindow.findViewById(R.id.collectmoney_material_yixuan);
	    tv_kexuan.setText(totalNumber + "");
	    tv_yixuan.setText("");
        
    	class Material_OnClick implements OnClickListener {
 		    public void onClick(View v) {
 			    //确定button
 			    if( v.getId() == R.id.collectmoney_material_ensure){
 			    	product.setMixtureList(selectedMixtureList); //把选中配料放进去
 			    	activity.mixtureResult();
 			        popupWindow.dismiss();
 			    }
 			    //关闭button
 			    if( v.getId() == R.id.collectmoney_material_close){
	 				popupWindow.dismiss();
 			    }
 		    }
        }  
    	
    	Button  btn_ensure = vPopupWindow.findViewById(R.id.collectmoney_material_ensure);
	    Button  btn_close = vPopupWindow.findViewById(R.id.collectmoney_material_close);
	    btn_ensure.setOnClickListener(new Material_OnClick());
	    btn_close.setOnClickListener(new Material_OnClick());

		allMixtureList = DBUtil.getMixtureList(activity);
		gv_material.setAdapter(new MaterialAdapter(allMixtureList));
		
		gv_material.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (selectedCounter < totalNumber) {
					selectedCounter++;
					tv_yixuan.setText(selectedCounter + "");
					TextView txt_name = view.findViewById(R.id.txt_name);
					MixtureInfo mixtureInfo = (MixtureInfo) txt_name.getTag();
					int quantity = mixtureInfo.getQuantity() + 1;
					mixtureInfo.setQuantity(quantity);
					if (!selectedMixtureList.contains(mixtureInfo)) { // 初次点击配料
						selectedMixtureList.add(mixtureInfo);
					}
				}
			}
		});

        popupWindow.showAtLocation(activity.btn_xianjin, Gravity.CENTER, 0, 0);
	}

}
