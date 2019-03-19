package org.its.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import org.its.db.entities.Profilo;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Profilo> {

    private Context context;
    private List<Profilo> values;

    public CustomArrayAdapter(Context context, List<Profilo> values) {
        super(context, R.layout.item_layout, values);
        this.values = values;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_layout, parent, false); //converte il layout da XML a oggetto view

        TextView textView = (TextView) rowView.findViewById(R.id.profileName);
        String itemName = this.values.get(position).getNome();
        textView.setText(itemName);

        return rowView;
    }

    @Override
    public int getCount(){

        return this.values.size();
    }
}

