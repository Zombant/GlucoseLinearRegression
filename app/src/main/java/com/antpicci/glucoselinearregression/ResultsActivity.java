package com.antpicci.glucoselinearregression;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultText = (TextView) findViewById(R.id.resultText);

        Intent intent = getIntent();
        double result = intent.getDoubleExtra("result", 0);
        resultText.setText(String.valueOf(result));

    }

    public void newData(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void sameData(View v){
        finish();
    }
}
