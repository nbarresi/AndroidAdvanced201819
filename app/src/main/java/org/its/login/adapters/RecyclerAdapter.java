package org.its.login.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.androidadvanced201819.R;

import org.its.db.entities.Profile;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ListViewHolder> {
    List<Profile> profiles;


    public RecyclerAdapter(List<Profile> profiles) {
       this.profiles =  profiles;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView profileName;



        public ListViewHolder(View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view);
            profileName = (TextView) itemView.findViewById(R.id.info_text);

        }
    }

    @Override
    public RecyclerAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_layout, parent, false);
        return new ListViewHolder(itemView);
    }


     public void onBindViewHolder(RecyclerAdapter.ListViewHolder holder, int position) {
        holder.profileName.setText("Elemento n°" + profiles.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

