package org.vatsag.utils;


/*
 * 	"v_id"; 
	"v_name";
	"v_p_id"; 
	"v_d_id"; 
	"v_pdate";
	"v_ndate";
	"v_remarks";
 * */
public class VaccineDetails {

	String m_vaccinename;
	PetDetail m_pet;
    DoctorDetails m_doc;
    String m_vaccinedate;
    String m_nxtvaccinedate;
    String m_remarks;
    long m_vacid;


    /*
	 * Default Constructor
	 * 
	 * */
	public VaccineDetails(){
		
	}
	
	/*
	 * Constructor
	 * 
	 * */
	public VaccineDetails(long vacid,String vaccinename,PetDetail pet,DoctorDetails doc, String vdate,String ndate,String remarks){
		this.m_vacid = vacid;
		this.m_vaccinename = vaccinename;
		this.m_pet = pet;
		this.m_doc = doc;
		this.m_vaccinedate = vdate;
		this.m_nxtvaccinedate = ndate;
		this.m_remarks = remarks;
	}


	public long getM_vacid() {
		return m_vacid;
	}

	public void setM_vacid(long m_vacid) {
		this.m_vacid = m_vacid;
	}

	public String getM_vaccinename() {
		return m_vaccinename;
	}


	public void setM_vaccinename(String m_vaccinename) {
		this.m_vaccinename = m_vaccinename;
	}


	public PetDetail getM_pet() {
		return m_pet;
	}


	public void setM_pet(PetDetail m_pet) {
		this.m_pet = m_pet;
	}


	public DoctorDetails getM_doc() {
		return m_doc;
	}


	public void setM_doc(DoctorDetails m_doc) {
		this.m_doc = m_doc;
	}


	public String getM_vaccinedate() {
		return m_vaccinedate;
	}


	public void setM_vaccinedate(String m_vaccinedate) {
		this.m_vaccinedate = m_vaccinedate;
	}


	public String getM_nxtvaccinedate() {
		return m_nxtvaccinedate;
	}


	public void setM_nxtvaccinedate(String m_nxtvaccinedate) {
		this.m_nxtvaccinedate = m_nxtvaccinedate;
	}


	public String getM_remarks() {
		return m_remarks;
	}


	public void setM_remarks(String m_remarks) {
		this.m_remarks = m_remarks;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return m_vaccinename;
	}
}
