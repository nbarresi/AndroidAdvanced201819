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
import org.its.db.dao.ProfiloDao;
import org.its.db.entities.Profilo;
import java.util.List;

public class ListActivity extends Activity {

    public final static String PROFILE = "accademia.lynxspa.com.PROFILE";

    CustomArrayAdapter adapter;
    List<Profilo> list;
    private ProfiloDao db;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        db = new ProfiloDao(getApplicationContext());

        db.openConn();
        list = db.getAllProfiles();
        db.closeConn();

        adapter = new CustomArrayAdapter(this, list);

        ListView listView = (ListView) findViewById(R.id.profileList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent updateIntent = new Intent(ListActivity.this, DetailActivity.class);
                Profilo profile = adapter.getItem(position);
                updateIntent.putExtra(PROFILE, profile);
                startActivity(updateIntent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
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
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
