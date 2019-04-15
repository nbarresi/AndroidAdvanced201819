package org.its.login.adapters;


import android.net.wifi.ScanResult;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import java.util.List;


public class BeaconListRecyclerAdapter extends RecyclerView.Adapter<BeaconListRecyclerAdapter.BeaconListViewHolder> {
    private List<ScanResult> wifiList;


    public BeaconListRecyclerAdapter(List<ScanResult> wifiList) {
       this.wifiList = wifiList;
    }

    public static class BeaconListViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;




        public BeaconListViewHolder(final View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view_wifi);      //TODO CONVERT BEACON

        }

    }

    @Override
    public BeaconListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.wifi_card_view, parent, false);
        return new BeaconListViewHolder(itemView);
    }


     public void onBindViewHolder(final BeaconListViewHolder holder, final int position) {
         ScanResult scanResult = wifiList.get(position);

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

