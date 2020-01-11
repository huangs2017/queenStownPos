package com.view;

import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;
import com.activity.CollectMoney;
import com.http.HttpUtil;
import com.http.UDPUtil;
import com.http.httpUrl;
import com.activity.R;
import com.util.db.DBUtil;
import com.util.JsonUtil;
import com.util.SPUtils;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class XianJinWindowClickListener implements OnClickListener {

    private CollectMoney activity;
    private PopupWindow popupWindow;
    private EditText edit_money;
    private Button btn_zhaoling;
    private LinearLayout lay_gone;

    public XianJinWindowClickListener(CollectMoney activity, PopupWindow popupWindow, EditText edit_money,
                                      Button btn_zhaoling, LinearLayout lay_gone) {
        this.activity = activity;
        this.popupWindow = popupWindow;
        this.edit_money = edit_money;
        this.btn_zhaoling = btn_zhaoling;
        this.lay_gone = lay_gone;
    }


    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        switch (v.getId()) {
            case R.id.collectmoney_pop_back:  //回退
                String temp1 = edit_money.getText().toString();
                if (temp1.length() > 0) {
                    edit_money.setText(temp1.substring(0, temp1.length() - 1));
                }
                break;
            case R.id.collectmoney_pop_delete:
                edit_money.setText("");
                break;
            case R.id.collectmoney_pop_ok:
                if (TextUtils.isEmpty(edit_money.getText().toString())) {
                    break;
                }

                int shouru = Integer.parseInt(edit_money.getText().toString());                    // 收入
                double zongji = Double.parseDouble(activity.txt_total_price.getText().toString()); // 总计
                btn_zhaoling.setVisibility(View.VISIBLE);
                if (shouru < zongji) {
                    btn_zhaoling.setText("金额不足");
                    edit_money.setText("");
                    new UDPUtil(activity.udp_port2, "金额不足").start();
                } else {
                    new UDPUtil(activity.udp_port2, "收入:" + shouru + "    总计:" + zongji + "    找零:" + (shouru - zongji)).start();
                    Time time = new Time();
                    time.setToNow();

                    //从SharedPreferences获取日期，并与当前日期比较
                    if (SPUtils.getString(activity, "orders_date") == null) {
                        SPUtils.putString(activity, "orders_date", time.format("%Y-%m-%d"));
                    }
                    String orders_date = SPUtils.getString(activity, "orders_date");
                    if (!(time.format("%Y-%m-%d").equals(orders_date))) {
                        SPUtils.putString(activity, "orders_date", time.format("%Y-%m-%d"));
                        SPUtils.putInt(activity, "orderidtail", 1);
                    }

                    String shopid = SPUtils.getString(activity, "shopid");
                    int orderidtail = SPUtils.getInt(activity, "orderidtail", 1);
                    String orderid = shopid + time.format("%Y%m%d") + String.format("%05d", orderidtail);

                    String cashierNum = SPUtils.getString(activity, "thisCashiernum");
                    ArrayList<BasicNameValuePair> arrayList = DBUtil.insertOrder(activity, zongji, orderid, time, cashierNum, activity.cartList);

                    SPUtils.putInt(activity, "orderidtail", ++orderidtail);
                    // 上传orders orderlist mixtureorderlist表的数据
                    new HttpUtil(activity, httpUrl.update, arrayList, activity, activity.http_uploadOrder).start();

                    lay_gone.setVisibility(View.GONE);
                    btn_zhaoling.setText("找零： ¥" + String.valueOf(shouru - zongji));
                    String biaotou = "皇后镇冰酸奶" + "\n";
                    String danhao = "销售单号：" + orderid + "\n";
                    String fenge = "-------------------------------\n";
                    String biaozhong = "商品名称          数量 单价 总价\n";
//					打印
//				JBInterface.printText(biaotou + danhao + fenge + biaozhong + printString + fenge + "总计:" + String.valueOf(zongji)
//						+ "  收入:" + String.valueOf(shouru) + "  找零:" + String.valueOf(shouru - zongji));
//				JBInterface.printText(biaotou + danhao + fenge + biaozhong + printString + fenge + "总计:" + String.valueOf(zongji)
//						+ "  收入:" + String.valueOf(shouru) + "  找零:" + String.valueOf(shouru - zongji));
                    //钱箱
//				JBInterface.QX_CTL(true);//打开钱箱
                    try {
                        Thread.sleep(100);
//					JBInterface.QX_CTL(false); //关闭钱箱
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.collectmoney_pop_close:
                popupWindow.dismiss();
                break;
            case R.id.btn_zhaoling:
                activity.cartList.clear();
                activity.cartAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                new UDPUtil(activity.udp_port1, JsonUtil.bean2Json(activity.cartList)).start();
                new UDPUtil(activity.udp_port2, "").start();
                break;
            case R.id.twenty:
            case R.id.fifty:
            case R.id.hundred:
                edit_money.setText(button.getText());
                break;
            default:  //数字键
                edit_money.append(button.getText());
                break;
        }
    }

}