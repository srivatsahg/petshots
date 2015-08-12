package org.vatsag.database;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VaccineTable {
	
	/*Logger */
	private static final String LOG_CLASS = "[VaccineTable]";
	
	public static final String TABLE_VACCINE = "VaccineDetails";
	public static final String TABLE_VET = "Doctor";
	public static final String TABLE_PET = "PetDetails";
	/*
	 * Vaccine details table
	 * */
	public static final String KEY_VID = "v_id"; /* primary key */
	public static final String KEY_VACCINE_NAME = "v_name";
	public static final String KEY_PET = "v_p_id"; /* foreign key */
	public static final String KEY_DOC = "v_d_id"; /* foreign key */
	public static final String KEY_VACCINEDATE = "v_pdate";
	public static final String KEY_NXTVACCINEDATE = "v_ndate";
	public static final String KEY_REMARKS = "v_remarks";

	/*	Pet id*/
	public static final String KEY_PETID = "p_id"; /* primary key */
	/*	Doctor id*/
	public static final String KEY_VETID = "c_id"; /* primary key of DoctorDetails table*/
	
	/*
	 * Vaccination table creation query
	 * id,vac_name,petid,docid,date,nextdate,remarks
	 * */
//	private static final String CREATE_VACCINE_DETAILS_TABLE = "CREATE TABLE " + TABLE_VACCINE + " ("
//			+ KEY_VID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
//			KEY_VACCINE_NAME + " TEXT," +
//			KEY_PET + " INTEGER NOT NULL," + " FOREIGN_KEY ("+ KEY_PET + ")" + " REFERENCES " + TABLE_PET + "("+ KEY_PETID + ")" + 
//			KEY_DOC + " INTEGER NOT NULL," + " FOREIGN_KEY ("+ KEY_DOC + ")" + " REFERENCES " + TABLE_VET + "("+ KEY_VETID + ")" +
//			KEY_VACCINEDATE + " TEXT," +  KEY_NXTVACCINEDATE + " TEXT," +
//			KEY_REMARKS + " TEXT" + ")";
	
	private static final String CREATE_VACCINE_DETAILS_TABLE = "CREATE TABLE " + TABLE_VACCINE + " ("
			+ KEY_VID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
			KEY_VACCINE_NAME + " TEXT," +
			KEY_PET + " INTEGER NOT NULL," +   
			KEY_DOC + " INTEGER NOT NULL," +
			KEY_VACCINEDATE + " TEXT," +  
			KEY_NXTVACCINEDATE + " TEXT," +
			KEY_REMARKS + " TEXT," + 
			" FOREIGN KEY ("+ KEY_PET + ")" + " REFERENCES " + TABLE_PET + "("+ KEY_PETID + ")" + " ON DELETE NO ACTION ON UPDATE NO ACTION," + 
			" FOREIGN KEY ("+ KEY_DOC + ")" + " REFERENCES " + TABLE_VET + "("+ KEY_VETID + ")" + " ON DELETE NO ACTION ON UPDATE NO ACTION " + 
			")";

	/*
	 * creates vet details table in the database
	 * */
	public static void onCreate(SQLiteDatabase database){
		Log.i(LOG_CLASS, "Query : " + CREATE_VACCINE_DETAILS_TABLE);
		database.execSQL(CREATE_VACCINE_DETAILS_TABLE);
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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE);
		
		/*Recreate new database*/
		onCreate(database); 
	}
	
}
