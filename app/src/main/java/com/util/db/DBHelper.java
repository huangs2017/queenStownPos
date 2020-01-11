package com.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper dbHelper;
    public SQLiteDatabase db;

	private DBHelper(Context ctx) {
		super(ctx, "temp.db", null, 1);
		db = getWritableDatabase(); // 得到数据库对象
	}

    //单例模式
    public static synchronized DBHelper getInstance(Context ctx) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(ctx);
        }
        return dbHelper;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		//1系统信息表
		db.execSQL("Create table systemset(deviceid, shopid, shopname, shopkeeperid, shopkeeperpwd, shopnature, powerontimes)");		
		//2收银员信息表
		db.execSQL("Create table cashier(cashiernum, cashierid, cashierpwd, cashiername, cashiersex, cashierpicurl)");
		//3商品大类表
		db.execSQL("Create table categories1(categories1id, categories1name)");
		//4商品小类表
		db.execSQL("Create table categories2(categories2id, categories2name, belongto)");
		//5商品信息表
		db.execSQL("Create table it(itid, belongto, itname, itprice decimal(12,0), itprice2 decimal(12,0), picurl, salemodel int)");
		//6配料库表
		db.execSQL("Create table mixture(mixtureid, mixturename, mixturepicurl)");
		
		
		//7销售单据表       一笔交易对应一条记录
		db.execSQL("Create table orders(orderid, orderdate, ordertime, amount decimal(12,0), cashiernum, tag, tag2)");
		//orderid = shopid + 20150205 + 00001    2015-02-05  05:15:20      total         SPUtils.getString(this, cashiernum);
		//已上传tag=1       已交班tag2=1(用到了)	
		
		//8销售详单表     一种商品对应一条记录
		db.execSQL("Create table orderlist(orderlistid, orderid, itid, itname, itnumber, cashiernum, amount, tag)"); //已上传tag=1
		//9配料销售表     一种配料对应一条记录
		db.execSQL("Create table mixtureorderlist(mixtureorderlistid, orderlistid, mixtureid, mixturename, mixturenumber, tag)"); //已上传tag=1
		//10交班表
		db.execSQL("Create table shifttable(shiftid, shiftdate, shifttime, cashiernum, cash, shuaka, huiyuan, amount, tag)");
		//tag否上传,已上传1  amount从开班到交班卖了多少钱
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}
