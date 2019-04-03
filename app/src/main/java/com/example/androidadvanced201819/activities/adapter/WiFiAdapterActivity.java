package com.example.androidadvanced201819.activities.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.model.WiFi;

import java.util.ArrayList;
import java.util.List;

public class WiFiAdapterActivity extends ArrayAdapter<WiFi> {

    private final Context context;
    private List<WiFi> wiFis = new ArrayList<>();

    public WiFiAdapterActivity(Context context) {
        super(context, R.layout.wifi_item);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.wifi_item, null);
            viewHolder = new ViewHolder();
            viewHolder.BSSID = (TextView) convertView.findViewById(R.id.wifiBSSID);
            viewHolder.SSID = (TextView) convertView.findViewById(R.id.wifiSSID);
            viewHolder.level = (TextView) convertView.findViewById(R.id.level);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.BSSID.setText(wiFis.get(position).getBSSID());
        viewHolder.SSID.setText(wiFis.get(position).getSSID());
        int level = wiFis.get(position).getLevel() * (-1);
        viewHolder.level.setText(level + "%");
        return convertView;
    }

    public void updateList(List<ScanResult> wifiList) {
        wiFis = new ArrayList<>();
        for (ScanResult scan : wifiList) {
            WiFi wiFi = new WiFi(scan.BSSID, scan.SSID, scan.level);
            wiFis.add(wiFi);
            this.setValues();
        }
    }

    public class ViewHolder {
        private TextView BSSID;
        private TextView SSID;
        private TextView level;
    }

    public void setValues() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return wiFis.size();
    }
}
