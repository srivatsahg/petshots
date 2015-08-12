
/*
 * @Author	:	Srivatsa Haridas
 * @Date	:	25th October 2013
 * @Desc	:	This activity shall display the list of available vet information
 * 				as saved in the database. This activity shall give the user an option to add new 
 * 				vet details via the action bar insert menu (which inturn shall invoke the VetInfoActivity).
 * 				The user can also delete an entry by long-pressing an item in the list.   
 * */

package org.vatsag.petshots;

import java.util.List;
import org.vatsag.R;
import org.vatsag.database.CentralDataSource;
import org.vatsag.database.DoctorDataBinder;
import org.vatsag.utils.DoctorDetails;
import org.vatsag.utils.DoctorListLayoutUpdater;
import org.vatsag.utils.ParcelableDocDetail;
import org.vatsag.utils.ParcelablePetDetail;
import org.vatsag.utils.PetDetail;
import org.w3c.dom.ls.LSInput;


import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class VetListActivity extends SherlockListActivity {

	static final String LOG_CLASS = "[VetListActivity]";
	private DoctorDataBinder adapter;
	private DoctorListLayoutUpdater layoutUpdater;
	private CentralDataSource datasource;
	private List<DoctorDetails> docs;
	ListView listview;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int REQUEST_CODE = 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psvetlistdata);
        listview = getListView();
        listview.setItemsCanFocus(false);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        Log.i(LOG_CLASS, "Before Datasource open !!");
        this.getListView().setDividerHeight(1);
        datasource = new CentralDataSource(this);
        datasource.open();
        Log.i(LOG_CLASS, "Datasource opened !!");
        layoutUpdater = new DoctorListLayoutUpdater(this);
        layoutUpdater.updateLayout();
        
        Log.i(LOG_CLASS, "<< --- Before the filldata --- >> ");
        //gets the list of vets from the db and populates in the listview
        filldata();
        Log.i(LOG_CLASS, "<< --- after the filldata --- >> ");
        registerForContextMenu(getListView());
        Log.i(LOG_CLASS, "<< --- after registering the context menu  --- >> ");
        
        listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int pos, long id) {
				
				long vetid = ((DoctorDetails)parent.getItemAtPosition(pos)).getM_id();
				
				DoctorDetails docdetail = (DoctorDetails)parent.getItemAtPosition(pos);
				if(docdetail != null){
					ParcelableDocDetail pdd = new ParcelableDocDetail(docdetail);
					Intent info = new Intent();
					info.setClass(VetListActivity.this, VetInformationViewActivity.class);
					info.putExtra("docdetail", pdd);
					startActivity(info);
					info.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //TODO: Testing activity
				}
//				
//				if(vetid != -1){
//					Intent info = new Intent();
//					info.setClass(VetListActivity.this, VetInformationViewActivity.class);
//					info.putExtra("docid", vetid);
//					startActivity(info);	
//				}
				else{
					makeToast("Error!! No such vet detail exists");
					Log.e(LOG_CLASS, "Error!! No such vet information present in the database");
				}
			}
    		
		});
    }
    
    /*
     * Warning msg to the user
     * */
    private void makeToast(String msg) {
        Toast.makeText(VetListActivity.this, msg,
            Toast.LENGTH_LONG).show();
      }

    
    /*
     * List of Vets
     * */
    private void filldata(){
    	
    	docs = datasource.getAllDoctors();
    	
    	if(!docs.isEmpty()){
    		adapter = new DoctorDataBinder(this,docs);
    		listview.setAdapter(adapter);
    	}
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.add(Menu.NONE,DELETE_ID,Menu.NONE,R.string.strmnudelete);
    }
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_action_bar, menu);
        return true;
    }
    
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		Log.i(LOG_CLASS, "<<---- Doctor selected : " + String.valueOf(info.position) + "---->> ");
		
		switch (item.getItemId()) {
		case DELETE_ID:
			Log.i(LOG_CLASS, "<<---- Inside the delete ctxt menu click handler ---->>");
			if (adapter.getCount() > 0) {
				Log.i(LOG_CLASS, "<<---- Adapter count is greater than zero ---->>");
				Log.i(LOG_CLASS, "<<---- Before retrieving Doctor Details ---->>");
				DoctorDetails doc = (DoctorDetails) adapter.getItem(info.position);
				Log.i(LOG_CLASS, "<<---- Information retrived for : " + doc.toString() + "---->>");
				datasource.deleteDoctorDetail(doc);
				docs.remove(doc);
				refreshadapter(docs); /* refresh the adapter */
				return true;
			}
		}
		
		return super.onContextItemSelected((android.view.MenuItem) item);
	}
	
	/*
     * Refresh the list from the UI thread 
     * */
    private void refreshadapter(final List<DoctorDetails> docList){
    	runOnUiThread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				adapter.refreshAdapter(docList);
			}
		});
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch(item.getItemId()){
    	
    	case R.id.add_item :
    		Log.i(LOG_CLASS, "<< --- Before invoking the VetInfoActivity --- >>");
    		createVetDetail();
    		Log.i(LOG_CLASS, "<< --- After invoking the VetInfoActivity --- >>");
    		return true;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    /*
     * Starts an activity to enter details of a vet
     * */
    @TargetApi(11)
	private void createVetDetail(){
    	
    	Log.i(LOG_CLASS, "<< --- inside  createVetDetail  --- >> ");
    	Intent i = null;
    	i = new Intent(VetListActivity.this,VetInfoActivity.class);
		startActivityForResult(i,REQUEST_CODE);
    }
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		filldata();
    	}
    }
}
