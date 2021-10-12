package edu.qc.seclass.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TipCalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void tipcalculate(View view){
        //get the user input of check amount and party size
        String checkAmount = ((TextView)findViewById(R.id.checkAmountValue)).getText().toString();
        String partySize = ((TextView)findViewById(R.id.partySizeValue)).getText().toString();

        //check that the user has entered anything
        if(checkAmount.isEmpty() || partySize.isEmpty()){
            Toast.makeText(getApplicationContext(),"Empty value!", Toast.LENGTH_SHORT).show();
        }
        //check the amount and party size that user has entered, it should not be 0 and negative
        else if(Integer.parseInt(checkAmount) <= 0  || Integer.parseInt(partySize) <= 0){
            Toast.makeText(getApplicationContext(),"Incorrect values!", Toast.LENGTH_SHORT).show();
        }
        //once we pass all condition, we need to calculate the tip value
        else{
            int checkValue = Integer.parseInt(checkAmount);
            int partyValue = Integer.parseInt(partySize);
            //the check equally distributed to every person
            int equally = checkValue / partyValue;

            //calculate each tip percentage and round to integer
            int fifteen = (int) Math.round(equally * 0.15);
            int twenty = (int) Math.round(equally * 0.20);
            int twentyfive = (int) Math.round(equally * 0.25);

            //display the tip calculation result to user
            ((TextView)findViewById(R.id.fifteenPercentTipValue)).setText(Integer.toString(fifteen));
            ((TextView)findViewById(R.id.twentyPercentTipValue)).setText(Integer.toString(twenty));
            ((TextView)findViewById(R.id.twentyfivePercentTipValue)).setText(Integer.toString(twentyfive));

            //display the total amount result to user
            ((TextView)findViewById(R.id.fifteenPercentTotalValue)).setText(Integer.toString(fifteen+equally));
            ((TextView)findViewById(R.id.twentyPercentTotalValue)).setText(Integer.toString(twenty+equally));
            ((TextView)findViewById(R.id.twentyfivePercentTotalValue)).setText(Integer.toString(twentyfive+equally));
        }
    }

    public void clearAll(View view){
        ((TextView)findViewById(R.id.checkAmountValue)).setText("");
        ((TextView)findViewById(R.id.partySizeValue)).setText("");
        ((TextView)findViewById(R.id.fifteenPercentTipValue)).setText("");
        ((TextView)findViewById(R.id.twentyPercentTipValue)).setText("");
        ((TextView)findViewById(R.id.twentyfivePercentTipValue)).setText("");
        ((TextView)findViewById(R.id.fifteenPercentTotalValue)).setText("");
        ((TextView)findViewById(R.id.twentyPercentTotalValue)).setText("");
        ((TextView)findViewById(R.id.twentyfivePercentTotalValue)).setText("");
    }
}