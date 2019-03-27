package org.its.login.adapters;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidadvanced201819.R;

import org.its.db.entities.Profile;
import org.its.login.activities.AddAppListActivity;
import org.its.login.activities.NewProfileActivity;

import java.util.List;


public class AppListRecyclerAdapter extends RecyclerView.Adapter<AppListRecyclerAdapter.AppListViewHolder> {
    //TEMP  String fino a decisione entità "app"
    private List<String> appList;
    private AdapterView.OnItemClickListener listener;
    private Profile profile;

    public AppListRecyclerAdapter(List<String> appList) {
       this.appList = appList;
    }

    public static class AppListViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView appName;



        public AppListViewHolder(final View view) {
            super(view);
            cardView = itemView.findViewById(R.id.card_view_app);
            appName = (TextView) itemView.findViewById(R.id.app_cv_name);
        }

    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.app_card_view, parent, false);
        return new AppListViewHolder(itemView);
    }


     public void onBindViewHolder(final AppListViewHolder holder, final int position) {
        holder.appName.setText(appList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setApp(appList.get(position));
                Intent intentAddApp = new Intent(v.getContext(), NewProfileActivity.class);
                intentAddApp.putExtra("tempProfileWithApp", profile);
                v.getContext().startActivity(intentAddApp);

            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }
}

