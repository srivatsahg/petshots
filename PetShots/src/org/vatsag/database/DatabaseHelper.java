package org.vatsag.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.vatsag.utils.DoctorDetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	Database helper class	
 * */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String LOG = "[DatabaseHelper]"; 
	
	/*Database information*/
	private static final String DATABASE_NAME = "PetShots.db";
	private static DatabaseHelper sInstance = null;
	
	public static DatabaseHelper getInstance(Context context) {
	      
	    // Use the application context, which will ensure that you
	    // don't accidentally leak an Activity's context.
	    // See this article for more information: http://bit.ly/6LRzfx
	    if (sInstance == null) {
	      sInstance = new DatabaseHelper(context.getApplicationContext());
	    }
	    return sInstance;
	    }
	
	
	/*
	 * Constructor
	 * */
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try{
			/*Creates Vet Details table in the db*/
			VetTable.onCreate(db);
			Log.i(LOG, "VetDetails table created");
			//TODO : Pet table
			PetTable.onCreate(db);
			
			//TODO : Vaccine table
			VaccineTable.onCreate(db);
		}
		catch(SQLiteException sqex){
			
			Log.e(LOG,"Error creating the db");
			Log.e(LOG, sqex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try{
			/*
			 * Drop table if exists
			 * */
			VetTable.onUpdate(db, oldVersion, newVersion);
			
			//TODO : Update pet 
			PetTable.onUpdate(db, oldVersion, newVersion);
			
			//TODO : Update vaccine table
			VaccineTable.onUpdate(db, oldVersion, newVersion);
		}
		catch(SQLiteException sqex){
			
			Log.e(LOG,"Error upgrading the db");
			Log.e(LOG, sqex.getMessage());
		}
	}
	
	/*
	 * Closes the DB connection
	 * */
	public void CloseDB(){
		SQLiteDatabase db = getReadableDatabase();
		if(db != null || db.isOpen()){
			db.close();
		}
	}
	
}
