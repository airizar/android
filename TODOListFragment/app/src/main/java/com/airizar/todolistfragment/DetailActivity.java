package com.airizar.todolistfragment;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.airizar.todolistfragment.fragment.TodoFragment;
import com.airizar.todolistfragment.model.Todo;

import java.text.SimpleDateFormat;


public class DetailActivity extends ActionBarActivity {
    private TextView lblTask;
    private TextView lblCreated;

    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent detailIntent = getIntent();
        //todo = detailIntent.getParcelableExtra(TodoFragment.TODO_ITEM);
        todo = (Todo) detailIntent.getSerializableExtra(TodoFragment.TODO_ITEM);
        getLayoutElements();
    }

    private void getLayoutElements() {
        lblCreated = (TextView) findViewById(R.id.lblCreated);
        lblTask = (TextView) findViewById(R.id.lblTask);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        lblCreated.setText(sdf.format(todo.getCreated()));
        lblTask.setText(todo.getTask().toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
