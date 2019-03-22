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


public class ProfileListRecyclerAdapter extends RecyclerView.Adapter<ProfileListRecyclerAdapter.ListViewHolder> {
    List<Profile> profiles;


    public ProfileListRecyclerAdapter(List<Profile> profiles) {
       this.profiles =  profiles;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView profileName;



        public ListViewHolder(View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view_profile);
            profileName = (TextView) itemView.findViewById(R.id.profile_cv_name);

        }
    }

    @Override
    public ProfileListRecyclerAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_card_view, parent, false);
        return new ListViewHolder(itemView);
    }


     public void onBindViewHolder(ProfileListRecyclerAdapter.ListViewHolder holder, int position) {
        holder.profileName.setText(profiles.get(position).getName());
         //TODO Listener
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

