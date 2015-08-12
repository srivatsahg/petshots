package org.vatsag.database;

import android.app.Application;

/*
 * 
 * */
public class DatabaseHandler extends Application {
	
	public DatabaseHelper dbhelper;
	
	public DatabaseHelper getDbhelper() {
		return dbhelper;
	}
	
	@Override
	public void onCreate() {
		dbhelper = DatabaseHelper.getInstance(this);
		super.onCreate();
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		dbhelper.CloseDB();
	}
	
}
