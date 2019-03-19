package org.its.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidadvanced201819.R;

import org.its.UI.CustomArrayAdapter;
import org.its.db.entities.Profilo;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    CustomArrayAdapter adapter;
    List<Profilo> list;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        list = new ArrayList<>();

        adapter = new CustomArrayAdapter(this, list);

        ListView listView = (ListView) findViewById(R.id.profileList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

        Button aggiungi = (Button) findViewById(R.id.aggiungi);
        aggiungi.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }

}
