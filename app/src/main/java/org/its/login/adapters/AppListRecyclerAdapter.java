package org.its.login.adapters;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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

import static android.app.Activity.RESULT_OK;
import static java.security.AccessController.getContext;


public class AppListRecyclerAdapter extends RecyclerView.Adapter<AppListRecyclerAdapter.AppListViewHolder> {
    private List<ApplicationInfo> appList;
    private AdapterView.OnItemClickListener listener;
    private PackageManager pm;
    private Activity mActivity;


    public AppListRecyclerAdapter(List<ApplicationInfo> appList, Activity activity ) {
       this.appList = appList;
        this.mActivity = activity;
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
        ApplicationInfo applicationInfo = (ApplicationInfo) appList.get(position);


        holder.appName.setText(applicationInfo.loadLabel(pm));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                profile.setApp(holder.appName.getText().toString());
//                Intent intentAddApp = new Intent(v.getContext(), NewProfileActivity.class);
//                intentAddApp.putExtra("tempProfileWithApp", profile);
//                v.getContext().startActivity(intentAddApp);

                Intent returnAppIntent = new Intent();
                returnAppIntent.putExtra("ADD_APP_REQUEST_CODE", holder.appName.getText().toString());
                mActivity.setResult(RESULT_OK, returnAppIntent);
                mActivity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public void setPackageManager(PackageManager packageManager){
        pm = packageManager;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

