package com.mblhcmute.calculatorproapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mblhcmute.calculatorproapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView tvInput;
    private boolean lastNumeric = false;
    private boolean lastDot = false;
    private boolean singledot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvInput = binding.tvInput;
        tvInput.setText("");
    }

    public void onDigit(View view) {
        tvInput.append(((Button) view).getText());
        lastNumeric = true;
        lastDot = false;
    }

    public void onClear(View view) {
        tvInput.setText("");
        lastNumeric = false;
        lastDot = false;
        singledot = false;
    }

    public void onRemove(View view) {
        String text = tvInput.getText().toString();
        if (text.length() > 1) {
            tvInput.setText(text.subSequence(0, text.length() - 1));
            String tmp = String.valueOf(text.charAt(text.length() - 1));
            text = tvInput.getText().toString();
            String lastChar = String.valueOf(text.charAt(text.length() - 1));
            if (lastChar.equals(".")) {
                lastNumeric = false;
                lastDot = true;
                singledot = true;
            } else if (lastChar.equals("+") || lastChar.equals("-")
                    || lastChar.equals("*") || lastChar.equals("/") || lastChar.equals("%")) {
                lastNumeric = false;
                lastDot = false;
                singledot = false;
            } else if (lastChar.charAt(0) >= '0' && lastChar.charAt(0) <= '9') {
                lastNumeric = true;
                lastDot = false;
                int operatorCount = countOperator(text);
                int dotCount = countDots(text);
                if (text.startsWith("-")) {
                    operatorCount -= 1;
                }
                if (singledot && tmp.equals(".") && (operatorCount>=dotCount)) {
                    singledot = false;
                }
                if (!singledot && !tmp.equals(".") && (dotCount>0) && (operatorCount ==0)) {
                    singledot = true;
                }
            }
        } else {
            onClear(view);
        }
    }

    public void onDecimalPoint(View view) {
        if (!singledot) {
            if (lastNumeric && !lastDot) {
                tvInput.append(".");
                lastNumeric = false;
                lastDot = true;
                singledot = true;
            }
        }
    }

    public int countDots(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                count++;
            }
        }
        return count;
    }


    public void onOperator(View view) {
        String text = tvInput.getText().toString();
        if (((Button) view).getText().equals("-") && text.equals("")) {
            tvInput.append(((Button) view).getText());
            singledot = false;
        }
        if (lastNumeric && !isOperatorAdded(text) && countOperator(text) < 2) {
            tvInput.append(((Button) view).getText());
            lastNumeric = false;
            lastDot = false;
            singledot = false;
        }
    }

    public int countOperator(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '%') {
                count++;
            }
        }
        return count;
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
                } else if (tvValue.contains("%")) {
                    String[] splitValue = tvValue.split("%");
                    String one = splitValue[0];
                    String two = splitValue[1];
                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(Double.toString(Double.parseDouble(one) % Double.parseDouble(two))));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Dữ liệu đầu vào không hợp lệ!", Toast.LENGTH_SHORT).show();
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
                    || value.contains("-")
                    || value.contains("%");
        }
    }

}