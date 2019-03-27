package com.example.androidadvanced201819.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.dataaccess.DataAccessUtils;
import com.example.androidadvanced201819.dataaccess.Singleton;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.database.ProfileDatabaseManager;
import com.example.androidadvanced201819.model.Profile;

public class AdapterActivity extends ArrayAdapter<Profile> {

    private final Context context;
    ProfileDatabaseManager profileDatabaseManager;

    //costruttore
    public AdapterActivity(Context context) {

        super(context, R.layout.profile_item);
        this.context = context;
        this.updateList(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profile_item, null);
            viewHolder = new ViewHolder();
            viewHolder.profileNameHolder = (TextView) convertView.findViewById(R.id.profileName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.profileNameHolder.setText(DataAccessUtils.getItemByPosition(this.context, position).getNome());
        return convertView;
    }


    public void updateList(Context context) {

        profileDatabaseManager = new ProfileDatabaseManager(context);
        profileDatabaseManager.open();
        Cursor cursor = profileDatabaseManager.fetchAllProfile();
        cursor.moveToFirst();
        int index = cursor.getCount();

        if (index > 0) {
            int i = 0;
            do {
                Profile profile = new Profile(
                        cursor.getString(cursor.getColumnIndex(ProfileDatabaseManager.KEY_PROFILE_NAME)),
                        cursor.getInt(cursor.getColumnIndex(ProfileDatabaseManager.KEY_OPTION_SELECTED)),
                        cursor.getInt(cursor.getColumnIndex(ProfileDatabaseManager.KEY_BRIGHTNESS)),
                        cursor.getInt(cursor.getColumnIndex(ProfileDatabaseManager.KEY_VOLUME)),
                        cursor.getInt(cursor.getColumnIndex(ProfileDatabaseManager.KEY_BLUETHOOTH)),
                        cursor.getInt(cursor.getColumnIndex(ProfileDatabaseManager.KEY_WIFI)),
                        cursor.getString(cursor.getColumnIndex(ProfileDatabaseManager.KEY_APPLICATION))
                );
                i++;
                DataAccessUtils.addItem(profile, context);
                cursor.moveToNext();
            } while (i < index);
        }
        profileDatabaseManager.close();
    }

    public class ViewHolder {
        private TextView profileNameHolder;
    }


    public void setValues() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Singleton.getInstance().getProfiles().size();

    }
}
