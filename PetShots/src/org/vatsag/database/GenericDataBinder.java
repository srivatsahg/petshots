package org.vatsag.database;

import java.util.List;

import org.vatsag.database.PetDataBinder.PetViewHolder;
import org.vatsag.utils.Constants;
import org.vatsag.utils.PetDetail;
import org.vatsag.utils.Typefaces;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GenericDataBinder<T> extends BaseAdapter {
	
	LayoutInflater inflater;
	List<T> genericData;
	GenericViewHolder holder;
	String KEY_TAG = "[GenericDataBinder]";
	Context ctxt;
	Typeface typeface;
	
	/*
	 * Default Constructor
	 * */
	public GenericDataBinder(){
		
	}
	
	/*
	 * Constructor overload
	 * */
	public GenericDataBinder(Activity act,List<T> somelist){
		this.genericData = somelist;
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/*
	 * Constructor overload
	 * */
	public GenericDataBinder(Activity act,List<T> somelist,Context ctxt){
		this.genericData = somelist;
		this.ctxt = ctxt;
		inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.typeface = Typefaces.get(ctxt, Constants.ROBOTA_FONTFILE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return genericData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return genericData.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		
	    if(convertView==null){
	     
	      vi = inflater.inflate(org.vatsag.R.layout.psgenericdata, null);
	      holder = new GenericViewHolder();
	      Log.i(KEY_TAG, "Before pet name access, convertView is null");
	      
	      holder.tvName = (TextView)vi.findViewById(org.vatsag.R.id.tvGenericName);
	      vi.setTag(holder);
	      
	      Log.i(KEY_TAG, "Tag set success, convertView is null !!");
	    }
	    else{
	    	
	    	holder = (GenericViewHolder)vi.getTag();
	    }

	    Log.i(KEY_TAG, "Before generic name access ");
	    holder.tvName.setText(genericData.get(position).toString());
	    holder.tvName.setTypeface(typeface);
	    return vi;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = super.getDropDownView(position, convertView, parent);
		v.setBackgroundResource(R.drawable.spinner_dropdown_background);
			((TextView) v).setTextColor(ctxt.getResources().getColorStateList(R.color.black));
		 	((TextView) v).setGravity(Gravity.CENTER);
		 	return v;
	}
	
	/*
	 * Information about the pet
	 * Name
	 * Image - [next version]
	 * */
	public static class GenericViewHolder{
		TextView tvName;
	}
}
