package com.airizar.todolistfragment.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airizar.todolistfragment.R;
import com.airizar.todolistfragment.model.Todo;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {
    private Button btnAdd;
    private TextView txtView;
    private TodoItemListener target;

    //Interfaz necesaria para pasarle datos al activity
    public interface TodoItemListener {
        public void addTodo(Todo todo);
    }

    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_input, container, false);
        btnAdd = (Button) layout.findViewById(R.id.btnAdd);
        txtView = (TextView) layout.findViewById(R.id.txtView);
        addEventListeners();
        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.target = (TodoItemListener) activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement TODOItem-Listener");
        }

    }

    private void addEventListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoStr = txtView.getText().toString();
                Todo todo = new Todo(todoStr);
                target.addTodo(todo);
                txtView.setText("");
            }
        });
    }


}
