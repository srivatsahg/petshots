package org.vatsag.utils;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Srivatsa Haridas
 * Date: 11/19/13
 * Time: 5:29 PM
 * Desc:
 */
public class HelpDescGroup {

    public String string;
        public final List<String> children = new ArrayList<String>();
        public final List<Map<String,String>> childelement = new ArrayList<Map<String, String>>();

        public HelpDescGroup(String string) {
        this.string = string;
    }
}
