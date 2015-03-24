package com.airizar.intent2;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {
    private static final int SHOW_ACTIVITY2 = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final String MESSAGE = "text_activity1";

    private EditText txtActivity1;
    private Button btnSend;
    private TextView lblActivity1;
    private ImageView imgPhoto;
    private Button btnPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getElementsFromLayout();
        addEventListener();
    }

    private void addEventListener() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtActivity1.getText().toString();
                if (message.length()>0) {
                    //en vez de utilizar MainActivity.this para pasarlo como contexto, se puede utilizar
                    //getApplicationContext(), asi en caso de hacer un listener generico, esta parte tambien sera generica

                    Intent intent = new Intent(MainActivity.this, Activity2.class);
                    intent.putExtra(MESSAGE, message);
                    startActivityForResult(intent, SHOW_ACTIVITY2);
                }else{
                    Toast toast= Toast.makeText(MainActivity.this, getResources().getString(R.string.no_text),Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //le pregunta al gestor de paquetes si hay alguna actividad capaz de sacar fotos
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }else{
                    Toast toast= Toast.makeText(MainActivity.this, getResources().getString(R.string.no_photoApp),Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void getElementsFromLayout() {
        txtActivity1 = (EditText) findViewById(R.id.txtActivity1);
        lblActivity1 = (TextView) findViewById(R.id.lblActivity1);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        btnSend = (Button) findViewById(R.id.btnSend);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            Log.d(Activity2.ACTIVITY2, "Canceled activity");
        } else {
            switch (requestCode) {
                case SHOW_ACTIVITY2:
                    lblActivity1.setText(data.getStringExtra(MESSAGE).toString());
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    imgPhoto.setImageBitmap((android.graphics.Bitmap) data.getExtras().get("data"));
                    break;
            }
        }
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
