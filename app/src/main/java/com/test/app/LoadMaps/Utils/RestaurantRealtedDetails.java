package com.test.app.LoadMaps.Utils;

import com.google.gson.annotations.SerializedName;

public class RestaurantRealtedDetails {
    @SerializedName("name")
    String name = null;

    @SerializedName("image")
    String image = null;

    @SerializedName("description")
    String description = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
