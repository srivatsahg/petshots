package org.vatsag.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontTextView extends TextView {

	public FontTextView(Context context) {
		super(context);
		initfont(context);
	}

	private void initfont(Context context) {
		Typeface tf = Typefaces.get(context, Constants.ROBOTA_FONTFILE);
		this.setTypeface(tf);
	}

}
