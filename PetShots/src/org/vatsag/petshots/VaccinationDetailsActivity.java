package org.vatsag.petshots;


import java.util.List;

import android.app.Dialog;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;

import com.actionbarsherlock.view.MenuItem;
import org.vatsag.R;
import org.vatsag.database.CentralDataSource;
import org.vatsag.database.VaccineDataBinder;
import org.vatsag.utils.*;

import android.R.color;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout.LayoutParams;

public class VaccinationDetailsActivity extends SherlockListActivity {

	static final String LOG_CLASS = "[VaccinationDetailsActivity]";
	
	private VaccineDataBinder adapter;
	private LayoutUpdater layoutUpdater;
	private CentralDataSource datasource;
	private List<VaccineDetails> vaccineList;
	ListView listview;
	private long petID;
	private TextView tvVaccineName,tvDate,tvNextDueDate,tvDoctor;
	private Typeface typeface;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.psvaccinehistory);
//        setContentView(R.layout.psvaccinesummarytable);
        
        tvVaccineName = (TextView)findViewById(R.id.tvlistvaccinename);
        tvDate = (TextView)findViewById(R.id.tvlistvisitdate);
        tvNextDueDate = (TextView)findViewById(R.id.tvlistnextdue);
        tvDoctor = (TextView)findViewById(R.id.tvlistdoctor);
        
        // Get position to display
        Intent i = getIntent();
        this.petID = i.getLongExtra("petid",-1);
        
        listview = getListView();  
        Log.i(LOG_CLASS, "Before Datasource open from " + LOG_CLASS + " class");
        this.getListView().setDividerHeight(1); 
        
        datasource = new CentralDataSource(this);
        datasource.open();
        Log.i(LOG_CLASS, "Datasource opened from " + LOG_CLASS + " class");
        
        layoutUpdater = new VaccinationDetailsLayoutUpdater(this);
        layoutUpdater.updateLayout();
        
        Log.i(LOG_CLASS, "<< --- Before the vaccination filldata --- >> ");
        //gets the list of pets from the db and populates in the listview
        fillvaccinedata();
