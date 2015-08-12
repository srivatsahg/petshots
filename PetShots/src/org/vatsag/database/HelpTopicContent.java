package org.vatsag.database;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import org.vatsag.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * User: Srivatsa Haridas
 * Date: 11/20/13
 * Time: 1:51 PM
 * Desc: Stores the help topics
 */
public class HelpTopicContent {

    private SparseArray<LinkedHashMap<Integer,LinkedHashMap<String,String>>> masterHelpList;
    private Context context;
    private final String LOG_CLASS = "[HelpTopicContent]";
    private static HelpTopicContent sHelpInstance = null;

    public static HelpTopicContent getInstance(Context context) {
        if (sHelpInstance == null) {
            sHelpInstance = new HelpTopicContent(context.getApplicationContext());
        }
        return sHelpInstance;
    }

    /*
    * Default constructor
    * */
    private HelpTopicContent(Context context){

        this.context = context;
        masterHelpList = new SparseArray<LinkedHashMap<Integer, LinkedHashMap<String, String>>>();
        BuildHelpTopics();
    }

    /*
  *   completeTopicsList
  *   |
  *   vetHelpNode
  *       |
  *       ---> childNode(help topic), childNodeDesc(complete description)
  *   |
  *   petHelpNode
  *       |
  *       ---> childNode(help topic), childNodeDesc(complete description)
  *   |
  *   otherHelpNode
  *       |
  *       ---> childNode(help topic), childNodeDesc(complete description)
   *
  * */
    private void BuildHelpTopics(){


        /*
        * Create Sub topics section
        * */

        //  1.  Doctor
        LinkedHashMap<Integer,LinkedHashMap<String,String>> subTopicDoctor = createSubTopicsDocSection();
        //  2.  Pet
        LinkedHashMap<Integer,LinkedHashMap<String,String>> subTopicPet = createSubTopicsPetSection();
        //  3.  Vaccination Information
        LinkedHashMap<Integer,LinkedHashMap<String,String>> subTopicVaccination = createSubTopicsVaccineSection();
        //4.    Others
        LinkedHashMap<Integer,LinkedHashMap<String,String>> subTopicOthers = createSubTopicsOtherSection();

        /*
        * Integer - shall represent groupPositionID
        * */

        masterHelpList.append(0,subTopicVaccination);
        masterHelpList.append(1,subTopicDoctor);
        masterHelpList.append(2,subTopicPet);
        masterHelpList.append(3,subTopicOthers);

    }


    /*
    * Other general help topics
    * */
    private LinkedHashMap<Integer,LinkedHashMap<String,String>> createSubTopicsOtherSection() {

        LinkedHashMap<String,String> mapOther = new LinkedHashMap<String, String>();

        /*
        * Integer - shall represent ChildPositionID
        * */
        LinkedHashMap<Integer, LinkedHashMap<String, String>> mapCollection = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();

        String topicVaccineDetail =    context.getResources().getString(R.string.help_string_vaccine_add_detail);
        String topicVaccineDetailDesc =    context.getResources().getString(R.string.help_string_vaccine_add_desc);

        String topicCalendarEntry =    context.getResources().getString(R.string.help_string_vaccine_calendar);
        String topicCalendarAddEvent =    context.getResources().getString(R.string.help_string_vaccine_calendar_add_event);
        String topicCallDefaultVet =    context.getResources().getString(R.string.help_string_call_default_vet);
        String topicSearchClinic =    context.getResources().getString(R.string.help_string_search_vet);


        /*First pet subtopic*/
        mapOther.put(topicVaccineDetail,topicVaccineDetailDesc);
        mapCollection.put(0,mapOther);

        /*Second pet subtopic*/
        mapOther.put(topicCalendarEntry,topicCalendarAddEvent);
        mapCollection.put(1,mapOther);

        /*  3.  */
        mapOther.put(topicCallDefaultVet,topicCallDefaultVet);
        mapCollection.put(2,mapOther);

        mapOther.put(topicSearchClinic,topicSearchClinic);
        mapCollection.put(3,mapOther);

        for(String str : mapOther.values()){

            Log.i(LOG_CLASS, str);
        }


        return mapCollection;
    }


    /*
    * Help topics on Vaccination
    * */
    private LinkedHashMap<Integer,LinkedHashMap<String,String>> createSubTopicsVaccineSection() {

        LinkedHashMap<String,String> mapVaccination = new LinkedHashMap<String, String>();

        /*
        * Integer - shall represent ChildPositionID
        * */
        LinkedHashMap<Integer, LinkedHashMap<String, String>> mapCollection = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();

        /*
        * Vaccination Guidelines as specified by
        * The VACCINATION GUIDELINES GROUP (VGG) OF THE WORLD SMALL ANIMAL VETERINARY ASSOCIATION (WSAVA)
        * */
        String topicVaccineGuidelinesTitle  =    context.getResources().getString(R.string.help_string_vaccine_guidelines);
        String topicVaccineGuidelines  =    context.getResources().getString(R.string.help_string_vaccine_guidelines_desc);
         /*
        * Pup vaccination guidelines
        * */
        String topicvaccinepuppies  =    context.getResources().getString(R.string.help_string_vaccine_pup);
        String topicvaccinepupdesc  =    context.getResources().getString(R.string.help_string_vaccine_pup_desc);
        /*
        * Kitten vaccination guidelines
        * */
        String topicvaccinekitten  =    context.getResources().getString(R.string.help_string_vaccine_kitten);
        String topicvaccinekittendesc  =    context.getResources().getString(R.string.help_string_vaccine_kitten_desc);

        /*
        * Vaccine Guidelines
        * */
        mapVaccination.put(topicVaccineGuidelinesTitle,topicVaccineGuidelines);
        mapCollection.put(0,mapVaccination);

         /* Pup vaccination subtopic*/
        mapVaccination.put(topicvaccinepuppies,topicvaccinepupdesc);
        mapCollection.put(1,mapVaccination);

        /* Feline kitten vaccination subtopic  */
        mapVaccination.put(topicvaccinekitten,topicvaccinekittendesc);
        mapCollection.put(2,mapVaccination);

        for(String str : mapVaccination.values()){

            Log.i(LOG_CLASS, str);
        }


        return mapCollection;
    }

