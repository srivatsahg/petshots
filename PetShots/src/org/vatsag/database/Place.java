package org.vatsag.database;

import java.io.Serializable;

public class Place implements Serializable {

	    public String id;
	     
	    public String name;
	     
	    public String reference;
	     
	    public String icon;
	     
	    public String vicinity;
	     
	    public Geometry geometry;
	     
	    public String formatted_address;
	     
	    public String formatted_phone_number;
	 
	    @Override
	    public String toString() {
	        return name + " - " + id + " - " + reference;
	    }
	     
	    public static class Geometry implements Serializable
	    {
	        public Location location;
	    }
	     
	    public static class Location implements Serializable
	    {
	        public double lat;
	        public double lng;
	    }
	    
}