//        filldata();
        
        this.typeface = Typefaces.get(this.getApplicationContext(), Constants.ROBOTA_FONTFILE);
        applyTypefaceFonts(typeface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_action_share, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.share_item :
                Log.i(LOG_CLASS, "<< --- Before invoking the intent to share the vaccination data via mail --- >>");
                shareVaccinationData();
                Log.i(LOG_CLASS, "<< --- After invoking the intent to share the vaccination data via mail --- >>");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * Shares the Pet data + vaccination history via mail intent
    * ---------------------------------
    * Name: Chilli
    * Sex : Female
    * DoB : 01.01.12
    * RegId:
    * ---------------------------------
    * Vaccination History table
    * Date | Due | Vaccination | Doctor
    * ---------------------------------
    * ---------------------------------
    *
    *
    * */
    private void shareVaccinationData() {
//        final Intent shareIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vaccination Schedule for Chilli ");
//        shareIntent.putExtra(
//                Intent.EXTRA_TEXT,
//                Html.fromHtml(new StringBuilder()
//                        .append("<h2>Vaccination Details</h2>")
//                        .append("<p>")
//                        .append("<ul>") //start of the list
//                        .append("<li><b>Name    :</b>Chilli</li>")
//                        .append("<li><b>Sex     :</b>Female</li>")
//                        .append("<li><b>D.O.B   :</b>01.01.2012</li>")
//                        .append("<li><b>ChipID   :</b>CHIP-12345</li>")
//                        .append("<li><b>RegistrationID   :</b>BBMP-12345</li>")
//                        .append("</ul>") //end of the list
//                        .append("</p>")
//                        .toString())
//        );
//        startActivity(shareIntent);
//        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PetDetail pet = null;
        if(vaccineList != null){
           pet  = vaccineList.get(0).getM_pet(); /* Get the pointer to the pet */

            String strVaccinationHeader = getBaseContext().getResources().getString(R.string.dash_vaccine);
            /*
            * Header information from string resource files
            * */
            String strHeaderName = getBaseContext().getResources().getString(R.string.strPetname);
            String strHeaderGender = getBaseContext().getResources().getString(R.string.strSex);
            String strHeaderDOB = getBaseContext().getResources().getString(R.string.strPetDOB);
            String strHeaderChip = getBaseContext().getResources().getString(R.string.strMicrochip);
            String strHeaderReg = getBaseContext().getResources().getString(R.string.strRegno);

            /*
            * Column headers from string resource files
            * */
            String strHeaderVaccineName = getBaseContext().getResources().getString(R.string.strtblVaccineName);
            String strHeaderVaccineDate = getBaseContext().getResources().getString(R.string.strtblVaccineDate);
            String strHeaderVaccineNextDate = getBaseContext().getResources().getString(R.string.strtblVaccineDueDate);
            String strHeaderVaccineDoctor = getBaseContext().getResources().getString(R.string.strtblDoctor);

            /*
            * Header details
            * */
            String strHeader =     new StringBuilder()
                    .append(strVaccinationHeader)
                    .append(System.getProperty("line.separator"))
                    .append("-----------------------------------") //line separator
                    .append(System.getProperty("line.separator"))
                    .append(strHeaderName + '\t' + ":" + '\t' +  pet.getName())
                    .append(System.getProperty("line.separator"))
                    .append(strHeaderGender + '\t' + ":" + '\t' + pet.getSex())
                    .append(System.getProperty("line.separator"))
                    .append(strHeaderDOB + '\t' + ":" + '\t' + pet.getDob())
                    .append(System.getProperty("line.separator"))
                    .append(strHeaderChip + '\t' + ":" + '\t' + pet.getMchip())
                    .append(System.getProperty("line.separator"))
                    .append(strHeaderReg + '\t' + ":" + '\t' + pet.getRegno())
                    .append(System.getProperty("line.separator"))
                    .append("-----------------------------------") //line separator
                    .append(System.getProperty("line.separator"))
                    .append(strHeaderVaccineName + '\t' + "|")
                    .append('\t' + strHeaderVaccineDate + '\t' + "|")
                    .append('\t' + strHeaderVaccineNextDate + '\t' + "|")
                    .append('\t' + strHeaderVaccineDoctor + '\t')
                    .append(System.getProperty("line.separator"))
                    .append("-----------------------------------") //line separator
                    .append(System.getProperty("line.separator"))
                    .toString();



            StringBuilder strColDetails = new StringBuilder();
            for(VaccineDetails details : vaccineList){
            strColDetails.append(details.getM_vaccinename() + '\t');
            strColDetails.append(details.getM_vaccinedate() + '\t');
            strColDetails.append(details.getM_nxtvaccinedate()+ '\t');
            strColDetails.append(details.getM_doc().getM_firstname() + '\t');
            strColDetails.append(System.getProperty("line.separator"));
            strColDetails.append("-----------------------------------");
            strColDetails.append(System.getProperty("line.separator"));
        }

        Log.i(LOG_CLASS,strHeader + strColDetails.toString());

        final Intent shareIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vaccination Schedule for " + pet.getName());
        shareIntent.putExtra(Intent.EXTRA_TEXT,strHeader + strColDetails.toString());
        startActivity(shareIntent);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//        Log.i(LOG_CLASS,new StringBuilder()
//                        .append("<h2>Vaccination Details of " + pet.getName() + "</h2>")
//                        .append("<p>")
//                        .append("<ul>") //start of the list
//                        .append("<li><b>Name    :</b> " + pet.getName() + " </li>")
//                        .append("<li><b>Sex     :</b> " + pet.getSex() + " </li>")
//                        .append("<li><b>D.O.B   :</b> " + pet.getDob() + " </li>")
//                        .append("<li><b>ChipID   :</b> " + pet.getMchip() + " </li>")
//                        .append("<li><b>RegistrationID   :</b> " + pet.getRegno() + " </li>")
//                        .append("</ul>") //end of the list
//                        .append("</p>")
//                        .toString());
        }
        else{
            showAlertDialog(getResources().getString(R.string.dialogTitleError),getResources().getString(R.string.dialogMessageNoVaccineData));
        }
    }

    /*
         * Apply fonts
         * */
    private void applyTypefaceFonts(Typeface tf){
    	tvVaccineName.setTypeface(tf);
    	tvDate.setTypeface(tf);
    	tvNextDueDate.setTypeface(tf);
    	tvDoctor.setTypeface(tf);
    }
    
    private void fillvaccinedata(){
    	if(datasource == null){
    		datasource = new CentralDataSource(this);
    		datasource.open();
    	}
    	
    	vaccineList = datasource.getAllVaccineInformation(petID);
    	
    	if(!vaccineList.isEmpty()){
    		
    		adapter = new VaccineDataBinder(this,vaccineList);	
    		listview.setAdapter(adapter);
    	}
    }

    /*
	 *  Alert dialog to be displayed when the selected date is <= current date
	 *  The vaccination entry date can only be a future date
	 * */
    private void showAlertDialog(String title, String message){

        final Dialog dialog = new Dialog(VaccinationDetailsActivity.this,R.style.ThemeDialogCustom);
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
     * List of Vaccination information
     * */
    private void filldata(){

    	if(datasource == null){
    		datasource = new CentralDataSource(this);
    		datasource.open();
    	}
    	
    	vaccineList = datasource.getAllVaccineInformation(petID);	
    	
    	if(!vaccineList.isEmpty()){
    		
    		/*	Using table layout approach	*/
    		
    		/* Populate the list in a string builder object */
    		StringBuilder builder = new StringBuilder();
    		for(VaccineDetails vac : vaccineList){
    			
    			builder.append(vac.getM_vaccinename()).append(";")
    			.append(vac.getM_vaccinedate()).append(";")
    			.append(vac.getM_nxtvaccinedate()).append(";")
    			.append(((DoctorDetails)vac.getM_doc()).getM_firstname()).append("_");
    		}
    		
    		builder.toString();
    	    String st = new String(builder);
    	    Log.d("Main",st);
    	    String[] rows  = st.split("_"); /*	Split the rows with underscore */
    	    
    	    TableLayout tableLayout = (TableLayout)findViewById(R.id.tabvaccinedetails);
    	    tableLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    	    tableLayout.removeAllViews();
    	    
    	    /*	Display headers */
    	    TableRow headerRow = new TableRow(getApplicationContext());
    	    headerRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1));
    	    
    	    /*1.	Vaccine */
    	    TextView vaccineHeader = new TextView(getApplicationContext());
    	    vaccineHeader.setTextColor(color.white);
    	    vaccineHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1));
