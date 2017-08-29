package com.mycompany.gyroproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import de.uzl.itm.ncoap.application.client.ClientCallback;
import de.uzl.itm.ncoap.application.client.CoapClient;
import de.uzl.itm.ncoap.message.CoapRequest;
import de.uzl.itm.ncoap.message.CoapResponse;
import de.uzl.itm.ncoap.message.MessageCode;
import de.uzl.itm.ncoap.message.MessageType;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    public static final String TAG = "MainActivity";

    SensorManager sensorManager;
    private Sensor orientationSensor;
    List<Sensor> sensors = null;
    private String sensorName;
    private double[] orientationSensorValues;
    private CoapClient coapClient;
    private CoapRequest coapRequest;
    private JSONObject jsonObject;
    private String json;
    public static String ipAdress = "192.168.3.133";
    private String buttonStatus="off";

    TextView readingYaw, readingPitch, readingRoll,txtSensorName,txtButtonStatus,txtMotionStatus;
    Compass myCompass;
    EditText editTextIpAddress;

    private float roll ;
    private float pitch;
    private float yaw;

    private float yawValue;
    private float rollValue;
    private float pitchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readingYaw = (TextView)findViewById(R.id.yaw);
        readingPitch = (TextView)findViewById(R.id.pitch);
        readingRoll = (TextView)findViewById(R.id.roll);
        txtSensorName=(TextView)findViewById(R.id.txtSensorName);
        editTextIpAddress=(EditText)findViewById(R.id.editTextIpAddress);
                txtButtonStatus=(TextView)findViewById(R.id.txtButtonStatus);
        myCompass = (Compass)findViewById(R.id.mycompass);
        txtMotionStatus=(TextView)findViewById(R.id.txtMotionStatus);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorName = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION).getName();
        txtSensorName.setText(sensorName);

        coapClient = new CoapClient();

    }


    @Override
    protected void onResume() {

        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        // sensorManager.registerListener(this, sensors.get(13), SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {

        super.onPause();
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            pitchValue=180-event.values[1]*180;

        }
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
            rollValue=event.values[1]+180;
            yawValue=360-event.values[0];
        }






        readingYaw.setText("Yaw: " + yawValue);
        readingPitch.setText("Pitch: " + pitchValue);
        readingRoll.setText("Roll: " + rollValue);

          myCompass.update(yawValue);
        axisTransmission(rollValue,pitchValue,yawValue);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void axisTransmission(float rollTemp, float pitchTemp, float yawTemp) {
        roll = rollTemp;
        pitch = pitchTemp;
        yaw = yawTemp;



        double yawValue;// 여기부터시작


        try {
            coapRequest = new CoapRequest(
                    MessageType.NON,
                    MessageCode.POST,
                    new URI("coap://"+ipAdress+":5683/gyroscope?sensor=gyroscope&yawAngle="+yaw+"&pitchAngle="+pitch+"&rollAngle="+roll)
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        coapClient.sendCoapRequest(
                coapRequest,
                new InetSocketAddress(ipAdress, 5683),
                new ClientCallback() {
                    @Override
                    public void processCoapResponse(CoapResponse coapResponse) {
                        String message = coapResponse.getContent().toString(Charset.forName("UTF-8"));
                        Log.i("mylog", message);
                        txtMotionStatus.setText(message);
                    }
                }
            );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        coapClient.shutdown();
    }

    public void handleBtnTrigger(View view){

        if(buttonStatus.equals("on")){
            buttonStatus="off";



        }else{
            buttonStatus="on";

        }
        txtButtonStatus.setText(buttonStatus);
        try {
            coapRequest = new CoapRequest(
                    MessageType.NON,
                    MessageCode.GET,
                    new URI("coap://"+ipAdress+":5683/button?sensor=button&status="+buttonStatus)
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        coapClient.sendCoapRequest(
                coapRequest,
                new InetSocketAddress(ipAdress, 5683),
                new ClientCallback() {
                    @Override
                    public void processCoapResponse(CoapResponse coapResponse) {
                        String message = coapResponse.getContent().toString(Charset.forName("UTF-8"));
                        Log.i("mylog", message);

                    }
                }
        );

    }


    public void handleIPAddress(View view){
       // onDestroy();
        Editable ip=editTextIpAddress.getText();
        ipAdress=String.valueOf(ip);
        coapClient.shutdown();
        coapClient = new CoapClient();

    }


}
