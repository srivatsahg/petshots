package org.vatsag.petshots;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.*;

import org.vatsag.R;
import org.vatsag.petshots.CustomGridViewAdapter.RecordHolder;
import org.vatsag.utils.*;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	Provides the user a list of available options
 * */
public class DashBoardActivity extends Activity {

	static final String KEY_IMAGE = "image";
	static final String KEY_TITLE = "title";
	static final String LOG_CLASS = "DashBoardActivity"; 
	
	ArrayList<CListItem> dashboardItemsCollection;
	CListItem dashboardItem;
	Bitmap bmVet,bmVaccine,bmPet,bmPhone,bmSearch,bmHelp;
	CustomGridViewAdapter gridViewAdapter;
	GridView gdview;
	Typeface typeface;
	DashboardLayoutUpdater dashLayoutUpdater;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psdashboard);
        
        dashboardItemsCollection = new ArrayList<CListItem>();
        
        /*	Updating the form title */
        dashLayoutUpdater = new DashboardLayoutUpdater(this);
        dashLayoutUpdater.updateLayout();
        
        
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPurgeable = true;
        
        /*
         * Dashboard menu items 
         * */
        bmVet = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_doctor,bmOptions);
        bmVaccine = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_pills,bmOptions);
        bmPet = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_pets,bmOptions);
        bmPhone = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_call,bmOptions);
        bmSearch = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_directions,bmOptions);
        bmHelp = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_help,bmOptions);
        
        //Dashboard item - Vet Details 
        dashboardItemsCollection.add(
        		new CListItem(this.getString(R.string.dash_vet),bmVet));
        
        //Dashboard item - Pet Details
        dashboardItemsCollection.add(
        		new CListItem(this.getString(R.string.dash_pet),bmPet));
        
        //Dashboard item - Vaccination Details
        dashboardItemsCollection.add(
        		new CListItem(this.getString(R.string.dash_vaccine),bmVaccine));
        
        //Dashboard item - Phone 
        dashboardItemsCollection.add(
        		new CListItem(this.getString(R.string.dash_call),bmPhone));
        
        //Dashboard item - Locate
        dashboardItemsCollection.add(
        		new CListItem(this.getString(R.string.dash_locate),bmSearch));
        
        //Dashboard item - Help
        dashboardItemsCollection.add(
        		new CListItem(this.getString(R.string.dash_help),bmHelp));
        
        Log.i(LOG_CLASS, "Dashboard items added");
        
        //Grid view 
        gdview = (GridView)findViewById(R.id.gridviewDash);
        gdview.setHorizontalSpacing(1);
        gdview.setVerticalSpacing(1);
        
        gridViewAdapter	= new CustomGridViewAdapter(this,R.layout.gmcgridrow,dashboardItemsCollection);
        gdview.setAdapter(gridViewAdapter);
        gdview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
			
				Intent i = null;
				RecordHolder holder = (RecordHolder)v.getTag();
				String item = holder.tvOption.getText().toString();
				Log.i("Item clicked", item +" : " +  pos);
				
				if(item == getString(R.string.dash_vet)){
					/*	Shows the activity where the vet details can be stored  */
					i = new Intent(DashBoardActivity.this,VetListActivity.class);
				}
				else if(item == getString(R.string.dash_pet)){
					/*	Shows the activity where the pet details can be stored  */
					i = new Intent(DashBoardActivity.this,PetListActivity.class);
				}
				else if(item == getString(R.string.dash_vaccine)){
					/*	Invokes an activity where the vaccination details can be entered	*/
					i = new Intent(DashBoardActivity.this,VaccineEntryActivity.class);
				}
				else if(item == getString(R.string.dash_call)){
					i = new Intent(Intent.ACTION_DIAL);
					i.setData(Uri.parse("tel:" + "9886300072"));
//					i = new Intent(DashBoardActivity.this, ABSActivity.class);
					
				}
				else if(item == getString(R.string.dash_locate)){
					String url = "http://www.google.com";
					i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
				}
				else if(item == getString(R.string.dash_help)){
					/*	Invokes the Help topics activity	*/
					i = new Intent(DashBoardActivity.this, HelpTopicActivity.class);
				}
					
				if(i != null){
					startActivity(i);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}
				else{
					
					Log.e(LOG_CLASS, "Functionality not implemented");
				}
					
			}
		});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.psdashboard, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch(item.getItemId()){
    	
    	case R.id.menu_contact :
    		Toast.makeText(DashBoardActivity.this, "VATSAG Inc. 2013 All Rights Reserved", Toast.LENGTH_LONG).show();
    		break;
    	case R.id.menu_close :
    			//TODO: Are you sure you want to exit the application

    			showAlertDialog(getResources().getString(R.string.msgtitleExit),getResources().getString(R.string.msgcloseconfirm));
    			
    			break;
    			
    	}
    	
    	return super.onOptionsItemSelected(item);
    }

    /*
	 *
	 * */
    private void showAlertDialog(String title, String message){

        final Dialog dialog = new Dialog(DashBoardActivity.this,R.style.ThemeDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pscustomalertdialog);
        dialog.setCancelable(true);

        this.typeface = Typefaces.get(dialog.getContext(), Constants.ROBOTA_FONTFILE);

        final TextView tvDialogTitle = (TextView)dialog.findViewById(R.id.dialogTitle);
        final TextView tvDialogMsg = (TextView)dialog.findViewById(R.id.dialogmessage);

        final Button btnCancel = (Button) dialog.findViewById(R.id.dialogbuttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Do nothing if the cancel button is pressed
                dialog.cancel();
            }
        });

        final Button btnOK = (Button) dialog.findViewById(R.id.dialogbuttonOK);
        btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                //The application shall gracefully shutdown if the OK button was selected
                System.exit(0);
            }
        });


        btnCancel.setTypeface(typeface);
        btnOK.setTypeface(typeface);
        tvDialogTitle.setText(title);
        tvDialogTitle.setTypeface(typeface);
        tvDialogMsg.setText(message);
        tvDialogMsg.setTypeface(typeface);

        dialog.show();
    }
}
