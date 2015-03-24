package com.airizar.intent2.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

/**
 * Created by cursomovil on 24/03/15.
 */

public class MyOnClick implements View.OnClickListener {

    public interface SendDataListenerInterface {
        public void sendData(String message);

        public void getData(Intent data);
    }

    private SendDataListenerInterface target;
    private String message;
    private Context context;
    private Intent intent;
    private Class<?> cls;

    public MyOnClick(SendDataListenerInterface target, Context context, Intent intent, Class<?> cls) {
        this.target = target;
        this.context = context;
        this.intent = intent;
        this.cls = cls;

    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        if (cls != null) {
/*String message = txtActivity1.getText().toString();
if (message.length()>0) {
        //en vez de utilizar MainActivity.this para pasarlo como contexto, se puede utilizar
        //getApplicationContext(), asi en caso de hacer un listener generico, esta parte tambien sera generica

        Intent intent = new Intent(MainActivity.this, Activity2.class);
        intent.putExtra(MESSAGE, message);
        startActivityForResult(intent, SHOW_ACTIVITY2);
        }else{
        Toast toast= Toast.makeText(MainActivity.this, getResources().getString(R.string.no_text),Toast.LENGTH_SHORT);
        toast.show();
        }*/
            target.sendData(btn.getText().toString());
        } else {
            target.getData(new Intent());
        }
    }
}
