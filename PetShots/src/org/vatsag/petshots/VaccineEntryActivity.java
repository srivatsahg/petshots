
/*
 * @Author	:	Srivatsa Haridas
        * @Date	:	25th October 2013
        * @Title	:	Vaccination details entry form
        * @Desc	:	This activity shall give the user an option to add new vaccination entries for a pet
        *
        * */

package org.vatsag.petshots;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.view.Window;
import org.vatsag.R;
import org.vatsag.R.layout;
import org.vatsag.R.menu;
import org.vatsag.database.CentralDataSource;
import org.vatsag.database.GenericDataBinder;
import org.vatsag.database.PetDataBinder;
import org.vatsag.utils.Constants;
import org.vatsag.utils.DoctorDetails;
import org.vatsag.utils.LayoutUpdater;
import org.vatsag.utils.PetDetail;
import org.vatsag.utils.Typefaces;
import org.vatsag.utils.VaccineDetails;
import org.vatsag.utils.VaccineEntryLayoutUpdater;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class VaccineEntryActivity extends Activity {

	static final String LOG_CLASS = "[VaccineEntryActivity]";
	
	private LayoutUpdater layoutupdater;
	private Button btnSave,btnDiscard;
	private TextView tvCurrentDate,tvSpinDoc,tvSpinPet,tvSpinVaccine;
	private TextView tvLabelDate,tvLabelDoc,tvLabelPet,tvLabelVaccine,tvLabelSetDueDate;
	private EditText editVaccineDate;
	private Calendar objcalendar;
	private Date nowDate;
	private ArrayAdapter<CharSequence> doctorList,petslist,vaccinelist;
	private Typeface typeface;
	
	List<PetDetail> petcollection;
	List<DoctorDetails> vetcollection;
	List<String> vaccinationlist;
	GenericDataBinder<PetDetail> petadapter;
	GenericDataBinder<DoctorDetails> docadapter;
	GenericDataBinder<String> vaccineadapter;
	
	private Spinner mSpinVaccine;
	private Spinner mSpinPet;
	private Spinner mSpinVet;
	private CentralDataSource datasource;
	private DoctorDetails selectedVet;
	private PetDetail selectedPet;
	
	/*	holds the vaccine name	*/
	private String mStrVaccineName;
	/*	holds the vet name	*/
	private String mStrDoctorName;
	/*	holds the vet clinic address */
	private String mStrDoctorAddress;
	/*	holds the pet name	*/
	private String mStrPetName;
	/*	holds current date */
	private String m_vaccinedate;
	
	private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psvaccinationentry);
        
        /*	Reference from the layout	*/
        mSpinVaccine = (Spinner)findViewById(R.id.spinVaccine);
        mSpinPet = (Spinner)findViewById(R.id.spinPet);
        mSpinVet = (Spinner)findViewById(R.id.spinDoc);
        
        editVaccineDate = (EditText)findViewById(R.id.editDueDate);
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrDate);
        
        this.context = this.getApplicationContext();
        
        tvLabelDate = (TextView) findViewById(R.id.tvDateHeader);
        tvLabelDoc= (TextView) findViewById(R.id.tvLabelDoc);
        tvLabelPet= (TextView) findViewById(R.id.tvLabelPet);
        tvLabelVaccine= (TextView) findViewById(R.id.tvLabelVaccine);
        tvLabelSetDueDate = (TextView) findViewById(R.id.tvLabelSetDueDate);
        
        btnSave = (Button)findViewById(R.id.btnSaveVaccineDetails);
        btnSave.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(selectedVet == null){
					makeToast("Atleast add one doctor information in the Doctor details page");
				}
				if(selectedPet == null){
					makeToast("Atleast add one pet information in the Pet details page");
				}
				if((selectedVet != null) && (selectedPet != null)){
					
				/*
				 * Proceed to save only when there are vet and pet details
				 * */	
				if (TextUtils.isEmpty(editVaccineDate.getText().toString())) {
		             makeToast("Please fill in all the required details");
		           } else {
		         	 
		         	  VaccineDetails vaccineData = retrieveVaccinationData();
		         	  //create an entry in the database
		         	 vaccineData = datasource.createVaccineDetail(vaccineData);
		         	  if(vaccineData!=null){
//		         		  updateCalendarEvent();
                          createCalendarEvent();
		         		  finish();
		         	  }
		         	  else{
		         		  Log.e(LOG_CLASS, "<<---- Error creating pet entry ---- >>");
		         	  }
		           }	
				}
			}

		});
        
        btnDiscard = (Button)findViewById(R.id.btnDiscardVaccineDetails);
        btnDiscard.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//Return to the previous activity without performing any action
				finish();
			}
		});
        Log.i(LOG_CLASS, "<<---- After btnDiscard code ---->>");
        
        layoutupdater = new VaccineEntryLayoutUpdater(this);
        layoutupdater.updateLayout();
        
        Log.i(LOG_CLASS, "<<---- After VaccineEntryLayoutUpdater ---->>");
        
        objcalendar = Calendar.getInstance();
        nowDate = objcalendar.getTime();
        
        Log.i(LOG_CLASS, "<<---- After Calendar getinstance ---->>");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        m_vaccinedate =  dateFormat.format(objcalendar.getTime());
        tvCurrentDate.setText(m_vaccinedate);
        
        editVaccineDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new DatePickerDialog(VaccineEntryActivity.this, date, objcalendar
	                    .get(Calendar.YEAR), objcalendar.get(Calendar.MONTH),
	                    objcalendar.get(Calendar.DAY_OF_MONTH)).show();
				
			}
		});
        
        Log.i(LOG_CLASS, "<<---- After DateFragment dialog  ---->>");
        
