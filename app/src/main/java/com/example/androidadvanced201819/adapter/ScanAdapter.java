package com.example.androidadvanced201819.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.DB.Entities.UserProfile;
import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.activities.CreateProfileActivity;

import java.util.HashMap;
import java.util.List;

public class ScanAdapter extends ArrayAdapter {

    private List<ScanResult> wifis;
    private final Context context;

    public ScanAdapter(Context context, List<ScanResult> data) {
        super(context, R.layout.scan_item_layout,data);
        this.context=context;
        this.wifis=data;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.scan_item_layout, parent, false);
        }

        final ScanResult wifi = wifis.get(position);


        TextView ssid = (TextView) listItem.findViewById(R.id.SSID);
        TextView bssid = (TextView) listItem.findViewById(R.id.BSSID);
        TextView livello = (TextView) listItem.findViewById(R.id.LEVEL);

        ssid.setText(wifi.SSID);
        bssid.setText(wifi.BSSID);
        livello.setText(""+wifi.level);

        return listItem;
    }
}