    /*
    * Help topics related to Pet information
    * */
    private LinkedHashMap<Integer,LinkedHashMap<String,String>> createSubTopicsPetSection() {

        LinkedHashMap<String,String> mapPet = new LinkedHashMap<String, String>();
        LinkedHashMap<Integer, LinkedHashMap<String, String>> mapCollection = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();

        /*First pet subtopic*/
//        HashMap<String,String> mapPet = new HashMap<String, String>();
        mapPet.put(context.getResources().getString(R.string.help_string_pet_add_detail),context.getResources().getString(R.string.help_string_pet_add_detail_desc));
        mapCollection.put(0,mapPet);

        /*Second pet subtopic*/
//        HashMap<Integer, HashMap<String, String>> mapCollection = new HashMap<Integer, HashMap<String, String>>();
        //HashMap<String,String> mapPet = new HashMap<String, String>();
        mapPet.put(context.getResources().getString(R.string.help_string_pet_edit_detail),context.getResources().getString(R.string.help_string_pet_edit_detail_desc));
        mapCollection.put(1,mapPet);


        /*  3.  */
//        HashMap<Integer, HashMap<String, String>> mapCollection = new HashMap<Integer, HashMap<String, String>>();
        //HashMap<String,String> mapPet = new HashMap<String, String>();
        mapPet.put(context.getResources().getString(R.string.help_string_pet_vaccine_detail),context.getResources().getString(R.string.help_string_pet_vaccine_detail_desc));
        mapCollection.put(2,mapPet);

        for(String str : mapPet.values()){

            Log.i(LOG_CLASS, str);
        }

        return mapCollection;
    }


    /*
    * Help topics related to Pet information
    * */
    private LinkedHashMap<Integer,LinkedHashMap<String,String>> createSubTopicsDocSection() {

        LinkedHashMap<String,String> mapDoc = new LinkedHashMap<String, String>();
        LinkedHashMap<Integer, LinkedHashMap<String, String>> mapCollection = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();

        /*First pet subtopic*/
        mapDoc.put(context.getResources().getString(R.string.help_string_doc_add_detail), context.getResources().getString(R.string.help_string_doc_add_desc));
        mapCollection.put(0,mapDoc);

        /*Second pet subtopic*/
        mapDoc.put(context.getResources().getString(R.string.help_string_doc_edit_detail),context.getResources().getString(R.string.help_string_doc_edit_desc));
        mapCollection.put(1,mapDoc);

        /*  3.  */
        mapDoc.put(context.getResources().getString(R.string.help_string_doc_phone_call),context.getResources().getString(R.string.help_string_doc_phone_call_desc));
        mapCollection.put(2,mapDoc);

        for(String str : mapDoc.values()){

            Log.i(LOG_CLASS, str);
        }

        return mapCollection;
    }


    /*
    * Show the complete help description
    * */
    public LinkedHashMap<String, String> ShowHelpDescription(int groupPosition, int childPosition){

        Log.i(LOG_CLASS,"<<---- Requested topic for GroupID : " + String.valueOf(groupPosition) + " and ChildID : " + String.valueOf(childPosition) + " ---->>");

        LinkedHashMap<String,String> subTopicDescription = null;

        if(masterHelpList != null){

            LinkedHashMap<Integer,LinkedHashMap<String,String>>  subTopics = masterHelpList.get(groupPosition,null);
            if(subTopics != null){
                if(subTopics.containsKey(childPosition)){
                    /*Child key is present*/
                    subTopicDescription = subTopics.get(childPosition);
                }
            }
        }

        for(String msgLog : subTopicDescription.values()){
            Log.i(LOG_CLASS,"<<---- Selected topic : " + msgLog + " ---->>");
        }
        return subTopicDescription;
    }

    /*
    * Show the complete help description
    * */
    public ArrayList<String> ShowHelpDesc(int groupPosition, int childPosition){

        Log.i(LOG_CLASS,"<<---- Requested topic for GroupID : " + String.valueOf(groupPosition) + " and ChildID : " + String.valueOf(childPosition) + " ---->>");

        LinkedHashMap<String,String> subTopicDescription = null;
        ArrayList<String> subTopicData = null;

        if(masterHelpList != null){

            LinkedHashMap<Integer,LinkedHashMap<String,String>>  subTopics = masterHelpList.get(groupPosition,null);
            if(subTopics != null){
                /*Child key is present*/
                    subTopicDescription = subTopics.get(childPosition);
                    String stringTitle = new ArrayList<String>(subTopicDescription.values()).get(childPosition);
                    String stringDesc = new ArrayList<String>(subTopicDescription.keySet()).get(childPosition);
                    subTopicData = new ArrayList<String>();
                    subTopicData.add(stringTitle);
                    subTopicData.add(stringDesc);


                /*
                * Log the items
                * */
                for(String msgLog : subTopicData){
                    Log.i(LOG_CLASS,"<<---- Selected topic using Arraylists : " + msgLog + " ---->>");
                }

             }
        }

        return subTopicData;
    }

}
