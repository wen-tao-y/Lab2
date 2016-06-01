package lab2_201_13.uwaterloo.ca.lab2_201_13;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Button;
import android.widget.TextView;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by ywt on 5/31/16.
 */
public class AccelerationSensorEventListener implements SensorEventListener {
    protected TextView output;
    protected TextView maxOutput;
    protected double[] maxValue;
    protected Button button;

    protected LineGraphView graph;

    public AccelerationSensorEventListener(TextView outputView, LineGraphView lineGraph, Button but) {
        output = outputView;

        graph = lineGraph;
        button = but;
        maxValue = new double[3];

    }
    @Override
    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            for(int i=0;i<3;i++){
                if(maxValue[i]<Math.abs(se.values[i])){
                    maxValue[i]=Math.abs(se.values[i]);
                }
            }
            graph.addPoint(se.values);
            String s = String.format("Accelerometer: \nx: %f, y: %f, z:%f", se.values[0],se.values[1],se.values[2]);
            output.setText(s);
            String sm = String.format("Max: \nx: %f, y: %f, z: %f", maxValue[0],maxValue[1],maxValue[2]);
            maxOutput.setText(sm);
        }
    }
}