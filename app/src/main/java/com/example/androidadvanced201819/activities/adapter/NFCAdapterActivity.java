package com.example.androidadvanced201819.activities.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import java.util.ArrayList;
import java.util.List;

public class NFCAdapterActivity extends ArrayAdapter<String> {

    private List<String> techListName = new ArrayList<>();

    public NFCAdapterActivity(Context context) {
        super(context, R.layout.row);
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
            convertView = inflater.inflate(R.layout.row, null);
            viewHolder = new ViewHolder();
            viewHolder.techName = (TextView) convertView.findViewById(R.id.techNFCName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.techName.setText(techListName.get(position));
        return convertView;
    }

    public void updateList(List<String> techList) {
        techListName = new ArrayList<>();
        for (String techRow : techList) {
            techListName.add(techRow);
            this.setValues();
        }
    }

    public class ViewHolder {
        private TextView techName;
    }

    public void setValues() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return techListName.size();
    }
}
