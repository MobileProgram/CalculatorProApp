package com.mblhcmute.calculatorproapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mblhcmute.calculatorproapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView tvInput;
    private boolean lastNumeric = false;
    private boolean lastDot = false;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvInput = binding.tvInput;
    }

    public void onDigit(View view) {
        tvInput.append(((Button) view).getText());
        lastNumeric = true;
        lastDot = false;
    }

    public void onClear(View view) {
        tvInput.setText("");
    }
    public void onRemove(View view) {
        if (tvInput.getText().length() > 0) {
            tvInput.setText(tvInput.getText().subSequence(0, tvInput.getText().length() - 1));
        }
    }
    public void onDecimalPoint(View view) {
        if (lastNumeric && !lastDot) {
            tvInput.append(".");
            lastNumeric = false;
            lastDot = true;
        }
    }

    public void onOperator(View view) {
        if (lastNumeric && !isOperatorAdded(tvInput.getText().toString())) {
            tvInput.append(((Button) view).getText());
            lastNumeric = false;
            lastDot = false;
        }
    }

    public void onEqual(View view) {
        if (lastNumeric) {
            String tvValue = tvInput.getText().toString();
            String prefix = "";
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-";
                    tvValue = tvValue.substring(1);
                }
                if (tvValue.contains("-")) {
                    String[] splitValue = tvValue.split("-");
                    String one = splitValue[0];
                    String two = splitValue[1];
                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(Double.toString(Double.parseDouble(one) - Double.parseDouble(two))));
                } else if (tvValue.contains("+")) {
                    String[] splitValue = tvValue.split("\\+");
                    String one = splitValue[0];
                    String two = splitValue[1];
                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(Double.toString(Double.parseDouble(one) + Double.parseDouble(two))));
                } else if (tvValue.contains("*")) {
                    String[] splitValue = tvValue.split("\\*");
                    String one = splitValue[0];
                    String two = splitValue[1];
                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(Double.toString(Double.parseDouble(one) * Double.parseDouble(two))));
                } else if (tvValue.contains("/")) {
                    String[] splitValue = tvValue.split("/");
                    String one = splitValue[0];
                    String two = splitValue[1];
                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(Double.toString(Double.parseDouble(one) / Double.parseDouble(two))));
                }else if (tvValue.contains("%")) {
                    String[] splitValue = tvValue.split("%");
                    String one = splitValue[0];
                    String two = splitValue[1];
                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(Double.toString(Double.parseDouble(one) % Double.parseDouble(two))));
                }
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
        }
    }

    private String removeZeroAfterDot(String result) {
        String value = result;
        if (result.contains(".0")) {
            value = result.substring(0, result.length() - 2);
        }
        return value;
    }

    private boolean isOperatorAdded(String value) {
        if (value.startsWith("-")) {
            return false;
        } else {
            return value.contains("*")
                    || value.contains("/")
                    || value.contains("+")
                    || value.contains("-");
        }
    }

}