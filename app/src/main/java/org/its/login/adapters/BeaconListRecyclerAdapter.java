package org.its.login.adapters;


import android.net.wifi.ScanResult;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import org.altbeacon.beacon.Beacon;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class BeaconListRecyclerAdapter extends RecyclerView.Adapter<BeaconListRecyclerAdapter.BeaconListViewHolder> {
    private Collection<Beacon> beacons;
    private List<Beacon> beaconList;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public BeaconListRecyclerAdapter(Collection<Beacon> beacons) {
       this.beacons = beacons;
       beaconList= new ArrayList<>(beacons);
    }

    public static class BeaconListViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView beaconName;
        private TextView beaconAddress;
        private TextView beaconDistance;



        public BeaconListViewHolder(final View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view_beacon);
            beaconName = itemView.findViewById(R.id.nome_beacon);
            beaconAddress = itemView.findViewById(R.id.indirizzo_beacon);
            beaconDistance = itemView.findViewById(R.id.distanza_beacon);
            df2.setRoundingMode(RoundingMode.DOWN);
        }
    }

    @Override
    public BeaconListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.beacon_card_view, parent, false);
        return new BeaconListViewHolder(itemView);
    }


     public void onBindViewHolder(final BeaconListViewHolder holder, final int position) {
         Beacon beacon = beaconList.get(position);
         holder.beaconName.setText(beacon.getBluetoothName().toString());
         holder.beaconDistance.setText(df2.format(beacon.getDistance())+"m");
         holder.beaconAddress.setText(beacon.getBluetoothAddress().toString());
    }

    @Override
    public int getItemCount() {
        return beaconList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

