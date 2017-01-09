package com.watermama.android.app;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.watermama.android.app.data.WatermamaContract;
import com.watermama.android.app.data.WatermamaContract.Entry;


public class HistoryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int RECORD_LOADER = 0;
    DrinkCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        ListView petListView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);
        mCursorAdapter = new DrinkCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(HistoryActivity.this, AddActivity.class);
                Uri currentRecordUri = ContentUris.withAppendedId(Entry.CONTENT_URI, id);
                intent.setData(currentRecordUri);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(RECORD_LOADER, null, this);
    }

    private void insertPet() {
        ContentValues values = new ContentValues();
        values.put(WatermamaContract.Entry.COLUMN_DATE, "20170104");
        values.put(Entry.COLUMN_TIME, "0914");
        values.put(Entry.COLUMN_VOLUME, 120);
        Uri newUri = getContentResolver().insert(Entry.CONTENT_URI, values);
    }

    private void deleteAllRecords() {
        int rowsDeleted = getContentResolver().delete(WatermamaContract.Entry.CONTENT_URI, null, null);
        Log.v("HistoryActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**case R.id.action_insert_dummy_data:
                    insertPet();
                    return true;*/
            case R.id.action_delete_all_entries:
                deleteAllRecords();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                Entry._ID,
                Entry.COLUMN_DATE,
                Entry.COLUMN_TIME};
        return new CursorLoader(this,
                Entry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
