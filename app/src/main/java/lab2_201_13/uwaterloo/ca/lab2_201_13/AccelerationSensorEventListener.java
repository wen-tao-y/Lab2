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
    double wmA;
    double[] cosine;


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
        wmA = 0;
        cosine= new double[24];
    }

    public void onSensorChanged(SensorEvent se) {

        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            x[loop] = se.values[0];
            y[loop] = se.values[1];
            z[loop] = se.values[2];
            if (loop == 25) { //Low pass filter
                for (int p = 0; p < 3; p++) {
                    values[p][loop - 1] += (se.values[p] - values[p][loop - 1]) / C;
                }
                loop = 0; //Reset loop to 0 when loop =25
                int divider=0;
                for(int i=0;i<24;i++){
                    wmA+=i*cosine[i];
                    divider+=i+1;
                }
                wmA/=divider;
            }
            //Compute the moving average (wmA), store in wmA   √
            //Update wmA    √
            //Test the value of the wmA
            if(wmA<=0.9&&wmA>=-0.15){
                step+=1;
            }
            
            //Adjust step by 1 √

            for (int p = 0; p < 3; p++) { //Assigns x,y,z values to values array
                values[p][loop] = se.values[p];
            }
            loop++;

            //Compute the cosine
            if (loop >0) {
                cosine[loop-1] = ((values[0][loop]*values[0][loop-1]) + (values[1][loop]*values[1][loop-1]) * (values[2][loop]*values[2][loop-1]));
                cosine[loop-1] = (cosine[loop-1])/Math.sqrt((values[0][loop]*values[0][loop]) + (values[1][loop]*values[1][loop]) * (values[2][loop]*values[2][loop]));
                cosine[loop-1] = (cosine[loop-1])/Math.sqrt((values[0][loop-1]*values[0][loop-1]) + (values[1][loop-1]*values[1][loop-1]) * (values[2][loop-1]*values[2][loop-1]));
            }



            graph.addPoint(se.values);
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