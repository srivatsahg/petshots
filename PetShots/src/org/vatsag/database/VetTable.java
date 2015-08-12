package org.vatsag.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	Database helper for VET Table
 * */
public class VetTable {

	public static final String TABLE_VET = "Doctor";
	private static final String LOG_CLASS = "[VetTable]";
	
	/*
	 * Doctor table column names
	 * "c_did","c_firstname","c_lastname","c_email","c_phoneprimary","c_phonesecondary","c_address"
	 * */
	public static final String KEY_CID = "c_id"; /* primary key */
	public static final String KEY_TITLE = "c_title";
	public static final String KEY_FIRST_NAME = "c_firstname";
	public static final String KEY_LAST_NAME = "c_lastname";
	public static final String KEY_EMAIL = "c_email";
	public static final String KEY_PHONE_PRIMARY = "c_phoneprimary";
	public static final String KEY_PHONE_SECONDARY = "c_phonesecondary";
	public static final String KEY_ADDRESS = "c_address";
	
	/*
	 * Veterinary doctors table creation query
	 * 
	 * */
	private static final String CREATE_DOCTORS_TABLE = "CREATE TABLE " + TABLE_VET + "("
			+ KEY_CID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," + 
			KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME + " TEXT," +
			KEY_EMAIL + " TEXT," + KEY_PHONE_PRIMARY + " TEXT," + KEY_PHONE_SECONDARY + " TEXT," +
			KEY_ADDRESS + " TEXT" + ")";
	
	/*
	 * creates vet details table in the database
	 * */
	public static void onCreate(SQLiteDatabase database){
		database.execSQL(CREATE_DOCTORS_TABLE);
	}
	
	/*
	 * Update the vet details database
	 * */
	public static void onUpdate(SQLiteDatabase database,int oldVersion , int newVersion){
		 Log.w(LOG_CLASS, "Upgrading database from version "
			        + oldVersion + " to " + newVersion
			        + ", which will destroy all old data");
		 	
		/*
		* Drop table if exists
		* */
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_VET);
		
		/*Recreate new database*/
		onCreate(database); 
	}
	
}
