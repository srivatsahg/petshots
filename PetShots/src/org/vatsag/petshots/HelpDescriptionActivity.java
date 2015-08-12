package org.vatsag.petshots;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;
import org.vatsag.R;
import org.vatsag.database.HelpTopicContent;
import org.vatsag.utils.*;

import java.util.*;

/**
 * User: Srivatsa Haridas
 * Date: 11/19/13
 * Time: 2:50 PM
 * Desc:
 */
public class HelpDescriptionActivity extends Activity {

    LayoutUpdater layoutupdater;
    int groupPosition,childPosition;
    private HelpTopicContent helpTopics;
    LinkedHashMap<String,String> topicDescription;
    List<String> topicDesc;
    SparseArray<HashMap<Integer,HashMap<String,String>>> masterHelpList = new SparseArray<HashMap<Integer, HashMap<String, String>>>();
    SparseArray<List<Map<String,String>>> completeTopicsList = new SparseArray<List<Map<String, String>>>();
    private String strHelpTopicTitle,strHelpDescription;
    private final static String LOG_CLASS = "[HelpDescriptionActivity]";

    TextView tvTitle,tvDescription;
    Typeface typeface;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.        pshelpdescription);

        tvTitle = (TextView)findViewById(R.id.tvHelpDescTitle);
        tvDescription = (TextView) findViewById(R.id.tvHelpDescContent);

        Log.i(LOG_CLASS,"<<---- Layout controls loaded successfully !! ---->>");

        /*
        * Create an instance of the help topics provider class
        * */
        helpTopics = HelpTopicContent.getInstance(this);


        /*
        * Font file
        * */

        this.typeface = Typefaces.get(this.getApplicationContext(), Constants.ROBOTA_FONTFILE);
        Log.i(LOG_CLASS,"<<---- Typeface loaded successfully !! ---->>");


        /*
        * Get the data from the invoking activity
        * */
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            this.groupPosition =  extras.getInt("groupid",0);
            this.childPosition =  extras.getInt("childid",0);
            Log.i(LOG_CLASS,"<<---- GroupId : " + String.valueOf(groupPosition) + " ChildId : " + String.valueOf(childPosition) + " ---->>");
        }

        layoutupdater = new HelpWindowLayoutUpdater(this);
        layoutupdater.updateLayout();
        Log.i(LOG_CLASS,"<<---- update Layout success !! ---->>");

        /*
        * Access the help topic from the Help file
        * */

        topicDesc = helpTopics.ShowHelpDesc(groupPosition,childPosition);

        if(topicDesc != null){

            strHelpTopicTitle = topicDesc.get(1);
            strHelpDescription = topicDesc.get(0);

            /*
            * Title heading
            * */
            tvTitle.setText(strHelpTopicTitle);
            tvTitle.setTypeface(typeface,Typeface.BOLD);

            /*
            * Help Description
            * */
            tvDescription.setText(strHelpDescription);
            tvDescription.setTypeface(typeface);
         }
        else
        {
        tvTitle.setText(getBaseContext().getResources().getString(R.string.err_help_title_not_found));
        tvTitle.setTypeface(typeface);
        tvDescription.setText(getBaseContext().getResources().getString(R.string.err_help_topic_not_found));
        tvDescription.setTypeface(typeface);
        Log.e(LOG_CLASS,"No help topic present for the selected topic");
    }
    }

}