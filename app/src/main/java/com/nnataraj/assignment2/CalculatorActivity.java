package com.nnataraj.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {

    private StringBuilder num = new StringBuilder();
    private Integer firstNum;
    private char op = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Calculator);
        setContentView(R.layout.activity_calculator);
    }

    public void onClickNum(View view) {

        if (num.length()>9) {
            Toast.makeText(this, "Maximum number of digits is 10", Toast.LENGTH_SHORT).show();
            return;
        }

        num.append(((Button) view).getText());
        ((TextView) findViewById(R.id.value)).setText(num);
    }

    public void onClickOp(View view) {
        if (num.length() == 0)
            return;
        op = ((Button) view).getText().charAt(0);
        firstNum = Integer.parseInt(num.toString());
        num = new StringBuilder();

    }

    public void onClickRes(View view) {
        op = 0;
        num = new StringBuilder();
        ((TextView) findViewById(R.id.value)).setText("0");
    }

    public void onClickCal(View view) {
        if (num.length() == 0)
            return;

        double result;
        switch (op) {
            case '/':
                result = (double) firstNum / Integer.parseInt(num.toString());
                break;
            case '*':
                result = (double) firstNum * Integer.parseInt(num.toString());
                break;
            case '-':
                result = (double) firstNum - Integer.parseInt(num.toString());
                break;
            case '+':
                result = (double) firstNum + Integer.parseInt(num.toString());
                break;
            case '%':
                result = (double) firstNum % Integer.parseInt(num.toString());
                break;
            default:
                return;
        }
        num = new StringBuilder();
        op = 0;
        ((TextView) findViewById(R.id.value)).setText(String.valueOf(result));
    }
}
