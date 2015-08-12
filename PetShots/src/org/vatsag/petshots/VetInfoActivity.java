package org.vatsag.petshots;

import org.vatsag.R;
import org.vatsag.contentprovider.VetContentProvider;
import org.vatsag.database.CentralDataSource;
import org.vatsag.database.VetTable;
import org.vatsag.utils.Constants;
import org.vatsag.utils.DoctorDetails;
import org.vatsag.utils.DoctorInfoLayoutUpdater;
import org.vatsag.utils.ParcelableDocDetail;
import org.vatsag.utils.ParcelablePetDetail;
import org.vatsag.utils.PetDetail;
import org.vatsag.utils.Typefaces;
import org.vatsag.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.Path.FillType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	VET Information activity	
 * */
public class VetInfoActivity extends Activity {

	private static final String LOG_CLASS = "VetInfoActivity";
	
	private EditText mTitle;
	private EditText mFirstname;
	private EditText mLastname;
	private EditText mPhone1;
	private EditText mPhone2;
	private EditText mEmail;
	private EditText mAddress;
	
	private TextView tvfirstname,tvlastname,tvemail,tvphone1,tvphone2,tvaddress;
	
	private Uri veturi;
	Button btnSave,btnDiscard;
	private DoctorInfoLayoutUpdater docLayoutUpdater;
	private CentralDataSource datasource;
	private long docID;
	private boolean isEditMode = false;
	private boolean isValidEmail = false;
	private DoctorDetails docinformation;
	private Typeface typeface;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor);
        
        /*
         * Editable entries 
         * */
        mFirstname = (EditText) findViewById(R.id.txtvetfirstname);
        mLastname = (EditText) findViewById(R.id.txtvetlastname);
        mPhone1 = (EditText) findViewById(R.id.txtVetPhonePrimary);
        mPhone2 = (EditText) findViewById(R.id.txtVetPhoneSecondary);
        mEmail = (EditText) findViewById(R.id.txtemail);
        mAddress = (EditText) findViewById(R.id.txtVetAddress);
        
        /*
         * TextView
         * */
        tvfirstname = (TextView) findViewById(R.id.tvdocdatafirstname);
        tvlastname =  (TextView) findViewById(R.id.tvdocdatalastname);
        tvemail = (TextView) findViewById(R.id.tvdocdataemail);
        tvphone1 = (TextView) findViewById(R.id.tvdocdataphone1);
        tvphone2 = (TextView) findViewById(R.id.tvdocdataphone2);
        tvaddress = (TextView) findViewById(R.id.tvdocdataaddress);
        
        /* Buttons */
        btnSave = (Button)findViewById(R.id.btnSubmit);
        btnDiscard = (Button)findViewById(R.id.btnCancel);
        
        Bundle extras = getIntent().getExtras();
        
        docLayoutUpdater = new DoctorInfoLayoutUpdater(this);
        docLayoutUpdater.updateLayout();
        
        datasource = new CentralDataSource(this);
        datasource.open();
        
        // Checks whether its new/edit 
        Intent i = getIntent();
        ParcelableDocDetail pdd = (ParcelableDocDetail) i.getParcelableExtra("docdetail");
        if(pdd != null){
        	docinformation = pdd.getDocdetail(); //Reconstructs a Doctordetails obj
        	if(docinformation!=null){
        		retrieveVetInformation(docinformation);
            	isEditMode = true;	
        	}
        }else{
        	isEditMode = false;
        }
        	
        
        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					if(!IsEmailValid()){
						makeToast("Please enter a valid email address");
					}
				}
			}
		});
        
        /* On Button OK Click */
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Log.i(LOG_CLASS, "<< --- OnSave Click --- >>");
            	
              if (TextUtils.isEmpty(mFirstname.getText().toString()) || TextUtils.isEmpty(mPhone1.getText().toString())) {
                makeToast("Please fill in all the required details");
              } else {
            	 
            	  DoctorDetails docData = retrieveDoctorData();
            	  if(!isEditMode){
            		//create an entry in the database
                	  docData = datasource.createDoctorDetail(docData);
                	  if(docData!=null){
                		  finish();	  
                	  }
                	  else{
                		  Log.e(LOG_CLASS, "Error creating doctor entry");
                	  }
            	  }
            	  else{
            		  /*
            		   * Update the doc 
            		   * */
            		  docData = datasource.updateDocInformation(docData);
            		  if(docData != null){
            			  Log.i(LOG_CLASS, "<<---- Vet Details update success !!---->>");
            				Intent info = new Intent();
    						info.setClass(VetInfoActivity.this, VetListActivity.class);
    						info.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    						startActivity(info);
    						finish();

//    						info.setClass(VetInfoActivity.this, VetInformationViewActivity.class);
//    						ParcelableDocDetail pdd = new ParcelableDocDetail(docData);
//    						info.putExtra("docdetail", pdd);
//    						startActivity(info);
//    						finish();

            		  }
            		  else{
            			  Log.e(LOG_CLASS, "<<---- Pet Details update failed !!---->>");
            		  }
            	  }
              	}
            }
            
      

			/*	Doctor information */
			private DoctorDetails retrieveDoctorData() {
				DoctorDetails doc = new DoctorDetails();
				if(isEditMode){
					doc.setM_id(docinformation.getM_id()); /*	Set the Doctor - ID explicitly */	
				}
				doc.setM_title("Dr"); /*	Hardcoded to Dr. */
				doc.setM_firstname(mFirstname.getText().toString());
				doc.setM_lastname(mLastname.getText().toString());
				doc.setM_email(mEmail.getText().toString());
				doc.setM_phonePrimary(mPhone1.getText().toString());
				doc.setM_phoneSecondary(mPhone2.getText().toString());
				doc.setM_address(mAddress.getText().toString());
				Log.i(LOG_CLASS, "Doctor Details retrieved from edit fields");
				return doc;
			}

          });
        
        btnDiscard.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//No action, just return to the previous activity
				finish();
			}
		});
        
        this.typeface = Typefaces.get(this.getApplicationContext(), Constants.ROBOTA_FONTFILE);
        applyTypefaceFonts(typeface);
    }

    @Override
    public void finish() {
      // Prepare data intent 
      Intent data = new Intent();
      // Activity finished ok, return the data
      setResult(RESULT_OK, data);
      super.finish();
    } 
    
    
    /*
     * Fonts
     * */
    private void applyTypefaceFonts(Typeface tf){
    	
    	 mFirstname.setTypeface(tf);
         mLastname.setTypeface(tf);
         mPhone1.setTypeface(tf);
         mPhone2.setTypeface(tf);
         mEmail.setTypeface(tf);
         mAddress.setTypeface(tf);
         
         /*
          * TextView
          * */
         tvfirstname.setTypeface(tf);
         tvlastname.setTypeface(tf);
         tvemail.setTypeface(tf);
         tvphone1.setTypeface(tf);
         tvphone2.setTypeface(tf);
         tvaddress.setTypeface(tf);
         
         /* Buttons */
         btnSave.setTypeface(tf);
         btnDiscard.setTypeface(tf);
    }
    
    /*	
     * Checks whether the entered email address is valid
     * true	-	valid
     * false-	invalid
     * */
    private boolean IsEmailValid() {
		isValidEmail =  Utils.validateEMail(mEmail.getText().toString());
		return isValidEmail;
	}
    
    /*
     * Retrieves the pet data from the database
     * */
    private void retrieveVetInformation(DoctorDetails pd) {
    	Log.i(LOG_CLASS, "retrieveVetInformation entered");
    	
    	/*
    	 * Set the fields 
    	 * */
    	if(pd != null){
    		
    		mFirstname.setText(pd.getM_firstname());
    		Log.i(LOG_CLASS, "<<---- Edit - FirstName field set ---->>");
        	mLastname.setText(pd.getM_lastname());
        	Log.i(LOG_CLASS, "<<---- Edit - LastName field set ---->>");
        	mEmail.setText(pd.getM_email());
        	Log.i(LOG_CLASS, "<<---- Edit - Email field set ---->>");
        	mPhone1.setText(pd.getM_phonePrimary());
        	Log.i(LOG_CLASS, "<<---- Edit - Chip field set ---->>");
        	mPhone2.setText(pd.getM_phoneSecondary());
        	Log.i(LOG_CLASS, "<<---- Edit - Reg field set ---->>");
        	mAddress.setText(pd.getM_address());
        	
    	}
    	
    	Log.i(LOG_CLASS, "Vet Details retrieved from database");
    	
    	Log.i(LOG_CLASS, "retrieveVetInformation success !!");
	}
    
    /*
     * Warning msg to the user
     * */
    private void makeToast(String msg) {
        Toast.makeText(VetInfoActivity.this,msg,
            Toast.LENGTH_LONG).show();
      }
    
    /*
     * 
     * */
    private void fillData(Uri uri) {
    	
        String[] projection = {VetTable.KEY_TITLE, VetTable.KEY_FIRST_NAME,VetTable.KEY_LAST_NAME,
        		VetTable.KEY_PHONE_PRIMARY, VetTable.KEY_PHONE_SECONDARY,
        		VetTable.KEY_EMAIL,VetTable.KEY_ADDRESS};
        
        Log.i(LOG_CLASS, "<< ---- before query the veturi inside filldata ---->>");
        
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
            null);
        
        if (cursor != null) {
        	
        	Log.i(LOG_CLASS, "<< ---- inside the filldata with cursor having data ---->>");
        	
          cursor.moveToFirst();

          mTitle.setText(cursor.getString(cursor
              .getColumnIndexOrThrow(VetTable.KEY_TITLE)));
          mFirstname.setText(cursor.getString(cursor
              .getColumnIndexOrThrow(VetTable.KEY_FIRST_NAME)));
          mLastname.setText(cursor.getString(cursor
                  .getColumnIndexOrThrow(VetTable.KEY_LAST_NAME)));
          mEmail.setText(cursor.getString(cursor
                  .getColumnIndexOrThrow(VetTable.KEY_EMAIL)));
          mPhone1.setText(cursor.getString(cursor
                  .getColumnIndexOrThrow(VetTable.KEY_PHONE_PRIMARY)));
          mPhone2.setText(cursor.getString(cursor
                  .getColumnIndexOrThrow(VetTable.KEY_PHONE_SECONDARY)));
          mAddress.setText(cursor.getString(cursor
                  .getColumnIndexOrThrow(VetTable.KEY_ADDRESS)));

          // always close the cursor
          cursor.close();
        }
      }
    
    /*
     * 
     * */
    private void saveState() {
    	
    	Log.i(LOG_CLASS, "<< ---- Inside the saveState method ---- >>");
    	
        String firstname = mFirstname.getText().toString();
        String lastname = mLastname.getText().toString();
        String phone1 = mPhone1.getText().toString();
        String phone2 = mPhone2.getText().toString();
        String email = mEmail.getText().toString();
        String address = mAddress.getText().toString();
        String title = "Dr";
        
        Log.i(LOG_CLASS, "<< ---- saveState method, data collected ---- >>");
        
        // only save if either summary or description
        // is available

        if (mFirstname.length() == 0 && 
        		mPhone1.length() == 0 && 
        		mEmail.length() == 0) {
        	Log.e(LOG_CLASS, "<< ---- parameters not supplied ---- >>");
          return;
        }

        Log.i(LOG_CLASS, "<< ----Before putting the values inside ContentValues obj ---- >>");
        
        ContentValues values = new ContentValues();
        
        values.put(VetTable.KEY_TITLE, title);
        values.put(VetTable.KEY_FIRST_NAME, firstname);
        values.put(VetTable.KEY_LAST_NAME, lastname);
        
        values.put(VetTable.KEY_EMAIL, email);
        values.put(VetTable.KEY_ADDRESS, address);
        values.put(VetTable.KEY_PHONE_PRIMARY, phone1);
        values.put(VetTable.KEY_PHONE_SECONDARY, phone2);
        
        Log.i(LOG_CLASS, "<< ---- ContentValues collected ---- >>");
        
        if (veturi == null) {
          // New todo
        	Log.i(LOG_CLASS, "<< ---- New Vet detail information ---- >>");
        	veturi = getContentResolver().insert(VetContentProvider.CONTENT_URI, values);
        } else {
          // Update todo
        	Log.i(LOG_CLASS, "<< ---- Update Vet information ---- >>");
          getContentResolver().update(veturi, values, null, null);
        }
      }
    
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	Log.i(LOG_CLASS, "<< ---- onPause Invoked ---->>");
    	  //saveState();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	//saveState();
    	outState.putParcelable(VetContentProvider.CONTENT_ITEM_TYPE, veturi);
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.psdashboard, menu);
        return true;
    }
}
