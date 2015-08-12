package org.vatsag.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Pet details
 * "p_name","p_dob","p_sex","p_age","p_mcid","p_regno"
 * */
public class PetDetail {
	
	String name;
	String dob;
	String sex;
	String age;
	String mchip;
	String regno;
	long p_id;
    String LOG_CLASS = "[PetDetail]";
	
	public long getP_id() {
		return p_id;
	}

	/*
	 * Default constructor 
	 * */
	public PetDetail(){
		
	}
	
	/*
	 * Constructor
	 * */
	public PetDetail(long id , String m_name,String m_dob, String m_sex,String m_age, String m_mchip, String m_regno){
		this.p_id = id;
		this.name = m_name;
		this.dob = m_dob;
		this.age = m_age;
		this.sex = m_sex;
		this.mchip = m_mchip;
		this.regno = m_regno;
	}


	public void setP_id(long p_id) {
		this.p_id = p_id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
        return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMchip() {
		return mchip;
	}

	public void setMchip(String mchip) {
		this.mchip = mchip;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	@Override
	public String toString() {
		return name;
	}
}
