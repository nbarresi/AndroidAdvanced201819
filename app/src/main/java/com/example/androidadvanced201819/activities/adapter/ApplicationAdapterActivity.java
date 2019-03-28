package com.example.androidadvanced201819.activities.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;
import com.example.androidadvanced201819.dataaccess.Singleton;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapterActivity extends ArrayAdapter<ApplicationInfo> {

    private final Context context;
    private List<ApplicationInfo> applicationsInfo = new ArrayList<>();

    public ApplicationAdapterActivity(Context context) {
        super(context, R.layout.profile_item);
        this.context = context;
        this.populateApplication(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profile_item, null);
            viewHolder = new ViewHolder();
            viewHolder.applicationName = (TextView) convertView.findViewById(R.id.profileName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PackageManager pm = context.getPackageManager();
        viewHolder.applicationName.setText(applicationsInfo.get(position).loadLabel(pm).toString());
        return convertView;
    }

    public void populateApplication(Context context) {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> applicationInfoList = pm.getInstalledApplications(0);
        this.applicationsInfo.addAll(applicationInfoList);
    }

    public class ViewHolder {
        private TextView applicationName;
    }

    public void setValues() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return applicationsInfo.size();

    }

    public ApplicationInfo getApplicationByPosition(int position) {
        return applicationsInfo.get(position);
    }
}
