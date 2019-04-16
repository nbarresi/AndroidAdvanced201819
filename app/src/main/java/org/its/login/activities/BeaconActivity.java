package org.its.login.activities;


import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidadvanced201819.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.its.db.entities.WiFiPoint;
import org.its.login.adapters.BeaconListRecyclerAdapter;
import org.its.login.adapters.WifiListRecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeaconActivity extends AppCompatActivity
        implements BeaconConsumer, RangeNotifier {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BeaconListRecyclerAdapter adapter;
    protected static final String TAG = "RangingActivity";
    private BeaconManager mBeaconManager;
    private Button btnDisplay;
    private ArrayList<Beacon> beaconList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        recyclerView = (RecyclerView) findViewById(R.id.rv_beacon);
        recyclerView.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        setImpostaButtonListener();

        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        // In this example, we will use Eddystone protocol, so we have to define it here
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
        // Binds this activity to the BeaconService
        mBeaconManager.bind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        // Encapsulates a beacon identifier of arbitrary byte length
        ArrayList<Identifier> identifiers = new ArrayList<>();

        // Set null to indicate that we want to match beacons with any value
        identifiers.add(null);
        // Represents a criteria of fields used to match beacon
        Region region = new Region(("AllBeaconsRegion"), identifiers);
        try {
            // Tells the BeaconService to start looking for beacons that match the passed Region object
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // Specifies a class that should be called each time the BeaconService gets ranging data, once per second by default
        mBeaconManager.addRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        if (beacons.size() > 0) {
            Log.i(TAG, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
            setRecyclerAdapter(beacons);
            beaconList = new ArrayList<>(beacons);
        }
    }


    public void setRecyclerViewLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerAdapter(Collection<Beacon> beacons) {
        adapter = new BeaconListRecyclerAdapter(beacons);
        recyclerView.setAdapter(adapter);
    }

    private void setImpostaButtonListener() {
        btnDisplay = (Button) findViewById(R.id.imposta_wifi);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beaconList.size() > 0) {
                    Intent returnBeaconIntent = new Intent();
                    returnBeaconIntent.putExtra("ADD_BEACON_REQUEST_CODE", (Serializable) beaconList);
                    setResult(RESULT_OK, returnBeaconIntent);
                    finish();
                }
            }
        });
    }
}
