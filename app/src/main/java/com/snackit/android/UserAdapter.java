package com.snackit.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Schmänd on 20.09.2016.
 */
class UsersAdapter extends ArrayAdapter<SnackDay> {

    public UsersAdapter(Context context, ArrayList<SnackDay> users) {

        super(context, 0, users);

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        SnackDay user = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.simplerow, parent, false);


        }


        // Lookup view for data population

        TextView tvTageszeit = (TextView) convertView.findViewById(R.id.tageszeit);

        TextView tvName = (TextView) convertView.findViewById(R.id.tag);

        TextView tvHome = (TextView) convertView.findViewById(R.id.essen);

        // Populate the data into the template view using the data object
        tvTageszeit.setText(user.tageszeit);

        tvName.setText(user.day);

        tvHome.setText(user.meal);

        // Return the completed view to render on screen

        return convertView;

    }

}