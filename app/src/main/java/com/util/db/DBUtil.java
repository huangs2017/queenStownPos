package com.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

import com.bean.CashierInfo;
import com.bean.CategoryInfo;
import com.bean.MixtureInfo;
import com.bean.OrderDetail;
import com.bean.OrderInfo;
import com.bean.ProductInfo;
import com.bean.ShiftInfo;
import com.bean.SystemSet;
import com.util.JsonUtil;
import com.util.MyUtil;
import com.util.annotation.DealAnnotation;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBUtil {

    public static SQLiteDatabase db;

    public static void initDB(Context ctx, String tableName, JSONArray jsonArray) {
        db = DBHelper.getInstance(ctx).db; //初始化数据库

        int size1 = jsonArray.length();
        ContentValues[] recordArray = new ContentValues[size1];
        ContentValues contentValues = new ContentValues();

        try {
            db.execSQL("delete from " + tableName);
            for (int i = 0; i < size1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                JSONArray jsonArray2 = jsonObj.names();
                int size2 = jsonArray2.length();
                for (int j = 0; j < size2; j++) {
                    contentValues.put(jsonArray2.getString(j), (String) jsonObj.get(jsonArray2.getString(j)));
                }
                recordArray[i] = contentValues;
                db.insert(tableName, null, recordArray[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String getShopName(Context ctx) {
        db = DBHelper.getInstance(ctx).db;
        Cursor cursor = db.rawQuery("select * from systemset", null);
        cursor.moveToNext();
        String shopName = cursor.getString(cursor.getColumnIndex("shopname"));
        return shopName;
    }


    public static ArrayList getSystemSet(Context ctx) {
        db = DBHelper.getInstance(ctx).db;
        Cursor systemCursor = db.rawQuery("select * from systemset", null);
        ArrayList<SystemSet> systemList = DealAnnotation.cursor2List(systemCursor, SystemSet.class);
        return systemList;
    }

    public static ArrayList getCashierList(Context ctx) {
        db = DBHelper.getInstance(ctx).db;
        Cursor cashierCursor = db.rawQuery("select * from cashier", null);
        ArrayList<CashierInfo> cashierList = DealAnnotation.cursor2List(cashierCursor, CashierInfo.class);
        return cashierList;
    }

    public static ArrayList getCategoryList(Context ctx) {
        db = DBHelper.getInstance(ctx).db;
        Cursor categoryCursor = db.rawQuery("select * from categories2", null);
        ArrayList<CategoryInfo> categoryList = DealAnnotation.cursor2List(categoryCursor, CategoryInfo.class);
        return categoryList;
    }

    public static ArrayList getProductList(Context ctx, String categories2id) {
        db = DBHelper.getInstance(ctx).db;
        Cursor cursor = db.rawQuery("select * from it where belongto='" + categories2id + "'", null);
        ArrayList<ProductInfo> productList = DealAnnotation.cursor2List(cursor, ProductInfo.class);
        return productList;
    }

    public static ArrayList getMixtureList(Context ctx) {
        db = DBHelper.getInstance(ctx).db;
        Cursor cursor = db.rawQuery("select * from mixture", null);
        ArrayList<MixtureInfo> mixtureList = DealAnnotation.cursor2List(cursor, MixtureInfo.class);
        return mixtureList;
    }

    public static ArrayList getOrderList(Context ctx, String cashierNum) {
        db = DBHelper.getInstance(ctx).db;
        Cursor cursor = db.rawQuery("select * from orders where cashiernum='" + cashierNum + "' AND tag2='0'", null);
        ArrayList<OrderInfo> orderList = DealAnnotation.cursor2List(cursor, OrderInfo.class);
        return orderList;
    }



    public static ArrayList getShiftList(Context ctx) {
        db = DBHelper.getInstance(ctx).db;
        Cursor shiftCursor = db.rawQuery("select * from shifttable where tag='0'", null);
        ArrayList<ShiftInfo> shiftList = DealAnnotation.cursor2List(shiftCursor, ShiftInfo.class);
        return shiftList;
    }

    public static void insertShiftTable(Context ctx, double total_cashier, String shiftid, Time time, String cashierNum) {
        db = DBHelper.getInstance(ctx).db;
        int shuaka = 0; //刷卡
        int huiyuan = 0; //会员
        double amount = total_cashier + shuaka + huiyuan;
        db.execSQL("insert into shifttable values(?, ?, ?, ?," + total_cashier + "," + shuaka + "," + huiyuan + "," + amount + ", '0')",
                new String[]{shiftid, time.format("%Y-%m-%d"), time.format("%H:%M:%S"), cashierNum});
    }



    public static void updateTableTag(Context ctx, String json, String tableName, String tableIdName) throws JSONException {
        db = ctx.openOrCreateDatabase("temp.db", SQLiteDatabase.OPEN_READWRITE, null);
        String jsonStr = JsonUtil.getString(json, tableName);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            String tableIdValue = jsonArray.getString(i);
    //      db.execSQL("update orders set tag='1' where orderid='" + tableId + "'");
            db.execSQL("update " + tableName + " set tag='1' where " + tableIdName + "='" + tableIdValue + "'");
        }
    }

    public static void updateOrderTableTag2(Context ctx, ArrayList<String> orderIdList) {
        db = DBHelper.getInstance(ctx).db;
        for(String orderId : orderIdList) {
            db.execSQL("update orders set tag2='1' where orderid='" + orderId + "'");
        }
    }


    public static ArrayList<BasicNameValuePair> insertOrder(Context ctx, double zongji, String orderid, Time time, String cashierNum, ArrayList<ProductInfo> cartList) {
        db = DBHelper.getInstance(ctx).db;
//		orders(orderid, orderdate, ordertime, amount decimal(12,0), cashiernum, tag,tag2)");
//      orderid = shopid + 20150205 + 00001    2015-02-05  05:15:20      total      cashiernum
        db.execSQL("insert into orders values(?, ?, ?," +zongji+ ", ?, '0', '0')", new String[] {orderid,
                time.format("%Y-%m-%d"), time.format("%H:%M:%S"), cashierNum});

        String printString="";
        int i=1;
        for (ProductInfo product : cartList) {
            String orderlistid = orderid + String.format("%03d", i);
            String itid = product.getItid() ;
            String itname = product.getItname();
            String itname_show = MyUtil.getItNameShow(product);
            int itnumber = product.getItnumber();
            double itprice = product.getItprice();
            String itamount = itnumber * itprice + "" ;
            printString = printString + MyUtil.print1(itname, itname_show, itnumber, itprice, itamount);
            db.execSQL("insert into orderlist values(?, ?, ?, ?, ?, ?, ?, '0')", new String[] {orderlistid, orderid,
                    itid, itname, itnumber+"", cashierNum, itamount});

            ArrayList<MixtureInfo> mixtureList = product.getMixtureList();

            if(mixtureList!=null) { //往mixtureorderlist表插入数据
                int j=1;
                for (MixtureInfo mixtureInfo : mixtureList) {
                    String mixtureorderlistid = orderlistid + String.format("%02d", j); //%2d  一位的话前有一个空格
                    db.execSQL("insert into mixtureorderlist values(?, ?, ?, ?, ?, '0')",
                            new String[] { mixtureorderlistid, orderlistid, mixtureInfo.getMixtureid(),
                                    mixtureInfo.getMixturename(), mixtureInfo.getQuantity() + "" });
                    j++;
                }
            }

            i++;
        }


        // 开始生成orders orderlist mixtureorderlist表的上传数据
        Cursor orderCursor = db.rawQuery("select * from orders where tag='0'", null);
        Cursor orderDetailCursor = db.rawQuery("select * from orderlist where tag='0'", null);
        Cursor mixtureCursor = db.rawQuery("select * from mixtureorderlist where tag='0'", null);

        ArrayList<OrderInfo> orderList = DealAnnotation.cursor2List(orderCursor, OrderInfo.class);
        ArrayList<OrderDetail> orderDetailList = DealAnnotation.cursor2List(orderDetailCursor, OrderDetail.class);
        ArrayList<MixtureInfo> mixtureList = DealAnnotation.cursor2List(mixtureCursor, MixtureInfo.class);

        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add( new BasicNameValuePair("orders", JsonUtil.bean2Json(orderList)) );
        arrayList.add( new BasicNameValuePair("orderlist", JsonUtil.bean2Json(orderDetailList)) );
        arrayList.add( new BasicNameValuePair("mixtureorderlist", JsonUtil.bean2Json(mixtureList)));
        return arrayList;
    }

}
