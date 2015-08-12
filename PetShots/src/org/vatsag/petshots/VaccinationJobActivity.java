package org.vatsag.petshots;

import org.vatsag.R;
import org.vatsag.R.layout;
import org.vatsag.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VaccinationJobActivity extends ListActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psvaccinejobcard);
        
        String[] data1 = new String[] { "Header1", "data1", "data2" };
        String[] data2 = new String[] { "Header2", "data1", "data2" };
        String[] data3 = new String[] { "Header3", "data1", "data2" };
        String[] data4 = new String[] { "Header4", "data1", "data2" };
        
        setListAdapter(new MyAdapter(this, R.layout.psvaccinejobcard,
                R.id.datavaccine, data1, data2, data3, data4));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
    
    
    private static class MyAdapter extends ArrayAdapter<String> {

        private String[] data1, data2, data3, data4;

        public MyAdapter(Context context, int resource, int textViewResourceId,
                String[] data1, String[] data2, String[] data3, String[] data4) {
            super(context, resource, textViewResourceId, data1);
            this.data1 = data1;
            this.data2 = data2;
            this.data3 = data3;
            this.data4 = data4;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            if (position == 0) {
                // header
                v.setBackgroundColor(Color.GREEN);
            } else {
                v.setBackgroundResource(android.R.drawable.list_selector_background);
            }
            TextView t1 = (TextView) v.findViewById(R.id.datavaccine);
            t1.setText(data1[position]);
            t1.setTextColor(Color.WHITE);
            TextView t2 = (TextView) v.findViewById(R.id.datavisitdate);
            t2.setText(data2[position]);
            t2.setTextColor(Color.WHITE);
            TextView t3 = (TextView) v.findViewById(R.id.datanxtdate);
            t3.setText(data3[position]);
            t3.setTextColor(Color.WHITE);
            TextView t4 = (TextView) v.findViewById(R.id.datadoctor);
            t4.setText(data4[position]);
            t4.setTextColor(Color.WHITE);
            return v;
        }

        @Override
        public boolean isEnabled(int position) {        
            return position == 0 ? false : true;
        }       

    }
}
