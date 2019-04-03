package org.its.UI;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import java.util.List;

public class WifiArrayAdapter extends ArrayAdapter<ScanResult> {

    private List<ScanResult> list;
    private Context context;

    public WifiArrayAdapter( Context context, List<ScanResult> values) {
        super(context, R.layout.wifi_list_item_layout ,values);
        this.list = values;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.wifi_list_item_layout, parent, false); //converte il layout da XML a oggetto view

        TextView wifiPower = (TextView) rowView.findViewById(R.id.wifiPower);
        TextView wifiBssid = (TextView) rowView.findViewById(R.id.wifiBSSID);
        TextView wifiName = (TextView) rowView.findViewById(R.id.wifiName);
        ScanResult itemName = this.list.get(position);
        wifiPower.setText(String.valueOf(itemName.level*(-1)) + "%");
        wifiBssid.setText(String.valueOf(itemName.BSSID));
        wifiName.setText(String.valueOf(itemName.SSID));

        return rowView;
    }

    @Override
    public int getCount(){
        return this.list.size();
    }

}
