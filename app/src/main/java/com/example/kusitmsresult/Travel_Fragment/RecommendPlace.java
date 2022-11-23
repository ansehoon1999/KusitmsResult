package com.example.kusitmsresult.Travel_Fragment;

import java.lang.Math;
import java.util.*;

class ICF {
    // static String[] place = {"광주", "광교", "베이글", "잠실", "계곡", "방탈출", "빠지"};
    // static String[] user = {"nayeon", "joy", "brave", "girls", "rolling"};
    //int[][] array=new int[user.length][place.length];


//     static double[][] array = {
//            {0, 3, 4, 2, 5},
//            {5, 3, 1, 4, 1},
//            {4, 2, 2, 3, 2},
//            {0, 2, 4, 2, 5},
//            {5, 0, 2, 4, 3},
//            {1, 2, 3, 4, 5},
//            {2, 5, 4, 2, 1}
//    };

    static String[] place;
    static String[] user;
    static double[][] array;
    static double[][] filtered_array;

    public ICF(ArrayList<String> placeList, ArrayList<String> userList, double[][] userRateList)  {
        this.place = new String[placeList.size()];
        this.user = new String[userList.size()];
        this.array = new double[placeList.size()][userList.size()];

        for(int i = 0; i<placeList.size(); i++) {
            this.place[i] = placeList.get(i);
        }

        for(int j = 0; j<userList.size(); j++) {
            this.user[j] = userList.get(j);
        }

        this.array = userRateList;
        this.filtered_array=  new double[place.length][place.length];
        // 배열 안에 값 넣기!
    }

    // 코사인 유사도 생성 후 삽입
    public void filtering() {
        for (int i = 0; i < place.length; i++) {
            for (int j = i + 1; j < place.length; j++) {
                double square_sum1 = 0;
                double square_sum2 = 0;
                double dot_product = 0;
                double total = 0;

                for (int k = 0; k < user.length; k++) {
                    square_sum1 += Math.pow(array[i][k], 2);
                    square_sum2 += Math.pow(array[j][k], 2);
                    dot_product += array[i][k] * array[j][k];
                }
                square_sum1 = Math.sqrt(square_sum1);
                square_sum2 = Math.sqrt(square_sum2);
                total = dot_product / (square_sum1 * square_sum2);
                filtered_array[i][j] = Math.round(total * 1000) / 1000.0;
                filtered_array[j][i] = Math.round(total * 1000) / 1000.0;
            }
        }
    }

    //array 출력하는 함수
    public void print_array() {
        for (int i = 0; i < place.length; i++) {
            for (int j = 0; j < user.length; j++) {
                System.out.print(array[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    //filtered_array 출력하는 함수
    public void print_filtered_array() {
        for (int i = 0; i < place.length; i++) {
            for (int j = 0; j < place.length; j++) {
                System.out.print(filtered_array[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public int find_index_by_place(String place_name) {
        for (int i = 0; i < place.length; i++) {
            if (place_name == place[i]) {
                return i;
            }
        }
        return -1;
    }

    public int find_index_by_value(double[] arr, double val) {
        for (int i = 0; i < arr.length; i++) {
            if (val == arr[i]) {
                return i;
            }
        }
        return -1;
    }

    public void Array_Copy(double[][] arr, double[][] arr_copy) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr_copy[i][j] = arr[i][j];
            }
        }
    }

    public String[] rank_slice(String place_name, int rank) {
        //double[][] filtered_array_copy=Arrays.copyOf(filtered_array,filtered_array.length);
        double[][] filtered_array_copy = new double[filtered_array.length][filtered_array.length];

        //복사
        Array_Copy(filtered_array, filtered_array_copy);
        int idx = find_index_by_place(place_name);

        double[] cs_between_place_array = filtered_array_copy[idx];
        double[] cs_between_place_array_idxing = filtered_array[idx];

        //내림차순 정렬
        Arrays.sort(cs_between_place_array);
        int arr_len = cs_between_place_array.length;

        //상위 rank개 출력
        int[] idx_list = new int[rank];
        String[] recommend_list = new String[100];
        for (int i = arr_len - 1; i > arr_len - rank - 1; i--) {
            idx_list[i - arr_len + rank] = find_index_by_value(cs_between_place_array_idxing, cs_between_place_array[i]);
        }
        for (int i = idx_list.length - 1; i >= 0; i--) {
            // 높은 거 리스트 : idx_list[i]
            System.out.println("slice: "+place[idx_list[i]]);
            recommend_list[rank-i-1] = place[idx_list[i]];

        }

        return recommend_list;
    }
}
