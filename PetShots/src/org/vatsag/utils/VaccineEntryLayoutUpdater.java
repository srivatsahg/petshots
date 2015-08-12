package org.vatsag.utils;

import org.vatsag.R;

import android.app.Activity;

public class VaccineEntryLayoutUpdater extends LayoutUpdater {
	
	private Activity activity;
	
	public VaccineEntryLayoutUpdater(Activity activity) {
		/*	Just calls the constructor of the superclass */
		super(activity);
		this.activity = activity;
	}

	@Override
	public void updateLayout() {
		new HeaderLayoutUpdate(activity, activity.getResources().getString(R.string.strVaccineData), R.drawable.trip_analyzer_title);
		SharedPrefsManager mPrefs = new SharedPrefsManager(activity,Constants.UI_VaccineEntry+Constants.UIUPDATE);
		mPrefs.EditPrefs(Constants.UI_VaccineEntry+ Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.
	}

}
