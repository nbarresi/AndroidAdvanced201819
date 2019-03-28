package com.example.androidadvanced201819.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import java.util.List;

public class AppAdapter extends ArrayAdapter<ApplicationInfo> {

    private Context context;
    private List<ApplicationInfo> apps;
    private PackageManager packageManager;

    public AppAdapter(Context context, List<ApplicationInfo> apps, PackageManager packageManager) {
        super(context, R.layout.list_item_layout, apps);
        this.context = context;
        this.apps = apps;
        this.packageManager = packageManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View appItem = convertView;

        if(appItem == null){
            appItem = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);
        }

        final ApplicationInfo appInfo = apps.get(position);

        TextView name = (TextView) appItem.findViewById(R.id.itemName);
        name.setText(appInfo.loadLabel(packageManager));

        return appItem;
    }
}
