package com.example.kusitmsresult.domain;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


/**
 * Created by manish on 11/29/2017.
 */
@Entity(indices = {@Index(value = {"date","time"},unique = true)})
public class userData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String time;
    private double distance;
    private double speed;


    public int getId() {
        return id;
    }
    public void setId(int key) {this.id = key;}

    public void setDate( String dt){
        this.date = dt;
    }

    public void setDistance(double dist){
        this.distance = dist;
    }

    public void setTime(String timedata){
        this.time = timedata;
    }

    public void setSpeed(double speeddata){
        this.speed = speeddata;
    }

    public String getDate(){
        return this.date;
    }

    public double getDistance(){
        return this.distance;
    }

    public String getTime(){
        return this.time;
    }

    public double getSpeed(){
        return this.speed;
    }
}
