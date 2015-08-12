package org.vatsag.petshots;

import java.util.ArrayList;

import org.vatsag.R;
import org.vatsag.utils.CListItem;
import org.vatsag.utils.Typefaces;


import org.vatsag.utils.Constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	Custom adapter for gridview
 * */
public class CustomGridViewAdapter extends ArrayAdapter<CListItem>{
	
	static final String KEY_IMAGE = "image";
	static final String KEY_TITLE = "title";
	Context ctxt;
	int layoutresourceid;
	ArrayList<CListItem> data;
	Typeface typeface;
	
	public CustomGridViewAdapter(Context context, int layResId,
			ArrayList<CListItem> data) {
		super(context, layResId, data);
		this.layoutresourceid =	layResId;
		this.ctxt = context;
		this.data = data;
		this.typeface = Typefaces.get(ctxt, Constants.ROBOTA_FONTFILE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		RecordHolder holder = null;
		
		if(row == null){
			
			LayoutInflater inflater = ((Activity)ctxt).getLayoutInflater();
			row = inflater.inflate(layoutresourceid,parent,false);
			
			holder = new RecordHolder();
			holder.tvImage = (ImageView) row.findViewById(R.id.item_image);
			holder.tvOption = (TextView) row.findViewById(R.id.item_text);
			row.setTag(holder);
					
		}
		else
		{
			holder = (RecordHolder)row.getTag();
		}
		
		/*
		 * Gets CListItem row  
		 * */
		holder.tvOption.setText(((CListItem)getItem(position)).getItemText());
		holder.tvOption.setTypeface(typeface);
		holder.tvImage.setImageBitmap(((CListItem)getItem(position)).getImage());
		
		return row;
	}
	/*
	 * Dashboard Menu item (image with text)
	 * */
	static class RecordHolder{
		
		TextView tvOption;
		ImageView tvImage;
	}
	
}
