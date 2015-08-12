
/*
 * @Author	:	Srivatsa Haridas
 * @Date	:	25th October 2013
 * @Desc	:	This activity shall display the list of available pet information
 * 				as saved in the database. This activity shall give the user an option to add new 
 * 				pet details via the action bar insert menu (which inturn shall invoke the PetInfoActivity).
 * 				The user can also delete an entry by long-pressing an item in the list.   
 * */
 
package org.vatsag.petshots;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.vatsag.R;
import org.vatsag.R.layout;
import org.vatsag.R.menu;
import org.vatsag.database.CentralDataSource;
import org.vatsag.database.DoctorDataBinder;
import org.vatsag.database.PetDataBinder;
import org.vatsag.utils.*;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

//TODO:	Pet edit/update has to be implemented
public class PetListActivity extends SherlockListActivity {
	
	static final String LOG_CLASS = "[PetListActivity]";
	private PetDataBinder petadapter;
	private PetListLayoutUpdater layoutUpdater;
	private CentralDataSource datasource;
	private List<PetDetail> petsList;
    private List<String> ages;
	ListView listview;

	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int REQUEST_CODE = 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pspetlistview);
        
        listview = getListView();
        
        Log.i(LOG_CLASS, "Before Datasource open !!");
        this.getListView().setDividerHeight(1);
        datasource = new CentralDataSource(this);
        datasource.open();
        Log.i(LOG_CLASS, "Datasource opened !!");
        
        layoutUpdater = new PetListLayoutUpdater(this);
        layoutUpdater.updateLayout();
        
        Log.i(LOG_CLASS, "<< --- Before the pets filldata --- >> ");
        //gets the list of pets from the db and populates in the listview
        filldata();
        
        Log.i(LOG_CLASS, "<< --- after the pets filldata --- >> ");
        registerForContextMenu(getListView());
        Log.i(LOG_CLASS, "<< --- after registering the context menu  --- >> ");
        
        	listview.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					
					PetDetail selPet = ((PetDetail)parent.getItemAtPosition(pos));
					
					if(selPet != null){
						Log.i(LOG_CLASS, "<<---- PetDetail retrieved from position : " + String.valueOf(pos) + " ---->>");
						
						ParcelablePetDetail ppd = new ParcelablePetDetail(selPet);	
						Intent info = new Intent();
						info.setClass(PetListActivity.this, PetInformationView.class);
						info.putExtra("petdetail", ppd);
						startActivity(info);
						info.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //TODO: Testing activity
					}else{
						makeToast("Error!! No such pet detail exists");
						Log.e(LOG_CLASS, "Error!! No such pet information present in the database");
					}
						
					
//					long petid = ((PetDetail)parent.getItemAtPosition(pos)).getP_id();
//					if(petid != -1){
//						Intent info = new Intent();
//						info.setClass(PetListActivity.this, PetInformationView.class);
//						info.putExtra("petid", petid);
//						startActivity(info);
//						info.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //TODO: Testing activity
//					}
//					else{
//						makeToast("Error!! No such pet detail exists");
//						Log.e(LOG_CLASS, "Error!! No such pet information present in the database");
//					}
				}
        		
			});
    }
    
    
    /*
     * Warning msg to the user
     * */
    private void makeToast(String msg) {
        Toast.makeText(PetListActivity.this, msg,
            Toast.LENGTH_LONG).show();
      }
    
    /*
     * List of Pets
     * */
    private void filldata(){
    	if(datasource == null){
    		datasource = new CentralDataSource(this);
    		datasource.open();
    	}

        /*
        * list from database
        * */
    	petsList = datasource.getAllPets();

        /*
        * update the age
        * */
        updateAge(petsList);

        /*
        * bind to the list using the petadapter
        * */
    	if(!petsList.isEmpty()){
    		petadapter = new PetDataBinder(this,petsList,ages);
    		listview.setAdapter(petadapter);
    	}
    }

    /*
    * Calculates the current age and update the list accordingly
    * */
    private List<String> updateAge(List<PetDetail> petlist){

        ages = new ArrayList<String>();

        if(!petlist.isEmpty()){
            for(PetDetail pet : petlist){
                ages.add(Utils.calculateExactAge(pet.getDob()));
            }
        }
        return ages;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_action_bar, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch(item.getItemId()){
    	
    	case R.id.add_item :
    		Log.i(LOG_CLASS, "<< --- Before invoking the PetInformationActivity --- >>");
    		createNewPetDetail();
    		Log.i(LOG_CLASS, "<< --- After invoking the PetInformationActivity --- >>");
    		return true;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.add(Menu.NONE,DELETE_ID,Menu.NONE,R.string.strmnudelete);
    }
    
    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Log.i(LOG_CLASS, "<<---- Pet selected : " + String.valueOf(info.position) + "---->> ");
		
		switch (item.getItemId()) {
		case DELETE_ID:
			Log.i(LOG_CLASS, "<<---- Inside the delete ctxt menu click handler ---->>");
			if (petadapter.getCount() > 0) {
				Log.i(LOG_CLASS, "<<---- Adapter count is greater than zero ---->>");
				Log.i(LOG_CLASS, "<<---- Before retrieving Pet Details ---->>");
				PetDetail myPet = (PetDetail) petadapter.getItem(info.position);
				Log.i(LOG_CLASS, "<<---- Information retrived for : " + myPet.toString() + "---->>");
				datasource.deletePetDetail(myPet);
				petsList.remove(myPet);
				refreshadapter(petsList); /* refresh the adapter */
				return true;
			}
		}
		
		return super.onContextItemSelected((android.view.MenuItem) item);
    }
    
    /*
     * Refresh the list from the UI thread 
     * */
    private void refreshadapter(final List<PetDetail> petsList){
    	runOnUiThread(new Runnable() {
			
			public void run() {
				petadapter.refreshAdapter(petsList);
			}
		});
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
    
    /*
     * Starts an activity to enter details of a Pet
     * */
    @TargetApi(11)
	private void createNewPetDetail(){
    	
    	Log.i(LOG_CLASS, "<< --- inside  createNewPetDetail  --- >> ");
    	Intent i = null;
    	i = new Intent(PetListActivity.this,PetInformationActivity.class);
    	startActivityForResult(i,REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		filldata();
    	}
    }
}
