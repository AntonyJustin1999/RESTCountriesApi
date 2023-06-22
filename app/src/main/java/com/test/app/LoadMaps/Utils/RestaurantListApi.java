package com.test.app.LoadMaps.Utils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantListApi {
    @SerializedName("restaurants")
    ArrayList<RestaurantDetails> restaurantList = null;

    public ArrayList<RestaurantDetails> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(ArrayList<RestaurantDetails> restaurantList) {
        this.restaurantList = restaurantList;
    }
}

