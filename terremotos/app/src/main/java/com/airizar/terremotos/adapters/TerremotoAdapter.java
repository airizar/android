package com.airizar.terremotos.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airizar.terremotos.R;
import com.airizar.terremotos.model.Terremoto;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by cursomovil on 25/03/15.
 */
public class TerremotoAdapter extends ArrayAdapter<Terremoto> {
    private int resource;

    public TerremotoAdapter(Context context, int resource, List<Terremoto> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            //si no existe la vista, la creamos
            layout = new LinearLayout(getContext());
            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        } else {
            layout = (LinearLayout) convertView;
        }
        Terremoto item = getItem(position);
        TextView txtMag = (TextView) layout.findViewById(R.id.txtMag);
        TextView txtLugar = (TextView) layout.findViewById(R.id.txtLugar);
        TextView txtFecha = (TextView) layout.findViewById(R.id.txtFech);

        txtMag.setText(String.valueOf(item.getMagnitud()));
        txtLugar.setText(item.getLugar());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        txtFecha.setText(sdf.format(item.getTime()));
        txtMag.setBackgroundColor(selectColor(txtMag.getText().toString()));

        Color c=new Color();
        return layout;
    }
    private int selectColor(String mag){
        int color=android.R.color.transparent;
        double magnitud=Double.parseDouble(mag);
        if (magnitud<=0 && magnitud>1){
           color=getContext().getResources().getColor(R.color.degRtoG_1);
        }else if (magnitud<=1 && magnitud>2){
            color=getContext().getResources().getColor(R.color.degRtoG_2);
        }else if (magnitud<=2 && magnitud>3){
            color=getContext().getResources().getColor(R.color.degRtoG_3);
        }else if (magnitud<=3 && magnitud>4){
            color=getContext().getResources().getColor(R.color.degRtoG_4);
        }else if (magnitud<=4 && magnitud>5){
            color=getContext().getResources().getColor(R.color.degRtoG_5);
        }else if (magnitud<=5 && magnitud>6){
            color=getContext().getResources().getColor(R.color.degRtoG_6);
        }else if (magnitud<=6 && magnitud>7){
            color=getContext().getResources().getColor(R.color.degRtoG_7);
        }else if (magnitud<=7 && magnitud>8){
            color=getContext().getResources().getColor(R.color.degRtoG_8);
        }else if (magnitud<=8 && magnitud>9){
            color=getContext().getResources().getColor(R.color.degRtoG_9);
        }else if (magnitud<=9 && magnitud>10){
            color=getContext().getResources().getColor(R.color.degRtoG_10);
        }
        return color;
    }


        //    TextView lblTask = (TextView) layout.findViewById(R.id.lblTask);
        //    TextView lblCreated = (TextView) layout.findViewById(R.id.lblCreated);
        //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        //
        //    lblTask.setText(item.getTask());
        //    lblCreated.setText(sdf.format(item.getCreated()));
        //    return layout;
}
