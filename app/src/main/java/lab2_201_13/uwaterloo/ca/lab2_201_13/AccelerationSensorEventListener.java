package lab2_201_13.uwaterloo.ca.lab2_201_13;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
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
    protected TextView output;
    protected TextView stepView;
    protected double[] maxValue;
    protected Button button;
    protected int step;
    protected LineGraphView graph;


    double[] values;

    double wmA;
    double[] cosine;


    public AccelerationSensorEventListener(TextView outputView, TextView stepView, LineGraphView lineGraph, Button but) {
        output = outputView;
        this.stepView=stepView;
        step=0;
        graph = lineGraph;
        button = but;
        maxValue = new double[3];


        values = new double[]{0,0,0};


        wmA = 0;
        cosine= new double[10];
        for(int i=0;i<10;i++){
            cosine[i]=0;
        }
    }

    public void onSensorChanged(SensorEvent se) {

        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            wmA=0;
            double cos=0;

            cos=(se.values[0]*values[0]+se.values[1]*values[1]+se.values[2]*values[2])/
                    Math.sqrt(se.values[0]*se.values[0]+se.values[1]*se.values[1]+se.values[2]*se.values[2])/
                    Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2]);

            //compute cos
            for(int i=0;i<9;i++){
                cosine[i]=cosine[i+1];
            }
            cosine[9]=cos;
            //update cosing[]  (Queue?)


            for(int i=0;i<10;i++){
                wmA=wmA+(double)i*cosine[i];
                Log.d("wma: ",String.valueOf(wmA));//debug wma

                }
                wmA/=(double)55;
            Log.d("cos: ",String.valueOf(cos));
            //compute wma √

                Log.d("wmaAfter: ",String.valueOf(wmA));//debug wma

                if(wmA<=0){
                    step+=1;
                }
                //update step

            //Prepare for next iteration:
            //1. store se.values
            for(int i=0;i<3;i++){
                values[i]=se.values[i];
            }
            //2. store wmA






            String s = String.format("Accelerometer: \nx: %f, y: %f, z:%f", se.values[0], se.values[1], se.values[2]);
            output.setText(s);
            String sm = String.format("Steps: %d", step);
            stepView.setText(sm);





        }

    }

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

        }

}