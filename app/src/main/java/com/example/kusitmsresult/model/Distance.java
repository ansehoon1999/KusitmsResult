package com.example.kusitmsresult.model;

public class Distance {
    static double lat1;
    static double lon1;
    static double lat2;
    static double lon2;
    static String unit;

    public Distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        this.lat1=lat1;
        this.lon1=lon1;
        this.lat2=lat2;
        this.lon2=lon2;
        this.unit=unit;
    }

    public static double distance() {
        double theta=lon1-lon2;
        double dist= Math.sin(deg_to_rad(lat1))* Math.sin(deg_to_rad(lat2))+ Math.cos(deg_to_rad(lat1))* Math.cos(deg_to_rad(lat2))* Math.cos(deg_to_rad(theta));
        dist= Math.acos(dist);
        dist=rad_to_deg(dist);
        dist=dist*60*1.1515;

        if (unit=="km") {
            dist*=1.609344;
            return (dist);
        }
        else {
            return -1;
        }
    }
    public static double deg_to_rad(double deg) {
        return (deg* Math.PI/180.0);
    }
    public static double rad_to_deg(double rad) {
        return (rad*180/ Math.PI);
    }


}
