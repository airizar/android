package com.airizar.todolistfragment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airizar.todolistfragment.R;
import com.airizar.todolistfragment.model.Todo;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cursomovil on 23/03/15.
 */
public class TodoAdapter extends ArrayAdapter<Todo> {
    //codigo hexadecimal del R
    private int resource;

    public TodoAdapter(Context context, int resource, List<Todo> objects) {
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
        Todo item = getItem(position);
        TextView lblTask = (TextView) layout.findViewById(R.id.lblTask);
        TextView lblCreated = (TextView) layout.findViewById(R.id.lblCreated);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        lblTask.setText(item.getTask());
        lblCreated.setText(sdf.format(item.getCreated()));
        return layout;
    }
}
