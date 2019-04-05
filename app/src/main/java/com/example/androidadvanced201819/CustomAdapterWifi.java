package com.example.androidadvanced201819;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterWifi extends ArrayAdapter<ScanResult>{

    List<ScanResult> wifis;

    public CustomAdapterWifi(Context context, int resource,List<ScanResult> wifis) {
        super(context, resource, wifis);
        this.wifis = wifis;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.wifi_row,parent,false);

        ScanResult result = wifis.get(position);

        TextView nomeRete=customView.findViewById(R.id.nomeRete);
        TextView codice_wifi=customView.findViewById(R.id.codice_wifi);
        TextView segnale_wifi=customView.findViewById(R.id.segnale_wifi);


        nomeRete.setText(result.SSID);
        codice_wifi.setText(result.BSSID);
        segnale_wifi.setText(""+result.level);
        return customView;

    }
}
