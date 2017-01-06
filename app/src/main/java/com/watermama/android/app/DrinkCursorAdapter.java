package com.watermama.android.app;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.watermama.android.app.data.WatermamaContract.Entry;


public class DrinkCursorAdapter extends CursorAdapter {
    public DrinkCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        int dateColumnIndex = cursor.getColumnIndex(Entry.COLUMN_DATE);
        int timeColumnIndex = cursor.getColumnIndex(Entry.COLUMN_TIME);
        String drinkDate = cursor.getString(dateColumnIndex);
        String drinkTime = cursor.getString(timeColumnIndex);
        if (TextUtils.isEmpty(drinkTime)) {
            drinkTime = context.getString(R.string.unknown_breed);
        }
        nameTextView.setText(drinkDate);
        summaryTextView.setText(drinkTime);
    }
}
