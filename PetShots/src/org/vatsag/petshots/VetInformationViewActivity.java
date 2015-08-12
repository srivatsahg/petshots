package org.vatsag.petshots;

import org.vatsag.R;
import org.vatsag.R.layout;
import org.vatsag.R.menu;
import org.vatsag.database.CentralDataSource;
import org.vatsag.utils.Constants;
import org.vatsag.utils.DoctorDetails;
import org.vatsag.utils.DoctorInfoLayoutUpdater;
import org.vatsag.utils.LayoutUpdater;
import org.vatsag.utils.ParcelableDocDetail;
import org.vatsag.utils.ParcelablePetDetail;
import org.vatsag.utils.PetDetail;
import org.vatsag.utils.PetInfoLayoutUpdater;
import org.vatsag.utils.Typefaces;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VetInformationViewActivity extends Activity {
	
	private static final String LOG_CLASS = "[VetInformationViewActivity]";
	private TextView tvfirstname,tvlastname,tvemail,tvphoneprimary,tvphonesecondary,tvaddress;
	private TextView tvhdrfirstname,tvhdrlastname,tvhdremail,tvhdrphoneprimary,tvhdrphonesecondary,tvhdraddress;
	private Button btnEdit;
	private long docID;
	private LayoutUpdater layoutupdater;
	private CentralDataSource datasource;
	private static final int REQUEST_CODE = 2;
	private DoctorDetails doctorinformation;
	private Typeface typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psvetinfo);
        
        //Layout updater
    	layoutupdater = new DoctorInfoLayoutUpdater(this);
    	layoutupdater.updateLayout();
        /*
         * Display fields
         * */
    	tvfirstname = (TextView)findViewById(R.id.txtvetinfoFirstName);
        tvlastname = (TextView)findViewById(R.id.txtvetinfoLastName);
        tvemail = (TextView)findViewById(R.id.txtvetinfoemail);
        tvphoneprimary = (TextView)findViewById(R.id.txtvetinfophone1);
        tvphonesecondary =  (TextView)findViewById(R.id.txtvetinfophone2);
        tvaddress =  (TextView)findViewById(R.id.txtvetinfoaddress);
        
        /*
         * Display fields
         * */
    	tvhdrfirstname = (TextView)findViewById(R.id.tvdocinfoheaderfirstname);
        tvhdrlastname = (TextView)findViewById(R.id.tvdocinfoheaderlastname);
        tvhdremail = (TextView)findViewById(R.id.tvdocinfoheaderemail);
        tvhdrphoneprimary = (TextView)findViewById(R.id.tvdocinfoheaderphone1);
        tvhdrphonesecondary =  (TextView)findViewById(R.id.tvdocinfoheaderphone2);
        tvhdraddress =  (TextView)findViewById(R.id.tvdocinfoheaderaddress);
        
        /*
         * Command buttons 
         * */
        btnEdit = (Button)findViewById(R.id.btnvetinfoEdit);
        btnEdit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Opens pet info edit activity where the user can edit the fields,add pics etc
				Intent i = new Intent();
				i.setClass(VetInformationViewActivity.this, VetInfoActivity.class);
				ParcelableDocDetail pdd = new ParcelableDocDetail(doctorinformation);
				i.putExtra("docdetail", pdd);
				startActivity(i);	
			
			}
		});
        
     // Get position to display
        Intent i = getIntent();
//        this.docID = i.getLongExtra("docid",-1);
        ParcelableDocDetail pdd = (ParcelableDocDetail) i.getParcelableExtra("docdetail");
        doctorinformation = pdd.getDocdetail(); //Reconstructs a parcel DoctorDetails obj
        
        //get the reference to the datasource 
        datasource = new CentralDataSource(this);
        datasource.open();
        
        if(doctorinformation != null){
        	retrieveDocInformation(doctorinformation);	
        }
        else{
        	makeToast("Error!! No such pet information is present in the database");
        	Log.e(LOG_CLASS, "Error while retrieving pet information");
        }
        
        this.typeface = Typefaces.get(this.getApplicationContext(), Constants.ROBOTA_FONTFILE);
        applyTypefaceFonts(typeface);
    }
    
    /*
     * Retrieves the pet data from the database
     * */
    private void retrieveDocInformation(DoctorDetails pd) {
    	Log.i(LOG_CLASS, "retrieveDocInformation entered");
    	
    	/*
    	 * Set the fields 
    	 * */
    	if(pd != null){
    		tvfirstname.setText(pd.getM_firstname().toString());
    		Log.i(LOG_CLASS, "<<---- FirstName set success ---->>");
        	tvlastname.setText(pd.getM_lastname().toString());
        	Log.i(LOG_CLASS, "<<---- Lastname set success !! ---->>");
        	tvemail.setText(pd.getM_email().toString());
        	Log.i(LOG_CLASS, "<<---- Email set success !! ---->>");
        	tvphoneprimary.setText(pd.getM_phonePrimary().toString());
        	Log.i(LOG_CLASS, "<<---- Phone 1 set success ---->>");
        	tvphonesecondary.setText(pd.getM_phoneSecondary().toString());	
        	Log.i(LOG_CLASS, "<<---- Phone 1 set success ---->>");
        	tvaddress.setText(pd.getM_address().toString());
        	Log.i(LOG_CLASS, "<<---- Vet address set success ---->>");
    	}
    	
    	Log.i(LOG_CLASS, "retrieveDocInformation success !!");
	}
    
    
    /*
     * Fonts
     * */
    private void applyTypefaceFonts(Typeface tf){
    	 /*
         * Display fields
         * */
    	tvfirstname.setTypeface(tf);
        tvlastname.setTypeface(tf);
        tvemail.setTypeface(tf);
        tvphoneprimary.setTypeface(tf);
        tvphonesecondary.setTypeface(tf);
        tvaddress.setTypeface(tf);
        
        /*
         * Display fields
         * */
    	tvhdrfirstname.setTypeface(tf);
        tvhdrlastname.setTypeface(tf);
        tvhdremail.setTypeface(tf);
        tvhdrphoneprimary.setTypeface(tf);
        tvhdrphonesecondary.setTypeface(tf);
        tvhdraddress.setTypeface(tf);
        
        btnEdit.setTypeface(tf);
    }
    
    /*
     * Warning msg to the user
     * */
    private void makeToast(String msg) {
        Toast.makeText(VetInformationViewActivity.this, msg,
            Toast.LENGTH_LONG).show();
      }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			retrieveDocInformation(doctorinformation);
    	}
	}
}
