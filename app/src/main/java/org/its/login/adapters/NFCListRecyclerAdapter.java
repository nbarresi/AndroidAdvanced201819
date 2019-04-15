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


public class NFCListRecyclerAdapter extends RecyclerView.Adapter<NFCListRecyclerAdapter.NFCListViewHolder> {
    private String[] techListsArray;


    public NFCListRecyclerAdapter(String[] techListsArray) {
       this.techListsArray = techListsArray;
    }

    public static class NFCListViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView nfcName;

        public NFCListViewHolder(final View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view_nfc);
            nfcName = (TextView) itemView.findViewById(R.id.nfc_cv_name);
        }
    }

    @Override
    public NFCListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nfc_card_view, parent, false);
        return new NFCListViewHolder(itemView);
    }


     public void onBindViewHolder(final NFCListViewHolder holder, final int position) {
         String tech= techListsArray[position];
         holder.nfcName.setText(tech);
    }

    @Override
    public int getItemCount() {
        return techListsArray.length;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

