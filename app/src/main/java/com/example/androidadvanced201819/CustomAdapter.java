package com.example.androidadvanced201819;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;

import java.util.List;

public class CustomAdapter extends CursorAdapter {

    public CustomAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
