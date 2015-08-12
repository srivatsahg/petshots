package org.vatsag.utils;

import org.vatsag.R;

import android.app.Activity;

/*
 * Abstract layout updater
 * */
public abstract class LayoutUpdater {
	
private Activity activity;
	
	public LayoutUpdater(Activity activity){
	
		this.activity = activity;
	}

	public abstract void updateLayout();
}
