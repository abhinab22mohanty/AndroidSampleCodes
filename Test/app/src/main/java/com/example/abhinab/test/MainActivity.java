package com.example.abhinab.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Process.*;
import android.view.View;


public class MainActivity extends Activity {
    int pid = android.os.Process.myPid();
    //ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //starting service
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Main Activity Process: "+pid);
                Thread t = Thread.currentThread();
                System.out.println("Main Activity Thread : "+t.getId());
                Intent intent = new Intent(MainActivity.this, HelloService.class);
                startService(intent);
            }
        });


        //service onDestroy callback method will be called
        findViewById(R.id.stop_Service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelloService.class);
                stopService(intent);
            }
        });

    }
}
