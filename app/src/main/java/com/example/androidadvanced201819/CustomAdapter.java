package com.example.androidadvanced201819;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.DB.Entities.UserProfile;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<UserProfile> {


    private final Context context;
    private List<UserProfile> profiles;

    public CustomAdapter(Context context, List<UserProfile> objects) {
        super(context, R.layout.list_item_layout ,objects);
        this.context = context;
        this.profiles = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);
        }

        UserProfile profile = profiles.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.itemName);
        name.setText(profile.getNome());

        return listItem;
    }



}
