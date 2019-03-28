package com.example.androidadvanced201819;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.DB.DbHelper;
import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.activities.CreateProfileActivity;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<UserProfile> {

    private final Context context;
    private List<UserProfile> profiles;
    private DbHelper dbHelper;

    public CustomAdapter(Context context, List<UserProfile> objects,DbHelper dbHelper) {
        super(context, R.layout.list_item_layout ,objects);
        this.context = context;
        this.profiles = objects;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);
        }


        final UserProfile profile = profiles.get(position);

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreateProfile = new Intent(context,CreateProfileActivity.class);
                intentCreateProfile.putExtra("PROFILE",profile);
                context.startActivity(intentCreateProfile);
            }
        });

        listItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dbHelper.removeProfile(profile.getId());
                profiles.remove(position);
                notifyDataSetChanged();
                return false;
            }
        });

        TextView name = (TextView) listItem.findViewById(R.id.itemName);
        name.setText(profile.getNome());

        return listItem;
    }

    public void resetData() {
        profiles.clear();
        profiles.addAll(dbHelper.getProfiles());
    }
}
