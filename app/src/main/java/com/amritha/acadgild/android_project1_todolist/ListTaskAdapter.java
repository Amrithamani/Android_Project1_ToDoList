package com.amritha.acadgild.android_project1_todolist;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amritha on 4/10/18.
 */

//class extending Base Adapter

class ListTaskAdapter extends BaseAdapter {

    //Initializing Activity variable and ArrayList

    private Activity activity;

    private ArrayList<HashMap<String, String>> data;

    //creating constructor for the class having two parameters

    ListTaskAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    //overriding all these methods for count and id

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    //overriding getView method

    public View getView(int position, View convertView, ViewGroup parent) {

        ListTaskViewHolder holder;//Initializing  ListTaskViewHolder

        //checking convertView is equal to null

        if (convertView == null) {
            holder = new ListTaskViewHolder();//creating instance of ListTaskViewHolder
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.to_do_list, parent, false);//Inflating the to_do_list layout in particular activity

            //Assigning values to all the variables in holder instance variable

            holder.task_dateTitle = convertView.findViewById(R.id.dateTitle);
            holder.task_title = convertView.findViewById(R.id.txtView1);
            holder.task_description = convertView.findViewById(R.id.txtView2);
            holder.task_date = convertView.findViewById(R.id.txtView3);
            convertView.setTag(holder);
        } else {
            holder = (ListTaskViewHolder) convertView.getTag();
        }
        //setting the variables in particular position

        holder.task_dateTitle.setId(position);
        holder.task_title.setId(position);
        holder.task_description.setId(position);
        holder.task_date.setId(position);

        HashMap<String, String> song;
        song = data.get(position);//Assigning the position of data ArrayList to song ArrayList

        try {

            //setting the Keys from MainActivity to holder

            holder.task_dateTitle.setText(song.get(MainActivity.KEY_DATE));
            holder.task_title.setText(song.get(MainActivity.KEY_TITLE));
            holder.task_description.setText(song.get(MainActivity.KEY_DESCRIPTION));
            holder.task_date.setText(song.get(MainActivity.KEY_DATE));

        } catch (Exception e) {
        }


        return convertView;//returning the views
    }
}

class ListTaskViewHolder {

    //Initializing TextView Variable
    TextView task_title, task_dateTitle;
    TextView task_description, task_date;

}
