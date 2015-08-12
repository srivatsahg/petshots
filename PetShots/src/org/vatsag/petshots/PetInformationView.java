
/*
 * @Author	:	Srivatsa Haridas
 * @Date	:	25th October 2013
 * @Title	:	Pet information View 
 * @Desc	:	This activity shall show the user the information about a pet
 * 				He also has the option to edit the information and view the vaccination details for that pet
 * */


package org.vatsag.petshots;

import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;
import org.vatsag.R;
import org.vatsag.R.layout;
import org.vatsag.R.menu;
import org.vatsag.database.CentralDataSource;
import org.vatsag.utils.*;

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

import java.util.List;

/*
 * Menu - Edit and Vaccination Details
 * */
public class PetInformationView extends Activity {

	private static final String LOG_CLASS = "[PetInformationView]";
	private TextView tvPetName,tvPetSex,tvDOB,tvChipCode,tvRegCode;
	private Button btnEdit,btnDetails;
	private long petID;
	private LayoutUpdater layoutupdater;
	private CentralDataSource datasource;
	private static final int REQUEST_CODE = 2;
	private PetDetail petInformation;
	private Typeface typeface;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pspetinfo);
        
        //Layout updater
    	layoutupdater = new PetInfoLayoutUpdater(this);
    	layoutupdater.updateLayout();
        /*
         * Display fields
         * */
        tvPetName = (TextView)findViewById(R.id.txtinfoPetName);
        tvPetSex = (TextView)findViewById(R.id.txtinfopetsex);
        tvDOB = (TextView)findViewById(R.id.txtinfodob);
        tvChipCode = (TextView)findViewById(R.id.txtinfochipid);
        tvRegCode =  (TextView)findViewById(R.id.txtinforegid);
        
        /*
         * Command buttons 
         * */
        btnEdit = (Button)findViewById(R.id.btnPetEdit);
        btnDetails = (Button) findViewById(R.id.btnPetVaccine);
     
        
        btnEdit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Opens pet info edit activity where the user can edit the fields,add pics etc
				Intent i = new Intent();
				i.setClass(PetInformationView.this, PetInformationActivity.class);
				ParcelablePetDetail ppd = new ParcelablePetDetail(petInformation);
				i.putExtra("petdetail", ppd);
				startActivity(i);	
				
			}
		});
        
      
        
        // Get position to display
        Intent i = getIntent();
        ParcelablePetDetail parcelablePetDetail = (ParcelablePetDetail) i.getParcelableExtra("petdetail");
        petInformation = parcelablePetDetail.getPetdetail(); //Reconstructs a petdetail obj
        
        //get the reference to the datasource 
        datasource = new CentralDataSource(this);
        datasource.open();
        
        if(petInformation != null){
        	petID = petInformation.getP_id();
        	retrievePetInformation(petInformation);	
        }
        else{
        	makeToast("Error!! No such pet information is present in the database");
        	Log.e(LOG_CLASS, "Error while retrieving pet information");
        }
        
        
        btnDetails.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                List<VaccineDetails> vaccineList = datasource.getAllVaccineInformation(petID);
                if (!vaccineList.isEmpty()) {
                    Intent i = new Intent();
                    i.setClass(PetInformationView.this, VaccinationDetailsActivity.class);
                    i.putExtra("petid", petID);
                    Log.i(LOG_CLASS, "<<---- Before invoking VaccinationDetailsActivity ---->>");
                    startActivity(i);
                }
                else{
                    showAlertDialog(getResources().getString(R.string.dialogTitleInformation),getResources().getString(R.string.dialogMessageNoVaccineData));
                }
            }
        });
        
        
        this.typeface = Typefaces.get(this.getApplicationContext(), Constants.ROBOTA_FONTFILE);
        applyTypefaceFonts(typeface);
    }

    /*
	 *  Alert dialog to be displayed when the selected date is <= current date
	 *  The vaccination entry date can only be a future date
	 * */
    private void showAlertDialog(String title, String message){

        final Dialog dialog = new Dialog(PetInformationView.this,R.style.ThemeDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pscustomdialogwindow);
        dialog.setCancelable(true);

        final TextView tvDialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
        final TextView tvDialogMsg = (TextView)dialog.findViewById(R.id.dialogmessage);
        final ImageView ivDialogIcon = (ImageView)dialog.findViewById(R.id.dialogicon);

        final Button btnOK = (Button) dialog.findViewById(R.id.dialogbuttonOK);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        ivDialogIcon.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_action_warning));

        tvDialogTitle.setText(title);
        tvDialogTitle.setTypeface(typeface);

        tvDialogMsg.setText(message);
        tvDialogMsg.setTypeface(typeface);

        dialog.show();
    }
    /*
     * Warning msg to the user
     * */
    private void makeToast(String msg) {
        Toast.makeText(PetInformationView.this, msg,
            Toast.LENGTH_LONG).show();
      }
    
    
    /*
     * Retrieves the pet data from the parcel data
     * */
    private void retrievePetInformation(PetDetail pd) {
    	Log.i(LOG_CLASS, "<<---- Parcelable impl ---->> retrievePetInformation entered");
    	
    	/*
    	 * Set the fields 
    	 * */
    	if(pd != null){
    		tvPetName.setText(pd.getName().toString());
    		Log.i(LOG_CLASS, "<<---- Name : " + pd.getName().toString() + "---->>");
        	tvPetSex.setText(pd.getSex().toString());
        	Log.i(LOG_CLASS, "<<---- Sex : " + pd.getSex().toString() + "---->>");
        	tvDOB.setText(pd.getDob().toString());
        	Log.i(LOG_CLASS, "<<---- DOB : " + pd.getDob().toString() + "---->>");
        	tvChipCode.setText(pd.getMchip().toString());
        	Log.i(LOG_CLASS, "<<---- Microchip : " + pd.getMchip().toString() + "---->>");
        	tvRegCode.setText(pd.getRegno().toString());	
        	Log.i(LOG_CLASS, "<<---- Registration : " + pd.getRegno().toString() + "---->>");
    	}
    	
    	Log.i(LOG_CLASS, "Pet Details retrieved from database" + "Name : " +  
    						pd.getName() +" DOB : "+  pd.getDob() +" Sex: "+ pd.getSex() +
    						" Microchip : " + pd.getMchip().toString() + " RegNo : " + pd.getRegno().toString());
    	
    	Log.i(LOG_CLASS, "retrievePetInformation success !!");
	}
    
