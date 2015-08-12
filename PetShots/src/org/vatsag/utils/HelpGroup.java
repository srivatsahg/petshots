package org.vatsag.utils;

import java.util.ArrayList;
import java.util.List;

/*
 * Domain model for the expandable list view
 * */

public class HelpGroup {

	 public String string;
	 public final List<String> children = new ArrayList<String>();

	  public HelpGroup(String string) {
	    this.string = string;
	  }
	  
}
