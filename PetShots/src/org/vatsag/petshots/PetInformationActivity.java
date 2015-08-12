package org.vatsag.petshots;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.vatsag.R;
import org.vatsag.database.CentralDataSource;
import org.vatsag.utils.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.util.Log;


/*
 * @Author	:	Srivatsa Haridas
 * @Date	:	25th October 2013
 * @Desc	:	Pet details entry form
 * */
public class PetInformationActivity extends Activity {

	private static final String LOG_CLASS = "[PetInformationActivity]";
	
	private PetInfoLayoutUpdater petLayoutUpdater;
	private EditText mName;
	private EditText mAge;
	private EditText mDob;
	private Spinner mSex;
	private EditText mChip;
	private EditText mReg;
	
	private TextView tvname,tvdob,tvsex,tvchip,tvreg;
	
	private CentralDataSource datasource;
	private Button btnSave;
	private Button btnDiscard;
	ArrayAdapter<CharSequence> adapter;
	private String spinSexSelection;
	private String strAge;
	private String strDob;
	private Calendar objCalendar;
	private Date nowDate;
	private long petID;
	private boolean isEditMode = false;
	private PetDetail petInformation;
	private Typeface typeface;
	private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petdata);
        petLayoutUpdater = new PetInfoLayoutUpdater(this);
        petLayoutUpdater.updateLayout();
        
        mName = (EditText)findViewById(R.id.txtTitle); /*Name*/
        mDob =(EditText)findViewById(R.id.txtDOB); /*DOB */
        mChip = (EditText)findViewById(R.id.txtChipid);
        mReg = (EditText)findViewById(R.id.txtRegid);
        mSex = (Spinner)findViewById(R.id.spinnerSex);
        
        tvname=(TextView)findViewById(R.id.tvpetdataname);
        tvdob=(TextView)findViewById(R.id.tvpetdatadob);
        tvsex = (TextView)findViewById(R.id.tvpetdatasex);
        tvchip = (TextView)findViewById(R.id.tvpetdatachipid);
        tvreg = (TextView)findViewById(R.id.tvpetdataregid);
        
        objCalendar = Calendar.getInstance();
        nowDate = objCalendar.getTime();
        
        