//    /*
//     * Retrieves the pet data from the database
//     * */
//    private void retrievePetInformation(long id) {
//    	Log.i(LOG_CLASS, "retrievePetInformation entered");
//    	
//    	PetDetail pd = datasource.retrievePetDetail(id);
//    	
//    	/*
//    	 * Set the fields 
//    	 * */
//    	if(pd != null){
//    		tvPetName.setText(pd.getName().toString());
//    		Log.i(LOG_CLASS, "<<---- Name : " + pd.getName().toString() + "---->>");
//        	tvPetSex.setText(pd.getSex().toString());
//        	Log.i(LOG_CLASS, "<<---- Sex : " + pd.getSex().toString() + "---->>");
//        	tvDOB.setText(pd.getDob().toString());
//        	Log.i(LOG_CLASS, "<<---- DOB : " + pd.getDob().toString() + "---->>");
//        	tvChipCode.setText(pd.getMchip().toString());
//        	Log.i(LOG_CLASS, "<<---- Microchip : " + pd.getMchip().toString() + "---->>");
//        	tvRegCode.setText(pd.getRegno().toString());	
//        	Log.i(LOG_CLASS, "<<---- Registration : " + pd.getRegno().toString() + "---->>");
//    	}
//    	
//    	Log.i(LOG_CLASS, "Pet Details retrieved from database" + "Name : " +  
//    						pd.getName() +" DOB : "+  pd.getDob() +" Sex: "+ pd.getSex() +
//    						" Microchip : " + pd.getMchip().toString() + " RegNo : " + pd.getRegno().toString());
//    	
//    	Log.i(LOG_CLASS, "retrievePetInformation success !!");
//	}
    
    
    /*
     * Apply fonts 
     * */
    private void applyTypefaceFonts(Typeface tf){
    	
    	 tvPetName.setTypeface(tf);
         tvPetSex.setTypeface(tf);
         tvDOB.setTypeface(tf);
         tvChipCode.setTypeface(tf);
         tvRegCode.setTypeface(tf);
         btnEdit.setTypeface(tf);
         btnDetails.setTypeface(tf);
    }

    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			retrievePetInformation(petInformation);
    	}
	}
}
