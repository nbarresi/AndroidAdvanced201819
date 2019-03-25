package com.example.androidadvanced201819;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.QuickContactBadge;

public class Lista_profili extends AppCompatActivity {
    String[] stringa={"Casa","Lavoro","Accademia"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_profili);

        ListView listView=findViewById(R.id.listView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stringa);
        listView.setAdapter(adapter);

        final Button add_profilo=findViewById(R.id.add_profilo);
        add_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AggiungiProfilo.class);
                startActivity(intent);

            }
        });
    }
}
