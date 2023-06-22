package com.test.app.LoadMaps.Utils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantDetailsApi {
    @SerializedName("restaurant")
    RestaurantRealtedDetails RestaurantRealtedDetails = null;

    public RestaurantRealtedDetails getRestaurantRealtedDetails() {
        return RestaurantRealtedDetails;
    }

    public void setRestaurantRealtedDetails(com.test.app.LoadMaps.Utils.RestaurantRealtedDetails restaurantRealtedDetails) {
        RestaurantRealtedDetails = restaurantRealtedDetails;
    }
}



