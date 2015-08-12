
/*
 * Author		:	Srivatsa Haridas
 * Date			:	November 6th 2013
 * Description	:	Parcelable Petdetail class implementation
 * */
package org.vatsag.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelablePetDetail implements Parcelable{

	private PetDetail petdetail;

	public PetDetail getPetdetail() {
		return petdetail;
	}
	
	public ParcelablePetDetail(PetDetail pd) {
		super();
		this.petdetail = pd;
	}
	
	private ParcelablePetDetail(Parcel in){
		petdetail = new PetDetail();
		petdetail.setP_id(in.readLong());
		petdetail.setName(in.readString());
		petdetail.setDob(in.readString());
		petdetail.setAge(in.readString());
		petdetail.setSex(in.readString());
		petdetail.setMchip(in.readString());
		petdetail.setRegno(in.readString());
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Actual object serialization / flattening happens inside this method.
	 * Individually parcel each properties of the object
	 * */
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(petdetail.getP_id());
		dest.writeString(petdetail.getName());
		dest.writeString(petdetail.getDob());
		dest.writeString(petdetail.getAge());
		dest.writeString(petdetail.getSex());
		dest.writeString(petdetail.getMchip());
		dest.writeString(petdetail.getRegno());
		
	}
	
	public static final Parcelable.Creator<ParcelablePetDetail> CREATOR = new Creator<ParcelablePetDetail>() {
		
		public ParcelablePetDetail[] newArray(int size) {

			return new ParcelablePetDetail[size];
		}
		
		public ParcelablePetDetail createFromParcel(Parcel source) {

			return new ParcelablePetDetail(source);
		}
	};

}
