package org.vatsag.petshots;

import org.vatsag.R;
import org.vatsag.R.layout;
import org.vatsag.R.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class ActionBarActivity extends SherlockActivity {

	  // Action bar
    private ActionBar mActionBar;
    private LayoutInflater mInflater;
    private View mCustomView;
    private TextView mTitleTextView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        initializeActionBar();
    }

    private void initializeActionBar() {
    	mActionBar = getSupportActionBar();
    	Log.i("Actionbar", mActionBar.toString());
    	mActionBar.setDisplayShowHomeEnabled(false);
    	mActionBar.setDisplayShowTitleEnabled(false);
    	mInflater = LayoutInflater.from(this);
    	mCustomView = mInflater.inflate(R.layout.psabsactiontext, null);
    	mTitleTextView = (TextView)findViewById(R.id.title_text);
    	mTitleTextView.setText("Pet Shots");
    	mActionBar.setCustomView(mCustomView);
    	mActionBar.setDisplayShowCustomEnabled(true);
    	
	}
	
}
