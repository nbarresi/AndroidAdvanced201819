package org.its.login.adapters;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class WifiListRecyclerAdapter extends RecyclerView.Adapter<WifiListRecyclerAdapter.WifiListViewHolder> {
    private List<ScanResult> wifiList;


    public WifiListRecyclerAdapter(List<ScanResult> wifiList) {
       this.wifiList = wifiList;
    }

    public static class WifiListViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView wifiName;
        private TextView wifiBSSID;
        private TextView wifiPower;



        public WifiListViewHolder(final View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view_wifi);
            wifiName = (TextView) itemView.findViewById(R.id.wifi_cv_name);
            wifiBSSID = (TextView) itemView.findViewById(R.id.wifi_cv_bssid);
            wifiPower= (TextView) itemView.findViewById(R.id.wifi_cv_power);
        }

    }

    @Override
    public WifiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.wifi_card_view, parent, false);
        return new WifiListViewHolder(itemView);
    }


     public void onBindViewHolder(final WifiListViewHolder holder, final int position) {
         ScanResult scanResult = wifiList.get(position);
        holder.wifiName.setText(scanResult.SSID);
        holder.wifiBSSID.setText(scanResult.BSSID);
        holder.wifiPower.setText(Integer.toString(scanResult.level));
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

