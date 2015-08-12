package org.vatsag.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.UriMatcher;

import org.vatsag.database.DatabaseHelper;
import org.vatsag.database.VetTable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	
 * */
public class VetContentProvider extends ContentProvider {

	DatabaseHelper dbhelper;
	
	private static final int DOC = 10;
	private static final int DOC_ID = 20;
	private static final String AUTHORITY 	= "org.vatsag.contentprovider";
	private static final String BASE_PATH = "vets";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
		      + "/" + BASE_PATH);
	 public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
		      + "/vets";
		  public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
		      + "/vet";
	

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	  static {
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH, DOC);
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", DOC_ID);
	}
	  
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = dbhelper.getWritableDatabase();
		int rowsDeleted = 0;
		
		   switch (uriType) {
		    case DOC:
		      rowsDeleted = sqlDB.delete(VetTable.TABLE_VET, selection,
		          selectionArgs);
		      break;
		    case DOC_ID:
		      String id = uri.getLastPathSegment();
		      
		      if (TextUtils.isEmpty(selection)) {
		        rowsDeleted = sqlDB.delete(VetTable.TABLE_VET,
		            VetTable.KEY_CID + "=" + id, 
		            null);
		      } else {
		        rowsDeleted = sqlDB.delete(VetTable.TABLE_VET,
		        		VetTable.KEY_CID + "=" + id 
		            + " and " + selection,
		            selectionArgs);
		      }
		      break;
		    default:
		      throw new IllegalArgumentException("Unknown URI: " + uri);
		    }
		   
		 getContext().getContentResolver().notifyChange(uri, null);
		 return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		 int uriType = sURIMatcher.match(uri);
		    SQLiteDatabase sqlDB = dbhelper.getWritableDatabase();
		    int rowsDeleted = 0;
		    long id = 0;
		    
		    switch (uriType) {
			case DOC:
				id = sqlDB.insert(VetTable.TABLE_VET, null, values);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
			}
		    
		    getContext().getContentResolver().notifyChange(uri, null);
		    return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public boolean onCreate() {
		dbhelper = DatabaseHelper.getInstance(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		checkColumns(projection);
		
		queryBuilder.setTables(VetTable.TABLE_VET);
		
		int uriType = sURIMatcher.match(uri);
		
		switch (uriType) {
	    case DOC:
	      break;
	    case DOC_ID:
	      // adding the ID to the original query
	      queryBuilder.appendWhere(VetTable.KEY_CID + "="
	          + uri.getLastPathSegment());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	      }
	      
		SQLiteDatabase db = dbhelper.getWritableDatabase();
	    Cursor cursor = queryBuilder.query(db, projection, selection,
	        selectionArgs, null, null, sortOrder);
	    // make sure that potential listeners are getting notified
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);
	    
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * to detect unidentified columns
	 * Throws IllegalArgumentException if an unknown column is queried
	 * */
	private void checkColumns(String[] projection) {
		
//	    String[] available = { VetTable.KEY_CID,
//	        VetTable.KEY_FIRST_NAME, VetTable.KEY_LAST_NAME,
//	        VetTable.KEY_TITLE, VetTable.KEY_EMAIL, VetTable.KEY_PHONE_PRIMARY,
//	        VetTable.KEY_PHONE_SECONDARY,VetTable.KEY_ADDRESS};
	    
	    String[] available = { "_id",
        VetTable.KEY_FIRST_NAME, VetTable.KEY_LAST_NAME,
        VetTable.KEY_TITLE, VetTable.KEY_EMAIL, VetTable.KEY_PHONE_PRIMARY,
        VetTable.KEY_PHONE_SECONDARY,VetTable.KEY_ADDRESS};

	    if (projection != null) {
	      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
	      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
	      // check if all columns which are requested are available
	      if (!availableColumns.containsAll(requestedColumns)) {
	        throw new IllegalArgumentException("Unknown columns in projection");
	      }
	    }
	  }

}
