package org.usfirst.team25.scouting.calculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    // Some variables accessed by all methods in this class
    private Operation currentOperation = null;
    private TextView mainDisplay;
    private RelativeLayout numButtonHolder;


    // The first number when completing a computation
    private double previousNumber;

    // Determines if a second number needs to be inputted (e.g. after an operation is pressed)
    private boolean inputNewNum = false;

    // Enumerates the different operations so they don't need to be defined by a literal int or string
    enum Operation {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, CLEAR
    }

    // Our "main" method of the activity. It's called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previousNumber = Double.parseDouble((String) mainDisplay.getText());

        // Need to link IDs defined in XML to programmatic variables
        // Note that the R(esource) class is automatically generated
        mainDisplay = findViewById(R.id.calcMainTextView);
        numButtonHolder = findViewById(R.id.numberButtonHolder);


        // TODO Note that you'll need to modify this to implement the other operations
        // Creating an array instead of separate variables here makes it easier to perform common functions
        Button[] operationButtons = {findViewById(R.id.addButton), findViewById(R.id.multiplyButton), findViewById(R.id.subtractButton), findViewById(R.id.divideButton)};


        // TODO Add two more number groups for the numbers 4-9 (inclusive)

        // A set of three groups of 3 number buttons
        LinearLayout numButtonGroup = new LinearLayout(getApplicationContext());

        // Programatically setting the layout parameters (analogous to XML ones) here
        RelativeLayout.LayoutParams buttonGroupParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonGroupParams.addRule(RelativeLayout.ABOVE, R.id.miscButtonGroup);
        numButtonGroup.setLayoutParams(buttonGroupParams);

        int currentId = ViewGroup.generateViewId();
        numButtonGroup.setId(currentId);

        for(int i = 1; i<=3; i++) {
            final Button numButton = new Button(getApplicationContext());
            numButton.setText(Integer.toString(i));

            // TODO Can you ensure the three buttons have equal widths?
            // This might require a bit of research on layout parameters...


            // This is what triggers an action upon clicking a view
            // OnClickListeners are very important!
            numButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inputNewNum)
                        mainDisplay.setText(numButton.getText());
                    else {
                        String currentNum = (String) mainDisplay.getText();
                        String newNum = currentNum + numButton.getText();
                        mainDisplay.setText(formatNumber(Double.parseDouble(newNum)));
                    }

                    // Lets the entire class know that a second number has been inputted
                    inputNewNum = false;
                }
            });

            numButtonGroup.addView(numButton);


            numButtonHolder.addView(numButtonGroup);
        }
        // Toasts are one among many ways to easily display feedback to users
        mainDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Text isn't directly editable!", Toast.LENGTH_SHORT).show();
            }
        });


            for (final Button operationButton : operationButtons) {
                operationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((currentOperation != null) && !(inputNewNum)) {
                            executeCalculation();
                        }



                        // We use casework here and in the implementation of executeCalculation()
                        // because the functions are essentially the same aside from the operation
                        if (operationButton.getText().equals(getString(R.string.plus_label))) {
                            currentOperation = Operation.ADDITION;
                        } else if (operationButton.getText().equals("x")) {
                            currentOperation = Operation.MULTIPLICATION;
                        } else if (operationButton.getText().equals("-")) {
                        currentOperation = Operation.SUBTRACTION;
                        } else if (operationButton.getText().equals("/")) {
                            currentOperation = Operation.DIVISION;
                        } else if (operationButton.getText().equals("C")) {
                            currentOperation = Operation.CLEAR;
                        }

                        inputNewNum = true;

                    }
                });
            }

    }

    /** Takes the previous number, selected operation, and current number to compute/display the result
     */
    void executeCalculation(){
        double currentNum = Double.parseDouble((String) mainDisplay.getText());
        double result = 0;


            switch (currentOperation){
                case ADDITION:
                    result = previousNumber + currentNum;
                    break;
                case MULTIPLICATION:
                    result = previousNumber * currentNum;
                    break;
                case SUBTRACTION:
                    result = previousNumber - currentNum;
                    break;
                case DIVISION:
                    result = previousNumber / currentNum;
                    break;
                case CLEAR:
                    result = Double.parseDouble("0");
                    break;
            }




        mainDisplay.setText(formatNumber(result));

        previousNumber = result;

    }

    // TODO Implement this as you see fit
    /** Formats numbers into scientific notation or truncates them
     *  if they are too long, removes leading zeroes, unnecessary decimal places, etc.
     * @param num
     * @return num formatted as a readable and parsable string
     */
    String formatNumber(double num){
        if((int)num == num)
            return Integer.toString((int)num);
        return Double.toString(num);
    }

}
