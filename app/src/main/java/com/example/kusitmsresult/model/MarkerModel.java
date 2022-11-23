package com.example.kusitmsresult.model;

import java.util.ArrayList;

public class MarkerModel {
    public double lat;
    public double lng;
    public String markerTitle;
    public String markerSnippet;
    public ArrayList<String> storename_list;
    public String storename_selected;

    public MarkerModel(double lat, double lng, String markerTitle, String markerSnippet, ArrayList<String> storename_list, String storename_result) {
        this.lat = lat;
        this.lng = lng;
        this.markerTitle = markerTitle;
        this.markerSnippet = markerSnippet;
        this.storename_list = storename_list;
        this.storename_selected = storename_result;
    }
    //    public void show_lat() {
//        Log.d("markerlistlatlng: ", Double.toString(this.lat));
//    }
    public double getLat() {
        return lat;
    }

    public void setLat(double lat){
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getMarkerTitle() {
        return markerTitle;
    }

    public void setMarkerTitle(String markerTitle) {
        this.markerTitle = markerTitle;
    }

    public String getMarkerSnippet() {
        return markerSnippet;
    }

    public void setMarkerSnippet(String markerSnippet) {
        this.markerSnippet = markerSnippet;
    }

    public void add_storename_list(String storename) { this.storename_list.add(storename); }

    public String get_storename_selected() { return storename_selected; }

    public void set_storename_selected(String storename_result) { this.storename_selected = storename_result; }

}