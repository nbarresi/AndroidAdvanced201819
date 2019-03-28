package com.example.androidadvanced201819;

import android.app.LauncherActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.QuickContactBadge;

import java.util.ArrayList;

public class ListaProfili extends AppCompatActivity {

    DB db;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_profili);

        listView=findViewById(R.id.listView);
        db=new DB(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getApplicationContext(),AggiungiProfilo.class);

                startActivity(intent);
            }
        });

        creazioneListView();
        final Button add_profilo=findViewById(R.id.add_profilo);
        add_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AggiungiProfilo.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    private void creazioneListView() {
        Cursor data=db.getData();
        ArrayList<String> lista=new ArrayList<>();
        while(data.moveToNext()){
            lista.add(data.getString(1));
        }
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(adapter);
    }

}
