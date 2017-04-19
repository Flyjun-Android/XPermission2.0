package com.flyjun.xpermission.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Permissions SQLiteOpenHelper
 * @author Flyjun
 *
 */
public class PermissionsSQLiteHelper extends SQLiteOpenHelper {

	private static final String DB_NAME="permissions.db";
	private static final int VERSION=1;
	
	public static final String TABLE_NAME="permission";
	
	public static String ID="_id";
	//请求的code
	public static String REQUEST_CODE ="requestcode";
	
	String sql=" CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            REQUEST_CODE+" INTEGER );";
	
	
	public PermissionsSQLiteHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
	}

}
