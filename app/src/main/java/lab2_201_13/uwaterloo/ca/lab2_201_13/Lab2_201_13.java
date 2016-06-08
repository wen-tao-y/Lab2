package lab2_201_13.uwaterloo.ca.lab2_201_13;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;

public class Lab2_201_13 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_201_13);
        TextView tv1 = (TextView) findViewById(R.id.label1);

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        Button button = (Button) findViewById(R.id.b);
        ll.setOrientation(LinearLayout.VERTICAL);

        LineGraphView g1 = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x","y","z"));
        ll.addView(g1); g1.setVisibility(View.VISIBLE);
        LineGraphView g2 = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x","y","z"));
        ll.addView(g2); g2.setVisibility(View.VISIBLE);

        SensorManager sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometerSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        AccelerationSensorEventListener a = new AccelerationSensorEventListener(tv1,g1,g2,button);
        sensorManager.registerListener(a, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }
}
