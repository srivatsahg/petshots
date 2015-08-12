package org.vatsag.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.vatsag.R;
import org.vatsag.database.DoctorDataBinder.DocViewHolder;
import org.vatsag.utils.Constants;
import org.vatsag.utils.DoctorDetails;
import org.vatsag.utils.PetDetail;
import org.vatsag.utils.Typefaces;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDataBinder extends BaseAdapter {

	LayoutInflater inflater;
//	ImageView petImageView;
	List<PetDetail> petsList;
	List<String> ageList;
	PetViewHolder holder;
	String KEY_TAG = "[PetDataBinder]";
	Typeface typeface;
    Context _ctxt;
	/*
	 * Default Constructor
	 * */
	public PetDataBinder(){
		
	}
	
	
	/*
	 * Constructor overload
	 * */
	public PetDataBinder(Activity act,List<PetDetail> petsList,List<String> ages){

		this.petsList = petsList;
        this.ageList = ages;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.typeface = Typefaces.get(act.getApplicationContext(), Constants.ROBOTA_FONTFILE);

        this._ctxt = act.getApplicationContext();
	}
	
	public int getCount() {
		return petsList.size();
	}

	/*
	 * Gets the selected pet relate information
	 * */
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return petsList.get(position);
	}

	/*
	 * 
	 * */
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * 
	 * */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(R.layout.pspetlistrow, null);
	      holder = new PetViewHolder();
	      Log.i(KEY_TAG, "Before pet name access, convertView is null");
	      holder.tvPetName = (TextView)vi.findViewById(org.vatsag.R.id.tvpetName);
	      Log.i(KEY_TAG, "Before pet sex access, convertView is null");
	      holder.tvPetSex = (TextView)vi.findViewById(org.vatsag.R.id.tvpetsex);
	      Log.i(KEY_TAG, "Before pet age access, convertView is null");
	      holder.tvPetAge = (TextView)vi.findViewById(org.vatsag.R.id.tvpetage);
	      
	      Log.i(KEY_TAG, "All information about the pet retrieved !!,convertView is null");
	      
	      vi.setTag(holder);
	      Log.i(KEY_TAG, "Tag set success, convertView is null !!");
	    }
	    else{
	    	
	    	holder = (PetViewHolder)vi.getTag();
	    }

	    Log.i(KEY_TAG, "Before Pet name access ");
	    holder.tvPetName.setText(petsList.get(position).getName());
	    holder.tvPetName.setTypeface(typeface);
	    Log.i(KEY_TAG, "Before Pet age access ");

        String strAge = _ctxt.getResources().getString(R.string.strAge);
        String currAge = ageList.get(position);
	    holder.tvPetAge.setText(strAge + " : "+  currAge);
	    holder.tvPetAge.setTypeface(typeface);
	    Log.i(KEY_TAG, "Before Pet sex access ");
	    holder.tvPetSex.setText(petsList.get(position).getSex());
	    holder.tvPetSex.setTypeface(typeface);
	    return vi;
	}

	/*
	 * 
	 * */
	public synchronized void refreshAdapter(List<PetDetail> petsList) {
		this.petsList = petsList;
		notifyDataSetChanged();
	}
	
	/*
	 * Information about the pet
	 * Name
	 * DOB
	 * Image - [next version]
	 * Age
	 * Sex
	 * Microchipid
	 * Registration number
	 * */
	public static class PetViewHolder{
		
		TextView tvPetName;
		TextView tvPetDob;
		TextView tvPetChipId;
		TextView tvPetAge;
		TextView tvPetSex;
		TextView tvPetRegId;
//		ImageView petImage;
	}
	
}
