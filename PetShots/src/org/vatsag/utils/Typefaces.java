package org.vatsag.utils;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * <b>Typefaces</b> loads each font once per instance of Application
 * @author Srivatsa Haridas
 *
 */
public class Typefaces {
	private static final String TAG = "[Typefaces]";

	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	/**
	 * 
	 * @param c Context
	 * @param fontFile Name of the FontFile (eg: Robota)
	 * @return Typeface
	 */
	public static Typeface get(Context c, String fontFile) {
		synchronized (cache) {
			if (!cache.containsKey(fontFile)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							"fonts/"+fontFile);
					cache.put(fontFile, t);
				} catch (Exception e) {
					Log.e(TAG, "Could not get typeface '" + fontFile
							+ "' because " + e.getMessage());
					return null;
				}
			}
			return cache.get(fontFile);
		}
	}
}