//        vaccinelist = ArrayAdapter.createFromResource(this,R.array.caninevaccinelist,R.layout.psgenericdata);
//        vaccinelist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinVaccine.setAdapter(vaccinelist);
        
        Log.i(LOG_CLASS, "<<---- After mSpinVaccine setadapter  ---->>");
        
        
        /*
         * Vaccination details selection
         * */
        mSpinVaccine.setOnItemSelectedListener(new OnItemSelectedListener() {
        	
        	public void onItemSelected(AdapterView<?> parent, View view,
        			int pos, long id) {
        		
        		mStrVaccineName =  parent.getItemAtPosition(pos).toString();
        		Log.i(LOG_CLASS, "<<---- Vaccine selected ---->>");
        	}

			public void onNothingSelected(AdapterView<?> parent) {
				mStrVaccineName =  parent.getItemAtPosition(0).toString(); /* Default selected is 0*/
				Log.i(LOG_CLASS, "<<---- Default vaccine selected ---->>");
			}
		});
        
        
        /*
         * Pet selection 
         * */
        mSpinPet.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				selectedPet = ((PetDetail)parent.getItemAtPosition(pos));
				mStrPetName = selectedPet.toString();
				Log.i(LOG_CLASS, "<<---- Pet selected ---->>");
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
				selectedPet = ((PetDetail)parent.getItemAtPosition(0));
				mStrPetName = selectedPet.toString(); /* Default selected is 0*/
				Log.i(LOG_CLASS, "<<---- First pet selected ---->>");
			}
        	
		});
        
        /*
         * Vet (doctor) selection
         * */
        mSpinVet.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				selectedVet = (DoctorDetails)parent.getItemAtPosition(pos);
				mStrDoctorName = selectedVet.toString();
				mStrDoctorAddress = selectedVet.getM_firstname() + " - " + selectedVet.getM_address().toString();
				Log.i(LOG_CLASS, "<<---- Vet selected ---->>");
			}

			public void onNothingSelected(AdapterView<?> parent) {
				selectedVet = (DoctorDetails)parent.getItemAtPosition(0);
				mStrDoctorName = selectedVet.toString(); /*	Default item is 0 */
				Log.i(LOG_CLASS, "<<---- Default vet selected ---->>");
			}
		});
        
        
        Log.i(LOG_CLASS, "<<---- Before Datasource access  ---->>");
        
        /*	Datasource initialization and open to get the reference to the writable db */
        datasource = new CentralDataSource(this);
        datasource.open();
        
        Log.i(LOG_CLASS, "<<---- After Datasource open ---->>");
        Log.i(LOG_CLASS, "<<---- Before filldata for available vets and pets ---->>");
        
        filldata();
        
        Log.i(LOG_CLASS, "<<---- After filldata for available vets and pets ---->>");
        
        this.typeface = Typefaces.get(this.getApplicationContext(), Constants.ROBOTA_FONTFILE);
        applyTypefaceFonts(typeface);
    }
    
    /*
     * Apply fonts 
     * */
    private void applyTypefaceFonts(Typeface tf){
    	
        editVaccineDate.setTypeface(tf);
        tvCurrentDate.setTypeface(tf);
        
        tvLabelDate.setTypeface(tf);
        tvLabelDoc.setTypeface(tf);
        tvLabelPet.setTypeface(tf);
        tvLabelVaccine.setTypeface(tf);
        tvLabelSetDueDate.setTypeface(tf);
        
        btnSave.setTypeface(tf);
        btnDiscard.setTypeface(tf);
    }
    
    /*
     * Update the vaccination details to the calendar
     * */
	private void updateCalendarEvent() {


//        int sdk = Build.VERSION.SDK_INT;
//        if(sdk < Build.VERSION_CODES.ICE_CREAM_SANDWICH){
//
//            Uri eventsURI = Uri.parse(getCalendarUriBase(this)+"events");
//            // all SDK below ice cream sandwich
//            ContentResolver  cr = getContentResolver();
//
//            ContentValues values = new ContentValues();
//            values.put(Events.DTSTART,objcalendar.getTimeInMillis());
//            values.put(Events.DTEND, objcalendar.getTimeInMillis());
//            values.put(Events.TITLE,  "Vaccination for " + mStrPetName);
//            values.put(Events.DESCRIPTION,  mStrVaccineName + " vaccination for " + mStrPetName);
//            values.put(Events.EVENT_LOCATION, mStrDoctorAddress);
//            values.put(Events.ALL_DAY, 1);
//            values.put(Events.STATUS, 1);
//            values.put(Events.HAS_ALARM, 1);
//
//            Log.i(LOG_CLASS,"<<--- After populating calendar event details --->>");
//
//            Uri uri = cr.insert(eventsURI,values);
//
//            Intent oldIntent = new Intent(Intent.ACTION_INSERT,uri);
//
//            Log.i(LOG_CLASS,"<<--- Just before invoking oldintent --->>");
////            oldIntent.setType("vnd.android.cursor.item/event");
//            startActivity(oldIntent);
////            calIntent.putExtra("beginTime", objcalendar.getTimeInMillis());
////            calIntent.putExtra("endTime", objcalendar.getTimeInMillis());
////            calIntent.putExtra("title", "Vaccination for " + mStrPetName);
////            calIntent.putExtra("description", mStrVaccineName + " vaccination for " + mStrPetName);
////            calIntent.putExtra("eventLocation", mStrDoctorAddress);
////            calIntent.putExtra("allDay", true);
//        }
//        else{
//            //ICS and above
//            Intent calIntent = new Intent(Intent.ACTION_INSERT);
//            calIntent.setType("vnd.android.cursor.item/event");
//            calIntent.putExtra(Events.TITLE, "Vaccination for " + mStrPetName);
//            calIntent.putExtra(Events.EVENT_LOCATION, mStrDoctorAddress);
//            calIntent.putExtra(Events.DESCRIPTION, mStrVaccineName + " vaccination for " + mStrPetName);
//            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,objcalendar.getTimeInMillis());
//            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,objcalendar.getTimeInMillis());
//            Log.i(LOG_CLASS,"<<--- Just before invoking calintent --->>");
//            startActivity(calIntent);
//        }
	}

    private void createEvent(){
        ContentValues cv = new ContentValues();
        cv.put("title", "sometitle");
        cv.put("dtstart", ""+objcalendar.getTimeInMillis());
        cv.put("dtend", ""+objcalendar.getTimeInMillis());
        cv.put("hasAlarm", 1);
        Uri newevent = getContentResolver().insert(Uri.parse("content://calendar/events"), cv);
    }

    private void createCalendarEvent(){

        String [] projection = new String [] {"_id", "name"};
        if (android.os.Build.VERSION.SDK_INT <= 7 )
        {
            Uri calendars = Uri.parse("content://calendar/calendars");
            Cursor managedCursor = managedQuery(calendars, projection, null, null, null);
            if (managedCursor.moveToFirst())
            {
                String calName;
                String calId;
                int nameColumn = managedCursor.getColumnIndex("name");
                int idColumn = managedCursor.getColumnIndex("_id");
                do
                {
                    calName = managedCursor.getString(nameColumn);
                    calId = managedCursor.getString(idColumn);
                    Log.e("Calendar Id : ",""+calId+" : "+calName);
                }
                while (managedCursor.moveToNext());

                if(calId != null)
                {
                    try
                    {
                        Log.e("Calendar Id : ",""+calId+" : "+calName);
                        ContentValues event = new ContentValues();
                        event.put("calendar_id", calId);
                        event.put("title", "Vaccination for " + mStrPetName);
                        event.put("description", mStrVaccineName + " vaccination for " + mStrPetName);
                        event.put("eventLocation", "");
                        event.put("dtstart", objcalendar.getTimeInMillis());
                        event.put("dtend", objcalendar.getTimeInMillis());
                        event.put("allDay", 1);
                        event.put("eventStatus", 1);
                        event.put("visibility", 0);
                        event.put("hasAlarm", 1);
                        Uri eventsUri = Uri.parse("content://calendar/events");
                        Uri url = getContentResolver().insert(eventsUri, event);
                        Log.e("Event Res : ",""+url);
                        if(!url.equals(""))
                            showAlertDialog("Information","Event Successfully Added");
                    }
                    catch (Exception kwse)
                    {
                        Log.e("Exception 1 kwse ",""+kwse.toString());
                    }
                }
            }
        }
        else
        {

            Intent calIntent = new Intent(Intent.ACTION_INSERT);
            calIntent.setType("vnd.android.cursor.item/event");
            calIntent.putExtra(Events.TITLE, "Vaccination for " + mStrPetName);
            calIntent.putExtra(Events.EVENT_LOCATION, mStrDoctorAddress);
            calIntent.putExtra(Events.DESCRIPTION, mStrVaccineName + " vaccination for " + mStrPetName);
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,objcalendar.getTimeInMillis());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,objcalendar.getTimeInMillis());
            Log.i(LOG_CLASS,"<<--- Just before invoking calintent --->>");
            startActivity(calIntent);

