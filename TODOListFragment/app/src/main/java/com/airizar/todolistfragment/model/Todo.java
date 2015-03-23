package com.airizar.todolistfragment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cursomovil on 23/03/15.
 */
public class Todo implements Serializable {
   // public class Todo implements Parcelable {
    private int mData;
    private String task;
    private Date created;

    public Todo(String task) {
        this.task = task;
        this.created = new Date();
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

   /* @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //escribir en la parcela los datos del objeto
        dest.writeString(task);
        dest.writeLong(created.getTime());
    }

    public static final Parcelable.Creator<Todo> CREATOR
            = new Parcelable.Creator<Todo>() {
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    //constructor privado
    private Todo(Parcel in) {
        task = in.readString();
        created = new Date(in.readLong());
    }*/
}
