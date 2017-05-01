package com.watermama.android.app;

import android.database.Cursor;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.watermama.android.app.data.WatermamaContract;
import com.watermama.android.app.data.WatermamaContract.Entry;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JingYun on 2017/1/6.
 */


public class DailyActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }


    private void displayDatabaseInfo() {
        float total_vol = 0;
        float percentage;
        SimpleDateFormat sdf_today = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        String todayString = sdf_today.format(today);
        String selection = Entry.COLUMN_DATE + "=?";
        String[] selectionArgs = {todayString};
        String[] projection = {
                Entry._ID,
                Entry.COLUMN_DATE,
                Entry.COLUMN_TIME,
                Entry.COLUMN_VOLUME};

        Cursor cursor = getContentResolver().query(
                WatermamaContract.Entry.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                selection,                   // Selection criteria
                selectionArgs,                   // Selection criteria
                null);                  // The sort order for the returned rows

        //TextView TextViewRecord = (TextView) findViewById(R.id.text_view_record);
        TextView TextViewTotalVol = (TextView) findViewById(R.id.text_view_total_vol);
        TextView TextViewPercentage = (TextView) findViewById(R.id.text_view_percentage);

        try {
            /**TextViewRecord.setText("The records table contains " + cursor.getCount() + " records.\n\n");
                    TextViewRecord.append(Entry._ID + " - " +
                    Entry.COLUMN_DATE + " - " +
                    Entry.COLUMN_TIME + " - " +
                    Entry.COLUMN_VOLUME + "\n");*/

            int idColumnIndex = cursor.getColumnIndex(Entry._ID);
            int dateColumnIndex = cursor.getColumnIndex(Entry.COLUMN_DATE);
            int timeColumnIndex = cursor.getColumnIndex(Entry.COLUMN_TIME);
            int volumeColumnIndex = cursor.getColumnIndex(Entry.COLUMN_VOLUME);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);
                int currentVolume = cursor.getInt(volumeColumnIndex);
                /**TextViewRecord.append(("\n" + currentID + " - " +
                             currentDate + " - " +
                             currentTime + " - " +
                             currentVolume));*/
                total_vol = total_vol + currentVolume;
                percentage = (total_vol / 2000) * 100;
                int int_percentage  = (int) percentage;
                TextViewTotalVol.setText(Float.toString(total_vol)+"ml");
                TextViewPercentage.setText(Integer.toString(int_percentage)+"%");
            }
        } finally {
            cursor.close();
        }
    }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String Consultation = "Water Consumption Consultation";
        final String Customize = "Customized WaterMama Service";

        switch (item.getItemId()) {
            case R.id.action_history:
                Intent intent_history = new Intent(DailyActivity.this, HistoryActivity.class);
                startActivity(intent_history);
                return true;

            case R.id.action_mailtowatermama_1:
                sendEmail(Consultation);
                return true;

            case R.id.action_mailtowatermama_2:
                sendEmail(Customize);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    protected void sendEmail(String Subject) {
        String[] TO = {"jingyun@water-mama.com "};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DailyActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
*/

    protected void sendEmail(String Subject) {
               Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "jingyun@water-mama.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }



}

