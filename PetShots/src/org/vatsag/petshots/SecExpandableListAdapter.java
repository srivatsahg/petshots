package org.vatsag.petshots;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;
import org.vatsag.R;
import org.vatsag.utils.Constants;
import org.vatsag.utils.HelpGroup;
import org.vatsag.utils.Typefaces;

public class SecExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private LayoutInflater inflater;

	/*	Logger */
	private static final String LOG_CLASS = "[SecExpandableListAdapter]";
	/*	Font	*/
	private Typeface typeface;

	/*
	 * Constructor
	 * */
	public SecExpandableListAdapter(Activity act) {
	    activity = act;
	    inflater = act.getLayoutInflater();
	    typeface = Typefaces.get(activity.getApplicationContext(), Constants.ROBOTA_FONTFILE);
	  }
	

	public Object getChild(int groupPosition, int childposition) {

        return childposition;
//		 return this.groups.get(groupPosition).children.get(childposition);
	}


	public long getChildId(int groupPosition, int childposition) {
		Log.i(LOG_CLASS, "<<---- getChildId : " + String.valueOf(childposition) + "---->>");
		return childposition;
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
            text.setTextColor(Color.BLACK);
            text.setBackgroundColor(Color.WHITE);
		    text.setTypeface(typeface);

		    return text;

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
//		Log.i(LOG_CLASS, "<<---- getChildrenCount : " + String.valueOf(groups.get(groupPosition).children.size()) + "---->>");
		return 1;
	}

	
	public Object getGroup(int groupPosition) {
//		return this.groups.get(groupPosition);
        return groupPosition;
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
//		  return this.groups.size();
        return 1;
	}


	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		Log.i(LOG_CLASS, "<<---- getGroupId : " + String.valueOf(groupPosition) + "---->>");
		 return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		Log.i(LOG_CLASS, "<<---- getGroupView entered ---->>");
		
//		 if (convertView == null) {
//		      convertView = inflater.inflate(R.layout.pshelplistgroup, null);
//		    }
//		    HelpGroup group = (HelpGroup) getGroup(groupPosition);
//		    ((CheckedTextView) convertView).setText(group.string);
//		    ((CheckedTextView) convertView).setTypeface(typeface);
//		    ((CheckedTextView) convertView).setChecked(isExpanded);
//		    return convertView;

//        final String firstChild = (String) getGroup(groupPosition);

        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pshelplistitem, null);
        }
        text = (TextView) convertView.findViewById(R.id.textViewListContent);
        text.setText(String.valueOf(groupPosition));
        text.setTypeface(typeface);

//        convertView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Toast.makeText(activity, "Group ID : " + String.valueOf(groupPosition) +  " Child position :  " + String.valueOf(childPosition),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        return text;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
