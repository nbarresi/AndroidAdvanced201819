package org.its.login.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.androidadvanced201819.R;

import org.its.db.CoordinatesDBHelper;
import org.its.db.entities.Coordinates;

public class TestMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map);

            //MOCK TEST COORDINATES DB
            Coordinates coordinates = new Coordinates();
            coordinates.setIdProfile(1);
            coordinates.setLatitude(-10.74823);
            coordinates.setLongitude(0.86090);
            coordinates.setRadius(100);
            CoordinatesDBHelper dbHelper = new CoordinatesDBHelper(getApplicationContext());
            dbHelper.insertCoordinates(coordinates);
            dbHelper.getAllCoordinates();
            coordinates.setRadius(300);
            dbHelper.updateCoordinates(coordinates);
            dbHelper.getCoordinatesByIdProfile(coordinates.getIdProfile());
            dbHelper.deleteCoordinates(coordinates);
        }
    }


