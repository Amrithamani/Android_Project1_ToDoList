package com.amritha.acadgild.android_project1_todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amritha on 4/9/18.
 */
public class AddTask extends AppCompatActivity {

    //Initializing Database

    MyDBHandler mydb;

    //Initializing year, month, day Integer variable

    int startYear = 0, startMonth = 0, startDay = 0;

    //Initializing String variables for title,description,date

    String dateFinal, titleFinal, descriptionFinal;

    //Initializing Boolean variable

    Boolean isUpdate;

    //Initializing intent and String variables

    Intent intent;
    String id;

    //creating EditText static variable

    static EditText dateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dialog);

        //going to previous activity

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //finding views by id for date EditText

        dateEdit = findViewById(R.id.date_picker_select_date);

        //creating instance of Database using Context

        mydb = new MyDBHandler(getApplicationContext());

        //getting Intent

        intent = getIntent();

        //Getting the passed value from MainActivity

        isUpdate = intent.getBooleanExtra("isUpdate", false);

        //creating Date variable

        Date your_date = new Date();
        //Initializing Calendar variable

        Calendar cal = Calendar.getInstance();

        //setting date for calendar

        cal.setTime(your_date);
        startYear = cal.get(Calendar.YEAR);
        startMonth = cal.get(Calendar.MONTH);
        startDay = cal.get(Calendar.DAY_OF_MONTH);

        //Creating on Click event for dateEditText

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);//calling this method
            }
        });

        //checking the boolean value is true

        if (isUpdate) {
            init_update();//calling a method
        }

    }

    public void showDatePickerDialog(View v) {

        //creating instance of DatePickerFragment class

        android.support.v4.app.DialogFragment dialogFragment = new DatePickerFragment();

        //showing the dialog using fragment for Date

        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void init_update() {

        //getting id from MainActivity class

        id = intent.getStringExtra("id");
        //getting ids for the EditText

        EditText task_title = findViewById(R.id.title_name_edit_text);
        EditText task_description = findViewById(R.id.description_edit_text);

        //calling Database method getDataSpecific passing id

        Cursor task = mydb.getDataSpecific(id);

        //checking cursor position is not equal to null

        if (task != null) {
            task.moveToFirst();//move to first position

            //setting all the values from particular column to EditText

            task_title.setText(task.getString(1).toString());
            task_description.setText(task.getString(2).toString());

            //calling Method in Function class

            Calendar cal = Function.Epoch2Calender(task.getString(3).toString());

            startYear = cal.get(Calendar.YEAR);
            startMonth = cal.get(Calendar.MONTH);
            startDay = cal.get(Calendar.DAY_OF_MONTH);

            //setting date text by calling method in Function class

            dateEdit.setText(Function.Epoch2DateString(task.getString(3).toString(), "dd/MM/yyyy"));

        }

    }

    public void closeAddTask(View view) {
        finish();
    }

    public void buttonClicked(View view) {

        //initializing Integer variable

        int errorStep = 0;

        //getting id for All Edit Text

        EditText task_title = findViewById(R.id.title_name_edit_text);
        EditText task_description = findViewById(R.id.description_edit_text);

        //Converting EditText values to String

        titleFinal = task_title.getText().toString();
        descriptionFinal = task_description.getText().toString();
        dateFinal = dateEdit.getText().toString();


        // Checking for validation

        if (titleFinal.trim().length() < 1) {
            errorStep++;
            task_title.setError("Provide a task title.");
        }

        if (descriptionFinal.trim().length() < 1) {
            errorStep++;
            task_description.setError("Provide a task description.");
        }

        if (dateFinal.trim().length() < 4) {
            errorStep++;
            dateEdit.setError("Provide a specific date");
        }

        //If there is any error in validation it won't insert or update
        if (errorStep == 0) {

            //checking whether the boolean value is true

            if (isUpdate) {

                //calling method in database class passing id,title, description and date

                mydb.updateContact(id, titleFinal, descriptionFinal, dateFinal);

                //creating a toast

                Toast.makeText(getApplicationContext(), "Task Updated.", Toast.LENGTH_SHORT).show();

            } else {

                //calling method in database class passing title, description and date

                mydb.insertContact(titleFinal, descriptionFinal, dateFinal);//calling insertContact method in Database

                //creating Toast
                Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();
            }
            finish();//finishing after it's done go back to previous activity
        } else {
            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
        }
    }

    //creating static class for DatePicker Dialog extends Dialog Fragment and implements DatePickerDialog


    public static class DatePickerFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        //overriding onDateSet method for setting the date

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Do something with the date chosen by the user
            dateEdit.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }
    }

}
