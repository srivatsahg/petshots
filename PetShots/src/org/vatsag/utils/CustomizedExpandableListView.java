package org.vatsag.utils;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * User: Srivatsa Haridas
 * Date: 11/19/13
 * Time: 3:26 PM
 * Desc:
 */
public class CustomizedExpandableListView extends ExpandableListView {

    int groupPositionId,childPositionId,intgroupId;

    /*
    * Constructor
    * */
    public CustomizedExpandableListView(Context ctxt){
        super(ctxt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(960,MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(600,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
