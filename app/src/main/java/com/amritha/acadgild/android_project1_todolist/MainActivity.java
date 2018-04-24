package com.amritha.acadgild.android_project1_todolist;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

//a. Activity should extend the AppCompactActivity class.

public class MainActivity extends AppCompatActivity {
    //Initializing List View

    private ListView lv;

    //Initializing Activity

    Activity activity;

    //Initializing Database

    MyDBHandler mydb;

    //Initializing Hash Map Array List

    ArrayList<HashMap<String, String>> list = new ArrayList<>();

    //Initializing all table Contents

    public static String KEY_ID = "ListID";

    public static String KEY_TITLE = "ListTitle";

    public static String KEY_DESCRIPTION = "ListDescription";

    public static String KEY_DATE = "DueDate";

    //Initializing TextView

    TextView dateTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;//Setting Activity to MainActivity class

        //creating instance of database using MainActivity

        mydb = new MyDBHandler(activity);

        //finding id for List View Items

        lv = findViewById(R.id.lvItems);

    }

    // Implement method onCreateOptionsMenu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);//inflating menu layout in actionbar

        return true;
    }

    // Implement method onOptionsItemSelected

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            //checking the id to match action_complete

            case R.id.action_complete:
                Intent i = new Intent(MainActivity.this, AddTask.class);
                this.startActivity(i);//starting AddTask Activity
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    //creating instance of Database using Main Activity
    public void populateData() {
        mydb = new MyDBHandler(activity);

    }

    //After the page loaded it will update with this class

    @Override
    public void onResume() {
        super.onResume();
        LoadTask loadTask = new LoadTask();//creating a instance of LoadTask class
        loadTask.execute();//executing that class

        populateData();//calling the method.

    }

    //creating a class extends AsyncTask runs in background

    public class LoadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            list.clear();//clearing the list variable using Hash Map

            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            String xml = "";

            Cursor today = mydb.getData();//calling the getData method in Database

            loadDataList(today, list);//calling method passing cursor and list String variables

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {


            loadListView(lv, list);//calling the method passing List View and Hash Map String

        }
    }

    public void loadDataList(Cursor cursor, ArrayList<HashMap<String, String>> dataList) {
        //checking whether  the cursor is not equal to null

        if (cursor != null) {
            //moving to first position in cursor

            cursor.moveToFirst();

            //cursor will go till its last positon

            while (cursor.isAfterLast() == false) {

                //creating another HashMap variable mapToday

                HashMap<String, String> mapToday = new HashMap<>();

                //putting Strings inside mapToday

                mapToday.put(KEY_ID, cursor.getString(0).toString());
                mapToday.put(KEY_TITLE, cursor.getString(1).toString());
                mapToday.put(KEY_DESCRIPTION, cursor.getString(2).toString());

                //calling Function class for getting correctDate Format
                mapToday.put(KEY_DATE, com.amritha.acadgild.android_project1_todolist.Function.Epoch2DateString(cursor.getString(3).toString(), "dd-MM-yyyy"));
                dataList.add(mapToday);//adding mapToday Variables to ArrayList
                cursor.moveToNext();//moving to next
            }
        }
    }

    public void loadListView(ListView listView, final ArrayList<HashMap<String, String>> dataList) {
        //creating instance of Adapter passing Arguments MainActivity class and ArrayList

        ListTaskAdapter adapter = new ListTaskAdapter(activity, dataList);

        //setting the Adapter

        listView.setAdapter(adapter);

        //on Click Listener for List View

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {


                //creating the dialog clicking List View

                final Dialog openDialog = new Dialog(MainActivity.this);

                openDialog.setContentView(R.layout.dialog_layout);//setting layout for diaolog

                openDialog.show();//showing dialog

                //finding views for buttons

                Button update = openDialog.findViewById(R.id.button);

                Button delete = openDialog.findViewById(R.id.button2);

                //On Click Event for Buttons

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(activity, AddTask.class);//creating external Intent

                        i.putExtra("isUpdate", true);//passing boolean value for update

                        i.putExtra("id", dataList.get(+position).get(KEY_ID));//passing all the data lists

                        startActivity(i);//starting a new Activity

                        openDialog.dismiss();//finishing or closing the dialog

                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(activity, DeleteTask.class);//creating external Intent

                        i.putExtra("isDelete", true);//passing boolean value for delete

                        i.putExtra("id", dataList.get(+position).get(KEY_ID));//passing all the data lists

                        startActivity(i);//starting a new Activity

                        openDialog.dismiss();//finishing or closing the dialog
                    }
                });

            }
        });
    }
}