package com.airizar.calculadora;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
//final operaciones
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn0;
    private Button btnSum;
    private Button btnRest;
    private Button btnMul;
    private Button btnDiv;
    private Button btnIgual;
    private Button btn1Punto;
    private TextView txtView;
    private Calculadora calc = new Calculadora();
    private String op = "";
    private String currentNumber="";
    ArrayList<Button> numberButtons;
    ArrayList<Button> operationButtons;
    private View.OnClickListener numberOnClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            setNumber(((Button)v).getText().toString());
        }
    };
    private View.OnClickListener operationOnClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            setOperation(((Button)v).getText().toString());
        }
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberButtons=new ArrayList();
        operationButtons=new ArrayList();
        //this.createControlls();
        getButtonsFromLayout();


    }

    private void getButtonsFromLayout() {
        String[] numbers={"1","2","3","4","5","6","7","8","9","0","/","*","-","+",".","="};
        String[] operations={"+","-","/","*"};
        String id;
        Button btn;
        for(int i=0;i<numbers.length;i++){
            id="btn".concat(numbers[i]);
            btn=(Button)findViewById(getResources().getIdentifier(id,"id",getPackageName()));
            numberButtons.add(btn);

        }
        for(int i=0;i<operations.length;i++){
            id="btn".concat(operations[i]);
            btn=(Button)findViewById(getResources().getIdentifier(id,"id",getPackageName()));
            operationButtons.add(btn);

        }
    }
    public void addEventListeners(){
        for(int i=0;i<numberButtons.size();i++){
            numberButtons.get(i).setOnClickListener(numberOnClickListener);
        }
        for(int i=0;i<operationButtons.size();i++){
            operationButtons.get(i).setOnClickListener(operationOnClickListener);
        }
    }

   /* private void addListeners() {
        ViewGroup group = (ViewGroup) findViewById(R.id.rootLinearLayout);
        ViewGroup vg;
        for (int i = 0; i < group.getChildCount(); i++) {
            vg = (ViewGroup) group.getChildAt(i);
            for (int j = 0; j < vg.getChildCount(); j++) {
                View v = vg.getChildAt(j);
                if (v instanceof Button) v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String btnText = ((Button) v).getText().toString();
                        String oldText = txtView.getText().toString();



                        switch (btnText) {
                            case "/":
                                op = "/";
                                currentNumber=oldText + btnText;
                                txtView.setText(currentNumber);

                                break;
                            case "*":
                                op = "*";
                                currentNumber=oldText + btnText;
                                txtView.setText(currentNumber);
                                calc.setTotal(Double.parseDouble(oldText+btnText));
                                break;
                            case "-":
                                op = "-";
                                currentNumber=oldText + btnText;
                                txtView.setText(currentNumber);
                                calc.setTotal(Double.parseDouble(oldText+btnText));
                                break;
                            case "+":
                                op = "+";
                                currentNumber=oldText + btnText;
                                txtView.setText(currentNumber);
                                calc.setTotal(Double.parseDouble(oldText+btnText));
                                break;
                            case "=":

                                switch (op) {
                                    case "/":
                                        calc.divide(btnText);

                                        break;
                                    case "*":
                                        calc.multiply(btnText);
                                        break;
                                    case "-":
                                        calc.subtract(btnText);
                                        break;
                                    case "+":
                                        calc.add(btnText);
                                        break;
                                }
                                op="";
                                txtView.setText(calc.getTotalString());
                                break;
                            default:
                                txtView.setText(oldText + btnText);
                               *//* if ((oldText+btnText).matches("-?\\d+(\\.\\d+)?")){
                                    calc.setTotal(Double.parseDouble(oldText+btnText));
                                }*//*

                                break;
                        }

//
                    }
                });
            }

        }
    }*/

    private void createControlls() {

        this.btn0 = (Button) findViewById(R.id.btn0);
        this.btn1 = (Button) findViewById(R.id.btn1);
        this.btn2 = (Button) findViewById(R.id.btn2);
        this.btn3 = (Button) findViewById(R.id.btn3);
        this.btn4 = (Button) findViewById(R.id.btn4);
        this.btn5 = (Button) findViewById(R.id.btn5);
        this.btn6 = (Button) findViewById(R.id.btn6);
        this.btn7 = (Button) findViewById(R.id.btn7);
        this.btn8 = (Button) findViewById(R.id.btn8);
        this.btn9 = (Button) findViewById(R.id.btn9);
        this.btnSum = (Button) findViewById(R.id.btnSum);
        this.btnRest = (Button) findViewById(R.id.btnRest);
        this.btnMul = (Button) findViewById(R.id.btnMulti);
        this.btnDiv = (Button) findViewById(R.id.btnDiv);
        this.btnIgual = (Button) findViewById(R.id.btnIgual);
        this.btn1Punto = (Button) findViewById(R.id.btnPunto);
        this.txtView = (TextView) findViewById(R.id.textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
