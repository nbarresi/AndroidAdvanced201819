package org.its.login.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.androidadvanced201819.R;

import org.its.db.entities.Profile;
import org.its.login.adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<Profile> profiles = new ArrayList<>();
    private RecyclerView recyclerView;
    RecyclerAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mock
        Profile p = new Profile();
        p.setName("swag");
        profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);profiles.add(p);

        setContentView(R.layout.listlayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        setRecyclerAdapter();
    }

    private void setRecyclerAdapter() {
       adapter  = new RecyclerAdapter(profiles);
        recyclerView.setAdapter(adapter);
    }

    public void setRecyclerViewLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


}