//            Uri calendars= Uri.parse("content://com.android.calendar/calendars");
//            Cursor managedCursor = managedQuery(calendars, projection, null, null, null);
//            if (managedCursor.moveToFirst())
//            {
//                String calName;
//                String calId;
//                int nameColumn = managedCursor.getColumnIndex("name");
//                int idColumn = managedCursor.getColumnIndex("_id");
//                do
//                {
//                    calName = managedCursor.getString(nameColumn);
//                    calId = managedCursor.getString(idColumn);
//                    Log.e("Calendar Id : ",""+calId+" : "+calName);
//                }
//                while (managedCursor.moveToNext());
//
//                if(calId != null)
//                {
//                    try
//                    {
//                        Log.e("Calendar Id : ",""+calId+" : "+calName);
//                        ContentValues event = new ContentValues();
//                        event.put("calendar_id", calId);
//                        event.put("title", "Vaccination for " + mStrPetName);
//                        event.put("description", mStrVaccineName + " vaccination for " + mStrPetName);
//                        event.put("eventLocation", "");
//                        event.put("dtstart", objcalendar.getTimeInMillis());
//                        event.put("dtend", objcalendar.getTimeInMillis());
//                        event.put("allDay", 1);
//                        event.put("eventStatus", 1);
//                        event.put("visibility", 0);
//                        event.put("hasAlarm", 1);
//                        Uri eventsUri = Uri.parse("content://com.android.calendar/events");
//                        Uri url = getContentResolver().insert(eventsUri, event);
//                        Log.e("Event Res : ",""+url);
//                        if(!url.equals(""))
//                            showAlertDialog("Information", "Event Successfully Added ");
//                    }
//                    catch (Exception kwse)
//                    {
//                        Log.e("Exception 2 kwse ",""+kwse.toString());
//                    }
//                }
//            }
        }
    }


    /*
     * fill all the data required for the spinners vets and pets  
     * */
    private void filldata(){
    	
    	petcollection = datasource.getAllPets();
    	vetcollection = datasource.getAllDoctors();
    	vaccinationlist = Arrays.asList(getResources().getStringArray(R.array.petvaccinelist));
    	
    	if(!petcollection.isEmpty()){
    		petadapter = new GenericDataBinder<PetDetail>(this, petcollection, getApplicationContext());
    		mSpinPet.setAdapter(petadapter);
    	}
    	
    	if(!vetcollection.isEmpty()){
    		docadapter = new GenericDataBinder<DoctorDetails>(this,vetcollection,getApplicationContext());
    		mSpinVet.setAdapter(docadapter);
    	}
    	
    	if(!vaccinationlist.isEmpty()){
    		vaccineadapter = new GenericDataBinder<String>(this,vaccinationlist,getApplicationContext());
    		mSpinVaccine.setAdapter(vaccineadapter);
    	}
    }
    
    /*
     * Retrieves all the data from the edit fields
     * */
    private VaccineDetails retrieveVaccinationData() {
    	
    	VaccineDetails vacdetails = new VaccineDetails();
    	vacdetails.setM_vaccinename(mStrVaccineName);
    	
    	//get doc
    	vacdetails.setM_doc(selectedVet);
    	
    	//get pet
    	vacdetails.setM_pet(selectedPet);
    	
    	//get date
    	vacdetails.setM_vaccinedate(m_vaccinedate);
    	
    	//get next due date
    	vacdetails.setM_nxtvaccinedate(editVaccineDate.getText().toString());
    	
    	//get remarks
    	//TODO : Next version
    	vacdetails.setM_remarks(""); /* For future */
    	
    	/*
		 *	 
		 * */
		Log.i(LOG_CLASS, "Vaccine details retrieved from edit fields");
		return vacdetails;
	}
    
    /*
     * Warning msg to the user
     * */
    private void makeToast(String msg) {
        Toast.makeText(VaccineEntryActivity.this, msg,
            Toast.LENGTH_LONG).show();
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
        	objcalendar.set(Calendar.YEAR, year);
        	objcalendar.set(Calendar.MONTH, monthOfYear);
        	objcalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        	Date vaccineDate = objcalendar.getTime();
        	if(nowDate.after(vaccineDate)){
//        		makeToast("Please select a valid future date !!");

                showAlertDialog(getResources().getString(R.string.dialogTitleError),getResources().getString(R.string.dialogMessageSelectLaterDate));
        	}
        	else{
        		updateVaccineDate();
        	}
        }

    };

    /*
	 *  Alert dialog to be displayed when the selected date is <= current date
	 *  The vaccination entry date can only be a future date
	 * */
    private void showAlertDialog(String title, String message){

        final Dialog dialog = new Dialog(VaccineEntryActivity.this,R.style.ThemeDialogCustom);
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
	
    /*
     * Vaccination due date selected
     * */
    private void updateVaccineDate(){
    	String dateFormat = "MM/dd/yyyy";
    	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,Locale.US);
    	editVaccineDate.setText(sdf.format(objcalendar.getTime()));
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insertactionmenu, menu);
        return true;
    }
}
