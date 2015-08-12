package org.vatsag.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	Database helper for PET Table
 * */
public class PetTable {

	public static final String TABLE_PET = "PetDetails";
	private static final String LOG_CLASS = "[PetTable]";
	
	/*
	 * Pet details table column names
	 * 	"p_id","p_name","p_dob","p_sex","p_age","p_mcid","p_regno"
	 * */
	
	public static final String KEY_PETID = "p_id"; /* primary key */
	public static final String KEY_NAME = "p_name";
	public static final String KEY_DOB = "p_dob";
	public static final String KEY_SEX = "p_sex";
	public static final String KEY_AGE = "p_age";
	public static final String KEY_MICCHIPID = "p_mcid";
	public static final String KEY_REGNO = "p_regno";
	

	/*
	 * Pet Details table creation query
	 * "p_id","p_name","p_dob","p_sex","p_age","p_mcid","p_regno"
	 * */
	private static final String CREATE_PET_DETAILS_TABLE = "CREATE TABLE " + TABLE_PET + "("
			+ KEY_PETID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + 
			KEY_DOB + " TEXT," + KEY_SEX + " TEXT," + KEY_AGE + " TEXT," +
			KEY_MICCHIPID + " TEXT," + KEY_REGNO + " TEXT" + ")";
	
	/*
	 * creates vet details table in the database
	 * */
	public static void onCreate(SQLiteDatabase database){
		database.execSQL(CREATE_PET_DETAILS_TABLE);
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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PET);
		
		/*Recreate new database*/
		onCreate(database); 
	}
	
}