package org.vatsag.petshots;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import org.vatsag.R;
import org.vatsag.utils.Constants;
import org.vatsag.utils.CustomizedExpandableListView;
import org.vatsag.utils.HelpGroup;
import org.vatsag.utils.Typefaces;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private final SparseArray<HelpGroup> groups;
	private LayoutInflater inflater;
	/*	Logger */
	private static final String LOG_CLASS = "[ExpandableListAdapter]";
	/*	Font	*/
	private Typeface typeface;
	
	/*
	 * Constructor 
	 * */
	public ExpandableListAdapter(Activity act, SparseArray<HelpGroup> groups) {
	    activity = act;
	    this.groups = groups;
	    inflater = act.getLayoutInflater();
	    typeface = Typefaces.get(activity.getApplicationContext(), Constants.ROBOTA_FONTFILE);
	  }
	

	public Object getChild(int groupPosition, int childposition) {
		 return this.groups.get(groupPosition).children.get(childposition);
	}


	public long getChildId(int groupPosition, int childposition) {
		Log.i(LOG_CLASS, "<<---- getChildId : " + String.valueOf(childposition) + "---->>");
		return 0;
	}
	
	 public View getChildView(final int groupPosition, final int childPosition,
		     boolean isLastChild, View convertView, ViewGroup parent) {
		    final String children = (String) getChild(groupPosition, childPosition);

		    TextView text = null;
		    if (convertView == null) {
		      convertView = inflater.inflate(R.layout.pshelplistitem, null);
		    }
		    text = (TextView) convertView.findViewById(R.id.textViewListContent);
		    text.setText(children);
		    text.setTypeface(typeface);

             convertView.setOnClickListener(new OnClickListener() {
                 public void onClick(View v) {
                     Intent intent = new Intent(activity.getApplicationContext(),HelpDescriptionActivity.class);
                     intent.putExtra("groupid",groupPosition);
                     intent.putExtra("childid",childPosition);
                     activity.startActivity(intent);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 }
             });

		    return convertView;

		  }
	 
	@Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupCollapsed(groupPosition);
	}
	
	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupExpanded(groupPosition);
	}
	

	public int getChildrenCount(int groupPosition) {
		Log.i(LOG_CLASS, "<<---- getChildrenCount : " + String.valueOf(groups.get(groupPosition).children.size()) + "---->>");
		return groups.get(groupPosition).children.size();
	}

	
	public Object getGroup(int groupPosition) {
		return this.groups.get(groupPosition);
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		  return this.groups.size();
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		Log.i(LOG_CLASS, "<<---- getGroupId : " + String.valueOf(groupPosition) + "---->>");
		 return 0;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		Log.i(LOG_CLASS, "<<---- getGroupView entered ---->>");
		
		 if (convertView == null) {
		      convertView = inflater.inflate(R.layout.pshelplistgroup, null);
		    }
		    HelpGroup group = (HelpGroup) getGroup(groupPosition);
		    ((CheckedTextView) convertView).setText(group.string);
		    ((CheckedTextView) convertView).setTypeface(typeface);
		    ((CheckedTextView) convertView).setChecked(isExpanded);
		    return convertView;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
