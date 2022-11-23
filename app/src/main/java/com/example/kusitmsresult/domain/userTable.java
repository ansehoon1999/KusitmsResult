package com.example.kusitmsresult.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;


/**
 * Created by manish on 12/15/2017.
 */


@Entity(primaryKeys = {"firstName", "lastName"})
public class userTable {
    @NonNull
    public String firstName;
    @NonNull
    public String lastName;
    public String gender;
    public long foot;
    public long inch;
    public long weight;
    public long age;
}
