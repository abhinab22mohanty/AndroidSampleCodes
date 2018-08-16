package com.example.abhinab.assignment2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends Activity implements SensorEventListener{

//    static int FILTER_DATA_MIN_TIME = 1000; // ms
//    long last = System.currentTimeMillis();
    DatabaseHelper sensorDb;
    private Sensor accSensor;
    private SensorManager AM;
    private int runCount = 0;
    private long startTime = 0;
    private long currentTime = 0;
    boolean runPressed = false;
    boolean stopPressed = false;
    static List<DataPoint> pointArray1 = Collections.synchronizedList(new ArrayList<DataPoint> ());
    static List<DataPoint> pointArray2 = Collections.synchronizedList(new ArrayList<DataPoint> ());
    static List<DataPoint> pointArray3 = Collections.synchronizedList(new ArrayList<DataPoint> ());
    //    static List<DataPoint> pointArray = new CopyOnWriteArrayList<DataPoint>();
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series1;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private float sensorX = 0;
    private float sensorY = 0;
    private float sensorZ = 0;
    private  int Timer = 0;
//    private  int Ycount = 0;
//    private  int Zcount = 0;
    DataPoint newPoint1;
    DataPoint newPoint2;
    DataPoint newPoint3;
    Thread graphThread;
    String Name,Sex;
    int Id,Age;

    private Button btnRun;
    private Button btnStop;
    private Button btnBack;
    private Button btnStore;


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

//        if ((System.currentTimeMillis() - last) > FILTER_DATA_MIN_TIME) {
//            last = System.currentTimeMillis();
            sensorX = sensorEvent.values[0];
            sensorY = sensorEvent.values[1];
            sensorZ = sensorEvent.values[2];
//        }





//        System.out.println("pointArray1"+pointArray1.get(pointArray1.size()-1));
//        System.out.println("Size pointArray1:"+pointArray1.size());
//        System.out.println("pointArray2"+pointArray2.get(pointArray2.size()-1));
//        System.out.println("Size pointArray2:"+pointArray2.size());
//        System.out.println("pointArray3"+pointArray3.get(pointArray3.size()-1));
//        System.out.println("Size pointArray3:"+pointArray2.size());
////        pointArray1.add(newPoint1);
////        pointArray2.add(newPoint2);
////        pointArray3.add(newPoint3);
//        series1.appendData(newPoint1,true,20);
//        series2.appendData(newPoint2,true,20);
//        series3.appendData(newPoint3,true,20);



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set buttons
        btnRun = (Button) findViewById(R.id.btnRun);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnStore = (Button) findViewById(R.id.btnStore);


        //Initialize Database Helper
        sensorDb = new DatabaseHelper(this);


        //Intent catch
        Intent calledActivity = getIntent();
        Id = Integer.parseInt(calledActivity.getExtras().get("Id").toString());
        Name = calledActivity.getExtras().getString("Name");
        Age = Integer.parseInt(calledActivity.getExtras().get("Age").toString());
        Sex = calledActivity.getExtras().getString("Sex");

        System.out.println(Id);
        System.out.println(Name);
        System.out.println(Age);
        System.out.println(Sex);

        //We get the text inserted for run and stop activation
        //


        // we get graph view instance
        final GraphView graph = (GraphView) findViewById(R.id.graph);


        //Sensor Manager settings
        AM = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Accelerometer Sensor
        accSensor = AM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        //Register Sensor listener
        AM.registerListener(this,accSensor,AM.SENSOR_DELAY_NORMAL);

        // data
        series1 = new LineGraphSeries<DataPoint>();
        series1.setColor(Color.RED);
        series1.setDrawDataPoints(true);
        series2 = new LineGraphSeries<DataPoint>();
        series2.setColor(Color.BLUE);
        series2.setDrawDataPoints(true);
        series3 = new LineGraphSeries<DataPoint>();
        series3.setColor(Color.GREEN);
        series3.setDrawDataPoints(true);

        // customize viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinY(-15);
        viewport.setMaxY(15);
        viewport.setMinX(0);
        viewport.setMaxX(10);

        viewport.scrollToEnd();
        viewport.setScalable(true);

        // legend
        series1.setTitle("X");
        series2.setTitle("Y");
        series3.setTitle("Z");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);




//        lblId.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Toast.makeText(getApplicationContext(),"Required Field",Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//                btnStop.setEnabled((!lblId.getText().toString().trim().isEmpty()) && (!lblName.getText().toString().trim().isEmpty()));
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });
//
//
//        lblName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Toast.makeText(getApplicationContext(),"Required Field",Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                btnRun.setEnabled((!lblId.getText().toString().trim().isEmpty()) && (!lblName.getText().toString().trim().isEmpty()));
//                btnStop.setEnabled((!lblId.getText().toString().trim().isEmpty()) && (!lblName.getText().toString().trim().isEmpty()));
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });




        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                runPressed = !runPressed;
