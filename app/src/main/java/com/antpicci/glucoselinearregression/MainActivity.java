package com.antpicci.glucoselinearregression;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MainActivity extends AppCompatActivity {

    TextView equationText, directionsText;
    EditText editTextX, editTextY, findValueText;
    Button calcButton, calcValButton;
    double[] dataX, dataY;
    double[][] data;

    SimpleRegression simpleRegression;
    //Equation
    double slope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        equationText = (TextView) findViewById(R.id.equationText);
        directionsText = (TextView) findViewById(R.id.directionsText);

        editTextX = (EditText) findViewById(R.id.enterX);
        editTextY = (EditText) findViewById(R.id.enterY);
        findValueText = (EditText) findViewById(R.id.findValueText);

        calcValButton = (Button) findViewById(R.id.findValButton);

        calcButton = (Button) findViewById(R.id.calculateButton);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData(v);
            }
        });
    }

    public void readData(View v){
        String stringsX[] = editTextX.getText().toString().split(",");
        String stringsY[] = editTextY.getText().toString().split(",");

        dataX = new double[stringsX.length];
        dataY = new double[stringsY.length];
        data = new double[stringsX.length][2];



        for(int i = 0; i < stringsX.length; i++){
            try{
            dataX[i] = Double.parseDouble(stringsX[i]);
            dataY[i] = Double.parseDouble(stringsY[i]);
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(dataX.length != dataY.length){
            Toast.makeText(getApplicationContext(), "Invalid data: Must have equal x and y values", Toast.LENGTH_SHORT).show();
            return;
        } else if(dataX.length <= 1 || dataY.length <= 1){
            Toast.makeText(getApplicationContext(), "Invalid data: Must have more than one data point", Toast.LENGTH_SHORT).show();
            return;
        }

        for(int j = 0; j < stringsX.length; j++){
            double[] tempData = {dataX[j], dataY[j]};
            data[j] = tempData;
        }
        getRegressionEquation();
    }

    public void getRegressionEquation(){
        simpleRegression = new SimpleRegression(false);
        simpleRegression.addData(data);

        slope = simpleRegression.getSlope();

        equationText.setText("Equation:\n" + "y = " + slope + "x");
        findValueText.setVisibility(View.VISIBLE);
        equationText.setVisibility(View.VISIBLE);
        calcValButton.setVisibility(View.VISIBLE);
        directionsText.setVisibility(View.VISIBLE);

        calcValButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double finalVal;
                finalVal = simpleRegression.predict(
                        Double.parseDouble(findValueText.getText().toString()));
                Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                intent.putExtra("result", finalVal);
                startActivity(intent);
            }
        });

    }


}
