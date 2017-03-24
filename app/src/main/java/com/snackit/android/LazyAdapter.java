package com.snackit.android;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.simplerow, null);

        TextView tag = (TextView)vi.findViewById(R.id.tag); // title
        TextView essen = (TextView)vi.findViewById(R.id.essen); // artist name

        HashMap<String, String> essens = new HashMap<String, String>();
        essens = data.get(position);

        // Setting all values in listview
        tag.setText(essens.get(SnackWeek.KEY_TAG));
        essen.setText(essens.get(SnackWeek.KEY_ESSEN));
        return vi;
    }
}