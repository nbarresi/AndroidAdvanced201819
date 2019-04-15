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

import org.altbeacon.beacon.Beacon;

import java.util.List;

public class BeaconAdapter extends ArrayAdapter<Beacon> {
    private Context context;
    private List<Beacon> beacons;

    public BeaconAdapter(Context context, List<Beacon> beacons) {
        super(context, R.layout.beacon_item, beacons);
        this.context = context;
        this.beacons = beacons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View beaconItem = convertView;

        if(beaconItem == null){
            beaconItem = LayoutInflater.from(context).inflate(R.layout.beacon_item,parent,false);
        }

        final Beacon beacon = beacons.get(position);

        TextView name = (TextView) beaconItem.findViewById(R.id.nome);
        TextView indirizzo = (TextView) beaconItem.findViewById(R.id.indirizzo);
        name.setText(beacon.getBluetoothName());
        indirizzo.setText(beacon.getBluetoothAddress());

        return beaconItem;
    }
}
