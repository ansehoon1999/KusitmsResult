package com.example.kusitmsresult.model;

public class DistanceRadius {

    private double latest_location_x=0.0, latest_location_y=0.0;  //직전 위치
    private double current_location_x, current_location_y; //현재 위치
    public int count_dist = 1; //카운트(횟수가 몇 번 이상 카운트 되면 핀 꽂기)

    public DistanceRadius(double x, double y) {
        current_location_x=x;
        current_location_y=y;
    }
    public void setCurrent(double x, double y) {
        current_location_x=x;
        current_location_y=y;
    }
    public void setLatest(double x, double y) {
        latest_location_x=x;
        latest_location_y=y;
    }
    public void printLatest() {
        System.out.println("latest_location_x: "+latest_location_x);
        System.out.println("latest_location_y: "+latest_location_y);
    }

    // 걷는 속도 줄어드는지
    public boolean Islessthan(MarkerModel markerModel) {
        Distance dist=new Distance(markerModel.getLat(),markerModel.getLng(),current_location_x, current_location_y,"km");
        double latest_current_distance=dist.distance();

        if (latest_current_distance > 100) {
            android.util.Log.d("far current_distance: ", Double.toString(latest_current_distance));
            return false;
        }
        else {
            android.util.Log.d("closecurrent_distance: ", Double.toString(latest_current_distance));
            return true;
        }
    }

    // 기준 위치에 대한 반경 d 내에 있는지
    public boolean within_radius(double x1, double y1, double x2, double y2, double d) {
        // 반경 안에 있으면  true/ else false
        // x1, y1이 기준, x2, y2가 new
        Distance dist=new Distance(x1,y1,x2,y2,"km");
        android.util.Log.d("distancemodel:", Double.toString(dist.distance()));
        if (dist.distance()<=d) {
            return true;
        }
        else
            return false;
    }

    //Islessthan()이 true 실행
    public void check_radius(MarkerModel markerModel) {
        if ((latest_location_x==0.0) & (latest_location_y==0.0)){
            // 맨 처음에는 조건 없이 latest로 넣는다.
            latest_location_x = current_location_x;
            latest_location_y = current_location_y;
        }
        else {
            //2km보다 큰 거
            if (!Islessthan(markerModel)) {
                // 현재 위치를 이전 위치에 넣고 끝
                count_dist = 0;
                latest_location_x = current_location_x;
                latest_location_y = current_location_y;
            }            //2km보다 작은 거
            else if (within_radius(markerModel.getLat(),markerModel.getLng(),current_location_x,current_location_y, 100)) {
                // 지정한 거리 1 내에 있다면 count+=1해서 count 5 되면 핀 꽂기!
                count_dist += 1;
            }
        }
    }

}


