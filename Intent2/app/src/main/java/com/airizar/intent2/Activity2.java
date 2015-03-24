package com.airizar.intent2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Activity2 extends ActionBarActivity {

    public static final String ACTIVITY2 = "ACTIVITY";
    private EditText txtActivity;
    private TextView lblActivity;
    private Button btnSend, btnClose;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        getElementsFromLayout();
        getDataFromPreviousActivity();
        addSendEventListener();

    }

    private void getDataFromPreviousActivity() {
        //el intent que ha hecho que esto se ejecute
        intent = getIntent();
        String text1 = intent.getStringExtra(MainActivity.MESSAGE);
        lblActivity.setText(text1);
        Log.d(ACTIVITY2, text1);
    }

    private void getElementsFromLayout() {
        txtActivity = (EditText) findViewById(R.id.txtActivity2);
        lblActivity = (TextView) findViewById(R.id.lblActivity2);
        btnSend = (Button) findViewById(R.id.btnSend2);
        btnClose = (Button) findViewById(R.id.btnClose);
    }

    private void addSendEventListener() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtActivity.getText().toString();
                if (message.length()>0) {
                    Intent data = new Intent();
                    data.putExtra(MainActivity.MESSAGE, message);
                    setResult(RESULT_OK, data);
                    finish();
                }else{
                    Toast toast= Toast.makeText(Activity2.this, getResources().getString(R.string.no_text),Toast.LENGTH_SHORT);
                    toast.show();
                }



            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
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
