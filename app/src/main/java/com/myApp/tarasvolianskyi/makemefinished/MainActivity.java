package com.myApp.tarasvolianskyi.makemefinished;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flMainActivity, mainFragment, "listView").commit();

        MobileAds.initialize(this, "ca-app-pub-3623739700338204~3749144762");
     //   mAdView = (AdView) findViewById(R.id.adView);
       // AdRequest adRequest = new AdRequest.Builder().build();
       // mAdView.loadAd(adRequest);
    }


}
