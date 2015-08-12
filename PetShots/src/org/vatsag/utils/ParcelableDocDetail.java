
/*
 * Author		:	Srivatsa Haridas
 * Date			:	November 6th 2013
 * Description	:	Parcelable Doctor detail class implementation
 * */
package org.vatsag.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableDocDetail implements Parcelable {

	private DoctorDetails docdetail;
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	public DoctorDetails getDocdetail() {
		return docdetail;
	}

	public ParcelableDocDetail(DoctorDetails docdetail){
		super();
		this.docdetail = docdetail;
	}
	
	
	private ParcelableDocDetail(Parcel in){
		
		docdetail = new DoctorDetails();
		docdetail.setM_id(in.readLong());
		docdetail.setM_title(in.readString());
		docdetail.setM_firstname(in.readString());
		docdetail.setM_lastname(in.readString());
		docdetail.setM_email(in.readString());
		docdetail.setM_phonePrimary(in.readString());
		docdetail.setM_phoneSecondary(in.readString());
		docdetail.setM_address(in.readString());
		
	}
	
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(docdetail.getM_id());
		dest.writeString(docdetail.getM_title());
		dest.writeString(docdetail.getM_firstname());
		dest.writeString(docdetail.getM_lastname());
		dest.writeString(docdetail.getM_email());
		dest.writeString(docdetail.getM_phonePrimary());
		dest.writeString(docdetail.getM_phoneSecondary());
		dest.writeString(docdetail.getM_address());
		
	}
	
	
	public static final Parcelable.Creator<ParcelableDocDetail> CREATOR = new Creator<ParcelableDocDetail>() {
		
		public ParcelableDocDetail[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ParcelableDocDetail[size];
		}
		
		public ParcelableDocDetail createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ParcelableDocDetail(source);
		}
	};
	
}