//    	    vaccineHeader.setBackgroundResource(R.drawable.cell_shape);
    	    vaccineHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
    	    vaccineHeader.setGravity(Gravity.CENTER);
    	    vaccineHeader.setText("Vaccine");
    	    /*Add the column view to the row*/
    	    headerRow.addView(vaccineHeader);
    	    
    	    /*2.	Date */
    	    TextView dateHeader = new TextView(getApplicationContext());
    	    dateHeader.setTextColor(color.white);
//    	    dateHeader.setBackgroundResource(R.drawable.cell_shape);
    	    dateHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
    	    dateHeader.setGravity(Gravity.CENTER);
    	    dateHeader.setText("Date");
    	    /*Add the column view to the row*/
    	    headerRow.addView(dateHeader);
    	    
    	    /*3.	Due date */
    	    TextView duedateHeader = new TextView(getApplicationContext());
    	    duedateHeader.setTextColor(color.white);
//    	    duedateHeader.setBackgroundResource(R.drawable.cell_shape);
    	    duedateHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
    	    duedateHeader.setGravity(Gravity.CENTER);
    	    duedateHeader.setText("Next Due on");
    	    /*Add the column view to the row*/
    	    headerRow.addView(duedateHeader);
    	    
    	    /*3.	Doctor */
    	    TextView docHeader = new TextView(getApplicationContext());
    	    docHeader.setTextColor(color.white);
//    	    docHeader.setBackgroundResource(R.drawable.cell_shape);
    	    docHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
    	    docHeader.setGravity(Gravity.CENTER);
    	    docHeader.setText("Doctor");
    	    docHeader.setVisibility(1);
    	    /*Add the column view to the row*/
    	    headerRow.addView(docHeader);
    	    
    	    
    	    tableLayout.addView(headerRow,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));
    	    
    	    
            
    	    for(int i=0;i<rows.length;i++){
    	        Log.d("Rows",rows[i]);
    	        String row  = rows[i];
    	        TableRow tableRow = new TableRow(getApplicationContext());
//    	        tableRow.setBackgroundResource(R.drawable.cell_shape);
    	        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,1));
    	        final String[] cols = row.split(";");

    	        Log.i(LOG_CLASS, "<<---- Columns count : " + cols.length + " ---->>");
    	        
    	        for (int j = 0; j < cols.length; j++) {             
    	            final String col = cols[j];                                 
    	            TextView columsView = new TextView(getApplicationContext());
    	            columsView.setBackgroundResource(R.drawable.cell_header);
    	            columsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
    	            columsView.setTextColor(color.black);
    	            columsView.setTypeface(null, Typeface.BOLD);
    	            columsView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
    	            columsView.setGravity(Gravity.CENTER);
    	            columsView.setText(col);
    	            Log.d("Cols", String.format("%7s", col));
    	            tableRow.addView(columsView);                
    	            }
//    	         tableLayout.addView(tableRow,new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
    	        tableLayout.addView(tableRow);

    	    }
    	    
    	    tableLayout.requestLayout();
    		
////    		Working Model - 1 
////    		adapter = new VaccineDataBinder(this,vaccineList);	
////    		listview.setAdapter(adapter);
    	}
    }
    
//    /*
//     * Refresh the list from the UI thread 
//     * */
//    private void refreshadapter(final List<VaccineDetails> vacList){
//    	runOnUiThread(new Runnable() {
//			
//			public void run() {
//				adapter.refreshAdapter(vacList);
//			}
//		});
//    }
}
