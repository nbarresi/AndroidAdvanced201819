package org.its.login.activities;


import android.bluetooth.le.ScanResult;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.androidadvanced201819.R;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeaconActivity extends AppCompatActivity
        implements BeaconConsumer, RangeNotifier
{

    //TODO SetResult
    private void setResultValueOnView(ScanResult result) {
        TextView nomeBeacon = findViewById(R.id.nome_beacon);
        TextView inirizzoBeacon = findViewById(R.id.indirizzo_beacon);
        TextView distanzaBeacon = findViewById(R.id.distanza_beacon);

    }


        protected static final String TAG = "RangingActivity";
        private BeaconManager mBeaconManager;
    private List<String> listBeaconId = new ArrayList<>();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_beacon);
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
            for (Beacon beacon: beacons){
                if (!listBeaconId.contains(beacon.getBluetoothAddress())){
                    listBeaconId.add(beacon.getBluetoothAddress());
                }
            }            Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");

        }
    }
}
