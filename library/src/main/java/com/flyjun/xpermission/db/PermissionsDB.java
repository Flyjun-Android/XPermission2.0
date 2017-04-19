package com.flyjun.xpermission.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 管理权限的code，存在code时不弹窗提示
 * @author Flyjun
 *
 */
public class PermissionsDB {
	
	private SQLiteDatabase database;
    private PermissionsSQLiteHelper dbHelper;
    
    public PermissionsDB(Context context) {
		// TODO Auto-generated constructor stub
    	this.dbHelper=new PermissionsSQLiteHelper(context);
	}

    /**
     * 打开数据库
     * @throws SQLException
     */
    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        dbHelper.close();
    }
    
    /**
     * 增加
     * @param requdestCode
     */
    public long add(int requdestCode){
    	
    	open();
    	
    	ContentValues values=new ContentValues();
    	values.put(PermissionsSQLiteHelper.REQUEST_CODE, requdestCode);
    	
    	long result=database.insert(PermissionsSQLiteHelper.TABLE_NAME, null, values);
    	
    	close();
    	
    	return result;
    }
    
    /**
     * 更新  error 0
     * @param requdestCode
     * @return
     */
    public int update(int requdestCode){
    	
    	open();
    	
    	ContentValues values=new ContentValues();
    	values.put(PermissionsSQLiteHelper.REQUEST_CODE, requdestCode);
    	
    	int result=database.update(PermissionsSQLiteHelper.TABLE_NAME, values, PermissionsSQLiteHelper.REQUEST_CODE+"=?", new String[]{String.valueOf(requdestCode)});
        
        close();
       
        return result;
    }
    
    /**
     * 查询是否存在这个code
     * @param requdestCode
     * @return
     */
    public int queryByCode(int requdestCode){
    	
    	open();
    	
    	int length=-1;
    	
    	Cursor cursor = database.rawQuery("select * from "+PermissionsSQLiteHelper.TABLE_NAME+" where "+PermissionsSQLiteHelper.REQUEST_CODE+"=?", new String[]{String.valueOf(requdestCode)});
        
        while(cursor.moveToNext()){
            length=cursor.getInt(cursor.getColumnIndex(PermissionsSQLiteHelper.REQUEST_CODE));
        }
        
        close();
        
		return length;
    }
    
    
    public boolean hasRequestCode(int requdestCode){
    	
    	if(-1 == queryByCode(requdestCode)){
    		add(requdestCode);
    		return false;
    	}
    	
		return true;
    	
    }
}
