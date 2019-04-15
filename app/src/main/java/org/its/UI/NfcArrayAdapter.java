package org.its.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import java.util.List;

public class NfcArrayAdapter extends ArrayAdapter<String> {

    private List<String> list;
    private Context context;

    public NfcArrayAdapter(Context context, List<String> values) {
        super(context, R.layout.nfc_list_item_layout, values);
        this.list = values;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.nfc_list_item_layout, parent, false); //converte il layout da XML a oggetto view

        TextView nfcListItem = (TextView) rowView.findViewById(R.id.nfc_list_item);

        String label = this.list.get(position);
        nfcListItem.setText(label);

        return rowView;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }
}
