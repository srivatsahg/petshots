package org.vatsag.database;

import java.util.List;

import org.vatsag.utils.Constants;
import org.vatsag.utils.DoctorDetails;
import org.vatsag.utils.PetDetail;
import org.vatsag.utils.Typefaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorDataBinder extends BaseAdapter {

	LayoutInflater inflater;
	ImageView thumb_image;
	List<DoctorDetails> doctorsList;
	DocViewHolder holder;
	String KEY_TAG = "[DoctorDataBinder]";
	Context context;
	Typeface typeface;
	
	/*
	 * Default constructor
	 * */
	public DoctorDataBinder(){
		
	}
	
	/*
	 * Constructor overload
	 * */
	public DoctorDataBinder(Activity act,List<DoctorDetails> docList){
		context = act.getApplicationContext();
		this.doctorsList = docList;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.typeface = Typefaces.get(act.getApplicationContext(), Constants.ROBOTA_FONTFILE);
	}
	
	public int getCount() {
		return doctorsList.size();
	}

	public Object getItem(int arg0) {
		return doctorsList.get(arg0);
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public synchronized void refreshAdapter(List<DoctorDetails> docsList) {
	   this.doctorsList = docsList;
	   notifyDataSetChanged();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(org.vatsag.R.layout.psdoctordetailrow, null);
	      holder = new DocViewHolder();
	      Log.i(KEY_TAG, "Before vet name access, convertView is null");
	      holder.tvDocName = (TextView)vi.findViewById(org.vatsag.R.id.tvnamevet);
	      Log.i(KEY_TAG, "Before vet email access, convertView is null");
	      holder.tvDocMail = (TextView)vi.findViewById(org.vatsag.R.id.tvemailvet);
	      Log.i(KEY_TAG, "Before vet call icon access, convertView is null");
	      holder.tvDoctorCall = (ImageView) vi.findViewById(org.vatsag.R.id.imgviewcall);
	      Log.i(KEY_TAG, "Successful retrieval,convertView is null");
	      vi.setTag(holder);
	      Log.i(KEY_TAG, "Tag set success, convertView is null !!");
	    }
	    else{
	    	
	    	holder = (DocViewHolder)vi.getTag();
	    }

	    Log.i(KEY_TAG, "Before Fullname access");
	      // Setting all values in listview
	      String docFullName = 	doctorsList.get(position).getM_title() + ". " +
	    		  				doctorsList.get(position).getM_firstname() + " " +
	    		  				doctorsList.get(position).getM_lastname();
	      
	      String docEmailId = doctorsList.get(position).getM_email();
	      final String phoneNumber = doctorsList.get(position).getM_phonePrimary().toString();
	      
	      holder.tvDocName.setText(docFullName);
	      holder.tvDocName.setTypeface(typeface);
	      Log.i(KEY_TAG, "Fullname set, success !!");
	      
	      holder.tvDocMail.setText(docEmailId);
	      holder.tvDocMail.setTypeface(typeface);
	      Log.i(KEY_TAG, "Email set, success !!");
	      
	      holder.tvDoctorCall.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_DIAL);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setData(Uri.parse("tel:" + phoneNumber));
				context.startActivity(i);
			}
		});

	      return vi;
	}
	
	/*
	 * Initial version shall just contain the image, name and primary phone
	 * 
	 * */
	static class DocViewHolder{
		
		ImageView tvDoctorCall;
		TextView tvDocName;
		TextView tvDocMail;
	}

}
