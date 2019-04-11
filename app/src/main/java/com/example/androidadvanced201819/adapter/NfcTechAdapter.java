package com.example.androidadvanced201819.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import java.util.List;

public class NfcTechAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] techs;

    public NfcTechAdapter(Context context, String[] apps) {
        super(context, R.layout.list_item_layout, apps);
        this.context = context;
        this.techs = apps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View appItem = convertView;

        if(appItem == null){
            appItem = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);
        }


        TextView name = (TextView) appItem.findViewById(R.id.itemName);
        name.setText(techs[position]);

        return appItem;
    }
}
