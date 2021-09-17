package com.example.myn3f;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;

public class MainActivity extends AppCompatActivity {


    private ImageButton toggleButton, rr;



    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Button recordB  = findViewById(R.id.imageButtonR);
        //--------------SHARE-----------------------------
        Button shareP  = findViewById(R.id.button);

        shareP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareProfile();
            }
        });

        //--------------------Voice

        rr = findViewById(R.id.imageButtonR);



        rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(i);

            }
        });

        toggleButton = findViewById(R.id.imageButtonT);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasCameraFlash){
                    if(flashOn){
                        flashOn = false;
                        toggleButton.setImageResource(R.drawable.ic_baseline_power_offf);
                        try {
                            flashLightOff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }else {
                        flashOn = true;
                        toggleButton.setImageResource(R.drawable.ic_baseline_power_settings_new_24);
                        try {
                            flashLightOn();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    Toast.makeText(MainActivity.this, "no flash available in your device", Toast.LENGTH_LONG).show();
                }
            }
        });

//---------------HMSADS


        HwAds.init(this);


        BannerView bottomBannerView = findViewById(R.id.hw_banner_vi);
        AdParam adParam = new AdParam.Builder().build();
        bottomBannerView.loadAd(adParam);

        BannerView topBannerView = new BannerView(this);
        topBannerView.setAdId("testw6vs28auh3");
        topBannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        topBannerView.loadAd(adParam);

        ConstraintLayout rootView = findViewById(R.id.root_View);
        rootView.addView(topBannerView);




    }

    private void shareProfile() {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "My Name is Themba Ngoma";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Themba Ngoma");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));


    }




    ///---------SHARE __________END_----------------

//------------------------
    //-------------






    private void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,true);
        Toast.makeText(MainActivity.this, "Flash Light is On", Toast.LENGTH_LONG).show();


    }

    private void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,false);
        Toast.makeText(MainActivity.this, "Flash Light is Off", Toast.LENGTH_LONG).show();

    }



}