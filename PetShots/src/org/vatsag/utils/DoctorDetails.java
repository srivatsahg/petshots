package org.vatsag.utils;

/*
 * Doctor 
 * "c_firstname","c_lastname","c_email","c_phoneprimary","c_phonesecondary","c_address"
 * */
public class DoctorDetails {

	String m_title;
	String m_firstname;
	String m_lastname;
	String m_email;
	String m_phonePrimary;
	String m_phoneSecondary;
	String m_address;
	
	long m_id;
	
	public String getM_title() {
		return m_title;
	}

	public void setM_title(String m_title) {
		this.m_title = m_title;
	}

	public String getM_firstname() {
		return m_firstname;
	}

	public void setM_firstname(String m_firstname) {
		this.m_firstname = m_firstname;
	}

	public String getM_lastname() {
		return m_lastname;
	}

	public void setM_lastname(String m_lastname) {
		this.m_lastname = m_lastname;
	}

	public String getM_email() {
		return m_email;
	}

	public void setM_email(String m_email) {
		this.m_email = m_email;
	}

	public String getM_phonePrimary() {
		return m_phonePrimary;
	}

	public void setM_phonePrimary(String m_phonePrimary) {
		this.m_phonePrimary = m_phonePrimary;
	}

	public String getM_phoneSecondary() {
		return m_phoneSecondary;
	}

	public void setM_phoneSecondary(String m_phoneSecondary) {
		this.m_phoneSecondary = m_phoneSecondary;
	}

	public String getM_address() {
		return m_address;
	}

	public void setM_address(String m_address) {
		this.m_address = m_address;
	}
	
	public long getM_id() {
		return m_id;
	}

	public void setM_id(long m_id) {
		this.m_id = m_id;
	}

	/*
	 * Constructor
	 * */
	public DoctorDetails(long id,String title, String firstname,String lastname,
				 	String email,String phonePrimary,String phoneSecondary,String address){
		
		this.m_title = title;
		this.m_firstname = firstname;
		this.m_lastname = lastname;
		this.m_email = email;
		this.m_phonePrimary = phonePrimary;
		this.m_phoneSecondary = phoneSecondary;
		this.m_address = address;
		this.m_id = id;
	}

	/*
	 * default constructor
	 * */
	public DoctorDetails(){
		
	}
	
	
	/*
	 * returns 
	 * for ex : Dr. Srivatsa Haridas  or
	 * Mr.	Srivatsa Haridas
	 * */
	@Override
	public String toString(){
		return m_title + ". " + m_firstname + " " + m_lastname;  
	}
}