//                if (runCount == 1 || stopPressed)
//
//                {

                if(runPressed || stopPressed){
                    System.out.println("<<<<<<<< INSIDE RUNNNNNNNNNN >>>>>>>");
                    Toast.makeText(getApplicationContext(), "Running", Toast.LENGTH_SHORT).show();

                    btnStore.setEnabled(runPressed && stopPressed);
                    //runCount++;
                    btnBack.setEnabled(false);
                    graph.addSeries(series1);
                    graph.addSeries(series2);
                    graph.addSeries(series3);

                    stopPressed = false;
                }




//                else if(!runPressed)
//                    Toast.makeText(getApplicationContext(),"Pausing",Toast.LENGTH_SHORT).show();
                    if (runPressed) {

                        System.out.println("<<<<<<<< INSIDE THREAD CREATE >>>>>>>");


                        graphThread = new Thread(new Runnable() {

                            @Override
                            public void run() {


                                // we add new entries
                                for (int i = 0; i >= 0; i++) {
                                    if (runPressed) {


                                    //pointArray.add(randPoint);
                                    runOnUiThread(new Runnable() {


                                        @Override
                                        public void run() {
                                            if (!stopPressed) {


//                                            currentTime = System.currentTimeMillis() - startTime;
//                                            int seconds = (int) (currentTime / 1000);
//                                            //int minutes = seconds / 60;
//                                            seconds = seconds % 60;
                                                updateData(Timer++);

                                            } else
                                                return;
                                        }
                                    });

                                    // sleep to slow down the add of entries
                                    try {
                                        Thread.sleep(1000);

                                    } catch (InterruptedException e) {
                                        // manage error ...
                                    }

                                        }

                                else
                                       continue;
                                }


                            }
                        });

                        graphThread.start();

                    }

                    if (!runPressed) {
                        System.out.println("CAN'T CATCH ME");
                        runPressed = true;
                        //return;

                    }


//                if(!runPressed)
//                {
//                    try {
//                        graphThread.join();
//
//                    } catch (InterruptedException e) {
//                        System.out.println("Error in join");
//                    }
//
//                }
                }
//            }
        });



        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPressed = true;

                System.out.println("X-Array: "+pointArray1);

                System.out.println("Y-Array: "+pointArray2);

                System.out.println("Z-Array "+pointArray3);





                btnStore.setEnabled(runPressed && stopPressed);
                graph.removeAllSeries();
                Toast.makeText(getApplicationContext(),"Stopping",Toast.LENGTH_SHORT).show();

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    graphThread.join();
//                } catch (InterruptedException e) {
//                    System.out.println("Exception in Join");
//                }

//                onDestroy();

                getIntentBack();


            }
        });


        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println((pointArray1.get(pointArray1.size()-1).getY()));
                System.out.println((pointArray2.get(pointArray2.size()-1).getY()));
                System.out.println((pointArray3.get(pointArray3.size()-1).getY()));
                String table_name = Name +"_"+Id+"_"+Age+"_"+Sex;

                new StoreAsyncTask (MainActivity.this).execute(table_name);




            }
        });








    }

    private void updateData(int time){

        //newPoint = new DataPoint(lastX++, RANDOM.nextDouble() * 10d);
        newPoint1 = new DataPoint(time , sensorX);

        newPoint2 = new DataPoint(time, sensorY);

        newPoint3 = new DataPoint(time, sensorZ);
        pointArray1.add(newPoint1);
        pointArray2.add(newPoint2);
        pointArray3.add(newPoint3);

        System.out.println("X: "+pointArray1.get(pointArray1.size()-1));
//        System.out.println("Size pointArray1: "+pointArray1.size());
        System.out.println("Y: "+pointArray2.get(pointArray2.size()-1));
//        System.out.println("Size pointArray2: "+pointArray2.size());
        System.out.println("Z: "+pointArray3.get(pointArray3.size()-1));
//        System.out.println("Size pointArray3: "+pointArray2.size());
//        pointArray1.add(newPoint1);
//        pointArray2.add(newPoint2);
//        pointArray3.add(newPoint3);
        series1.appendData(newPoint1,true,20);
        series2.appendData(newPoint2,true,20);
        series3.appendData(newPoint3,true,20);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        pointArray1.clear();
        pointArray2.clear();
        pointArray3.clear();


        try {
            graphThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void getIntentBack() {

//        if(graphThread.isAlive()) {
//            try {
//                graphThread.join();
//            } catch (InterruptedException e) {
//                System.out.println("Exception in Thread Join");
//            }
//        }
        Intent getInfoIntent = new Intent(this,EnterScreen.class);
        startActivity(getInfoIntent);
//        onDestroy();
    }


     public class StoreAsyncTask extends AsyncTask<String,Void,Void> {
        private ProgressDialog dialog;
         boolean createAndInsert;

        public StoreAsyncTask(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
//            dialog.setMessage("Storing in your SDCard. Please Wait");
//            dialog.show();
        }

        @Override
        protected Void doInBackground(String... args) {
            // do background work here
            String table_name = args[0];
            createAndInsert = sensorDb.createAndInsertTable(table_name,pointArray1,pointArray2,pointArray3);





            sensorDb.showData(table_name);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // do UI work here
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }

            btnBack.setEnabled(true);

            if(createAndInsert){
                Toast.makeText(getApplicationContext(),"Data Inserted Successfully",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext(),"Sorry!! Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }




}






