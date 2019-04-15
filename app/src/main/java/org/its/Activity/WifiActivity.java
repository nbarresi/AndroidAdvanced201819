package org.its.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidadvanced201819.R;

import org.its.UI.WifiArrayAdapter;
import org.its.db.dao.WifiDao;
import org.its.db.entities.ListWifiConnection;
import org.its.db.entities.WifiConnection;
import org.its.utilities.RequestCodes;
import org.its.utilities.ResultsCode;
import org.its.utilities.StringCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity {

    private List<ScanResult> list = new ArrayList<>();
    private WifiManager wifiManager;
    private WifiArrayAdapter nfcAdapter;
    private List<ScanResult> trovati;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_list_layout);
        final ListView listView = (ListView) findViewById(R.id.wifiList);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Intent currentIntent = getIntent();
        final int idForUpdate = currentIntent.getIntExtra(StringCollection.isUpdate, -1);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                trovati = wifiManager.getScanResults();

                for (ScanResult item : trovati) {
                    list.add(item);
                }
                if (list.size() > 0) {
                    nfcAdapter = new WifiArrayAdapter(context, list);
                    listView.setAdapter(nfcAdapter);
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();

        Button salva = (Button) findViewById(R.id.impostaWifi);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wifiIntent = new Intent(WifiActivity.this, DetailActivity.class);
                List<WifiConnection> listToSave = new ArrayList<>();

                for (ScanResult wifiConnectionSelected : list) {
                    WifiConnection wifiConnection = new WifiConnection();
                    wifiConnection.setPower(wifiConnectionSelected.level);
                    wifiConnection.setName(wifiConnectionSelected.SSID);
                    wifiConnection.setBSSID(wifiConnectionSelected.BSSID);
                    listToSave.add(wifiConnection);
                }

                WifiDao wifiDao = new WifiDao();
                if (idForUpdate != -1) {
                    wifiDao.openConn(getApplicationContext());
                    wifiDao.deleteListForAProfile(idForUpdate);
                    wifiDao.closeConn();
                }
                wifiIntent.putExtra(ResultsCode.WIFI_RESULT, new ListWifiConnection(listToSave));
                setResult(Activity.RESULT_OK, wifiIntent);
                finish();
            }
        });
    }


}
