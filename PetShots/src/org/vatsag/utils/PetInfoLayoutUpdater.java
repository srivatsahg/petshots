package org.vatsag.utils;

import org.vatsag.R;

import android.app.Activity;

public class PetInfoLayoutUpdater extends LayoutUpdater{

	private Activity activity;
	
	/*	Constructor */
	public PetInfoLayoutUpdater(Activity activity){
		/*	Just calls the constructor of the superclass */
		super(activity);
		this.activity = activity;
	}
	
	public void updateLayout(){
		new HeaderLayoutUpdate(activity, activity.getResources().getString(R.string.strPetdetails), R.drawable.trip_analyzer_title);
		SharedPrefsManager mPrefs = new SharedPrefsManager(activity,Constants.UI_Pet+Constants.UIUPDATE);
		mPrefs.EditPrefs(Constants.UI_Pet+ Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.
	}
}
