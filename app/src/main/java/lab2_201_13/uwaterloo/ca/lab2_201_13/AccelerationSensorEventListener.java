package lab2_201_13.uwaterloo.ca.lab2_201_13;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by ywt on 5/31/16.
 */
public class AccelerationSensorEventListener implements SensorEventListener {
    LineGraphView g1;
    LineGraphView g2;
    TextView output;
    Button but;
    double max;
    int step = 0;
    int state = 0;
    final int wait = 0;
    final int fall = 2;
    final int rise = 1;
    float[] acc=new float[1];

    //A constant for low pass filter
    float c = (float)1.8;

    //Used to store the previous smooth value
    float[] prev = new float[1];

    //The smooth values for linear acceleration sensor
    float[] current = new float[1];


    //Constructor for the Accelerometer class with the parameter of text and a graph
    public AccelerationSensorEventListener(TextView outputView, LineGraphView g1, LineGraphView g2, Button but){
        output = outputView;
        this.g1 = g1;
        this.g2 = g2;
        this.but = but;
        max=0;
    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {



        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {


            prev[0] = current[0];

            acc[0]=(float)Math.sqrt(se.values[0]*se.values[0]+se.values[1]*se.values[1]+se.values[2]*se.values[2]);
            current[0] += (acc[0] - current[0])/c;
            g2.addPoint(current);
            g1.addPoint(acc);
            if(acc[0]>max){
                max=acc[0];
            }
            switch(state){

                case 1:
                    if (prev[0]-current[0]>0.02&&prev[0]<7&&prev[0]>0.7) {
                        this.state = this.fall;

                    }
                    else if(prev[0]-current[0]<0.2){
                        //Nothing
                    }
                    else {
                    this.state = wait;
                    }
                    break;


                case 0:
                    if (prev[0]-current[0] < -0.02) {
                        this.state = this.rise;
                    }
                    break;

                //Count a step and turn the mode back to waiting
                case 2:
                    step++;
                    this.state = wait;
                    break;

                default: break;

            }

            //Creat a button to reset number of steps and turn the mode to waiting.
            but.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    step = 0;
                    state=wait;
                    max=0;
                }
            });

            //Outputs to the text label.
            output.setText(String.format("Step: %d \nState:: %d\nMax: %f", step, state,max));
        }

    }


}