//        this.petID = i.getLongExtra("petid",-1);
        
        
        btnSave = (Button)findViewById(R.id.btnPetSubmit);
        btnDiscard = (Button)findViewById(R.id.btnPetCancel);
        
        datasource = new CentralDataSource(this);
        datasource.open();
        
        this.context = this.getApplicationContext();  
        
        //Sex selection
        adapter = ArrayAdapter.createFromResource(this,R.array.sexlist,R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSex.setAdapter(adapter);
        
        mSex.setOnItemSelectedListener(new OnItemSelectedListener() {
        	
        	public void onItemSelected(AdapterView<?> parent, View view,
        			int pos, long id) {
        		
        		spinSexSelection = parent.getItemAtPosition(pos).toString();
        	}

			public void onNothingSelected(AdapterView<?> parent) {
				
				spinSexSelection = parent.getItemAtPosition(0).toString();
				
			}
        	
		});
       
        
        /*
         * Readonly field
         * */
//        mAge = (EditText)findViewById(R.id.txtEditAge); /*	Age	*/
//        mAge.setKeyListener(null);
//        mAge.setEnabled(false);
        
        /*
         * Gets the age based on the DOB
         * */
        mDob.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 // TODO Auto-generated method stub
	            new DatePickerDialog(PetInformationActivity.this, date, objCalendar
	                    .get(Calendar.YEAR), objCalendar.get(Calendar.MONTH),
	                    objCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
        
        
        //Enters edit mode if its != -1, else behave just as new pet detail entry 
//        if(petID != -1){
//        	retrievePetInformation(petID);
//        	isEditMode = true;
//        }
        
        // Checks whether its new/edit 
        Intent i = getIntent();
        ParcelablePetDetail parcelablePetDetail = (ParcelablePetDetail) i.getParcelableExtra("petdetail");
        if(parcelablePetDetail != null){
        	petInformation = parcelablePetDetail.getPetdetail(); //Reconstructs a petdetail obj
        	if(petInformation != null){
            	retrievePetInformation(petInformation);
            	isEditMode = true;
            }
        }else{
        	isEditMode = false;
        }
       
        
        btnSave.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Log.i(LOG_CLASS, "<< --- OnSave Click --- >>");
			
				 if (TextUtils.isEmpty(mName.getText().toString())) {
		                makeToast();
		              } else {
		            	 
		            	  PetDetail petData = retrievePetDetails();
		            	  
		            		Log.i(LOG_CLASS, "Pet Details as modified by the user " + "Name : " +  
		            				petData.getName() +" DOB : "+  petData.getDob() +" Sex: "+ petData.getSex() +
		    						" Microchip : " + petData.getMchip().toString() + " RegNo : " + petData.getRegno().toString());
		            		
		            	  //TODO: Create a new entry in the db or update the present details
		            	  if(!isEditMode){
								// create an entry in the database
								petData = datasource.createPetDetail(petData);
								if (petData != null) {
									finish();
								} else {
									Log.e(LOG_CLASS,"<<---- Error creating pet entry ---- >>");
								}
		            	  }
		            	  else{
		            		  /*
		            		   * Update the entry
		            		   * */
		            		  petData = datasource.updatePetInformation(petData);
		            		  if(petData != null){
		            			  Log.i(LOG_CLASS, "<<---- Pet Details update success !!---->>");
		            			  //finish();
		            				Intent info = new Intent();
		    						info.setClass(PetInformationActivity.this, PetListActivity.class);
		    						info.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    						startActivity(info);
		    						finish();
		    						
//		    						info.setClass(PetInformationActivity.this, PetInformationView.class);
//		    						ParcelablePetDetail ppd = new ParcelablePetDetail(petData);
//		    						info.putExtra("petdetail", ppd);
//		    						startActivity(info);
//		    						finish();
		            		  }
		            		  else{
		            			  Log.e(LOG_CLASS, "<<---- Pet Details update failed !!---->>");
		            		  }
		            	  }
		              }
				}
			
			 
			/*	
			 * 
			 * Retrieve unique pet information 
			 * 
			 * */
			private PetDetail retrievePetDetails() {
				
				PetDetail pet = new PetDetail();
				
				if(isEditMode){
					pet.setP_id(petInformation.getP_id()); //id from the parcel obj	
				}
				pet.setName(mName.getText().toString());
				pet.setMchip(mChip.getText().toString());
				pet.setRegno(mReg.getText().toString());
				strDob = mDob.getText().toString();
				pet.setDob(strDob);
				String age = Utils.calculateExactAge(strDob);
				pet.setAge(age);
				/*	Spinner control selection */
				pet.setSex(spinSexSelection.toString());
				
				
				/*
				 *	 
				 * */
        		Log.i(LOG_CLASS, "Pet Details retrieved from edit fields " + "Name : " +  
        				pet.getName() +" DOB : "+  pet.getDob() +" Sex: "+ pet.getSex() +
						" Microchip : " + pet.getMchip().toString() + " RegNo : " + pet.getRegno().toString());
        		
				return pet;
			}
		});
        
        btnDiscard.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
					//Return to the previous activity without performing any action
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
    
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

    	
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            // TODO Auto-generated method stub
        	objCalendar.set(Calendar.YEAR, year);
        	objCalendar.set(Calendar.MONTH, monthOfYear);
        	objCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        	Date dobDate = objCalendar.getTime();
        	if(dobDate.after(nowDate)){
//        		makeToast("Please select a valid earlier date !!");
        		showAlertDialog(getResources().getString(R.string.dialogTitleError),getResources().getString(R.string.dialogMessageSelectEarlierDate));
        	}else{
        		updateDOB();	
        	}
        	
        }

    };
    
    /*
     * Apply fonts 
     * */
    private void applyTypefaceFonts(Typeface tf){
    	mName.setTypeface(tf);
    	mDob.setTypeface(tf);
    	mChip.setTypeface(tf);
    	mReg.setTypeface(tf);
    	
    	tvname.setTypeface(tf);
    	tvsex.setTypeface(tf);
    	tvdob.setTypeface(tf);
    	tvsex.setTypeface(tf);
    	tvchip.setTypeface(tf);
    	tvreg.setTypeface(tf);
    	
    	btnSave.setTypeface(tf);
    	btnDiscard.setTypeface(tf);
    }
    
    /*
     * Retrieves the pet data from the database
     * */
    private void retrievePetInformation(PetDetail pd) {
    	Log.i(LOG_CLASS, "<<---- Parcelable impl ---->> retrievePetInformation entered");
    	/*
    	 * Set the fields 
    	 * */
    	if(pd != null){
    		Log.i(LOG_CLASS, "<<---- Inside retrievePetInformation  pd not null ---->>");
    		int idxsex = adapter.getPosition(pd.getSex());
    		
    		mName.setText(pd.getName());
    		Log.i(LOG_CLASS, "<<---- Edit - Name field set ---->>");
        	mSex.setSelection(idxsex);
        	Log.i(LOG_CLASS, "<<---- Edit - Sex field set ---->>");
        	mDob.setText(pd.getDob());
        	Log.i(LOG_CLASS, "<<---- Edit - DOB field set ---->>");
        	mChip.setText(pd.getMchip());
        	Log.i(LOG_CLASS, "<<---- Edit - Chip field set ---->>");
        	mReg.setText(pd.getRegno());	
        	Log.i(LOG_CLASS, "<<---- Edit - Reg field set ---->>");
        	
    	}
    	
    	Log.i(LOG_CLASS, "Pet Details retrieved from database" + "Name : " +  
    						pd.getName() +" DOB : "+  pd.getDob() +" Sex: "+ pd.getSex() +
    						" Microchip : " + pd.getMchip().toString() + " RegNo : " + pd.getRegno().toString());
    	
    	Log.i(LOG_CLASS, "<<---- Parcelable impl ---->> retrievePetInformation success !!");
	}
    
    
    /*
     * DOB and age
     * */
    private void updateDOB(){
    	String dateFormat = "MM/dd/yyyy";
    	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,Locale.US);
    	mDob.setText(sdf.format(objCalendar.getTime()));
    	Utils.calculateExactAge(mDob.getText().toString());
    }
    
    /*
     * Warning msg to the user
     * */
    private void makeToast() {
        Toast.makeText(PetInformationActivity.this, "Please fill in all the required details",
            Toast.LENGTH_LONG).show();
      }
    
    /*
     * Warning msg to the user
     * */
    private void makeToast(String msg) {
        Toast.makeText(PetInformationActivity.this, msg,
            Toast.LENGTH_LONG).show();
      }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.psdashboard, menu);
        return true;
    }
    
    /*
     * Calculates the age in years 
     * */
    private int getAge (int _year, int _month, int _day) {
        
        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;         

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                        || ((m == cal.get(Calendar.MONTH)) && 
                        		(d < cal.get(Calendar.DAY_OF_MONTH)))) {
                --a;
        }
        if(a < 0)
                throw new IllegalArgumentException("Age < 0");
        return a;
}
    
    /*
	 * 
	 * */
	private void showAlertDialog(String title, String message){
		
        final Dialog dialog = new Dialog(PetInformationActivity.this,R.style.ThemeDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pscustomdialogwindow);
		dialog.setCancelable(true);
		
		final TextView tvDialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
		final TextView tvDialogMsg = (TextView)dialog.findViewById(R.id.dialogmessage);
		
		final Button btnOK = (Button) dialog.findViewById(R.id.dialogbuttonOK);
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		
		tvDialogTitle.setText(title);
		tvDialogTitle.setTypeface(typeface);

        tvDialogMsg.setText(message);
        tvDialogMsg.setTypeface(typeface);
		
		dialog.show();
	}

}
