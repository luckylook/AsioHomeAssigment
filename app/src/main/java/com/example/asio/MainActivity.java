package com.example.asio;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final double MIN_VALUE = 1.01;
    private static final int MAX_VALUE = 3;
    private TextInputEditText userInputBagWeight;
    Button insertButton, startButton, resetButton;
    TextView bagsWeightTextView, resultTextView, errorTextView;
    AlgorithmSolution algorithmSolution;
    List<Double> bags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInputBagWeight = findViewById(R.id.bag_weight_text_input_edittext);
        insertButton = findViewById(R.id.insert_bag_weight);
        startButton = findViewById(R.id.start_button);
        resetButton = findViewById(R.id.reset_button);
        bagsWeightTextView = findViewById(R.id.bags_weight_text_view);
        resultTextView = findViewById(R.id.result_text_view);
        errorTextView = findViewById(R.id.error_text);


        bagsWeightTextView.setMovementMethod(new ScrollingMovementMethod());
        resultTextView.setMovementMethod(new ScrollingMovementMethod());

        algorithmSolution = new AlgorithmSolution();

        insertButton.setOnClickListener((view) -> {
            insertNumber();
        });

        startButton.setOnClickListener((view) -> {
            clearErrorText();
            calculate();
        });

        resetButton.setOnClickListener((view) -> {
            reset();
        });

    }

    private void insertNumber() {
        clearErrorText();
        Editable editable = userInputBagWeight.getText();
        if (editable == null) {
            showUiError(getString(R.string.error_empty_field));
            return;
        }

        String value = userInputBagWeight.getText().toString();
        try {
            double number = Double.parseDouble(value);
            if (number >= MIN_VALUE && number <= MAX_VALUE) {
                addToList(number);
                showList();
            } else {
                showUiError(getString(R.string.error_min_max));
            }
        } catch (Exception e) {
            showUiError(getString(R.string.error_value_must_be_double));
        }
        clearInputUser();
    }

    private void showList() {
        bagsWeightTextView.setText("");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for (double bag : bags) {
            stringBuilder.append(bag);
            stringBuilder.append(" ,");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(" ]");
        bagsWeightTextView.setText(stringBuilder.toString());
    }

    private void addToList(double number) {
        bags.add(number);
    }


    private void showUiError(String message) {
        errorTextView.setText(message);
    }

    private void clearErrorText() {
        errorTextView.setText("");
    }

    private void calculate() {
        List<List<Double>> result = algorithmSolution.getMinimalWays(bags);
        String stringBuilder = String.format(Locale.getDefault(), "%s%d", getString(R.string.number_of_tours), result.size()) + "\n" + result;
        resultTextView.setText(stringBuilder);
    }

    private void clearInputUser() {
        userInputBagWeight.setText("");
    }

    private void clearBagsText() {
        bagsWeightTextView.setText("");
    }

    private void clearResult() {
        resultTextView.setText("");
    }

    private void reset() {
        bags.clear();
        clearErrorText();
        clearBagsText();
        clearInputUser();
        clearResult();
    }
}