package com.test.app.LoadMaps.Utils;

import com.google.gson.annotations.SerializedName;

public class RestaurantDetails {
    @SerializedName("restaurant_id")
    String restaurant_id = null;

    @SerializedName("name")
    String name = null;

    @SerializedName("description")
    String description = null;

    @SerializedName("image")
    String image = null;

    @SerializedName("logo")
    String logo = null;

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
