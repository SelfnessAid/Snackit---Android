package com.snackit.android;

/**
 * Created by Schmänd on 20.09.2016.
 */
public class SnackDay {

    public String id;

    public String day;

    public String meal;

    public String tageszeit;

    public SnackDay(String tageszeit,String day,String id, String meal) {

        this.tageszeit = tageszeit;

        this.id = id;

        this.day = day;

        this.meal = meal;

    }

}