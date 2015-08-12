
package org.vatsag.petshots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.Toast;
import org.vatsag.R;
import org.vatsag.R.layout;
import org.vatsag.R.menu;
import org.vatsag.utils.HelpGroup;
import org.vatsag.utils.HelpWindowLayoutUpdater;
import org.vatsag.utils.LayoutUpdater;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.ExpandableListView;

public class HelpTopicActivity extends Activity {

	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    LayoutUpdater layoutupdater;
    SparseArray<HelpGroup> groups = new SparseArray<HelpGroup>();
    private static final String LOG_CLASS = "[HelpTopicActivity]";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pshelpwindow);
        
        layoutupdater = new HelpWindowLayoutUpdater(this);
        layoutupdater.updateLayout();
        
        /*	Get the listview object reference from the layout file*/
        expListView = (ExpandableListView) findViewById(R.id.explistviewhelp);
        
        
//        prepareHelpTopics();
        
        createHelpTopics();
        
        Log.i(LOG_CLASS, "<<---- Compilation of help topics success !! ---->>");
        
        listAdapter = new ExpandableListAdapter(this,groups);
        
        Log.i(LOG_CLASS,"<<---- Before setadapter ---->>");
        
        expListView.setAdapter(listAdapter);
    }


    /*
     * Creation of Help topics 
     * */
    private void createHelpTopics(){

        /*
        * Vaccination Guidelines topic
        * */
        HelpGroup vaccinationgroup = new HelpGroup(this.getResources().getString(R.string.help_string_vaccine_page));
        vaccinationgroup.children.add(this.getResources().getString(R.string.help_string_vaccine_guidelines));
        vaccinationgroup.children.add(this.getResources().getString(R.string.help_string_vaccine_pup));
        vaccinationgroup.children.add(this.getResources().getString(R.string.help_string_vaccine_kitten));
        groups.append(0, vaccinationgroup);

        /*
        * Doctor page related topics
        * */
        HelpGroup docgroup = new HelpGroup(this.getResources().getString(R.string.help_string_doc_page));
        docgroup.children.add(this.getResources().getString(R.string.help_string_doc_add_detail));
        docgroup.children.add(this.getResources().getString(R.string.help_string_doc_edit_detail));
        docgroup.children.add(this.getResources().getString(R.string.help_string_doc_phone_call));
        groups.append(1, docgroup);

        /*
        * Pet related topics
        * */
        HelpGroup petgroup = new HelpGroup(this.getResources().getString(R.string.help_string_pet_page));
        petgroup.children.add(this.getResources().getString(R.string.help_string_pet_add_detail));
        petgroup.children.add(this.getResources().getString(R.string.help_string_pet_edit_detail));
        petgroup.children.add(this.getResources().getString(R.string.help_string_pet_vaccine_detail));

        groups.append(2, petgroup);

        /*
        * Miscellaneous topics
        * */
        HelpGroup othersgroup = new HelpGroup(this.getResources().getString(R.string.help_string_other_page));
        othersgroup.children.add(this.getResources().getString(R.string.help_string_vaccine_add_detail));
        othersgroup.children.add(this.getResources().getString(R.string.help_string_vaccine_calendar));
        othersgroup.children.add(this.getResources().getString(R.string.help_string_call_default_vet));
        othersgroup.children.add(this.getResources().getString(R.string.help_string_search_vet));

        groups.append(3, othersgroup);
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
}
