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
    protected TextView stepView;
    protected double[] maxValue;
    protected Button button;
    protected int step;
    protected LineGraphView graph;
    double[] y;
    double[] z;
    double[]x;
    int loop;
    double[][] values;
    final int C;

    public AccelerationSensorEventListener(TextView outputView, TextView stepView, LineGraphView lineGraph, Button but) {
        output = outputView;
        this.stepView=stepView;
        step=0;
        graph = lineGraph;
        button = but;
        maxValue = new double[3];
        x= new double[25];
        y= new double[25];
        z= new double[25];
        loop = 0;
        values = new double [3][25];
        C=5;
    }

    public void onSensorChanged(SensorEvent se) {

        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            x[loop]=se.values[0];
            y[loop]=se.values[1];
            z[loop]=se.values[2];
            if (loop==25) {
                for (int p = 0; p < 3; p++) {
                    values[p][loop - 1] += (se.values[p] - values[p][loop - 1]) / C;
                }
            }
                loop=0;
            }
            for (int p=0;p<3;p++) {
                values[p][loop]=se.values[p];
            }


            graph.addPoint(se.values);
            String s = String.format("Accelerometer: \nx: %f, y: %f, z:%f", se.values[0],se.values[1],se.values[2]);
            output.setText(s);
            String sm = String.format("Steps: %d",step);
            stepView.setText(sm);
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}