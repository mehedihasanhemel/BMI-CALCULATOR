package com.example.bmicalculater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences( "prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean( "firstStart", true);
        if (firstStart) {
            showStartDialog();
        }
        final EditText edWeg,edHei;
        final TextView txtRes,txtInter;
        Button btnRes,btnReset;

        edWeg=(EditText) findViewById(R.id.edweg);
        edHei= (EditText) findViewById(R.id.edhei);

        txtInter=(TextView) findViewById(R.id.txtinter);
        txtRes=(TextView) findViewById(R.id.txtres);

        btnRes= (Button) findViewById(R.id.btnres);
        btnReset= (Button) findViewById(R.id.btnreset);

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strweg= edWeg.getText().toString();
                String strhei= edHei.getText().toString();

                if(strweg.equals("")){
                    edWeg.setError("Please Enter Your Weight ");
                    edWeg.requestFocus();
                    return;
                }
                if(strhei.equals("")){
                    edHei.setError("Please Enter Your Height");
                    edHei.requestFocus();
                    return;
                }

                float weight = Float.parseFloat(strweg);
                float height = Float.parseFloat(strhei)/100;

                float bmiVlaue = BMICalculate(weight,height);

                txtInter.setText(interpreteBMI(bmiVlaue));
                txtRes.setText("BMI= "+bmiVlaue);


            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edHei.setText("");
                edWeg.setText("");
                txtInter.setText("");
                txtRes.setText("");
            }
        });

    }
    public float BMICalculate(float weight,float height){
        return weight / (height * height);
    }
    public String interpreteBMI(float bmiValue){
        if( bmiValue <16){
            return "Servely Underweight";
        }
        else if(bmiValue <18.5){
            return "Underweight";
        }
        else if(bmiValue < 25){
            return "Normal";
        }
        else if(bmiValue <30){
            return "OverWeight";
        }
        else
            return "Obese";
    }
    private void showStartDialog(){
        new AlertDialog.Builder(this)
                .setTitle("BMI Chart")
                .setMessage("less then 18.5   Under Weight\n"+
                        "18.5 – 24.9        Normal Weight\n"+
                        "25.0 – 29.9        Over Weight\n"+
                        "30.0 – 34.9        Obese")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
        .create().show();
        SharedPreferences prefs = getSharedPreferences( "prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", true);
        editor.apply();

    }
}
