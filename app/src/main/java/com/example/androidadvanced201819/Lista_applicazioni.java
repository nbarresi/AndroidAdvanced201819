package com.example.androidadvanced201819;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lista_applicazioni extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_applicazioni);
        String[] stringa={
                "Phone and Messaging Storage",
                "Google App",
                "Calendar Storage",
                "Media Storage",
                "Home screen tips","Documents",
                "Camera",
                "External Storage",
                "Html Viewer",
                "MmsService",
                "Download Manager",
                "Messaging"};
        ListView listView=findViewById(R.id.listView_app);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stringa);
        listView.setAdapter(adapter);
    }
}
