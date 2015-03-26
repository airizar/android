package com.airizar.terremotos.adapters;

import android.content.Context;
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
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if(convertView==null){
            //si no existe la vista, la creamos
            layout=new LinearLayout(getContext());
            LayoutInflater li;
            String inflater=Context.LAYOUT_INFLATER_SERVICE;
            li=(LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource,layout,true);
        }else{
            layout=(LinearLayout)convertView;
        }
        Terremoto item = getItem(position);
        TextView txtMag=(TextView) layout.findViewById(R.id.txtMag);
        TextView txtLugar=(TextView) layout.findViewById(R.id.txtLugar);
        TextView txtFecha=(TextView) layout.findViewById(R.id.txtFech);

        txtMag.setText(String.valueOf(item.getMagnitud()));
        txtLugar.setText(item.getLugar());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        txtFecha.setText(sdf.format(item.getTime()));


        return layout;
    }


//    TextView lblTask = (TextView) layout.findViewById(R.id.lblTask);
//    TextView lblCreated = (TextView) layout.findViewById(R.id.lblCreated);
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//
//    lblTask.setText(item.getTask());
//    lblCreated.setText(sdf.format(item.getCreated()));
//    return layout;
}
